package com.sippbox.bot.listeners;

import com.sippbox.bot.listeners.events.BoogieBombEvent;
import com.sippbox.bot.listeners.events.BotPatEvent;
import com.sippbox.bot.listeners.events.URLBlacklistEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) { // If this message was sent to a Guild TextChannel
            new URLBlacklistEvent(event);
            new BotPatEvent(event);
            new BoogieBombEvent(event);

            // If the message content equals "java sucks", reply with "no u"

            if (event.getMessage().getContentRaw().equalsIgnoreCase("java sucks")) {
                event.getChannel().sendMessage("Fuck off").queue();
            }
        }
    }
}