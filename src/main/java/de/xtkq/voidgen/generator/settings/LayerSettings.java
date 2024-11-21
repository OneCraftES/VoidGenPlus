package de.xtkq.voidgen.generator.settings;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Material;

public class LayerSettings {
    @SerializedName("y")
    private int y;

    @SerializedName("material")
    private Material material;

    @SerializedName("count")
    private int count = 1;

    public LayerSettings() {}

    public LayerSettings(int y, Material material, int count) {
        this.y = y;
        this.material = material;
        this.count = count;
    }

    public int getY() {
        return y;
    }

    public Material getMaterial() {
        return material;
    }

    public int getCount() {
        return count;
    }

    public static LayerSettings[] parseLayerString(String layerString) {
        if (layerString == null || layerString.isEmpty()) {
            return new LayerSettings[0];
        }

        String[] layers = layerString.split(",");
        LayerSettings[] settings = new LayerSettings[layers.length];
        int currentY = 0;

        for (int i = 0; i < layers.length; i++) {
            String layer = layers[i];
            int count = 1;
            Material material;

            // Parse layer format: [count*]minecraft:material
            if (layer.contains("*")) {
                String[] parts = layer.split("\\*");
                count = Integer.parseInt(parts[0]);
                material = parseMaterial(parts[1]);
            } else {
                material = parseMaterial(layer);
            }

            settings[i] = new LayerSettings(currentY, material, count);
            currentY += count;
        }

        return settings;
    }

    private static Material parseMaterial(String materialString) {
        // Remove 'minecraft:' prefix if present
        if (materialString.startsWith("minecraft:")) {
            materialString = materialString.substring(10);
        }
        return Material.valueOf(materialString.toUpperCase());
    }
}
