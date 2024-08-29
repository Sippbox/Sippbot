package com.sippbox.enums;

import com.sippbox.bot.Sippbot;
import net.dv8tion.jda.api.entities.Guild;

import javax.management.relation.Role;

public enum SABRoles {
    MEMBER,
    MODERATOR,
    AVATAR_HELPER;

    public String getId(){
        return switch (this) {
            case MEMBER -> "874445484180250755";
            case MODERATOR -> "988171143376687154";
            case AVATAR_HELPER -> "874438640703512636";
        };
    }
}