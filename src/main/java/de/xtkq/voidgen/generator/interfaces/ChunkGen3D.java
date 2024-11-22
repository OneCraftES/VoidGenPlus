package de.xtkq.voidgen.generator.interfaces;

import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ChunkGen3D extends ChunkGen {

    public ChunkGen3D(JavaPlugin plugin, String settings) {
        super(plugin, settings);
    }

    public void setBiomes3D(Environment environment, ChunkGenerator.BiomeGrid paramBiomeGrid) {
        if (paramBiomeGrid != null) {
            Biome biome = this.chunkGenSettings.getDefaultBiome(environment);
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 256; y++) {
                    for (int z = 0; z < 16; z++) {
                        paramBiomeGrid.setBiome(x, y, z, biome);
                    }
                }
            }
        }
    }
}
