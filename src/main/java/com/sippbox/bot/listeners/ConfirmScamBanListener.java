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

/**
 * This class extends ListenerAdapter from JDA (Java Discord API) and overrides the onButtonInteraction method.
 * It listens for the ban button being clicked and triggers a ban on the user.
 */
public class ConfirmScamBanListener extends ListenerAdapter {

    private final Member MEMBER;

    /**
     * Constructor for the ConfirmScamBanListener class.
     * @param member The member to be banned.
     */
    public ConfirmScamBanListener(Member member) {
        this.MEMBER = member;
    }

    /**
     * This method is triggered whenever a button interaction is received.
     * It checks if the interaction is related to banning a user and if so, it defers the reply, sends a ban message to the user, and bans the user.
     * @param event The event of a button interaction.
     */
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

    /**
     * This method sends a ban message to the user.
     * It opens a private channel with the user and sends an embed message notifying them of the ban.
     * @return A CompletableFuture that completes with a boolean indicating whether the message was sent successfully.
     */
    private CompletableFuture<Boolean> sendBanMessage() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        MEMBER.getUser().openPrivateChannel().queue(channel -> {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Ban Notification")
                    .setDescription("You have been banned from Sipp's Avatar Box because malicious/scamming activity was detected from your account. If you are not a scammer and we made a mistake, please fill out the appeal form below. If you *are* a scammer, please stop wasting our time and yours. **You know who you are.**")
                    .addField("Appeal Form", "https://dyno.gg/form/e887a05c", false)
                    .setThumbnail("https://cdn.discordapp.com/icons/873806377183752212/a_16f71e81793a68f20ebce0ad5145b11b.gif?size=128")
                    .setColor(Color.RED);

            try {
                channel.sendMessageEmbeds(embed.build()).queue(success -> future.complete(true), failure -> future.complete(false));
            } catch (Exception e) {
                System.out.println("Failed to send ban message to user: " + e.getMessage());
                future.complete(false);
            }
        });
        return future;
    }

    /**
     * This method bans the user from the guild.
     * It also edits the original message to reflect the ban status.
     * @param event The event of a button interaction.
     */
    private void banUser(ButtonInteractionEvent event) {
        event.getGuild().ban(MEMBER, 7, TimeUnit.DAYS).reason("[Sippbot] Scamming").queue(success -> {
            Button confirmedBanButton = Button.success("ban_success", "Banned").asDisabled();
            event.getMessage().editMessageComponents(ActionRow.of(confirmedBanButton)).queue();
        }, failure -> {
            event.reply("Failed to ban user. Are you sure they are in the server?").queue();
        });
    }

}