package me.notprankster.kitselector.commands;

import me.notprankster.kitselector.KitSelector;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    private final KitSelector plugin;

    public SpawnCommand(KitSelector plugin) {
        this.plugin = plugin;
        plugin.getCommand("spawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = null;

        if (sender instanceof Player) {
            p = (Player) sender;

            p.getInventory().clear();
            p.closeInventory();
            p.updateInventory();
            p.teleport(p.getWorld().getSpawnLocation());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Teleported to &bspawn&7."));
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("confirm")) {

                if (p != null) {
                    p.getInventory().clear();
                    p.closeInventory();
                    p.updateInventory();
                    p.teleport(p.getWorld().getSpawnLocation());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Teleported to &bspawn&7."));

                    return true;
                }

                if (sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
                }
            } else {
                if (p != null || sender instanceof ConsoleCommandSender) {
                    sender.sendMessage(ChatColor.RED + "Usage: /spawn");
                }
            }
        } else {
            if (p != null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        "&6[&cKitSelector&6] &cAre you sure? Doing this will clear your inventory. Do /spawn confirm to teleport to spawn"));

                return true;
            }

            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            }
        }

        return false;
    }
}
