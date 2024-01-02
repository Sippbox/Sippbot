package com.sippbox.bot.listeners.events;

import com.sippbox.utils.ChannelRegistry;
import com.sippbox.enums.Channels;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * This class handles URL blacklist events.
 * When a message is received, it checks if the message contains any blacklisted URLs.
 * If a blacklisted URL is found, it sends alerts to the avatar helper chat and the user who sent the message.
 */
public class URLBlacklistEvent {
    // Array of blacklisted URLs
    private static final String[] BLACKLISTED_URLS = {"vrcmods.com", "ripper.store", "vrmodels.store"};
    // ID of the avatar helper chat
    // TextChannel object representing the avatar helper chat
    private final TextChannel avatarHelperChat;

    /**
     * Constructor for the URLBlacklistEvent class.
     * @param event The MessageReceivedEvent that triggered this event.
     */
    public URLBlacklistEvent(MessageReceivedEvent event) {
        avatarHelperChat = ChannelRegistry.getTextChannel(event.getGuild(), Channels.AVATAR_HELPER_CHAT);
        String messageContent = event.getMessage().getContentRaw();

        // Check if the message contains any blacklisted URLs
        for (String blacklistedURL : BLACKLISTED_URLS) {
            if (messageContent.contains(blacklistedURL)) {
                sendAlerts(event, blacklistedURL);
                break;
            }
        }
    }

    /**
     * Sends alerts to the avatar helper chat and the user who sent the message.
     * @param event The MessageReceivedEvent that triggered this event.
     * @param flaggedURL The blacklisted URL that was found in the message.
     */
    private void sendAlerts(MessageReceivedEvent event, String flaggedURL) {
        sendAvatarHelperAlert(event, flaggedURL);
        sendUserAlert(event, flaggedURL);
        event.getMessage().delete().queue();
    }

    /**
     * Sends an alert to the avatar helper chat.
     * @param event The MessageReceivedEvent that triggered this event.
     * @param flaggedURL The blacklisted URL that was found in the message.
     */
    private void sendAvatarHelperAlert(MessageReceivedEvent event, String flaggedURL) {
        EmbedBuilder avatarHelperAlert = new EmbedBuilder()
                .setTitle("URL Blacklist Detection")
                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                .setDescription("**" + event.getMember().getUser().getAsTag() + " tried to post a blacklisted link in " + event.getChannel().getAsMention() + "**")
                .addField("Flagged Link:", flaggedURL, false)
                .setColor(Color.RED);

        avatarHelperChat.sendMessageEmbeds(avatarHelperAlert.build()).queue();
    }

    /**
     * Sends an alert to the user who sent the message.
     * @param event The MessageReceivedEvent that triggered this event.
     * @param flaggedURL The blacklisted URL that was found in the message.
     */
    private void sendUserAlert(MessageReceivedEvent event, String flaggedURL) {
        EmbedBuilder alert = new EmbedBuilder()
                .setTitle("**That link is not allowed!**")
                .setDescription("Flagged Link: " + flaggedURL)
                .setThumbnail("https://nrkno.github.io/yr-warning-icons/png/icon-warning-generic-red.png")
                .addField("Reason", "This site is known to host ripped content.", false)
                .setColor(Color.RED);

        // Mention the user and send the embed message, then delete both messages after 5 seconds
        event.getChannel().sendMessage(event.getMember().getAsMention()).queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
        event.getChannel().sendMessageEmbeds(alert.build()).queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));
    }
}