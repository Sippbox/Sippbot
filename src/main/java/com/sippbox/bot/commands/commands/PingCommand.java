package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import com.sippbox.utils.MessageUtils;

public class PingCommand extends SlashCommand {


    @Override
    public String name() {
        return "ping";
    }

    @Override
    public String description() {
        return "Measures the time needed to reply";
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
        return new OptionData[]{};
    }

    @Override
    public void execute(SlashCommandRecord info) {
        final long time = System.currentTimeMillis();

        info.event().replyEmbeds(MessageUtils.createInfoEmbed("Getting Response Time...").build()).setEphemeral(true).queue(response -> {
            response.editOriginalEmbeds(MessageUtils.createSuccessEmbed("Response Time: " + (System.currentTimeMillis() - time) + "ms").build()).queue();
        }, failure -> info.event().replyEmbeds(MessageUtils.createErrorEmbed("Failed to get response time!").build()).queue());
    }
}
