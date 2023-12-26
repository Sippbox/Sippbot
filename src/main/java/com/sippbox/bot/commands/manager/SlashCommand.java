package com.sippbox.bot.commands.manager;

import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class SlashCommand {

    public abstract String name();
    public abstract String description();
    public abstract Permission[] neededPermissions();
    public abstract boolean guildOnly();
    public abstract OptionData[] options();
    public abstract void execute(SlashCommandRecord info);
}
