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
import java.util.UUID;

public final class KitSelector extends JavaPlugin {

    public static KitSelector instance = null;
    private static HashMap<UUID,Integer> combatList;

    public static HashMap<UUID,Integer> getCombatList() {
        return combatList;
    }

    public void registerCommands() {
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("permission").setExecutor(new PermissionCommand());

    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PreProcessCommandListener(),this);
    }

    private Kit registerNewKit(String kitName, boolean setUnbreakable) {
        return new Kit(kitName, setUnbreakable);
    }

    public void registerRunnables() {
        //CombatTag runnable
        new BukkitRunnable() {
            @Override
            public void run() {
                EntityDamageListener.onInterval();
            }
        }.runTaskTimer(this,0,20L);
    }

    public static KitSelector getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        combatList = new HashMap<>();
        registerCommands();
        registerEvents();
        registerRunnables();

        //register new kits
        registerNewKit("Tank",true);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
