package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.HashMap;
import java.util.Map;

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
                        .addChoice("Contacts", "contacts"),
                new OptionData(OptionType.STRING, "hash-link", "Link to a specific section of the documentation, e.g., 'maximum-bounds' for 'Physbones'. ", false)
        };
    }

    @Override
    public void execute(SlashCommandRecord info) {
        // Acknowledge the command
        info.event().deferReply().queue();

        String documentation = info.options().get(0).getAsString();

        // Map documentation to URL
        Map<String, String> docToUrlMap = new HashMap<>();
        docToUrlMap.put("animatorlayers", "https://creators.vrchat.com/avatars/playable-layers");
        docToUrlMap.put("animatorparameters", "https://creators.vrchat.com/avatars/animator-parameters");
        docToUrlMap.put("statebehaviors", "https://creators.vrchat.com/avatars/state-behaviors");
        docToUrlMap.put("perfranks", "https://creators.vrchat.com/avatars/avatar-performance-ranking-system");
        docToUrlMap.put("optimization", "https://creators.vrchat.com/avatars/avatar-optimizing-tips");
        docToUrlMap.put("physbones", "https://creators.vrchat.com/avatars/avatar-dynamics/physbones");
        docToUrlMap.put("contacts", "https://creators.vrchat.com/avatars/avatar-dynamics/contacts");

        // Get the URL
        String url = docToUrlMap.getOrDefault(documentation, "Invalid documentation!");

        // Add hash link if provided
        if (info.options().size() > 1) {
            String hashLink = info.options().get(1).getAsString();

            //check if the hash link provided contains #, if not, add it
            if (!hashLink.startsWith("#")) {
                hashLink = "#" + hashLink;
            }
            url += hashLink;
        }

        info.event().getHook().editOriginal(url).queue();
    }
}
