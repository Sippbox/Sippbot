package com.sippbox.bot.commands.manager;

import com.sippbox.Sippbot;
import com.sippbox.bot.commands.commands.*;
import com.sippbox.bot.commands.commands.PingCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandRegistry {

    private final Optional<JDA> jda = Sippbot.getInstance().getJdaService().getJda();
    private List<SlashCommand> activeCommands = new ArrayList<>();

    public CommandRegistry() {
        registerCommands();
    }

    private void registerCommands() {
        jda.ifPresentOrElse(jda -> {
            jda.updateCommands().addCommands(
                    create(new TutorialCommand()),
                    create(new DocumentationCommand()),
                    create(new BlenderExportCommand()),
                    create(new RigCommand()),
                    create(new PingCommand()),
                    create(new ScamBanCommand()),
                    create(new MessageUserCommand()),
                    create(new ToolCommand()),
                    create(new MessageChannelCommand())

            ).queue();
        }, () -> {
            throw new IllegalStateException("JDA is not present!");
        });
    }

    private CommandData create(SlashCommand command) {
        this.activeCommands.add(command);
        SlashCommandData commandData = Commands.slash(command.name(), command.description()).setGuildOnly(command.guildOnly());

        if(command.options().length > 0) {
            System.out.print("Registering command " + command.name() + " with " + command.options().length + " options");
            commandData.addOptions(command.options());
        } else {
            System.out.print("Registering command " + command.name() + " with no options");
        }

        if (!command.subcommands().isEmpty()) {
            System.out.print("and " + command.subcommands().size() + " subcommands!");
            System.out.println();
            commandData.addSubcommands(command.subcommands());
        } else {
            System.out.print(" and no subcommands! ");
            System.out.println();
        }

        return commandData;
    }

    public List<SlashCommand> getActiveCommands() {
        return activeCommands;
    }
}