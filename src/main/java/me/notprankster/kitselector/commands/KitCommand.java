package me.notprankster.kitselector.commands;

import me.notprankster.kitselector.KitSelector;
import me.notprankster.kitselector.events.EntityDamageListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KitCommand implements CommandExecutor {
    public static NamespacedKey key;
    public static NamespacedKey getKey() {
        if (key != null) {
            return key;
        }

        return null;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (KitSelector.getCombatList().containsKey(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED + "You are in combat! You cannot execute this command");
                return false;
            }

            //Make a namespaced key for PersistentDataContainer
            key = new NamespacedKey(KitSelector.getInstance(), "kit-type");


            //Create the inventory
            Inventory inv =KitSelector.getInstance().getServer().createInventory(null, 27, "Select a kit");

            //Make tank kit
            ItemStack Tank = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta tankMeta = Tank.getItemMeta();
            tankMeta.setDisplayName(ChatColor.AQUA + "Tank Kit");
            tankMeta.setUnbreakable(true);
            tankMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "Tank");
            tankMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            tankMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            Tank.setItemMeta(tankMeta);


            // All the kits below tank kit are not functional yet, and are in the works.

            //Make Axeman Kit
            ItemStack Axe = new ItemStack(Material.IRON_AXE);
            ItemMeta axeMeta = Axe.getItemMeta();
            axeMeta.setDisplayName(ChatColor.RESET + "Axeman Kit");
            axeMeta.setUnbreakable(true);
            axeMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "Axe");
            axeMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            axeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            Axe.setItemMeta(axeMeta);

            //Make Pawn Kit
            ItemStack Pawn = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta pawnMeta = Pawn.getItemMeta();
            pawnMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            pawnMeta.setDisplayName(ChatColor.RESET + "Pawn Kit");
            pawnMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            pawnMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "Pawn");
            Pawn.setItemMeta(pawnMeta);


            //Make Ninja Kit
            ItemStack Ninja = new ItemStack(Material.IRON_BOOTS);
            ItemMeta ninjaMeta = Ninja.getItemMeta();
            ninjaMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            ninjaMeta.setDisplayName(ChatColor.AQUA + "Ninja Kit");
            ninjaMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            ninjaMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING,"Ninja");
            Ninja.setItemMeta(ninjaMeta);



            //Make Archer Kit
            ItemStack Archer = new ItemStack(Material.BOW);
            ItemMeta archerMeta = Archer.getItemMeta();
            archerMeta.setDisplayName(ChatColor.RESET + "Archer Kit");
            archerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            archerMeta.getPersistentDataContainer().set(key,PersistentDataType.STRING,"Archer");
            archerMeta.addEnchant(Enchantment.DAMAGE_ALL,1,true);
            Archer.setItemMeta(archerMeta);

            //Make Marksman Kit
            ItemStack Marksman = new ItemStack(Material.CROSSBOW);
            ItemMeta marksmanMeta = Marksman.getItemMeta();
            marksmanMeta.setDisplayName(ChatColor.RED + "" + ChatColor.ITALIC + "Marksman Kit");
            List<String> loreList = new ArrayList<>();
            loreList.add("An enhanced version of the Archer kit.");
            marksmanMeta.setLore(loreList);
            marksmanMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            marksmanMeta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
            marksmanMeta.getPersistentDataContainer().set(key,PersistentDataType.STRING,"Marksman");
            Marksman.setItemMeta(marksmanMeta);
            
            //"Make" Bomberman Kit
            ItemStack Bomberman = new ItemStack(Material.TNT);
            ItemMeta bombermanMeta = Bomberman.getItemMeta();
            bombermanMeta.setDisplayName(ChatColor.RED + "Bomberman Kit");
            bombermanMeta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
            bombermanMeta.getPersistentDataContainer().set(key,PersistentDataType.STRING,"Bomberman");
            Bomberman.setItemMeta(bombermanMeta);


            //display inv

            inv.setItem(1, Tank);
            inv.setItem(3, Pawn);
            inv.setItem(5, Ninja);
            inv.setItem(7, Axe);
            inv.setItem(10,Archer);
            inv.setItem(12,Marksman);
            inv.setItem(14,Bomberman);
            p.openInventory(inv);

        } else {
            sender.sendMessage(ChatColor.RED + "The console cannot execute this command.");
            return false;
        }

        return true;
    }
}
