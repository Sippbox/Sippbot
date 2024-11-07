package com.sippbox.enums;

import com.sippbox.bot.Sippbot;
import net.dv8tion.jda.api.entities.Guild;

import javax.management.relation.Role;

public enum SABRoles {
    MEMBER,
    MODERATOR,
    AVATAR_HELPER;

    public String getId(boolean devMode){
        if(devMode){
            return switch (this) {
                case MEMBER -> "1103761495009005697";
                case MODERATOR -> "1103761495042564207";
                case AVATAR_HELPER -> "1103761495042564205";
            };
        }
        return switch (this) {
            case MEMBER -> "874445484180250755";
            case MODERATOR -> "988171143376687154";
            case AVATAR_HELPER -> "874438640703512636";
        };
    }

    public String getId() {
        return getId(false);
    }
}