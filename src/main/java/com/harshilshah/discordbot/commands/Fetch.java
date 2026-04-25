package com.harshilshah.discordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Fetch implements CommandHandler {

    private static final Random RANDOM = new Random();

    private static final String[] OBJECTS = {
            "stick  \uD83E\uDEB5",
            "ball  \uD83E\uDD4E",
            "sock  \uD83E\uDDE6",
            "rock  \uD83E\uDEA8",
            "bone  \uD83E\uDDB4"
    };

    @Override
    public void handlePrefixCommand(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Fetched a " + OBJECTS[RANDOM.nextInt(OBJECTS.length)]).queue();
    }

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        event.reply("Fetched a " + OBJECTS[RANDOM.nextInt(OBJECTS.length)]).queue();
    }
}
