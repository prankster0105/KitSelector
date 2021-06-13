package me.notprankster.kitselector.events;

import me.notprankster.kitselector.KitSelector;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PreProcessCommandListener implements Listener {

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent event)
    {
        List<String> commands = Arrays.asList("/spawn","/tpa","/tpahere","/tpaccept","/home","/warp","/tp","/kill");

        String[] parts = event.getMessage().split(" ");

        String cmd = parts[0].toLowerCase();

        if (commands.contains(cmd) && KitSelector.getCombatList().get(event.getPlayer().getUniqueId()) != null)
        {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You cannot execute this command, you are in combat!");
        }
    }
}
