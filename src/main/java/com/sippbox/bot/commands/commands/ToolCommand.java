package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.EmbedBuilder;
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
            put("Av3Emulator", "https://github.com/lyuma/Av3Emulator");
            put("Avatars 3.0 Manager", "https://github.com/VRLabs/Avatars-3.0-Manager");
            put("Combo Gesture Expressions", "https://github.com/hai-vr/combo-gesture-expressions-av3");
            put("VRCQuestTools", "https://github.com/kurotu/VRCQuestTools");
            put("Animator as Code", "https://github.com/hai-vr/av3-animator-as-code");
            put("Toggle Assistant", "https://shatteredfur.gumroad.com/l/vrctoggle");
        }
    };

    public static Map<String, String> optimizationTools = new HashMap<>(){
        {
            put("D4rk's Avatar Optimizer", "https://github.com/d4rkc0d3r/d4rkAvatarOptimizer");
            put("Avatar Performance Tools", "https://github.com/Thryrallo/VRC-Avatar-Performance-Tools");
            put("Unity Mesh Transfer Utility", "https://github.com/CascadianVR/Unity-Mesh-Transfer-Utility");

        }
    };

    public static Map<String, String> featureTools = new HashMap<>() {
        {
            put("AudioLink", "https://github.com/llealloo/vrc-udon-audio-link");
            put("VRCLens", "https://hirabiki.gumroad.com/l/rpnel");
            put("Gogo Loco", "https://github.com/Franada/gogoloco");
            put("Poiyomi Toon Shader", "https://github.com/poiyomi/PoiyomiToonShader");
            put("lilToon", "https://github.com/lilxyzw/lilToon");
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

        if (tool.equals("all") || tool.equals("listall")) {
            //Display all tools categorized by type in an embed
            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("All Tools");

            StringBuilder productivityToolsBuilder = new StringBuilder();
            productivityTools.forEach((key, value) -> productivityToolsBuilder.append(key).append(" - ").append(value).append("\n"));
            embedBuilder.addField("Productivity", productivityToolsBuilder.toString(), false);

            StringBuilder optimizationToolsBuilder = new StringBuilder();
            optimizationTools.forEach((key, value) -> optimizationToolsBuilder.append(key).append(" - ").append(value).append("\n"));
            embedBuilder.addField("Optimization", optimizationToolsBuilder.toString(), false);

            StringBuilder featureToolsBuilder = new StringBuilder();
            featureTools.forEach((key, value) -> featureToolsBuilder.append(key).append(" - ").append(value).append("\n"));
            embedBuilder.addField("Features", featureToolsBuilder.toString(), false);

            info.event().replyEmbeds(embedBuilder.build()).queue();
            return;
        }

        if (highestScore >= 0.7) {
            info.event().reply(bestMatch).queue();
            System.out.println("Best match: " + bestMatch);
        } else {
            info.event().reply("No tool found with that name!").queue();
            System.out.println("No match found!");
        }
    }
}
