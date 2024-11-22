package de.xtkq.voidgen.generator.settings;

import org.bukkit.Material;
import org.bukkit.World.Environment;

public class LayerSettings {
    private Material material;
    private int y;
    private int count;
    private Environment environment;

    public LayerSettings(int y, Material material, int count) {
        this.y = y;
        this.material = material;
        this.count = count;
        this.environment = null;
    }

    public LayerSettings(int y, Material material, int count, Environment environment) {
        this.y = y;
        this.material = material;
        this.count = count;
        this.environment = environment;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public boolean isValidForEnvironment(Environment env) {
        return this.environment == null || this.environment == env;
    }

    public int getStartY() {
        return y;
    }

    public int getEndY() {
        return y + count;
    }
}
