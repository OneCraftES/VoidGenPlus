package de.xtkq.voidgen.events;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldLoadListener implements Listener {
    private final JavaPlugin plugin;

    public WorldLoadListener(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        World world = event.getWorld();
        ChunkGenerator generator = world.getGenerator();

        if (generator != null && generator.getClass().getPackage().getName().startsWith("de.xtkq.voidgen")) {
            plugin.getLogger().info("VoidGenPlus world initializing: " + world.getName());
            plugin.getDefaultWorldGenerator(world.getName(), null);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        ChunkGenerator generator = world.getGenerator();

        if (generator != null && generator.getClass().getPackage().getName().startsWith("de.xtkq.voidgen")) {
            plugin.getLogger().info("VoidGenPlus world loaded: " + world.getName());
            plugin.getDefaultWorldGenerator(world.getName(), null);
        }
    }

    public void terminate() {
        HandlerList.unregisterAll(this);
    }
}
