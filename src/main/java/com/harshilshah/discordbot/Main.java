package com.harshilshah.discordbot;

import com.harshilshah.discordbot.config.Setup;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            JDA jda = JDABuilder.createDefault(System.getenv("BOT_TOKEN"))
                    .enableIntents(Setup.getIntents())
                    .addEventListeners(Setup.getEventListeners()).build();

            jda.awaitReady();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
