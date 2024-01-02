package com.sippbox.utils;

import com.google.gson.Gson;
import com.sippbox.enums.Channels;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ChannelRegistry {
    private static final String CHANNEL_IDS_FILE_PATH = "channel_ids.json";
    private static Map<String, String> channelIds;

    static {
        try {
            channelIds = new Gson().fromJson(new FileReader(CHANNEL_IDS_FILE_PATH), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TextChannel getTextChannel(Guild guild, Channels channel) {
        String channelId = channelIds.get(channel.name().toLowerCase());
        if (channelId == null) {
            throw new IllegalArgumentException("Channel ID not found for " + channel.name());
        }
        return guild.getTextChannelById(channelId);
    }
}