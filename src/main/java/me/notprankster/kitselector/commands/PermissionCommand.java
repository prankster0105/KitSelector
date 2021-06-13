package me.notprankster.kitselector.commands;

import me.notprankster.kitselector.miscellaneous.PlayerPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        if (args.length == 3) {
            if (args[0].equals("add")) {
                Player target = Bukkit.getServer().getPlayer(args[2]);

                if (target != null) {
                    PlayerPermissions.givePlayerPermission(target,args[1]);
                    String response = "&7Set %target%'s permission for &b%node% &7to &btrue";
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',response.replace(
                            "%target%",args[2]
                    ).replace("%node%",args[1])));
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "That player could not be found.");
                }
            } else if (args[0].equals("remove")) {
                Player target = Bukkit.getServer().getPlayer(args[2]);

                if (target != null) {
                    PlayerPermissions.removePlayerPermission(target,args[1]);
                    String response = "&7Set %target%'s permission for &b%node% &7 to &bfalse";
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',response.replace(
                            "%target%",args[2]
                    ).replace("%node%",args[1])));
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.GRAY + "Usage: /permission [add|remove] <permission_node> <playername>");
            }
        }

        return false;
    }
}
