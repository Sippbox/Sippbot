package com.sippbox.bot.commands.commands;

import com.sippbox.enums.ToolCategory;
import com.sippbox.beans.Tool;
import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a command that provides links to useful tools for VRChat Avatars.
 * It extends the SlashCommand class and overrides its methods to implement the functionality of the command.
 */
public class ToolCommand extends SlashCommand {
    /**
     * A list of tools that the command can provide links to.
     */
    public static List<Tool> tools = new ArrayList<>(){
        {
            add(new Tool("Pumkin's Avatar Tools", "An editor script that adds tools to help you setup avatars faster and easier.",
                    "https://github.com/rurre/PumkinsAvatarTools", ToolCategory.PRODUCTIVITY));
            add(new Tool("Editor Screenshot", "A unity editor script for taking screenshots in the editor",
                    "https://github.com/rurre/Editor-Screenshot", ToolCategory.PRODUCTIVITY));
            add(new Tool("RATS", "Modifies the Unity animator to fix bugs, add customization options, and streamline the animator workflow",
                    "https://github.com/rrazgriz/RATS", ToolCategory.PRODUCTIVITY));
            add(new Tool("VRCFury", "Non-Destructive Tools for VRChat Avatars",
                    "https://vrcfury.com/", ToolCategory.PRODUCTIVITY));
            add(new Tool("Gesture Manager", "Allows previewing and editing of avatar expressions directly in Unity.",
                    "https://github.com/BlackStartx/VRC-Gesture-Manager", ToolCategory.PRODUCTIVITY));
            add(new Tool("Av3Emulator", "An emulator for Avatars 3.0",
                    "https://github.com/lyuma/Av3Emulator", ToolCategory.PRODUCTIVITY));
            add(new Tool("Avatars 3.0 Manager", "Avatars 3.0 Manager",
                    "A tool for managing playable layers and parameters for Avatars 3.0.", ToolCategory.PRODUCTIVITY));
            add(new Tool("Combo Gesture Expressions", "Unity Editor tool that lets you attach face expressions to hand gestures, and make it react to other Avatars 3.0's features, including Contacts, PhysBones and OSC.",
                    "https://github.com/hai-vr/combo-gesture-expressions-av3", ToolCategory.PRODUCTIVITY));
            add(new Tool("VRCQuestTools", "VRCQuestTools",
                    "Avatar Converter and Utilities for Quest", ToolCategory.PRODUCTIVITY));
            add(new Tool("Animator as Code", "Animator as Code",
                    "Write code to create animations in bulk.", ToolCategory.PRODUCTIVITY));
            add(new Tool("Toggle Assistant", "Make toggles extremely quickly by just selecting a game object",
                    "https://shatteredfur.gumroad.com/l/vrctoggle", ToolCategory.PRODUCTIVITY));
            add(new Tool("Modular Avatar", "A suite of non-destructive tools for modularizing your avatars and distributing avatar components.",
                    "https://github.com/bdunderscore/modular-avatar", ToolCategory.PRODUCTIVITY));
            add(new Tool("D4rk's Avatar Optimizer", "Combines skinned mesh renders and materials in unity.",
                    "https://github.com/d4rkc0d3r/d4rkAvatarOptimizer", ToolCategory.OPTIMIZATION));
            add(new Tool("Avatar Performance Tools", "Used to check VRAM and other stats before uploading an avatar.",
                    "https://github.com/Thryrallo/VRC-Avatar-Performance-Tools", ToolCategory.OPTIMIZATION));
            add(new Tool("Unity Mesh Transfer Utility", "A tool that lets you join meshes in unity so you don't have to use blender.",
                    "https://github.com/CascadianVR/Unity-Mesh-Transfer-Utility", ToolCategory.OPTIMIZATION));
            add(new Tool("AudioLink", "Make avatars react to music in worlds that support it.",
                    "https://github.com/llealloo/vrc-udon-audio-link", ToolCategory.FEATURES));
            add(new Tool("VRCLens", "A set of photographic extensions to the stock VRChat camera to be added on an avatar",
                    "https://hirabiki.gumroad.com/l/rpnel", ToolCategory.FEATURES));
            add(new Tool("Gogo Loco", "A locomotion prefab that improves the default controller with extra features.",
                    "https://github.com/Franada/gogoloco", ToolCategory.FEATURES));
            add(new Tool("Poiyomi Toon Shader", "Feature-rich shaders intended for use with VRChat, supports multiple shading modes and robust light handling.",
                    "https://github.com/poiyomi/PoiyomiToonShader", ToolCategory.SHADERS));
            add(new Tool("lilToon", "Feature-rich shaders commonly used for Booth models.",
                    "https://github.com/lilxyzw/lilToon", ToolCategory.SHADERS));
        }
    };

    /**
     * Returns the name of the command.
     * @return the name of the command.
     */
    @Override
    public String name() {
        return "tools";
    }

    /**
     * Returns the description of the command.
     * @return the description of the command.
     */
    @Override
    public String description() {
        return "Links to useful tools for VRChat Avatars.";
    }

    /**
     * Returns the permissions needed to use the command.
     * @return an array of permissions needed to use the command.
     */
    @Override
    public Permission[] neededPermissions() {
        return new Permission[]{Permission.USE_APPLICATION_COMMANDS};
    }

    /**
     * Returns whether the command can only be used in a guild.
     * @return true if the command can only be used in a guild, false otherwise.
     */
    @Override
    public boolean guildOnly() {
        return true;
    }

    /**
     * Returns the options for the command.
     * @return an array of options for the command.
     */
    @Override
    public OptionData[] options() {
        return new OptionData[]{
                new OptionData(OptionType.STRING, "tool", "The name of the tool to link", true),
        };
    }

    /**
     * Executes the command.
     * @param info the record of the command execution.
     */
    @Override
    public void execute(SlashCommandRecord info) {
        // Get the name of the tool from the command options and normalize it
        String toolName = info.event().getOption("tool").getAsString().replaceAll("\\s+","").replaceAll("'", "").toLowerCase();

        // Find the tool that best matches the provided name
        Tool bestMatch = tools.stream()
                .max((tool1, tool2) -> Double.compare(StringUtils.getJaroWinklerDistance(toolName, tool1.getName().toLowerCase()), StringUtils.getJaroWinklerDistance(toolName, tool2.getName().toLowerCase())))
                .orElse(null);

        // If the provided name is "all" or "listall", reply with a list of all tools
        if (toolName.equals("all") || toolName.equals("listall")) {
            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("All Tools");
            tools.stream()
                    .collect(Collectors.groupingBy(Tool::getCategory))
                    .forEach((toolCategory, tools) -> {
                        String toolsList = tools.stream().map(tool -> tool.getName() + " - " + tool.getLink()).collect(Collectors.joining("\n"));
                        embedBuilder.addField(toolCategory.toString(), toolsList, false);
                    });
            info.event().replyEmbeds(embedBuilder.build()).queue();
            return;
        }

        // If a tool was found that matches the provided name, reply with the link to the tool
        if (bestMatch != null && StringUtils.getJaroWinklerDistance(toolName, bestMatch.getName().toLowerCase()) >= 0.7) {
            info.event().reply("**[" + bestMatch.getName() + "]" + "(" + bestMatch.getLink() + ")" + " - " + bestMatch.getDescription() + "**").queue();
        } else {
            // If no tool was found, reply with a message indicating that no tool was found
            info.event().reply("No tool found with that name!").queue();
        }
    }
}