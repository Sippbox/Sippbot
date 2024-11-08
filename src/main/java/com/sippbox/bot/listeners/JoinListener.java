package com.sippbox.bot.listeners;

import com.sippbox.enums.SABChannels;
import com.sippbox.enums.SABRoles;
import com.sippbox.utils.ChannelRegistry;
import com.sippbox.utils.RoleRegistry;
import com.sippbox.utils.SLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class JoinListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        sendSystemMessage(event);
        assignMemberRole(event);
    }

    private void sendSystemMessage(GuildMemberJoinEvent event) {
        var channel = ChannelRegistry.getTextChannel(event.getGuild(), SABChannels.SYSTEM);
        var embed = new EmbedBuilder()
                .setTitle("New Member: " + event.getUser().getName())
                .setDescription(event.getUser().getAsMention() + " (" + event.getUser().getId() + ")")
                .setThumbnail(event.getUser().getEffectiveAvatarUrl())
                .addField("Account Created", "<t:" + event.getMember().getTimeCreated().toInstant().getEpochSecond() + ":f>", false)
                .addField("Joined Server", "<t:" + event.getMember().getTimeJoined().toInstant().getEpochSecond() + ":f>", false)
                .setColor(Color.BLACK)
                .build();
        channel.sendMessage(event.getUser().getAsMention() + " has joined the server!").addEmbeds(embed).queue();
    }

    private void assignMemberRole(GuildMemberJoinEvent event) {
        Role memberRole = event.getGuild().getRoleById(SABRoles.MEMBER.getId());
        Member member = event.getMember();


        if (memberRole == null) {
            SLog.logRoleApplicationResult(false, member);
            return;
        }

        event.getGuild().addRoleToMember(member, memberRole).queue();

        SLog.logRoleApplicationResult(true, member);
    }
}