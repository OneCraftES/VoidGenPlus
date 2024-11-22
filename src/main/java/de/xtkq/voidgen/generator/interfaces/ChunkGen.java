package de.xtkq.voidgen.generator.interfaces;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.xtkq.voidgen.generator.settings.ChunkGenSettings;
import de.xtkq.voidgen.generator.settings.LayerSettings;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public abstract class ChunkGen extends ChunkGenerator {

    protected final JavaPlugin plugin;
    protected ChunkGenSettings chunkGenSettings;

    public ChunkGen(JavaPlugin plugin, String settings) {
        this.plugin = plugin;
        this.chunkGenSettings = new ChunkGenSettings(plugin);
        
        if (StringUtils.isNotBlank(settings)) {
            try {
                // Set the layer string directly instead of trying to parse as JSON
                this.chunkGenSettings.setLayerString(settings);
            } catch (Exception e) {
                this.plugin.getLogger().warning("Failed to parse generator settings: " + e.getMessage());
                this.plugin.getLogger().info("Using default values:");
            }
        } else {
            this.plugin.getLogger().info("Generator settings have not been set. Using default values:");
        }
        
        // Log only relevant settings instead of the entire object
        this.plugin.getLogger().info("Current generator settings:");
        this.plugin.getLogger().info("- Bedrock: " + this.chunkGenSettings.isBedrock());
        this.plugin.getLogger().info("- Caves: " + this.chunkGenSettings.isCaves());
        this.plugin.getLogger().info("- Decoration: " + this.chunkGenSettings.isDecoration());
        this.plugin.getLogger().info("- Mobs: " + this.chunkGenSettings.isMobs());
        this.plugin.getLogger().info("- Structures: " + this.chunkGenSettings.isStructures());
        this.plugin.getLogger().info("- Noise: " + this.chunkGenSettings.isNoise());
        this.plugin.getLogger().info("- Surface: " + this.chunkGenSettings.isSurface());
        this.plugin.getLogger().info("- Default Biome: " + this.chunkGenSettings.getBiome().name());
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        Environment environment = world.getEnvironment();
        int y;
        
        // Get the first layer Y position or use default spawn height
        LayerSettings[] layers = this.chunkGenSettings.getLayers();
        if (layers != null && layers.length > 0) {
            y = layers[0].getY();
        } else {
            // Default spawn heights for different environments
            switch (environment) {
                case NETHER:
                    y = 127; // On top of the bedrock layer
                    break;
                case THE_END:
                    y = 100; // Standard end height
                    break;
                default:
                    y = 64; // Standard overworld height
            }
        }
        
        return new Location(world, 0, y, 0);
    }

    @Override
    public boolean shouldGenerateCaves() {
        return this.chunkGenSettings.isCaves();
    }

    @Override
    public boolean shouldGenerateDecorations() {
        return this.chunkGenSettings.isDecoration();
    }

    @Override
    public boolean shouldGenerateMobs() {
        return this.chunkGenSettings.isMobs();
    }

    @Override
    public boolean shouldGenerateStructures() {
        return this.chunkGenSettings.isStructures();
    }

    @Override
    public boolean shouldGenerateNoise() {
        return this.chunkGenSettings.isNoise();
    }

    @Override
    public boolean shouldGenerateSurface() {
        return this.chunkGenSettings.isSurface();
    }

    @Override
    public boolean shouldGenerateBedrock() {
        return this.chunkGenSettings.isBedrock();
    }

    protected void placeBedrock(ChunkData paramChunkData, int paramChunkX, int paramChunkZ) {
        // Bedrock block position
        int x = 0, y = 64, z = 0;

        if ((x >= paramChunkX * 16) && (x < (paramChunkX + 1) * 16)) {
            if ((z >= paramChunkZ * 16) && (z < (paramChunkZ + 1) * 16)) {
                paramChunkData.setBlock(x, y, z, Material.BEDROCK);
            }
        }
    }

    @Override
    public void generateBedrock(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // Bedrock block position
        final int x = 0, y = 64, z = 0;

        if ((x >= chunkX * 16) && (x < (chunkX + 1) * 16)) {
            if ((z >= chunkZ * 16) && (z < (chunkZ + 1) * 16)) {
                chunkData.setBlock(x, y, z, Material.BEDROCK);
            }
        }
    }
}
