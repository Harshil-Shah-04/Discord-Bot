package com.harshilshah.discordbot.events;

import com.harshilshah.discordbot.commands.CommandHandler;
import com.harshilshah.discordbot.config.Setup;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandInteraction extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        CommandHandler handler = Setup.getCommandHandlerMap().get(event.getName());
        handler.handleSlashCommand(event);
    }
}
