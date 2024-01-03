package com.sippbox.utils;

import com.google.gson.Gson;
import com.sippbox.bot.JdaService;
import com.sippbox.bot.Sippbot;
import com.sippbox.enums.SABRoles;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class RoleRegistry {
    private static final String ROLE_IDS_FILE_PATH = "role_ids.json";
    private static Map<String, String> roleIds;

    static {
        try {
            roleIds = new Gson().fromJson(new FileReader(ROLE_IDS_FILE_PATH), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Role getRole(Guild guild, SABRoles role) {
        String roleId = roleIds.get(role.name().toLowerCase());
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID not found for " + role);
        }
        return guild.getRoleById(roleId);
    }
}