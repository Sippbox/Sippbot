package com.sippbox.bot.commands.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;

public class MessageUserCommand extends SlashCommand {
    @Override
    public String name() {
        return "messageuser";
    }

    @Override
    public String description() {
        return "Sends a message to a user.";
    }

    @Override
    public Permission[] neededPermissions() {
        return new Permission[]{Permission.USE_APPLICATION_COMMANDS, Permission.ADMINISTRATOR};
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public OptionData[] options() {
        return new OptionData[]{
                new OptionData(OptionType.USER, "targetid", "The ID of the target user", true),
                new OptionData(OptionType.STRING, "message", "The message to send", true),
        };
    }

    @Override
    public void execute(SlashCommandRecord info) {
        //Get the target ID from the options
        String targetId = info.options().get(0).getAsString();
        //Get the message from the options
        String message = info.options().get(1).getAsString();

        //Get the user from the ID and send the message
        try {
            info.event().getGuild().retrieveMemberById(targetId).queue(member -> {
                member.getUser().openPrivateChannel().queue(channel -> {
                    channel.sendMessage(message).queue();
                });

                info.event().reply("Message sent to user!").setEphemeral(true).queue();
            });
        } catch (Exception e) {
            info.event().reply("User not found!").queue();
        }
    }
}