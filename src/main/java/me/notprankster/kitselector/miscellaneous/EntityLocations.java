package me.notprankster.kitselector.miscellaneous;


import org.bukkit.Location;

public class EntityLocations {
    static Location ffaSpawnLocation;

    public static Location getFFASpawnLocation() {
        return ffaSpawnLocation;
    }

    public static void setFFASpawnLocation(Location loc) {
        ffaSpawnLocation = loc;
    }
}
