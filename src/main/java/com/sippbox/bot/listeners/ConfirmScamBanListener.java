package com.sippbox.bot.listeners;

import com.sippbox.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.concurrent.TimeUnit;

public class ConfirmScamBanListener extends ListenerAdapter {

    public Member member;
    public ConfirmScamBanListener(Member member) {
        this.member = member;
    }
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("ban_user")) {
            event.deferReply().queue(); // Acknowledge the button click

            if (event.getMember() == null) {
                return;
            }

            //Message the user to let them know they have been banned
            member.getUser().openPrivateChannel().queue(channel -> {
                channel.sendMessageEmbeds(MessageUtils.createWarningEmbed()
                        .setTitle("**Banned from Sipp's Avatar Box**")
                        .setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Vienna_Convention_road_sign_Aa-32-V1.svg/170px-Vienna_Convention_road_sign_Aa-32-V1.svg.png")
                        .setDescription("You have been banned from Sipp's Avatar Box because malicious/scamming activity was detected from your account.\n \n" +
                                "If you are not a scammer and we made a mistake, please fill out the appeal form below. \n \n" +
                                "If you *are* a scammer, please stop wasting our time and yours. **You know who you are.** \n \n")
                        .addField("Appeal Form", "https://dyno.gg/form/e887a05c", false)
                        .build()).queue();
            });


            // Perform the ban operation
            event.getGuild().ban(member, 7, TimeUnit.DAYS).queue(success -> {
                Button confirmedBanButton = Button.success("ban_success", "Banned").asDisabled().withEmoji(Emoji.fromUnicode("✅"));
                event.getMessage().editMessageComponents(ActionRow.of(confirmedBanButton)).queue();
            }, failure -> {
                event.reply("Failed to ban user. Are you sure they are in the server?").queue();
            });

        }
    }
}