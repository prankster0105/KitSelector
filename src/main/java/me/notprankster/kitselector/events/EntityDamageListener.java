package me.notprankster.kitselector.events;

import me.notprankster.kitselector.KitSelector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class EntityDamageListener implements Listener {
    private static KitSelector plugin;
    static HashMap<UUID,Integer> combatTagList;
    public EntityDamageListener(KitSelector plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        combatTagList = plugin.getCombatList();
    }


    final int combatTagTime = KitSelector.getInstance().getConfig().getInt("combatTagTime");

    public static void onInterval() {
        HashMap<UUID,Integer> tempCombatList = new HashMap<>();
        for (UUID id : combatTagList.keySet())
        {
            int timer = combatTagList.get(id) - 1;

            if (timer > 0) {
                tempCombatList.put(id,timer);
            } else {


                try {
                    tempCombatList.remove(id);
                } catch(Exception e) {
                    KitSelector.getInstance().getLogger().severe("Something went wrong while trying to obtain player using UUID from tempCombatList: " + e);
                }

                Bukkit.getPlayer(id).sendMessage(ChatColor.GREEN + "You are no longer in combat.");
            }
        }

        combatTagList = tempCombatList;
        plugin.combatList = combatTagList;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Player damager = null;
        Player plr = null;
        Set<Player> invinciblePlayers = InventoryClickListener.invinciblePlayers;
        if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();

        } else {
            if (event.getDamager() instanceof Arrow  && ((Arrow) event.getDamager()).getShooter() instanceof Player) {
                damager = (Player) ((Arrow) event.getDamager()).getShooter();
            }
        }

        if (damager != null) {
            if (invinciblePlayers.contains(damager)) {
                event.setCancelled(true);
            }
        }

        if (event.getEntity() instanceof Player) {
            plr = (Player) event.getEntity();

            if (invinciblePlayers.contains(plr)) {
                event.setCancelled(true);
            } else {

            if (damager != null) {

                combatTagList.put(plr.getUniqueId(),combatTagTime);
                combatTagList.put(damager.getUniqueId(),combatTagTime);
            }

               /* if (!Tagged.containsKey(plr.getName())) {
                    Tagged.put(plr.getName(),System.currentTimeMillis());
                    plr.sendMessage(ChatColor.GREEN + "You are now in combat.");

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Long delay = KitSelector.getInstance().getConfig().getInt("combatTagTime") * 20L;
                            if (plr.isOnline()) {
                                if (Tagged.containsKey(plr.getName())) {
                                    if (System.currentTimeMillis() - Tagged.get(plr.getName()) > delay * 1000) {
                                        plr.sendMessage(ChatColor.GREEN + "You are no longer in combat.");
                                        Tagged.remove(plr.getName());
                                    }
                                }
                            }

                        }
                    }.runTaskLater(KitSelector.getInstance(),KitSelector.getInstance().getConfig().getInt("combatTagTime")*20L);
                } else {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            long delay = KitSelector.getInstance().getConfig().getInt("combatTagTime");
                            if (plr.isOnline()) {
                                if (Tagged.containsKey(plr.getName())) {
                                    if (System.currentTimeMillis() - Tagged.get(plr.getName()) > delay * 1000) {
                                        plr.sendMessage(ChatColor.GREEN + "You are no longer in combat.");
                                        Tagged.remove(plr.getName());
                                    }
                                }
                            }

                        }
                    }.runTaskLater(KitSelector.getInstance(),KitSelector.getInstance().getConfig().getInt("combatTagTime")*20L);
                } */
            }
        }
    }
}
