package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;

public class RigCommand extends SlashCommand {
    @Override
    public String name() {
        return "rig";
    }

    @Override
    public String description() {
        return "Returns the recommended rig setup for avatars.";
    }

    @Override
    public Permission[] neededPermissions() {
        return new Permission[]{Permission.USE_APPLICATION_COMMANDS};
    }

    @Override
    public boolean guildOnly() {
        return false;
    }

    @Override
    public OptionData[] options() {
        return new OptionData[]{};
    }

    @Override
    public void execute(SlashCommandRecord info) {
        FileUpload file = FileUpload.fromData(new File("src/main/resources/rig.png"), "rig.png");
        info.event().replyFiles(file).queue();
    }
}
