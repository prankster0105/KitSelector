package me.notprankster.kitselector.events;

import me.notprankster.kitselector.KitSelector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class EntityDamageListener implements Listener {
    //static HashMap<String, Long> Tagged = new HashMap<String,Long>();


   /* public static HashMap<String, Long> getTagged() {
        return Tagged;
    }*/

    final int combatTagTime = KitSelector.getInstance().getConfig().getInt("combatTagTime");
    static private HashMap<UUID,Integer> combatList = KitSelector.getCombatList();

    public static void onInterval() {
        HashMap<UUID,Integer> tempCombatList = new HashMap<>();
        for (UUID id : combatList.keySet())
        {
            int timer = combatList.get(id) - 1;

            if (timer > 0) {
                tempCombatList.put(id,timer);
            } else {
                tempCombatList.remove(id);

                try {

                } catch(Exception e) {
                    KitSelector.getInstance().getLogger().severe("Something went wrong while trying to obtain player using UUID from tempCombatList: " + e);
                }

                Bukkit.getPlayer(id).sendMessage(ChatColor.GREEN + "You are no longer in combat.");
            }
        }

        combatList = tempCombatList;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        Set<Player> invinciblePlayers = InventoryClickListener.invinciblePlayers;
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();

            if (invinciblePlayers.contains(damager)) {
                event.setCancelled(true);
            }
        }

        if (event.getEntity() instanceof Player) {
            final Player plr = (Player) event.getEntity();

            if (invinciblePlayers.contains(plr)) {
                event.setCancelled(true);
            } else {

            if (event.getDamager() instanceof Player) {
                final Player damager = (Player) event.getDamager();

                combatList.put(plr.getUniqueId(),combatTagTime);
                combatList.put(plr.getUniqueId(),combatTagTime);
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
