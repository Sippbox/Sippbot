package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class BlenderExportCommand extends SlashCommand {
    @Override
    public String name() {
        return "blenderexport";
    }

    @Override
    public String description() {
        return "Specifies the recommended blender export settings for exporting VRChat avatars.";
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
        String settingsMessage = """
                ## FBX Export from Blender to Unity settings (courtesy of catboy)
                                
                **Path:** Unity project.
                **Path Mode:** Auto
                **Include:**
                **Object Types:** Empty: On, Camera: Off, Lamp: Off, Armature: On, Mesh: On, Other: Off.
                **Transform:**
                > Scale: 1.00
                > Apply Scalings: FBX All
                > Forward: -Z Forward
                > Up: Y Up
                > Apply Transform: On
                **Armature:**
                > Add Leaf Bones: Off
                > Bake Animations: On
                """;
        info.event().reply(settingsMessage).queue();
    }
}
