package com.sippbox.bot.listeners;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.concurrent.TimeUnit;

public class ConfirmScamBanListener extends ListenerAdapter {

    private Member member;

    public ConfirmScamBanListener(Member member) {
        this.member = member;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("ban_user")) {
            event.deferReply().setEphemeral(true).queue();
            if (event.getMember() == null) return;

            sendBanMessage();
            banUser(event);
        }
    }

    private void sendBanMessage() {
        member.getUser().openPrivateChannel().queue(channel -> {
            channel.sendMessage("You have been banned from Sipp's Avatar Box because malicious/scamming activity was detected from your account. If you are not a scammer and we made a mistake, please fill out the appeal form below. If you *are* a scammer, please stop wasting our time and yours. **You know who you are.** \n \n Appeal Form: https://dyno.gg/form/e887a05c").queue();
        });
    }

    private void banUser(ButtonInteractionEvent event) {
        event.getGuild().ban(member, 7, TimeUnit.DAYS).queue(success -> {
            Button confirmedBanButton = Button.success("ban_success", "Banned").asDisabled();
            event.getMessage().editMessageComponents(ActionRow.of(confirmedBanButton)).queue();
        }, failure -> {
            event.reply("Failed to ban user. Are you sure they are in the server?").queue();
        });
    }
}