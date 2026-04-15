package com.harshilshah.discordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandHandler {
    void handlePrefixCommand(MessageReceivedEvent event);
    void handleSlashCommand(SlashCommandInteractionEvent event);
}
