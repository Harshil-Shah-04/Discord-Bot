package com.harshilshah.discordbot;

import com.harshilshah.discordbot.commands.*;
import com.harshilshah.discordbot.events.*;
import com.sun.net.httpserver.HttpServer;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

public class Setup {

    private static final Logger logger = LoggerFactory.getLogger(Setup.class);
    private static final HashMap<String, CommandHandler> commandHandlerMap = new HashMap<>();

    public static void startServer() {
        try {
            int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", exchange -> {
                String method = exchange.getRequestMethod();

                if (method.equalsIgnoreCase("HEAD")) {
                    exchange.sendResponseHeaders(200, -1);
                } else {
                    String response = "Bot is running!";
                    exchange.sendResponseHeaders(200, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                }
                exchange.close();
            });
            server.start();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void registerCommands(Guild guild) {
        guild.updateCommands().addCommands(
                        Commands.slash("fetch", "Grizzy fetches a random object"),

                        Commands.slash("fact", "Grizzy replies with a fact"),

                        Commands.slash("impersonate", "Send a message as another user")
                                .addOption(OptionType.USER, "user", "User to impersonate", true)
                                .addOption(OptionType.STRING, "message", "Message to send", true)

                        // Commands.slash("help", "Show help")
                )
                .queue();
    }

    public static void mapCommands() {
        commandHandlerMap.put("fact", new Fact());
        commandHandlerMap.put("fetch", new Fetch());
        commandHandlerMap.put("impersonate", new Impersonate());
    }

    public static HashMap<String, CommandHandler> getCommandHandlerMap() {
        return commandHandlerMap;
    }

    public static EnumSet<GatewayIntent> getIntents() {
        return EnumSet.of(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.DIRECT_MESSAGE_TYPING,
                GatewayIntent.GUILD_EXPRESSIONS
        );
    }

    public static Object[] getEventListeners() {
        return List.of(
                new GuildJoin(),
                new GuildMemberJoin(),
                new MessageReceived(),
                new Ready(),
                new SlashCommandInteraction()
        ).toArray();
    }
}