package com.sippbox.bot.commands.commands;

import com.sippbox.enums.SABChannels;
import com.sippbox.enums.SABRoles;
import com.sippbox.utils.ChannelRegistry;
import com.sippbox.utils.RoleRegistry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

public class ReportMessageCommand extends ListenerAdapter {
    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        TextChannel modChannel = ChannelRegistry.getTextChannel(event.getGuild(), SABChannels.MODERATOR_ONLY);
        Role modRole = event.getGuild().getRolesByName("Moderator", true).get(0);

        if (event.getName().equals("Report Message")) {
            event.replyModal(reportMessageModal()).queue();

            messageModChannel(modChannel, modRole, event);
        }

    }

    public Modal reportMessageModal() {
        TextInput reportReason = TextInput.create("report_reason", "Report Reason", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Please enter a reason for your report")
                .setRequired(true)
                .build();

        return Modal.create("report_message", "Report Message")
                .addComponents(ActionRow.of(reportReason))
                .build();
    }

    public void messageModChannel(TextChannel modChannel, Role modRole, MessageContextInteractionEvent event) {
        var embed = new EmbedBuilder()
                .setTitle("Reported Message")
                .setDescription("A message has been reported in " + event.getChannel().getAsMention() + " by " + event.getUser().getAsMention())
                .setThumbnail(event.getUser().getAvatarUrl())
                .addField("Reported User", event.getInteraction().getUser().getAsMention(), false)
                .build();

        modChannel.sendMessage(modRole.getAsMention()).addEmbeds(embed).queue();
    }
}