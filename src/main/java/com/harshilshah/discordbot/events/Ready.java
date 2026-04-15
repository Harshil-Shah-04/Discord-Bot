package com.harshilshah.discordbot.events;

import com.harshilshah.discordbot.config.Setup;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        Setup.startServer();
        Setup.mapCommands();

        for (Guild guild : event.getJDA().getGuilds()) {
            Setup.registerCommands(guild);
        }
    }
}
