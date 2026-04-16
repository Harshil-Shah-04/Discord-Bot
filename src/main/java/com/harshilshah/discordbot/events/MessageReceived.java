package com.harshilshah.discordbot.events;

import com.harshilshah.discordbot.commands.CommandHandler;
import com.harshilshah.discordbot.Setup;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();
        String botName = event.getJDA().getSelfUser().getName().toLowerCase();

        if (event.getMessage().getMentions().isMentioned(event.getJDA().getSelfUser())) {
            event.getChannel().sendMessage("Woof!").queue();
            return;
        }

        if (message.toLowerCase().startsWith(botName)) {

            String[] args = message.split("\\s+");

            if (args.length == 1) {
                event.getChannel().sendMessage("Woof!").queue();
                return;
            }

            String commandName = args[1].toLowerCase();

            CommandHandler handler = Setup.getCommandHandlerMap().get(commandName);

            if (handler != null) {
                handler.handlePrefixCommand(event);
            } else {
                event.getChannel().sendMessage("Woof!").queue();
            }
            return;
        }

        if (message.toLowerCase().contains(botName)) {
            event.getChannel().sendMessage("Woof!").queue();
        }
    }
}
