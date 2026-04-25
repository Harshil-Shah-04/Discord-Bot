package com.harshilshah.discordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Fact implements CommandHandler {

    private static final Random RANDOM = new Random();

    private static final String[] FACTS = {
            "Swishy is the best \uD83D\uDC36",
            "Flash is stoopid <:cat_despair:1354200379054755872>",
            "Samosa is tasty \uD83D\uDE0B",
            "Yashi is the server mommy",
            "SH is ||lurking <:pokachu:1354217186079805593>||",
            "DA5H is cool",
            "Prince is awesome",
            "Valerian is emo <:val:1369931453554495518>",
            "Orca is a liar",
    };

    @Override
    public void handlePrefixCommand(MessageReceivedEvent event) {
        event.getChannel().sendMessage(FACTS[RANDOM.nextInt(FACTS.length)]).queue();
    }

    @Override
    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        event.reply(FACTS[RANDOM.nextInt(FACTS.length)]).queue();
    }
}
