package de.xtkq.voidgen;

import de.xtkq.voidgen.events.EventManager;
import de.xtkq.voidgen.generator.annotations.VoidChunkGenInfo;
import de.xtkq.voidgen.generator.instances.*;
import de.xtkq.voidgen.settings.ConfigManager;
import de.xtkq.voidgen.utils.UpdateUtils;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class VoidGen extends JavaPlugin {

    private ChunkGenVersion chunkGenVersion;
    private EventManager eventManager;

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        // Parse environment from id if provided
        World.Environment environment = World.Environment.NORMAL;
        String settings = "";
        
        if (id != null && !id.isEmpty()) {
            String[] parts = id.split(";", 2);
            if (parts.length > 0) {
                try {
                    environment = World.Environment.valueOf(parts[0].toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Invalid environment, use settings as is
                    settings = id;
                }
                if (parts.length > 1) {
                    settings = parts[1];
                }
            }
        }
        
        // Create generator with environment type
        ChunkGenerator generator;
        switch (this.chunkGenVersion) {
            case VERSION_1_8:
                generator = new VoidChunkGen_1_8_8(this, settings);
                break;
            case VERSION_1_15:
                generator = new VoidChunkGen_1_15(this, settings);
                break;
            case VERSION_1_17:
                generator = new VoidChunkGen_1_17(this, settings);
                break;
            case VERSION_1_17_1:
                generator = new VoidChunkGen_1_17_1(this, settings);
                break;
            case VERSION_1_21_3:
            default:
                generator = new VoidChunkGen_1_21_3(this, settings);
                break;
        }
        
        // Log the environment type being used
        getLogger().info("Creating void world '" + worldName + "' with environment: " + environment);
        return generator;
    }

    @Override
    public void onEnable() {
        this.chunkGenVersion = this.setupVoidChunkGen();
        this.getLogger().info("Using VoidChunkGen: " + this.chunkGenVersion.name());

        ConfigManager configManager = new ConfigManager(this);
        UpdateUtils updateUtils = new UpdateUtils(this);
        this.eventManager = new EventManager(this);

        if (configManager.getConfiguration().getCheckForUpdates()) {
            updateUtils.checkForUpdates();
            this.eventManager.initialize();
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

        // Default to latest version for unknown versions
        return ChunkGenVersion.VERSION_1_21_3;
    }
}
