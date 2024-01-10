package com.sippbox.bot.commands.commands;

import com.sippbox.bot.commands.manager.SlashCommand;
import com.sippbox.bot.commands.status.SlashCommandRecord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScamBanCommand extends SlashCommand {

    @Override
    public String name() { return "scam"; }

    @Override
    public String description() { return "Bans a user for scamming, confirms with a button, sends appeal info to banned user."; }

    @Override
    public Permission[] neededPermissions() { return new Permission[]{Permission.USE_APPLICATION_COMMANDS, Permission.BAN_MEMBERS}; }

    @Override
    public boolean guildOnly() { return false; }

    @Override
    public OptionData[] options() {
        return new OptionData[]{ new OptionData(OptionType.STRING, "userid", "The ID of the user to ban", true) };
    }

    @Override
    public void execute(SlashCommandRecord info) {
        info.event().deferReply().queue();
        Member member = info.event().getGuild().retrieveMemberById(info.options().get(0).getAsString()).complete();

        if (member == null) {
            info.event().getHook().editOriginal("User not found!").queue();
            return;
        }

        AtomicBoolean messageSent = new AtomicBoolean(false);
        member.getUser().openPrivateChannel().queue(channel -> {
            channel.sendMessageEmbeds(createUserEmbed()).queue(success -> {
                messageSent.set(true);
                sendModEmbed(info, member, messageSent.get());
            }, failure -> {
                messageSent.set(false);
                sendModEmbed(info, member, messageSent.get());
            });
        });

        info.event().getGuild().ban(member, 7, TimeUnit.DAYS).queue();
    }

    private void sendModEmbed(SlashCommandRecord info, Member member, boolean messageSent) {
        info.event().getHook().editOriginalEmbeds(createModEmbed(member, messageSent)).queue();
    }

    private MessageEmbed createUserEmbed() {
        return new EmbedBuilder()
                .setTitle("Ban Notification")
                .setDescription("You have been banned from Sipp's Avatar Box because malicious/scamming activity was detected from your account. If you are not a scammer and we made a mistake, please fill out the appeal form below. If you *are* a scammer, please stop wasting our time and yours. **You know who you are.**")
                .addField("Appeal Form", "https://dyno.gg/form/e887a05c", false)
                .setThumbnail("https://cdn.discordapp.com/icons/873806377183752212/a_16f71e81793a68f20ebce0ad5145b11b.gif?size=128")
                .setColor(Color.RED)
                .build();
    }

    private MessageEmbed createModEmbed(Member member, boolean messageSent) {
        return new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Scammer Banned")
                .setDescription(member.getUser().getAsMention() + " has been banned for scamming!")
                .addField("Account Age", "<t:" + member.getUser().getTimeCreated().toInstant().getEpochSecond() + ":f>", true)
                .addField("Joined Server", "<t:" + member.getTimeJoined().toInstant().getEpochSecond() + ":f>", false)
                .addField("Appeal Message Sent", messageSent ? "True" : "False", false)
                .setThumbnail(member.getUser().getAvatarUrl())
                .setFooter("User ID: " + member.getUser().getId())
                .build();
    }
}