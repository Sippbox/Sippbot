package com.sippbox.bot.commands.manager;

import com.sippbox.bot.Sippbot;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import com.sippbox.utils.MessageUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
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

        // Handle TextChannel and ThreadChannel separately
        if (e.getChannel() instanceof TextChannel textChannel) {
            SlashCommandRecord record = new SlashCommandRecord(command.get(), e, e.getMember(), textChannel, e.getOptions());
            command.get().execute(record);
        } else if (e.getChannel() instanceof ThreadChannel threadChannel) {
            SlashCommandRecord record = new SlashCommandRecord(command.get(), e, e.getMember(), threadChannel, e.getOptions());
            command.get().execute(record);
        }
    }


    private Optional<SlashCommand> getCommand(String name) {
        return Sippbot.getInstance().getCommandRegistry().getActiveCommands().stream().filter(command -> command.name().equalsIgnoreCase(name)).findFirst();
    }




}
