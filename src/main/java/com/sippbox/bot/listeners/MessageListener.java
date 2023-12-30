package com.sippbox.bot.listeners;

import com.sippbox.bot.listeners.events.BoogieBombEvent;
import com.sippbox.bot.listeners.events.BotPatEvent;
import com.sippbox.bot.listeners.events.URLBlacklistEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * This class extends ListenerAdapter from JDA (Java Discord API) and overrides the onMessageReceived method.
 * It listens for any message received in a Guild TextChannel and triggers specific events based on the message content.
 */
public class MessageListener extends ListenerAdapter {

    /**
     * This method is triggered whenever a message is received in a Guild TextChannel.
     * It creates new instances of URLBlacklistEvent, BotPatEvent, and BoogieBombEvent with the received message event.
     * @param event The event of a message being received.
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) { // If this message was sent to a Guild TextChannel
            new URLBlacklistEvent(event);
            new BotPatEvent(event);
            new BoogieBombEvent(event);
        }
    }
}