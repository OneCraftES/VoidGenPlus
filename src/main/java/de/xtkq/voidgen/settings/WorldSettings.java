package de.xtkq.voidgen.settings;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WorldSettings {
    private final JavaPlugin plugin;
    private final File configFile;
    private FileConfiguration config;
    private final Map<String, String> cachedSettings = new HashMap<>();

    public WorldSettings(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "world-settings.yml");
        loadConfig();
    }

    private void loadConfig() {
        if (!configFile.exists()) {
            plugin.saveResource("world-settings.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        
        // Load cached settings
        if (config.contains("worlds")) {
            for (String worldName : config.getConfigurationSection("worlds").getKeys(false)) {
                String settings = config.getString("worlds." + worldName);
                if (settings != null) {
                    cachedSettings.put(worldName, settings);
                }
            }
        }
    }

    public void saveWorldSettings(String worldName, String settings) {
        config.set("worlds." + worldName, settings);
        cachedSettings.put(worldName, settings);
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Could not save world settings for " + worldName + ": " + e.getMessage());
        }
    }

    public String getWorldSettings(String worldName) {
        return cachedSettings.get(worldName);
    }

    public void removeWorldSettings(String worldName) {
        config.set("worlds." + worldName, null);
        cachedSettings.remove(worldName);
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Could not remove world settings for " + worldName + ": " + e.getMessage());
        }
    }
}
