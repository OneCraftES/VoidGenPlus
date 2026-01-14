package de.xtkq.voidgen.events;

import org.bukkit.plugin.java.JavaPlugin;

public class EventManager {

    private final JavaPlugin javaPlugin;
    private PlayerJoinListener playerJoin;
    private WorldLoadListener worldLoad;

    public EventManager(JavaPlugin paramPlugin) {
        this.javaPlugin = paramPlugin;
    }

    public void initialize() {
        this.playerJoin = new PlayerJoinListener(this.javaPlugin);
        this.worldLoad = new WorldLoadListener(this.javaPlugin);
    }

    public void terminate() {
        if (this.playerJoin != null) {
            this.playerJoin.terminate();
        }
        if (this.worldLoad != null) {
            this.worldLoad.terminate();
        }
    }
}
