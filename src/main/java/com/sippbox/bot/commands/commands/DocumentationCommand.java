package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class DocumentationCommand extends SlashCommand {
    @Override
    public String name() {
        return "documentation";
    }

    @Override
    public String description() {
        return "Link VRChat's documentation to the current channel.";
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
        return new OptionData[]{
                new OptionData(OptionType.STRING, "documentation", "The documentation you want to link.", true)
                        .addChoice("Animator Layers", "animatorlayers")
                        .addChoice("Animator Parameters", "animatorparameters")
                        .addChoice("State Behaviors", "statebehaviors")
                        .addChoice("Performance Ranking", "perfranks")
                        .addChoice("Avatar Optimization", "optimization")
                        .addChoice("PhysBones", "physbones")
                        .addChoice("Contacts", "contacts")
        };
    }

    @Override
    public void execute(SlashCommandRecord info) {
        // Acknowledge the command
        info.event().deferReply().queue();

        String documentation = info.options().get(0).getAsString();

        switch (documentation) {
            case "animatorlayers":
                info.event().getHook().editOriginal("https://creators.vrchat.com/avatars/playable-layers").queue();
                break;
            case "animatorparameters":
                info.event().getHook().editOriginal("https://creators.vrchat.com/avatars/animator-parameters").queue();
                break;
            case "statebehaviors":
                info.event().getHook().editOriginal("https://creators.vrchat.com/avatars/state-behaviors").queue();
                break;
            case "perfranks":
                info.event().getHook().editOriginal("https://creators.vrchat.com/avatars/avatar-performance-ranking-system").queue();
                break;
            case "optimization":
                info.event().getHook().editOriginal("https://creators.vrchat.com/avatars/avatar-optimizing-tips").queue();
                break;
            case "physbones":
                info.event().getHook().editOriginal("https://creators.vrchat.com/avatars/avatar-dynamics/physbones").queue();
                break;
            case "contacts":
                info.event().getHook().editOriginal("https://creators.vrchat.com/avatars/avatar-dynamics/contacts").queue();
                break;
            default:
                info.event().getHook().editOriginal("Invalid documentation!").queue();
                break;
        }
    }
}
