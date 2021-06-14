package me.notprankster.kitselector.events;

import me.notprankster.kitselector.KitSelector;
import me.notprankster.kitselector.commands.KitCommand;
import me.notprankster.kitselector.miscellaneous.EntityLocations;
import me.notprankster.kitselector.objects.Kit;
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
    private KitSelector plugin;

    public InventoryClickListener(KitSelector plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

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
                p.closeInventory();
                String namespacedKey = item.getItemMeta().getPersistentDataContainer().get(KitCommand.getKey(), PersistentDataType.STRING);
                Set<Kit> kits = this.plugin.getKits();

                clearPlayerEffects(p);

                for (Kit kit : kits) {
                    if (kit.getDisplayName().equals(namespacedKey)) {
                        //Will remove this thing in the future, just wanna make sure that the kit actually exists.
                        KitSelector.getInstance().getLogger().info(kit.getDisplayName() + " was chosen!");

                        playerInv.setHelmet(kit.getHelmet());
                        playerInv.setChestplate(kit.getChestplate());
                        playerInv.setLeggings(kit.getLeggings());
                        playerInv.setBoots(kit.getBoots());
                        playerInv.setItemInOffHand(kit.getItemInOffHand());
                        playerInv.setItem(0, kit.getItemInMainHand());;

                        for (PotionEffect potionEffect : kit.getPotionEffects()) {
                            p.addPotionEffect(potionEffect);
                        }
                    }
                }

                teleportPlayer(p);
                makePlayerInvincible(p);
           }
        }


    }
}
