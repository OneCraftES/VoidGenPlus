package de.xtkq.voidgen.events;

import de.xtkq.voidgen.utils.UpdateUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerJoinListener(JavaPlugin paramPlugin) {
        this.plugin = paramPlugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (UpdateUtils.isUpdateAvailable()) {
            if (player.isOp()) {
                // Delay is still good practice to ensure the client is ready to receive
                // messages after joining
                this.plugin.getServer().getScheduler().runTaskLater(this.plugin,
                        () -> player.sendMessage(this.getUpdateMessage()), 60L);
            }
        }
    }

    private String getUpdateMessage() {
        String updateMessage = String.format("&e%s &7v.%s is available here: &e%s&r", this.plugin.getName(),
                UpdateUtils.getLatestRelease(), UpdateUtils.getLatestReleaseURL());
        return ChatColor.translateAlternateColorCodes('&', updateMessage);
    }

    public void terminate() {
        PlayerJoinEvent.getHandlerList().unregister(this.plugin);
    }
}
