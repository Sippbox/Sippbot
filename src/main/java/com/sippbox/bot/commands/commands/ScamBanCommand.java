package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import com.sippbox.bot.listeners.ConfirmScamBanListener;
import com.sippbox.utils.MessageUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * This class represents a command for banning a user for scamming.
 * It extends the SlashCommand class and overrides its methods to provide the functionality for the ban command.
 */
public class ScamBanCommand extends SlashCommand {

    /**
     * Returns the name of the command.
     * @return the name of the command.
     */
    @Override
    public String name() {
        return "scam";
    }

    /**
     * Returns the description of the command.
     * @return the description of the command.
     */
    @Override
    public String description() {
        return "Bans a user for scamming, confirms with a button, sends appeal info to banned user.";
    }

    /**
     * Returns the permissions needed to execute the command.
     * @return an array of Permission objects representing the needed permissions.
     */
    @Override
    public Permission[] neededPermissions() {
        return new Permission[]{Permission.USE_APPLICATION_COMMANDS, Permission.BAN_MEMBERS};
    }

    /**
     * Returns whether the command can only be executed in a guild.
     * @return false, indicating that the command can be executed in any context.
     */
    @Override
    public boolean guildOnly() {
        return false;
    }

    /**
     * Returns the options for the command.
     * @return an array of OptionData objects representing the command options.
     */
    @Override
    public OptionData[] options() {
        return new OptionData[]{
                //Option data for the user ID of the user to ban
                new OptionData(OptionType.STRING, "userid", "The ID of the user to ban", true),
        };
    }

    /**
     * Executes the command.
     * This method is called when the command is executed. It retrieves the user ID from the command options,
     * retrieves the member associated with the user ID, creates a message embed and a "Ban user" button,
     * sends the embed and the button as a reply to the command, and adds a listener to handle the button click event.
     * @param info a SlashCommandRecord object containing information about the command execution.
     */
    @Override
    public void execute(SlashCommandRecord info) {
        String userId = info.options().get(0).getAsString();

        info.event().getGuild().retrieveMemberById(userId).queue(member -> {
            MessageEmbed embed = MessageUtils.createBanScammerConfirmationEmbed(member);
            Button banButton = Button.danger("ban_user", "Ban user").withEmoji(Emoji.fromUnicode("⚠"));
            Button confirmedBanButton = Button.success("ban_success", "Banned").asDisabled().withEmoji(Emoji.fromUnicode("✅"));
            info.event().replyEmbeds(embed).addActionRow(banButton).queue();

            info.event().getJDA().addEventListener(new ConfirmScamBanListener(member));


        }, failure -> info.event().reply("Failed to find user!").setEphemeral(true).queue());
    }
}