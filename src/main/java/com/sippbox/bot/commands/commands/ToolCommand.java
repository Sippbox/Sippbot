package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ToolCommand extends SlashCommand {
    public static Map<String, String> productivityTools = new HashMap<>(){
        {
            put("Pumkin's Avatar Tools", "https://github.com/rurre/PumkinsAvatarTools");
            put("Editor Screenshot", "https://github.com/rurre/Editor-Screenshot");
            put("RATS", "https://github.com/rrazgriz/RATS");
            put("VRCFury", "https://vrcfury.com/");
            put("Gesture Manager", "https://github.com/BlackStartx/VRC-Gesture-Manager");
            put("Combo Gesture Expressions", "https://github.com/hai-vr/combo-gesture-expressions-av3");
        }
    };

    public static Map<String, String> optimizationTools = new HashMap<>(){
        {
            put("D4rk's Avatar Optimizer", "https://github.com/d4rkc0d3r/d4rkAvatarOptimizer");
            put("Avatar Performance Tools", "https://github.com/Thryrallo/VRC-Avatar-Performance-Tools");
        }
    };

    public static Map<String, String> featureTools = new HashMap<>() {
        {
            put("AudioLink", "https://github.com/llealloo/vrc-udon-audio-link");
            put("VRCLens", "https://hirabiki.gumroad.com/l/rpnel");
            put("Gogo Loco", "https://github.com/Franada/gogoloco");
            put("Poiyomi Toon Shader", "https://github.com/poiyomi/PoiyomiToonShader");
        }
    };


    @Override
    public String name() {
        return "tools";
    }

    @Override
    public String description() {
        return "Links to useful tools for VRChat Avatars.";
    }

    @Override
    public Permission[] neededPermissions() {
        return new Permission[]{Permission.USE_APPLICATION_COMMANDS};
    }

    @Override
    public boolean guildOnly() {
        return true;
    }

    @Override
    public OptionData[] options() {
        return new OptionData[]{
                new OptionData(OptionType.STRING, "tool", "The name of the tool to link", true),
        };
    }

    @Override
    public void execute(SlashCommandRecord info) {
        String tool = info.event().getOption("tool").getAsString();
        //remove whitespace
        tool = tool.replaceAll("\\s+","");
        //remove apostrophes
        tool = tool.replaceAll("'", "");
        //convert to lowercase
        tool = tool.toLowerCase();

        Map<String, String> allTools = new HashMap<>();
        allTools.putAll(productivityTools);
        allTools.putAll(optimizationTools);
        allTools.putAll(featureTools);

        String bestMatch = "";
        double highestScore = 0;

        System.out.println("--------------------");

        for (Map.Entry<String, String> entry : allTools.entrySet()) {
            double score = StringUtils.getJaroWinklerDistance(tool, entry.getKey().toLowerCase());
            System.out.println(entry.getKey() + " " + score);
            if (score > highestScore) {
                highestScore = score;
                bestMatch = entry.getValue();
            }
        }

        System.out.println("--------------------");

        if (highestScore >= 0.7) {
            info.event().reply(bestMatch).queue();
            System.out.println("Best match: " + bestMatch);
        } else {
            info.event().reply("No tool found with that name!").queue();
            System.out.println("No match found!");
        }
    }
}
