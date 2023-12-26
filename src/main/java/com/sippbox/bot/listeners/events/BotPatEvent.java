package com.sippbox.bot.listeners.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class BotPatEvent {
    private Random r = new Random();

    public BotPatEvent(MessageReceivedEvent event) {
        if (isPatPrompt(event)) {
            event.getChannel().sendMessage(getPatReaction(event)).queue();
        }
    }

    private boolean isPatPrompt(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase();
        boolean isBotMentioned = event.getMessage().getMentions().isMentioned(event.getJDA().getSelfUser());
        return isBotMentioned && (message.contains("*pats*") || message.contains("*pets*") || message.contains("*headpats*"));
    }

    private String getPatReaction(MessageReceivedEvent event) {
        String[] patReactions = {

                "https://github.com/Sippbox/Sippbot/blob/main/src/main/resources/bean.png"
        };
        return patReactions[r.nextInt(patReactions.length)];
    }
}