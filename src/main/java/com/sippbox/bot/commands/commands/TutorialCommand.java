package com.sippbox.bot.commands.commands;

import com.sippbox.bot.JdaService;
import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

/**
 * This class represents a tutorial command for a bot.
 * It extends the SlashCommand class and overrides its methods.
 */
public class TutorialCommand extends SlashCommand {

    // Permissions required to use this command
    private static final Permission[] PERMISSIONS = {Permission.USE_APPLICATION_COMMANDS};
    // Options for this command
    private static final OptionData[] OPTIONS = {
            new OptionData(OptionType.STRING, "video", "The video you want to link", true)
                    .addChoice("Basic Setup", "bSwMz4WcajQ")
                    .addChoice("Toggles", "XqtSg6_W07Y")
                    .addChoice("Radial Toggle", "c7Jd9YWz8pI")
                    .addChoice("Facial Expressions", "UU9NFJz2qls")
                    .addChoice("VRam Optimization", "8F9K1QEPF6A")
                    .addChoice("AudioLink", "Gp1woO9RzGY")
                    .addChoice("PhysBones", "PTTnWUkswkU")
                    .addChoice("PhysBone Colliders", "pT6wO01dwTU")
                    .addChoice("Contacts", "LOZu6e8ozns")
                    .addChoice("Contact Toggles", "Cp0ysawmizM")
                    .addChoice("Finger Tracking", "2DFT0LsOaIw")
                    .addChoice("Quest Optimization", "8MkgXcidxg4")
                    .addChoice("Voice Activated Effects", "kgs5gz8BPtY")
                    .addChoice("Unity 2022 Upgrade", "M9l0SVF0fJY")
                    .addChoice("Sound Effects", "BmXCXP6UsQ8")
    };
    // Regex pattern for youtube video IDs
    private static final String YOUTUBE_REGEX = "^[a-zA-Z0-9_-]{11}$";

    /**
     * Returns the name of the command.
     * @return the name of the command.
     */
    @Override
    public String name() {
        return "tutorial";
    }

    /**
     * Returns the description of the command.
     * @return the description of the command.
     */
    @Override
    public String description() {
        return "Link a tutorial video to the chosen channel";
    }

    /**
     * Returns the permissions needed to use the command.
     * @return the permissions needed to use the command.
     */
    @Override
    public Permission[] neededPermissions() {
        return PERMISSIONS;
    }

    /**
     * Returns whether the command can only be used in a guild.
     * @return true if the command can only be used in a guild, false otherwise.
     */
    @Override
    public boolean guildOnly() {
        return false;
    }

    /**
     * Returns the options for the command.
     * @return the options for the command.
     */
    @Override
    public OptionData[] options() {
        return OPTIONS;
    }

    /**
     * Executes the command.
     * @param info the record of the command.
     */
    @Override
    public void execute(SlashCommandRecord info) {

        // Send an initial response
        info.event().deferReply().queue();

        String channel = info.event().getChannel().getId();

        String videoID = info.options().get(0).getAsString();

        //TODO: Create string with the name of the choice (tutorial name)

        // Check if the video ID matches the regex pattern
        if(!videoID.matches(YOUTUBE_REGEX)){
            info.event().getHook().editOriginal("Invalid video ID: `" + videoID + "` does not match the correct regex pattern.").queue();
            return;
        }

        // Try to send the message
        try {
            info.event().getHook().editOriginal("https://www.youtube.com/watch?v=" + videoID).queue();
        } catch (Exception e) {
            info.event().getHook().editOriginal("There was an error sending the message: ```"
                    + e.getMessage() + "``` ").queue();
            //TODO: DM the server owner and/or log the error in a log channel
        }
    }
}