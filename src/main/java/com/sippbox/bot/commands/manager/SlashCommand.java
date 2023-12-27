package com.sippbox.bot.commands.manager;

import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Collections;
import java.util.List;

public abstract class SlashCommand {

    public abstract String name();
    public abstract String description();
    public abstract Permission[] neededPermissions();
    public abstract boolean guildOnly();
    public abstract OptionData[] options();
    public List<SubcommandData> subcommands() {
        return Collections.emptyList();
    }
    public abstract void execute(SlashCommandRecord info);
}
