package com.harshilshah.discordbot.events;

import com.harshilshah.discordbot.Setup;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoin extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        Setup.registerCommands(event.getGuild());
    }
}
