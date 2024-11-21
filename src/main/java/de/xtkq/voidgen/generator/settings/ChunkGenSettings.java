package de.xtkq.voidgen.generator.settings;

import com.google.gson.annotations.SerializedName;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;

public class ChunkGenSettings {

    @SerializedName("biome")
    private Biome biome = null;

    @SerializedName("caves")
    private boolean caves = false;

    @SerializedName("decoration")
    private boolean decoration = false;

    @SerializedName("mobs")
    private boolean mobs = false;

    @SerializedName("structures")
    private boolean structures = false;

    @SerializedName("noise")
    private boolean noise = false;

    @SerializedName("surface")
    private boolean surface = false;

    @SerializedName("bedrock")
    private boolean bedrock = false;

    @SerializedName("minHeight")
    private Integer minHeight = null;

    @SerializedName("maxHeight")
    private Integer maxHeight = null;

    @SerializedName("netherMinHeight")
    private Integer netherMinHeight = null;

    @SerializedName("netherMaxHeight")
    private Integer netherMaxHeight = null;

    @SerializedName("endMinHeight")
    private Integer endMinHeight = null;

    @SerializedName("endMaxHeight")
    private Integer endMaxHeight = null;

    public Biome getBiome() {
        return this.biome;
    }

    public Biome getDefaultBiome(Environment environment) {
        if (this.biome != null) {
            return this.biome;
        }
        
        // Return appropriate default biomes based on environment
        switch (environment) {
            case NETHER:
                return Biome.NETHER_WASTES;
            case THE_END:
                return Biome.THE_END;
            case NORMAL:
            default:
                return Biome.PLAINS;
        }
    }

    public int getMinHeight(Environment environment) {
        switch (environment) {
            case NETHER:
                return netherMinHeight != null ? netherMinHeight : -64;
            case THE_END:
                return endMinHeight != null ? endMinHeight : -64;
            case NORMAL:
            default:
                return minHeight != null ? minHeight : -64;
        }
    }

    public int getMaxHeight(Environment environment) {
        switch (environment) {
            case NETHER:
                return netherMaxHeight != null ? netherMaxHeight : 320;
            case THE_END:
                return endMaxHeight != null ? endMaxHeight : 320;
            case NORMAL:
            default:
                return maxHeight != null ? maxHeight : 320;
        }
    }

    public boolean isCaves() {
        return this.caves;
    }

    public boolean isDecoration() {
        return this.decoration;
    }

    public boolean isMobs() {
        return this.mobs;
    }

    public boolean isStructures() {
        return this.structures;
    }

    public boolean isNoise() {
        return this.noise;
    }

    public boolean isSurface() {
        return this.surface;
    }

    public boolean isBedrock() {
        return this.bedrock;
    }
}
