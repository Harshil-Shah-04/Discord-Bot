package com.harshilshah.discordbot.events;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        if (event.getUser().isBot()) return;

        event.getGuild().getTextChannelsByName("general", true)
                .stream().findFirst().ifPresent(channel ->
                        channel.sendMessage("Welcome to "+event.getGuild().getName()+" "+event.getMember().getAsMention()).queue());
    }
}
