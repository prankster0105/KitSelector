package me.notprankster.kitselector.commands;

import me.notprankster.kitselector.KitSelector;
import me.notprankster.kitselector.miscellaneous.EntityLocations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    private final KitSelector plugin;

    public SetSpawnCommand(KitSelector plugin) {
        this.plugin = plugin;

        plugin.getCommand("setspawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player)sender;

            int pX = p.getLocation().getBlockX();
            int pY = p.getLocation().getBlockY();
            int pZ = p.getLocation().getBlockZ();

            String coords = String.valueOf(pX) + ", " + String.valueOf(pY) + ", " + String.valueOf(pZ);

            if (p.isOp() || p.hasPermission("kitselector.spawn.set")) {
                EntityLocations.setFFASpawnLocation(p.getLocation());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&7Set spawn to &b%coords% &7in &b%worldname%")
                        .replace("%coords%",coords)
                        .replace("%worldname%",p.getWorld().getName()));
                return true;
            }
        }

        return false;
    }
}
