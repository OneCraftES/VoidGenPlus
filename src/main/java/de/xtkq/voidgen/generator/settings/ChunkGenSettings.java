package de.xtkq.voidgen.generator.settings;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ChunkGenSettings {
    @SerializedName("bedrock")
    private boolean bedrock = true;

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

    @SerializedName("layers")
    private String layerString;
    private LayerSettings[] layerSettings;

    @SerializedName("biome")
    private String biome = "PLAINS";

    @SerializedName("minHeight")
    private int minHeight = -64;

    @SerializedName("maxHeight")
    private int maxHeight = 320;

    @SerializedName("netherMinHeight")
    private int netherMinHeight = 0;

    @SerializedName("netherMaxHeight")
    private int netherMaxHeight = 256;

    @SerializedName("endMinHeight")
    private int endMinHeight = 0;

    @SerializedName("endMaxHeight")
    private int endMaxHeight = 256;

    private transient final Logger logger;
    private transient Plugin plugin;

    public ChunkGenSettings(Plugin plugin) {
        this.logger = plugin.getLogger();
        this.plugin = plugin;
    }

    public boolean isBedrock() {
        return bedrock;
    }

    public void setBedrock(boolean bedrock) {
        this.bedrock = bedrock;
    }

    public boolean isCaves() {
        return caves;
    }

    public void setCaves(boolean caves) {
        this.caves = caves;
    }

    public boolean isDecoration() {
        return decoration;
    }

    public void setDecoration(boolean decoration) {
        this.decoration = decoration;
    }

    public boolean isMobs() {
        return mobs;
    }

    public void setMobs(boolean mobs) {
        this.mobs = mobs;
    }

    public boolean isStructures() {
        return structures;
    }

    public void setStructures(boolean structures) {
        this.structures = structures;
    }

    public boolean isNoise() {
        return noise;
    }

    public void setNoise(boolean noise) {
        this.noise = noise;
    }

    public boolean isSurface() {
        return surface;
    }

    public void setSurface(boolean surface) {
        this.surface = surface;
    }

    public LayerSettings[] getLayers() {
        if (layerSettings == null || layerSettings.length == 0) {
            parseLayerString();
        }
        return layerSettings;
    }

    public void setLayerString(String layerString) {
        String oldLayerString = this.layerString;
        this.layerString = layerString;
        
        if (oldLayerString != null && !oldLayerString.equals(layerString)) {
            plugin.getLogger().warning("Generator settings have been changed for an existing world!");
            plugin.getLogger().warning("Existing chunks will keep their old generation pattern.");
            plugin.getLogger().warning("Only newly generated chunks will use the new settings.");
            plugin.getLogger().warning("This may cause visible borders between old and new chunks.");
        }
        
        this.layerSettings = null; // Force reparse
        parseLayerString();
    }

    public Biome getBiome() {
        try {
            return Biome.valueOf(biome.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Biome.PLAINS;
        }
    }

    public Biome getDefaultBiome(Environment environment) {
        try {
            switch (environment) {
                case NETHER:
                    return Biome.NETHER_WASTES;
                case THE_END:
                    return Biome.THE_END;
                default:
                    return Biome.valueOf(biome.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            return Biome.PLAINS;
        }
    }

    public void setDefaultBiome(String biome) {
        this.biome = biome;
    }

    public int getMinHeight(Environment environment) {
        switch (environment) {
            case NETHER:
                return netherMinHeight;
            case THE_END:
                return endMinHeight;
            default:
                return minHeight;
        }
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMaxHeight(Environment environment) {
        switch (environment) {
            case NETHER:
                return netherMaxHeight;
            case THE_END:
                return endMaxHeight;
            default:
                return maxHeight;
        }
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    private void parseLayerString() {
        if (layerString == null || layerString.isEmpty()) {
            layerSettings = new LayerSettings[0];
            return;
        }

        // Split into layers and options
        String[] parts = layerString.split(";");
        String layersSection = parts[0];
        int yOffset = 0;
        
        // Parse Y-offset if present
        if (parts.length > 1) {
            try {
                yOffset = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                plugin.getLogger().warning("Invalid Y-offset: " + parts[1]);
            }
        }

        // Parse biome if present
        if (parts.length > 2) {
            for (String option : parts[2].split(",")) {
                if (option.startsWith("biome=")) {
                    String biomeName = option.substring(6).toUpperCase()
                        .replace("MINECRAFT:", "")
                        .replace(":", "_");
                    this.biome = biomeName;
                }
            }
        }

        List<LayerSettings> settingsList = new ArrayList<>();
        int currentY = yOffset;

        // Split layers and process each one
        String[] layers = layersSection.split(",");

        for (String layer : layers) {
            layer = layer.trim();
            int count = 1;
            String materialString;

            // Check for multiplier
            if (layer.contains("*")) {
                String[] multiplierParts = layer.split("\\*", 2);
                try {
                    count = Integer.parseInt(multiplierParts[0].trim());
                    materialString = multiplierParts[1].trim();
                } catch (NumberFormatException e) {
                    plugin.getLogger().warning("Invalid layer multiplier: " + layer);
                    materialString = layer;
                }
            } else {
                materialString = layer;
            }

            Material material = parseMaterial(materialString);
            if (material != null) {
                settingsList.add(new LayerSettings(currentY, material, count));
                currentY += count;
            } else {
                plugin.getLogger().warning("Invalid material: " + materialString);
            }
        }

        this.layerSettings = settingsList.toArray(new LayerSettings[0]);
    }

    private Material parseMaterial(String materialString) {
        // Remove minecraft: prefix if present
        materialString = materialString.trim();
        if (materialString.startsWith("minecraft:")) {
            materialString = materialString.substring(10);
        }
        
        // Convert to Material enum format
        materialString = materialString.toUpperCase().replace(":", "_");
        
        try {
            return Material.valueOf(materialString);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid material: " + materialString);
            return null;
        }
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
