package de.xtkq.voidgen.generator.interfaces;

import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ChunkGen2D extends ChunkGen {

    public ChunkGen2D(JavaPlugin plugin, String settings) {
        super(plugin, settings);
    }

    @Deprecated
    public void setBiomeGrid(BiomeGrid paramBiomeGrid, ChunkData paramChunkData) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                paramBiomeGrid.setBiome(x, z, this.chunkGenSettings.getBiome());
            }
        }
    }

    public void setBiomes2D(Environment environment, ChunkGenerator.BiomeGrid paramBiomeGrid) {
        if (paramBiomeGrid != null) {
            Biome biome = this.chunkGenSettings.getDefaultBiome(environment);
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    paramBiomeGrid.setBiome(x, z, biome);
                }
            }
        }
    }
}
