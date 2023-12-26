package com.sippbox.bot.listeners;

import com.sippbox.bot.listeners.events.BoogieBombEvent;
import com.sippbox.bot.listeners.events.BotPatEvent;
import com.sippbox.bot.listeners.events.URLBlacklistEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        new URLBlacklistEvent(event);
        new BotPatEvent(event);
        new BoogieBombEvent(event);
    }
}
