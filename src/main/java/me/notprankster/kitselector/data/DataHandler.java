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
        this.kitInfoFile = new File(KitSelector.getInstance().getDataFolder(), "kitInfo.yml");
        if (!kitInfoFile.exists()) {
            try {
                this.kitInfoFile.getParentFile().mkdirs();
                this.kitInfoFile.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
        this.kitInfo = YamlConfiguration.loadConfiguration(this.kitInfoFile);
    }

    private File kitInfoFile;
    private FileConfiguration kitInfo;

    public FileConfiguration getKitInfo() {
        return kitInfo;
    }
    
    public void saveKitInfo() {
        try {
            this.kitInfo.save(kitInfoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
