package de.xtkq.voidgen.generator.instances;

import de.xtkq.voidgen.generator.annotations.VoidChunkGenInfo;
import de.xtkq.voidgen.generator.interfaces.ChunkGen;
import de.xtkq.voidgen.generator.settings.LayerSettings;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

@VoidChunkGenInfo(versions = {"1.8.8"})
public class VoidChunkGen_1_8_8 extends ChunkGen {

    public VoidChunkGen_1_8_8(JavaPlugin plugin, String settings) {
        super(plugin, settings);
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
        LayerSettings[] layers = this.chunkGenSettings.getLayers();
        if (layers.length > 0) {
            for (LayerSettings layer : layers) {
                int startY = layer.getY();
                Material material = layer.getMaterial();
                
                // Ensure Y is within world bounds
                startY = Math.max(this.chunkGenSettings.getMinHeight(environment), startY);
                int endY = Math.min(this.chunkGenSettings.getMaxHeight(environment), startY + layer.getCount());
                
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            chunkData.setBlock(x, y, z, material);
                        }
                    }
                }
            }
        } else if (this.chunkGenSettings.isBedrock()) {
            // Place bedrock if enabled and no layers specified
            int bedrockY = this.chunkGenSettings.getMinHeight(environment);
            if ((0 >= chunkX * 16) && (0 < (chunkX + 1) * 16)) {
                if ((0 >= chunkZ * 16) && (0 < (chunkZ + 1) * 16)) {
                    chunkData.setBlock(0, bedrockY, 0, Material.BEDROCK);
                }
            }
        }

        return chunkData;
    }
}
