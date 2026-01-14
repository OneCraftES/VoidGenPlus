package de.xtkq.voidgen;

import de.xtkq.voidgen.events.EventManager;
import de.xtkq.voidgen.generator.annotations.VoidChunkGenInfo;
import de.xtkq.voidgen.generator.interfaces.ChunkGen;
import de.xtkq.voidgen.generator.instances.*;
import de.xtkq.voidgen.settings.ConfigManager;
import de.xtkq.voidgen.settings.WorldSettings;
import de.xtkq.voidgen.utils.UpdateUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class VoidGenPlus extends JavaPlugin {

    private ChunkGenVersion chunkGenVersion;
    private EventManager eventManager;
    private WorldSettings worldSettings;

    @Override
    public void onEnable() {
        this.chunkGenVersion = this.setupVoidChunkGen();
        this.worldSettings = new WorldSettings(this);
        this.getLogger().info("Using VoidChunkGen: " + this.chunkGenVersion.name());

        // Wait for Multiverse-Core to load
        Plugin multiverseCore = getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCore == null) {
            getLogger().warning("Multiverse-Core not found! Plugin may not work correctly.");
        } else {
            // Register our worlds with Multiverse after it's loaded
            getServer().getScheduler().runTaskLater(this, () -> {
                // Force Multiverse to recognize our generator
                try {
                    Class<?> mvWorldManagerClass = Class.forName("com.onarandombox.MultiverseCore.api.MVWorldManager");
                    Class<?> mvCoreClass = Class.forName("com.onarandombox.MultiverseCore.MultiverseCore");
                    Object mvCore = multiverseCore;
                    Object worldManager = mvCoreClass.getMethod("getMVWorldManager").invoke(mvCore);

                    // Check each world
                    for (World world : getServer().getWorlds()) {
                        if (world.getGenerator() != null && world.getGenerator().getClass().getPackage().getName().startsWith("de.xtkq.voidgen")) {
                            getLogger().info("Ensuring generator settings for world: " + world.getName());
                            String settings = worldSettings.getWorldSettings(world.getName());
                            if (settings != null) {
                                getLogger().info("Restoring generator settings for world: " + world.getName());
                                
                                // Force Multiverse to update its world entry
                                Object mvWorld = mvWorldManagerClass.getMethod("getMVWorld", String.class)
                                    .invoke(worldManager, world.getName());
                                if (mvWorld != null) {
                                    // Update the generator in Multiverse's config
                                    mvWorld.getClass().getMethod("setGenerator", String.class)
                                        .invoke(mvWorld, "VoidGenPlus:" + settings);
                                    getLogger().info("Updated Multiverse generator settings for: " + world.getName());
                                }
                                
                                // Ensure our generator is set
                                getDefaultWorldGenerator(world.getName(), settings);
                            }
                        }
                    }
                    
                    // Save Multiverse's world config
                    mvCoreClass.getMethod("saveWorldConfig").invoke(mvCore);
                    getLogger().info("Saved Multiverse world configuration");
                    
                } catch (Exception e) {
                    getLogger().warning("Failed to update Multiverse configuration: " + e.getMessage());
                    e.printStackTrace();
                }
            }, 40L); // Wait 2 seconds after server start
        }

        ConfigManager configManager = new ConfigManager(this);
        UpdateUtils updateUtils = new UpdateUtils(this);
        this.eventManager = new EventManager(this);

        if (configManager.getConfiguration().getCheckForUpdates()) {
            updateUtils.checkForUpdates();
        }
        
        this.eventManager.initialize();
        getLogger().info("VoidGenPlus generator registered and ready!");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        getLogger().info("Getting generator for world: " + worldName + " with settings: " + id);
        
        // Store settings for this world if provided
        if (id != null && !id.isEmpty()) {
            worldSettings.saveWorldSettings(worldName, id);
            getLogger().info("Saved generator settings for world: " + worldName);
        } else {
            // Try to get stored settings
            id = worldSettings.getWorldSettings(worldName);
            if (id != null) {
                getLogger().info("Using stored generator settings for world: " + worldName);
            }
        }

        switch (this.chunkGenVersion) {
            case VERSION_1_8:
                return new VoidChunkGen_1_8_8(this, id);
            case VERSION_1_15:
                return new VoidChunkGen_1_15(this, id);
            case VERSION_1_17:
                return new VoidChunkGen_1_17(this, id);
            case VERSION_1_21_3:
                return new VoidChunkGen_1_21_3(this, id);
            default:
                return new VoidChunkGen_1_17_1(this, id);
        }
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
        this.eventManager.terminate();
    }

    private ChunkGenVersion setupVoidChunkGen() {
        VoidChunkGenInfo annotation;
        String bukkitVersion = Bukkit.getBukkitVersion().split("-")[0];

        annotation = VoidChunkGen_1_8_8.class.getAnnotation(VoidChunkGenInfo.class);
        if (Arrays.asList(annotation.versions()).contains(bukkitVersion)) {
            return ChunkGenVersion.VERSION_1_8;
        }

        annotation = VoidChunkGen_1_15.class.getAnnotation(VoidChunkGenInfo.class);
        if (Arrays.asList(annotation.versions()).contains(bukkitVersion)) {
            return ChunkGenVersion.VERSION_1_15;
        }

        annotation = VoidChunkGen_1_17.class.getAnnotation(VoidChunkGenInfo.class);
        if (Arrays.asList(annotation.versions()).contains(bukkitVersion)) {
            return ChunkGenVersion.VERSION_1_17;
        }

        annotation = VoidChunkGen_1_17_1.class.getAnnotation(VoidChunkGenInfo.class);
        if (Arrays.asList(annotation.versions()).contains(bukkitVersion)) {
            return ChunkGenVersion.VERSION_1_17_1;
        }

        annotation = VoidChunkGen_1_21_3.class.getAnnotation(VoidChunkGenInfo.class);
        if (Arrays.asList(annotation.versions()).contains(bukkitVersion)) {
            return ChunkGenVersion.VERSION_1_21_3;
        }

        return ChunkGenVersion.VERSION_1_17_1; // Default to 1.17.1 as fallback
    }
}
