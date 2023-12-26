package com.sippbox.bot.listeners.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BoogieBombEvent {
    private static final String[] GIFS = {
            "https://tenor.com/view/money-boogie-love-languages-gif-25896590",
            "https://tenor.com/view/dj-khaled-another-one-gif-24892740",
            "https://tenor.com/view/fortnite-boogie-bomb-kryxcore-kry-kryxcxre-gif-26405390",
            "https://tenor.com/view/boogie-dance-moves-dancing-shake-gif-17796617",
            "I don't play fortnite."
    };

    private static final Random RANDOM = new Random();
    private static final Map<String, Long> COOLDOWNS = new HashMap<>();

    public BoogieBombEvent(MessageReceivedEvent event) {
        String messageContent = event.getMessage().getContentRaw().toLowerCase();

        if (messageContent.startsWith("boogie bomb") && event.getChannel().getName().equalsIgnoreCase("general-chat")) {
            if (isUserOnCooldown(event)) {
                sendCooldownMessage(event);
                return;
            }

            sendRandomGif(event);
            updateCooldown(event);
        }
    }

    private boolean isUserOnCooldown(MessageReceivedEvent event) {
        if (COOLDOWNS.containsKey(event.getAuthor().getId())) {
            long cooldownEndTime = COOLDOWNS.get(event.getAuthor().getId()) + TimeUnit.SECONDS.toMillis(30);
            return (cooldownEndTime - System.currentTimeMillis()) > 0;
        }
        return false;
    }

    private void sendCooldownMessage(MessageReceivedEvent event) {
        long cooldownEndTime = COOLDOWNS.get(event.getAuthor().getId()) + TimeUnit.SECONDS.toMillis(30);
        long secondsLeft = (cooldownEndTime - System.currentTimeMillis()) / 1000;
        event.getChannel().sendMessage(event.getAuthor().getAsMention() + ", you must wait " + secondsLeft + " seconds before deploying another boogie bomb.").queue(msg -> {
            msg.delete().queueAfter(3, TimeUnit.SECONDS);
        });
    }

    private void sendRandomGif(MessageReceivedEvent event) {
        String randomGif = GIFS[RANDOM.nextInt(GIFS.length)];
        event.getChannel().sendMessage(randomGif).queue();
    }

    private void updateCooldown(MessageReceivedEvent event) {
        COOLDOWNS.put(event.getAuthor().getId(), System.currentTimeMillis());
    }
}