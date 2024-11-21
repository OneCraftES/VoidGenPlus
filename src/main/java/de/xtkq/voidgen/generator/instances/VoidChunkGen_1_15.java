package de.xtkq.voidgen.generator.instances;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.xtkq.voidgen.generator.annotations.VoidChunkGenInfo;
import de.xtkq.voidgen.generator.interfaces.ChunkGen;
import de.xtkq.voidgen.generator.settings.ChunkGenSettings;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

@VoidChunkGenInfo(versions = {"1.15", "1.15.1", "1.15.2", "1.16.1", "1.16.2", "1.16.3", "1.16.4", "1.16.5"})
public class VoidChunkGen_1_15 extends ChunkGen {

    public VoidChunkGen_1_15(JavaPlugin paramPlugin, String paramIdentifier) {
        super(paramPlugin);
        Gson gson = new Gson();

        if (StringUtils.isBlank(paramIdentifier)) {
            this.chunkGenSettings = new ChunkGenSettings();
            this.javaPlugin.getLogger().info("Generator settings have not been set. Using default values:");
        } else {
            try {
                this.chunkGenSettings = gson.fromJson(paramIdentifier, ChunkGenSettings.class);
            } catch (JsonSyntaxException jse) {
                this.chunkGenSettings = new ChunkGenSettings();
                this.javaPlugin.getLogger().info("Generator settings \"" + paramIdentifier + "\" syntax is not valid. Using default values:");
            }
        }
        this.javaPlugin.getLogger().info(gson.toJson(chunkGenSettings));
    }

    @Override
    public ChunkGenerator.ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, ChunkGenerator.BiomeGrid paramBiomeGrid) {
        ChunkGenerator.ChunkData chunkData = createChunkData(world);
        Environment environment = world.getEnvironment();
        
        // Set biomes for the entire chunk
        if (paramBiomeGrid != null) {
            Biome biome = this.chunkGenSettings.getDefaultBiome(environment);
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    paramBiomeGrid.setBiome(x, z, biome);
                }
            }
        }

        // Generate layers if specified
        ChunkGenSettings.LayerSettings[] layers = this.chunkGenSettings.getLayers();
        if (layers.length > 0) {
            for (ChunkGenSettings.LayerSettings layer : layers) {
                int startY = Math.max(0, layer.getY()); // Ensure Y >= 0
                Material material = layer.getMaterial();
                
                // Handle legacy material names if needed
                try {
                    if (!material.isBlock()) {
                        material = Material.STONE; // Fallback for invalid materials
                    }
                } catch (Exception e) {
                    material = Material.STONE; // Fallback for incompatible materials
                }
                
                for (int y = startY; y < Math.min(256, startY + layer.getCount()); y++) {
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            chunkData.setBlock(x, y, z, material);
                        }
                    }
                }
            }
        } else if (this.chunkGenSettings.isBedrock()) {
            // Place bedrock if enabled and no layers specified
            if ((0 >= chunkX * 16) && (0 < (chunkX + 1) * 16)) {
                if ((0 >= chunkZ * 16) && (0 < (chunkZ + 1) * 16)) {
                    chunkData.setBlock(0, 0, 0, Material.BEDROCK);
                }
            }
        }

        return chunkData;
    }
}