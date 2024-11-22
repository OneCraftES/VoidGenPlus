package de.xtkq.voidgen;

import de.xtkq.voidgen.events.EventManager;
import de.xtkq.voidgen.generator.annotations.VoidChunkGenInfo;
import de.xtkq.voidgen.generator.interfaces.ChunkGen;
import de.xtkq.voidgen.generator.instances.*;
import de.xtkq.voidgen.settings.ConfigManager;
import de.xtkq.voidgen.utils.UpdateUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Random;

public final class VoidGen extends JavaPlugin {

    private ChunkGenVersion chunkGenVersion;
    private EventManager eventManager;

    @Override
    public void onEnable() {
        this.chunkGenVersion = this.setupVoidChunkGen();
        this.getLogger().info("Using VoidChunkGen: " + this.chunkGenVersion.name());

        // Wait for Multiverse-Core to load
        if (getServer().getPluginManager().getPlugin("Multiverse-Core") == null) {
            getLogger().warning("Multiverse-Core not found! Plugin may not work correctly.");
        }

        ConfigManager configManager = new ConfigManager(this);
        UpdateUtils updateUtils = new UpdateUtils(this);
        this.eventManager = new EventManager(this);

        if (configManager.getConfiguration().getCheckForUpdates()) {
            updateUtils.checkForUpdates();
            this.eventManager.initialize();
        }

        getLogger().info("VoidGenPlus generator registered and ready!");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        getLogger().info("Getting generator for world: " + worldName + " with settings: " + id);
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
