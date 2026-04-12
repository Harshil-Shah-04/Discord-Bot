package com.harshilshah.discordbot;

import com.sun.net.httpserver.HttpServer;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class Main extends ListenerAdapter {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            JDA jda = JDABuilder
                    .createDefault(System.getenv("BOT_TOKEN"))
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new Main())
                    .build();

            jda.awaitReady();
            startServer();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String msg = event.getMessage().getContentRaw();

        if (msg.equalsIgnoreCase("grizzy")) {
            event.getChannel().sendMessage("Woof!").queue();
        }
    }

    public static void startServer() throws Exception {
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
    }
}
