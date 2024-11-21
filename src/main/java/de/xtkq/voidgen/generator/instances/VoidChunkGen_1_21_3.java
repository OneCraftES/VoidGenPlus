package de.xtkq.voidgen.generator.instances;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.xtkq.voidgen.generator.annotations.VoidChunkGenInfo;
import de.xtkq.voidgen.generator.interfaces.ChunkGen;
import de.xtkq.voidgen.generator.settings.ChunkGenSettings;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import java.util.*;

@VoidChunkGenInfo(versions = {"1.21.3", "1.21.4"})
public class VoidChunkGen_1_21_3 extends ChunkGen {
    private final NoiseGenerator noiseGenerator;

    public VoidChunkGen_1_21_3(JavaPlugin paramPlugin, String paramIdentifier) {
        super(paramPlugin);
        Gson gson = new Gson();
        this.noiseGenerator = new SimplexNoiseGenerator(new Random());

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
        // Posting the currently used chunkGenSettings to console.
        this.javaPlugin.getLogger().info(gson.toJson(chunkGenSettings));
    }

    @Override
    public BiomeProvider getDefaultBiomeProvider(WorldInfo worldInfo) {
        return new VoidBiomeProvider(this.chunkGenSettings.getDefaultBiome(worldInfo.getEnvironment()));
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid paramBiomeGrid) {
        ChunkData chunkData = createChunkData(world);
        Environment environment = world.getEnvironment();
        
        Biome biome = this.chunkGenSettings.getDefaultBiome(environment);
        
        // Set biomes for the entire chunk
        if (paramBiomeGrid != null) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    // Use environment-specific height limits
                    for (int y = this.chunkGenSettings.getMinHeight(environment); 
                         y < this.chunkGenSettings.getMaxHeight(environment); 
                         y++) {
                        paramBiomeGrid.setBiome(x, y, z, biome);
                    }
                }
            }
        }

        // Place bedrock if enabled
        if (this.chunkGenSettings.isBedrock()) {
            // Adjust bedrock Y position based on environment
            int bedrockY = this.chunkGenSettings.getMinHeight(environment);
            if ((0 >= chunkX * 16) && (0 < (chunkX + 1) * 16)) {
                if ((0 >= chunkZ * 16) && (0 < (chunkZ + 1) * 16)) {
                    chunkData.setBlock(0, bedrockY, 0, org.bukkit.Material.BEDROCK);
                }
            }
        }

        return chunkData;
    }

    private static class VoidBiomeProvider extends BiomeProvider {
        private final Biome biome;

        public VoidBiomeProvider(Biome paramBiome) {
            this.biome = paramBiome;
        }

        @Override
        public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
            return this.biome;
        }

        @Override
        public List<Biome> getBiomes(WorldInfo worldInfo) {
            return Collections.singletonList(this.biome);
        }
    }
}
