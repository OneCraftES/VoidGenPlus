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
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

@VoidChunkGenInfo(versions = {"1.17"})
public class VoidChunkGen_1_17 extends ChunkGen {

    public VoidChunkGen_1_17(JavaPlugin paramPlugin, String paramIdentifier) {
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
    public BiomeProvider getDefaultBiomeProvider(WorldInfo worldInfo) {
        return new VoidBiomeProvider(this.chunkGenSettings.getDefaultBiome(worldInfo.getEnvironment()));
    }

    @Override
    public ChunkGenerator.ChunkData generateChunkData(World world, java.util.Random random, int chunkX, int chunkZ, ChunkGenerator.BiomeGrid paramBiomeGrid) {
        ChunkGenerator.ChunkData chunkData = createChunkData(world);
        Environment environment = world.getEnvironment();
        
        // Set biomes for the entire chunk
        if (paramBiomeGrid != null) {
            Biome biome = this.chunkGenSettings.getDefaultBiome(environment);
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = this.chunkGenSettings.getMinHeight(environment); 
                         y < this.chunkGenSettings.getMaxHeight(environment); 
                         y++) {
                        paramBiomeGrid.setBiome(x, y, z, biome);
                    }
                }
            }
        }

        // Generate layers if specified
        ChunkGenSettings.LayerSettings[] layers = this.chunkGenSettings.getLayers();
        if (layers.length > 0) {
            for (ChunkGenSettings.LayerSettings layer : layers) {
                int startY = layer.getY();
                org.bukkit.Material material = layer.getMaterial();
                for (int y = startY; y < startY + layer.getCount(); y++) {
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