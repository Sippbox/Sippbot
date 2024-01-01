package com.sippbox.bot.listeners;

import com.sippbox.ChannelRegistry;
import com.sippbox.enums.Channels;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        TextChannel systemChannel = guild.getSystemChannel();

        if (systemChannel != null) {
            Member member = event.getMember();
            String welcomeMessage = "Welcome " + member.getAsMention() + " to the server! We're glad to have you here.";

            systemChannel.sendMessage(welcomeMessage).queue();
        }
    }
}
