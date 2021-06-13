package me.notprankster.kitselector.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            p.getInventory().clear();
            p.closeInventory();;
            p.updateInventory();
            p.teleport(p.getWorld().getSpawnLocation());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Teleported to &bspawn&7."));
        }

        return false;
    }
}
