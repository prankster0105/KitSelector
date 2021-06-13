package me.notprankster.kitselector.events;

import me.notprankster.kitselector.KitSelector;
import me.notprankster.kitselector.commands.KitCommand;
import me.notprankster.kitselector.miscellaneous.EntityLocations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InventoryClickListener implements Listener {
    public static Set<Player> invinciblePlayers = new HashSet<Player>();

    public static Set<Player> getInvinciblePlayers() {
        return invinciblePlayers;
    }

    void clearPlayerEffects(Player p) {
        Collection<PotionEffect> playerEffects = p.getActivePotionEffects();

        if (playerEffects != null) {
            for (PotionEffect effect : playerEffects) {
                p.removePotionEffect(effect.getType());
            }
        }
    }

    void teleportPlayer(Player p) {
        Location spawnPoint = EntityLocations.getFFASpawnLocation();

        if (spawnPoint != null) {
            p.teleport(spawnPoint);

        } else {
            p.sendMessage(ChatColor.RED + "The spawn location does not exist. Ask a server administrator to set one");
        }
    }

    public void makePlayerInvincible(Player p) {
        int invincibilityTime = KitSelector.getInstance().getConfig().getInt("invincibilityTime");

        invinciblePlayers.add(p);

        BukkitScheduler scheduler = KitSelector.getInstance().getServer().getScheduler();


        new BukkitRunnable() {
            @Override
            public void run() {
                invinciblePlayers.remove(p);
            }
        }.runTaskLater(KitSelector.getInstance(), invincibilityTime*20L);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        String invName = event.getView().getTitle();
        ItemStack item = event.getCurrentItem();
        PlayerInventory playerInv = p.getInventory();


        if (invName.equals("Select a kit")) {
            if (item != null) {
                event.setCancelled(true);

                if (item.getItemMeta().getPersistentDataContainer().get(KitCommand.getKey(), PersistentDataType.STRING).equals("TANK")) {
                    p.closeInventory();
                    clearPlayerEffects(p);
                    playerInv.clear();

                    ItemStack diamondHelmet = new ItemStack(Material.DIAMOND_HELMET);
                    ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                    ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                    ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
                    ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);

                    diamondHelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    diamondChestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                    diamondLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                    diamondBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    diamondSword.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);


                    ItemMeta helmetMeta = diamondHelmet.getItemMeta();
                    ItemMeta chestplateMeta = diamondChestplate.getItemMeta();
                    ItemMeta leggingsMeta = diamondLeggings.getItemMeta();
                    ItemMeta bootsMeta = diamondBoots.getItemMeta();
                    ItemMeta swordMeta = diamondSword.getItemMeta();

                    helmetMeta.setUnbreakable(true);
                    diamondHelmet.setItemMeta(helmetMeta);

                    chestplateMeta.setUnbreakable(true);
                    diamondChestplate.setItemMeta(chestplateMeta);

                    leggingsMeta.setUnbreakable(true);
                    diamondLeggings.setItemMeta(leggingsMeta);

                    bootsMeta.setUnbreakable(true);
                    diamondBoots.setItemMeta(bootsMeta);

                    swordMeta.setUnbreakable(true);
                    swordMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    swordMeta.setDisplayName(ChatColor.RESET + "Diamond Sword");
                    diamondSword.setItemMeta(swordMeta);

                    playerInv.setHelmet(diamondHelmet);
                    playerInv.setChestplate(diamondChestplate);
                    playerInv.setLeggings(diamondLeggings);
                    playerInv.setBoots(diamondBoots);
                    playerInv.setItem(0, diamondSword);



                    p.updateInventory();
                    makePlayerInvincible(p);

                    teleportPlayer(p);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Chose kit &bTank"));
                } else if (item.getItemMeta().getPersistentDataContainer().get(KitCommand.getKey(),PersistentDataType.STRING).equals("PAWN")) {
                    p.closeInventory();
                    playerInv.clear();
                    clearPlayerEffects(p);
                    ItemStack diamondHelmet = new ItemStack(Material.DIAMOND_HELMET);
                    ItemMeta helmetMeta = diamondHelmet.getItemMeta();

                    ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                    ItemMeta chestplateMeta = diamondChestplate.getItemMeta();

                    ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                    ItemMeta leggingsMeta = diamondLeggings.getItemMeta();

                    ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
                    ItemMeta bootsMeta = diamondBoots.getItemMeta();

                    ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
                    ItemMeta swordMeta = diamondSword.getItemMeta();

                    helmetMeta.setUnbreakable(true);
                    diamondHelmet.setItemMeta(helmetMeta);

                    chestplateMeta.setUnbreakable(true);
                    diamondChestplate.setItemMeta(chestplateMeta);

                    leggingsMeta.setUnbreakable(true);
                    diamondLeggings.setItemMeta(leggingsMeta);

                    bootsMeta.setUnbreakable(true);
                    diamondBoots.setItemMeta(bootsMeta);

                    swordMeta.setUnbreakable(true);
                    swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, false);
                    diamondSword.setItemMeta(swordMeta);

                    playerInv.setHelmet(diamondHelmet);
                    playerInv.setChestplate(diamondChestplate);
                    playerInv.setLeggings(diamondLeggings);
                    playerInv.setBoots(diamondBoots);

                    playerInv.setItem(0,diamondSword);

                    p.updateInventory();
                    teleportPlayer(p);

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Chose kit &bPawn"));
                    makePlayerInvincible(p);
                } else if (item.getItemMeta().getPersistentDataContainer().get(KitCommand.getKey(),PersistentDataType.STRING).equals("AXE")) {
                    p.closeInventory();
                    playerInv.clear();
                    clearPlayerEffects(p);
                    ItemStack diamondHelmet = new ItemStack(Material.DIAMOND_HELMET);
                    ItemMeta helmetMeta = diamondHelmet.getItemMeta();

                    ItemStack diamondChestplate = new ItemStack(Material.IRON_CHESTPLATE);
                    ItemMeta chestplateMeta = diamondChestplate.getItemMeta();

                    ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                    ItemMeta leggingsMeta = diamondLeggings.getItemMeta();

                    ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
                    ItemMeta bootsMeta = diamondBoots.getItemMeta();

                    ItemStack diamondSword = new ItemStack(Material.IRON_AXE);
                    ItemMeta swordMeta = diamondSword.getItemMeta();

                    helmetMeta.setUnbreakable(true);
                    helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                    diamondHelmet.setItemMeta(helmetMeta);

                    chestplateMeta.setUnbreakable(true);
                    chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                    diamondChestplate.setItemMeta(chestplateMeta);

                    leggingsMeta.setUnbreakable(true);
                    leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                    diamondLeggings.setItemMeta(leggingsMeta);

                    bootsMeta.setUnbreakable(true);
                    bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                    diamondBoots.setItemMeta(bootsMeta);

                    swordMeta.setUnbreakable(true);
                    swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
                    diamondSword.setItemMeta(swordMeta);

                    playerInv.setHelmet(diamondHelmet);
                    playerInv.setChestplate(diamondChestplate);
                    playerInv.setLeggings(diamondLeggings);
                    playerInv.setBoots(diamondBoots);

                    playerInv.setItem(0,diamondSword);

                    p.updateInventory();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Chose kit &bAxeman"));
                    teleportPlayer(p);

                    makePlayerInvincible(p);

                } else if (item.getItemMeta().getPersistentDataContainer().get(KitCommand.getKey(),PersistentDataType.STRING).equals("NINJA")) {
                    p.closeInventory();
                    playerInv.clear();
                    clearPlayerEffects(p);


                    ItemStack diamondHelmet = new ItemStack(Material.IRON_HELMET);
                    ItemMeta helmetMeta = diamondHelmet.getItemMeta();

                    ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                    ItemMeta chestplateMeta = diamondChestplate.getItemMeta();

                    ItemStack diamondLeggings = new ItemStack(Material.IRON_LEGGINGS);
                    ItemMeta leggingsMeta = diamondLeggings.getItemMeta();

                    ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
                    ItemMeta bootsMeta = diamondBoots.getItemMeta();

                    ItemStack diamondSword = new ItemStack(Material.IRON_SWORD);
                    ItemMeta swordMeta = diamondSword.getItemMeta();

                    helmetMeta.setUnbreakable(true);
                    helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondHelmet.setItemMeta(helmetMeta);

                    chestplateMeta.setUnbreakable(true);
                    chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondChestplate.setItemMeta(chestplateMeta);

                    leggingsMeta.setUnbreakable(true);
                    leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondLeggings.setItemMeta(leggingsMeta);

                    bootsMeta.setUnbreakable(true);
                    bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondBoots.setItemMeta(bootsMeta);

                    swordMeta.setUnbreakable(true);
                    swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
                    diamondSword.setItemMeta(swordMeta);

                    playerInv.setHelmet(diamondHelmet);
                    playerInv.setChestplate(diamondChestplate);
                    playerInv.setLeggings(diamondLeggings);
                    playerInv.setBoots(diamondBoots);

                    playerInv.setItem(0,diamondSword);

                    p.updateInventory();
                    PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 999999,1);
                    p.addPotionEffect(speed);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Chose kit &bNinja"));
                    teleportPlayer(p);

                    makePlayerInvincible(p);
                } else if (item.getItemMeta().getPersistentDataContainer().get(KitCommand.getKey(),PersistentDataType.STRING).equals("ARCHER")) {
                    p.closeInventory();
                    playerInv.clear();
                    clearPlayerEffects(p);


                    ItemStack diamondHelmet = new ItemStack(Material.CHAINMAIL_HELMET);
                    ItemMeta helmetMeta = diamondHelmet.getItemMeta();

                    ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                    ItemMeta chestplateMeta = diamondChestplate.getItemMeta();

                    ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                    ItemMeta leggingsMeta = diamondLeggings.getItemMeta();

                    ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
                    ItemMeta bootsMeta = diamondBoots.getItemMeta();

                    ItemStack diamondSword = new ItemStack(Material.STONE_SWORD);
                    ItemMeta swordMeta = diamondSword.getItemMeta();

                    ItemStack bow = new ItemStack(Material.BOW);
                    ItemMeta bowMeta = bow.getItemMeta();

                    helmetMeta.setUnbreakable(true);
                    helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                    diamondHelmet.setItemMeta(helmetMeta);

                    chestplateMeta.setUnbreakable(true);
                    chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondChestplate.setItemMeta(chestplateMeta);

                    leggingsMeta.setUnbreakable(true);
                    leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondLeggings.setItemMeta(leggingsMeta);

                    bootsMeta.setUnbreakable(true);
                    bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                    diamondBoots.setItemMeta(bootsMeta);

                    swordMeta.setUnbreakable(true);

                    diamondSword.setItemMeta(swordMeta);

                    bowMeta.setUnbreakable(true);
                    bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
                    bow.setItemMeta(bowMeta);

                    playerInv.setHelmet(diamondHelmet);
                    playerInv.setChestplate(diamondChestplate);
                    playerInv.setLeggings(diamondLeggings);
                    playerInv.setBoots(diamondBoots);
                    playerInv.setItemInOffHand(bow);

                    playerInv.setItem(0,diamondSword);
                    ItemStack arrow = new ItemStack(Material.ARROW);
                    arrow.setAmount(24);
                    playerInv.setItem(1,arrow);

                    p.updateInventory();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Chose kit &bArcher"));
                    teleportPlayer(p);

                    makePlayerInvincible(p);
                } else if (item.getItemMeta().getPersistentDataContainer().get(KitCommand.getKey(),PersistentDataType.STRING).equals("MARKSMAN")) {
                    p.closeInventory();
                    playerInv.clear();
                    clearPlayerEffects(p);


                    ItemStack diamondHelmet = new ItemStack(Material.IRON_HELMET);
                    ItemMeta helmetMeta = diamondHelmet.getItemMeta();

                    ItemStack diamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                    ItemMeta chestplateMeta = diamondChestplate.getItemMeta();

                    ItemStack diamondLeggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                    ItemMeta leggingsMeta = diamondLeggings.getItemMeta();

                    ItemStack diamondBoots = new ItemStack(Material.DIAMOND_BOOTS);
                    ItemMeta bootsMeta = diamondBoots.getItemMeta();

                    ItemStack diamondSword = new ItemStack(Material.IRON_SWORD);
                    ItemMeta swordMeta = diamondSword.getItemMeta();

                    ItemStack bow = new ItemStack(Material.CROSSBOW);
                    ItemMeta bowMeta = bow.getItemMeta();

                    helmetMeta.setUnbreakable(true);
                    helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondHelmet.setItemMeta(helmetMeta);

                    chestplateMeta.setUnbreakable(true);
                    chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondChestplate.setItemMeta(chestplateMeta);

                    leggingsMeta.setUnbreakable(true);
                    leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondLeggings.setItemMeta(leggingsMeta);

                    bootsMeta.setUnbreakable(true);
                    bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
                    diamondBoots.setItemMeta(bootsMeta);

                    swordMeta.setUnbreakable(true);
                    swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
                    diamondSword.setItemMeta(swordMeta);

                    bowMeta.setUnbreakable(true);
                    bowMeta.addEnchant(Enchantment.QUICK_CHARGE, 1, false);
                    bowMeta.addEnchant(Enchantment.PIERCING,5,true);
                    bow.setItemMeta(bowMeta);

                    playerInv.setHelmet(diamondHelmet);
                    playerInv.setChestplate(diamondChestplate);
                    playerInv.setLeggings(diamondLeggings);
                    playerInv.setBoots(diamondBoots);
                    playerInv.setItemInOffHand(bow);

                    playerInv.setItem(0,diamondSword);
                    ItemStack arrow = new ItemStack(Material.ARROW);
                    arrow.setAmount(32);
                    playerInv.setItem(1,arrow);

                    p.updateInventory();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Chose kit &bMarksman"));
                    teleportPlayer(p);

                    makePlayerInvincible(p);
                } else if (item.getItemMeta().getPersistentDataContainer().get(KitCommand.getKey(),PersistentDataType.STRING).equals("BOMBERMAN")) {
                    p.closeInventory();
                    p.sendTitle("JEBAITED","that kit doesn't exist",80,240,80);
                }
            }
        }


    }
}
