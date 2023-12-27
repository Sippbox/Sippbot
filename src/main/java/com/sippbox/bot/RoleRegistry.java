package com.sippbox.bot;

import com.sippbox.Sippbot;
import net.dv8tion.jda.api.entities.Role;

public class RoleRegistry {
    public static final long MODERATOR_TEST = 1103761495042564206L;
    public static final long ACTIVE_MOD_TEST = 1103761495042564207L;
    public static final long ADMIN_TEST = 1103761495009005691L;
    public static final long AVATAR_HELPER_TEST = 1103761495042564205L;

    public static final long MODERATOR = 1103761495042564206L;
    public static final long ACTIVE_MOD = 1103761495042564207L;
    public static final long ADMIN = 1103761495009005691L;
    public static final long AVATAR_HELPER = 1103761495042564205L;

    public static Role getRole(long roleId) {
        return Sippbot.getInstance().getJdaService().getJda().get().getRoleById(roleId);
    }
}
