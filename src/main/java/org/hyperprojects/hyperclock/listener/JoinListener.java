package org.hyperprojects.hyperclock.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hyperprojects.hyperclock.manager.ConfigManager;
import org.hyperprojects.hyperclock.util.UpdateChecker;

public class JoinListener implements Listener {

    private final UpdateChecker updateChecker;
    private final ConfigManager configManager;

    public JoinListener(UpdateChecker updateChecker, ConfigManager configManager) {
        this.updateChecker = updateChecker;
        this.configManager = configManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!updateChecker.isUpdateAvailable()) return;

        String notifyType = configManager.getString("updates.notify-type");

        switch (notifyType) {
            case "EVERYONE":
                break;
            case "OPS":
                if (!event.getPlayer().isOp()) return;
                break;
            case "PERMISSION":
                if (!event.getPlayer().hasPermission("hyperclock.admin")) return;
                break;
            default:
                return;
        }

        event.getPlayer().sendMessage(
                "§e[HyperClock] Update available! §7New version: §c" + updateChecker.getCurrentVersion() + " §7→ §a" + updateChecker.getLatestVersion()
        );
    }
}
