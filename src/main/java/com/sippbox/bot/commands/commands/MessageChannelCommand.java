package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.nio.channels.Channel;

public class MessageChannelCommand extends SlashCommand {
    @Override
    public String name() {
        return "messagechannel";
    }

    @Override
    public String description() {
        return "Sends a message to a channel.";
    }

    @Override
    public Permission[] neededPermissions() {
        return new Permission[]{Permission.USE_APPLICATION_COMMANDS, Permission.ADMINISTRATOR};
    }

    @Override
    public boolean guildOnly() {
        return true;
    }

    @Override
    public OptionData[] options() {
        return new OptionData[]{
                new OptionData(OptionType.CHANNEL, "targetid", "The ID of the target channel", true),
                new OptionData(OptionType.STRING, "message", "The message to send", true),
        };
    }

    @Override
    public void execute(SlashCommandRecord info) {
        //Get the message from the options
        String message = info.options().get(1).getAsString();

        //Get the channel from the options
        TextChannel channel = info.options().get(0).getAsChannel().asTextChannel();

        //Get the target channel from the options and send the message
        try {
            channel.sendMessage("**[SERVER]** " + message).queue();
            info.event().reply("Message sent to channel!").setEphemeral(true).queue();
        } catch (Exception e) {
            info.event().reply("Channel not found!").queue();
        }
    }
}
