package me.notprankster.kitselector.miscellaneous;

import me.notprankster.kitselector.KitSelector;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class PlayerPermissions {
    static HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();

    public static void givePlayerPermission(Player player, String permissionNode) {
        if (perms.get(player.getUniqueId()) != null) {
            PermissionAttachment pperms = perms.get(player.getUniqueId());
            pperms.setPermission(permissionNode,true);

        } else {
            PermissionAttachment attachment = player.addAttachment(KitSelector.getInstance());
            attachment.setPermission(permissionNode,true);
            perms.put(player.getUniqueId(),attachment);


        }


    }

    public static void removePlayerPermission(Player player, String permissionNode) {
        if (perms.get(player.getUniqueId()) != null) {
            PermissionAttachment pperms = perms.get(player.getUniqueId());
            pperms.setPermission(permissionNode,false);
        } else {
            PermissionAttachment attachment = player.addAttachment(KitSelector.getInstance());
            attachment.setPermission(permissionNode,false);

            perms.put(player.getUniqueId(),attachment);
        }
    }
}
