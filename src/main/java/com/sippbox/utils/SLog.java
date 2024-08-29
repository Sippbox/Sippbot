package com.sippbox.utils;

import com.sippbox.bot.JdaService;
import com.sippbox.bot.Sippbot;
import com.sippbox.enums.SABChannels;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;

/**
 * Custom class for logging
 */
public class SLog extends ListenerAdapter {

    private static final TextChannel MOD_LOG_CHANNEL;

    static {
        MOD_LOG_CHANNEL = ChannelRegistry.getTextChannel(
                Sippbot.getInstance().getGuild(),
                SABChannels.MOD_LOG
        );
    }

    public static void logRoleApplicationResult(boolean success, Member member) {
        String message = success ? "Successfully applied Member role to " + member.getUser().getAsMention()
                : "Failed to apply Member role to " + member.getUser().getAsMention() + ". The ID may be incorrect.";

        MessageEmbed embed = new EmbedBuilder()
                .setDescription(message)
                .setColor(success ? Color.GREEN : Color.RED)
                .build();

        logToChannel(embed);
        logToConsole(message);
    }

    private static void logToConsole(String message) {
        System.out.println("[Sippbot] " + message);
    }

    private static void logToChannel(MessageEmbed embed) {
        MOD_LOG_CHANNEL.sendMessageEmbeds(embed).queue();
    }
}