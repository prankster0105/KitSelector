package me.notprankster.kitselector;


import me.notprankster.kitselector.commands.KitCommand;
import me.notprankster.kitselector.commands.PermissionCommand;
import me.notprankster.kitselector.commands.SetSpawnCommand;
import me.notprankster.kitselector.commands.SpawnCommand;
import me.notprankster.kitselector.events.EntityDamageListener;
import me.notprankster.kitselector.events.InventoryClickListener;
import me.notprankster.kitselector.events.PreProcessCommandListener;
import me.notprankster.kitselector.objects.Kit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class KitSelector extends JavaPlugin {

    public static KitSelector instance = null;
    public HashMap<UUID,Integer> combatList = new HashMap<>();
    private Set<Kit> kits = new HashSet<>();

    public HashMap<UUID,Integer> getCombatList() {
        return combatList;
    }
    public static KitSelector getInstance() {
        return instance;
    }
    public Set<Kit> getKits() {
        return kits;
    }





    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        registerCommands();
        registerEvents();

        //register new kits
        registerNewKit("Tank",true);
        registerNewKit("Pawn",true);
        registerNewKit("Ninja",true);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("KitSelector is shutting down!");
        getLogger().info("Thanks a lot for using KitSelector! Feedback is always appreciated!");
    }

    public void registerCommands() {
        new KitCommand(this);
        new SpawnCommand(this);
        new SetSpawnCommand(this);
        new PermissionCommand(this);
    }

    public void registerEvents() {
        new InventoryClickListener(this);
        new EntityDamageListener(this);
        new PreProcessCommandListener(this);

        //CombatTag runnable
        new BukkitRunnable() {
            @Override
            public void run() {
                EntityDamageListener.onInterval();
            }
        }.runTaskTimer(this,0,20L);
    }

    private void registerNewKit(String kitName, boolean setUnbreakable) {
        kits.add(new Kit(kitName,setUnbreakable));
    }
}
