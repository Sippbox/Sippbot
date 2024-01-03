package com.sippbox.bot.listeners;

import com.sippbox.enums.SABChannels;
import com.sippbox.enums.SABRoles;
import com.sippbox.utils.ChannelRegistry;
import com.sippbox.utils.RoleRegistry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class JoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        sendWelcomeMessage(event);
        assignMemberRole(event);
    }

    private void sendWelcomeMessage(GuildMemberJoinEvent event) {
        var channel = ChannelRegistry.getTextChannel(event.getGuild(), SABChannels.SYSTEM);
        var embed = new EmbedBuilder()
                .setTitle("New Member")
                .setDescription(event.getUser().getAsMention() + "(" + event.getUser().getId() + ")")
                .setThumbnail(event.getUser().getAvatarUrl())
                .addField("Account Created", "<t:" + event.getMember().getTimeCreated().toInstant().getEpochSecond() + ":f>", true)
                .setFooter("Joined at " + event.getMember().getTimeJoined().toInstant().getEpochSecond())
                .setColor(Color.BLACK)
                .build();
        channel.sendMessageEmbeds(embed).queue();
    }

    private void assignMemberRole(GuildMemberJoinEvent event) {
        Role memberRole = RoleRegistry.getRole(event.getGuild(), SABRoles.MEMBER);
        event.getGuild().addRoleToMember(event.getMember(), memberRole).queue();
    }
}