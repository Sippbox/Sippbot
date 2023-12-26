package com.sippbox.bot.commands.manager;

import com.sippbox.Sippbot;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import com.sippbox.utils.MessageUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Optional;

public class SlashCommandHandler extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        //Check if the command is from a guild and if the user is a bot
        if (e.getGuild() == null) return;
        if (e.getUser().isBot()) return;
        if (e.getMember() == null) return;

        Optional<SlashCommand> command = getCommand(e.getName());
        if (command.isEmpty()) return;

        //Iterate over the permissions in the command and check if the user has them
        for (Permission permission : command.get().neededPermissions()) {
            if (!e.getMember().hasPermission(permission)) {
                e.replyEmbeds(MessageUtils.createErrorEmbed("You don't have the permission to execute this command!").build()).setEphemeral(true).queue();
                return;
            }
        }


        SlashCommandRecord record = new SlashCommandRecord(command.get(), e, e.getMember(), e.getChannel().asTextChannel(), e.getOptions());
        command.get().execute(record);
    }


    private Optional<SlashCommand> getCommand(String name) {
        return Sippbot.getInstance().getCommandRegistry().getActiveCommands().stream().filter(command -> command.name().equalsIgnoreCase(name)).findFirst();
    }




}
