package com.sippbox.bot.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ConfirmScamBanListener extends ListenerAdapter {

    private final Member MEMBER;

    public ConfirmScamBanListener(Member member) {
        this.MEMBER = member;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("ban_user") && !event.isAcknowledged()) {
            event.deferReply().setEphemeral(true).queue(interactionHook -> {
                if (event.getMember() == null) return;
                banUser(event);
                sendBanMessage().thenAccept(messageSent -> {
                    interactionHook.editOriginal("User banned" + (messageSent ? "." : ", but failed to send ban message. They may not have DMs enabled.")).queue();
                });
            });
        }
    }

    private CompletableFuture<Boolean> sendBanMessage() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        MEMBER.getUser().openPrivateChannel().queue(channel -> {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Ban Notification")
                    .setDescription("You have been banned from Sipp's Avatar Box because malicious/scamming activity was detected from your account. If you are not a scammer and we made a mistake, please fill out the appeal form below. If you *are* a scammer, please stop wasting our time and yours. **You know who you are.**")
                    .addField("Appeal Form", "https://dyno.gg/form/e887a05c", false)
                    .setThumbnail("https://cdn.discordapp.com/icons/873806377183752212/a_16f71e81793a68f20ebce0ad5145b11b.gif?size=128")
                    .setColor(Color.RED);

            channel.sendMessageEmbeds(embed.build()).queue(success -> future.complete(true), failure -> future.complete(false));
        });
        return future;
    }

    private void banUser(ButtonInteractionEvent event) {
        event.getGuild().ban(MEMBER, 7, TimeUnit.DAYS).reason("[Sippbot] Scamming").queue(success -> {
            Button confirmedBanButton = Button.success("ban_success", "Banned").asDisabled();
            event.getMessage().editMessageComponents(ActionRow.of(confirmedBanButton)).queue();
        }, failure -> {
            event.reply("Failed to ban user. Are you sure they are in the server?").queue();
        });
    }
}