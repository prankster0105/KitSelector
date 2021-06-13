package me.notprankster.kitselector.data;

import me.notprankster.kitselector.KitSelector;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataHandler {

    private static DataHandler ourInstance = new DataHandler();
    public static DataHandler getInstance() {
        return ourInstance;
    }
    
    private DataHandler() {
        this.kitsFile = new File(KitSelector.getInstance().getDataFolder(), "kits.yml");
        if (!kitsFile.exists()) {
            try {
                this.kitsFile.getParentFile().mkdirs();
                this.kitsFile.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
        this.kits = YamlConfiguration.loadConfiguration(this.kitsFile);
    }

    private File kitsFile;
    private FileConfiguration kits;

    public FileConfiguration getKitInfo() {
        return kits;
    }
    
    public void saveKitInfo() {
        try {
            this.kits.save(kitsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
