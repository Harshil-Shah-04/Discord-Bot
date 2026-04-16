package com.harshilshah.discordbot.commands;

import com.harshilshah.discordbot.WebhookService;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Impersonate implements CommandHandler {
    @Override
    public void handlePrefixCommand(MessageReceivedEvent event) {
        return;
    }

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {

        Member target = event.getOption("user").getAsMember();
        String message = event.getOption("message").getAsString();

        if (!event.isFromGuild() || !(event.getChannel() instanceof TextChannel)) {
            event.reply("Use this command in a server text channel.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        TextChannel channel = event.getGuildChannel().asTextChannel();

        event.deferReply(true).queue();

        long channelId = channel.getIdLong();
        long guildId = event.getGuild().getIdLong();

        String webhookUrl = WebhookService.getWebhook(channelId);

        if (webhookUrl == null) {

            if (!event.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_WEBHOOKS)) {
                event.reply("I need MANAGE_WEBHOOKS permission!").setEphemeral(true).queue();
                return;
            }

            channel.createWebhook("Impersonator").queue(webhook -> {

                String url = webhook.getUrl();

                WebhookService.saveWebhook(
                        guildId,
                        channelId,
                        url,
                        event.getUser().getIdLong()
                );

                try {
                    sendWebhookMessage(url, target, message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                event.getHook().sendMessage("Webhook created and message sent!").queue();

            }, error -> {
                event.getHook().sendMessage("Failed to create webhook. Check permissions.").queue();
            });

        } else {
            try {
                sendWebhookMessage(webhookUrl, target, message);
                event.getHook().sendMessage("Message sent!").queue();

            } catch (Exception e) {

                WebhookService.removeFromCache(channelId);

                channel.createWebhook("Impersonator").queue(webhook -> {

                    String newUrl = webhook.getUrl();
                    WebhookService.saveWebhook(guildId, channelId, newUrl, event.getUser().getIdLong());

                    try {
                        sendWebhookMessage(newUrl, target, message);
                        event.getHook().sendMessage("Webhook recreated and message sent!").queue();
                    } catch (Exception ignored) {
                        event.getHook().sendMessage("Webhook recreated but message failed.").queue();
                    }
                });
            }
        }
    }

    private void sendWebhookMessage(String webhookUrl, Member target, String message) throws Exception {
        try (WebhookClient client = WebhookClient.withUrl(webhookUrl)) {

            client.send(new WebhookMessageBuilder()
                    .setUsername(target.getEffectiveName())
                    .setAvatarUrl(target.getEffectiveAvatarUrl())
                    .setContent(message)
                    .build()).join();
        }
    }

}
