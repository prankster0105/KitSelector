package me.notprankster.kitselector;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private KitSelector plugin;
    public PlayerQuitListener(KitSelector kitSelector) {
        this.plugin = kitSelector;
        kitSelector.getServer().getPluginManager().registerEvents(this, kitSelector);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (plugin.getCombatList().containsKey(e.getPlayer().getUniqueId())) {
            plugin.getCombatList().remove(e.getPlayer().getUniqueId());
            plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                    "&b%username% &7&lcombat logged!")
            .replace("%username%",e.getPlayer().getName()));
            e.getPlayer().setHealth(0.0);
        }
    }
}
