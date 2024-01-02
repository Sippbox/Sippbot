package com.sippbox.utils;

/**
 * Created by Nyvil on 11/06/2021 at 21:54
 * in project Discord Base
 */


import com.sippbox.bot.Sippbot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public final class MessageUtils {

    private static final Color BOT_COLOR_SUCCESS = new Color(88, 170, 137);
    private static final Color BOT_COLOR_ERROR = new Color(191, 61, 39);
    private static final Color BOT_COLOR_INFO = new Color(32, 102, 148);
    private static final Color BOT_COLOR_INFO_ALT = new Color(87, 97, 133);
    private static final Color BOT_COLOR_WARNING = new Color(255, 122, 0);

    private MessageUtils() {
        throw new AssertionError("No, bad! No instances of util classes!");
    }

    /**
     * Creates a success embed with the usual format used by the bot
     *
     * @return {@link EmbedBuilder}
     * @since 1.0
     */
    public static EmbedBuilder createSuccessEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_SUCCESS);
        return embedBuilder;
    }

    /**
     * Creates a success embed with usual format used by the bot, including the message in the embed
     *
     * @return {@link EmbedBuilder}
     * @since 1.0
     */
    public static EmbedBuilder createSuccessEmbed(String message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_SUCCESS);
        embedBuilder.setFooter(Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getName(), Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }

    /**
     * Creates a error embed with the usual format used by the bot
     *
     * @return {@link EmbedBuilder}
     * @since 1.0
     */
    public static EmbedBuilder createErrorEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_ERROR);
        return embedBuilder;
    }

    /**
     * Creates a error embed with usual format used by the bot, including the message in the embed
     *
     * @return {@link EmbedBuilder}
     * @since 1.1
     */
    public static EmbedBuilder createErrorEmbed(String message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_ERROR);
        embedBuilder.setFooter(Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getName(), Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }

    /**
     * Creates a info embed with the usual format used by the bot
     *
     * @return {@link EmbedBuilder}
     * @since 1.0
     */
    public static EmbedBuilder createInfoEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_INFO);
        embedBuilder.setFooter(Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getName(), Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        return embedBuilder;
    }

    /**
     * Creates a info embed with usual format used by the bot, including the message in the embed
     *
     * @return {@link EmbedBuilder}
     * @since 1.1
     */
    public static EmbedBuilder createInfoEmbed(String message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_INFO);
        embedBuilder.setFooter(Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getName(), Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }

    /**
     * Creates an info embed with usual format used by the bot, including the message and the title in the embed
     *
     * @param message message the embed shall contain
     * @param title   title of the embed
     * @return {@link EmbedBuilder}
     */
    public static EmbedBuilder createInfoEmbedWithTitle(String message, String title) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_INFO);
        embedBuilder.setTitle(title);
        embedBuilder.setFooter(Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getName(), Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }

    /**
     * Creates a alt info embed with the usual format used by the bot
     *
     * @return Embed
     * @since 1.0
     */
    public static EmbedBuilder createAltInfoEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_INFO_ALT);
        return embedBuilder;
    }

    /**
     * Creates an alt info embed with usual format used by the bot, including the message in the embed
     *
     * @return Embed
     * @since 1.1
     */
    public static EmbedBuilder createAltInfoEmbed(String message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_INFO_ALT);
        embedBuilder.setFooter(Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getName(), Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }

    /**
     * Creates a warning info embed with the usual format used by the bot
     *
     * @return Embed
     * @since 1.0
     */
    public static EmbedBuilder createWarningEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_WARNING);
        return embedBuilder;
    }

    /**
     * Creates a warning embed with usual format used by the bot, including the message in the embed
     *
     * @return Embed
     * @since 1.1
     */
    public static EmbedBuilder createWarningEmbed(String message) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_WARNING);
        embedBuilder.setFooter(Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getName(), Sippbot.getInstance().getJdaService().getJda().get().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }

    /**
     * Creates an embed for the ban scammer command
     *
     * @return Embed
     */
    public static MessageEmbed createBanScammerConfirmationEmbed(Member member){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.RED);
        embed.setTitle("Ban Scammer");
        embed.setDescription("Are you sure you want to ban " + member.getUser().getAsMention() + " for **scamming?**");
        //add fields for the users account age
        long accountCreationTimestamp = member.getUser().getTimeCreated().toInstant().getEpochSecond();
        embed.addField("Account Age", "<t:" + accountCreationTimestamp + ":f>", true);
        // add field for when the user joined the server
        long joinedServerTimestamp = member.getTimeJoined().toInstant().getEpochSecond();
        embed.addField("Joined Server", "<t:" + joinedServerTimestamp + ":f>", false);
        embed.setThumbnail(member.getUser().getAvatarUrl());
        embed.setFooter("User ID: " + member.getUser().getId());

        return embed.build();
    }

    /**
     * Sets the message for an embed
     *
     * @param embedBuilder embed you want to set the message for
     * @param message      the message to set
     */
    public static void setEmbedMessage(EmbedBuilder embedBuilder, String message) {
        embedBuilder.setDescription(message);
    }

}