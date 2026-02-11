package org.hyperprojects.hyperclock.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hyperprojects.hyperclock.util.UpdateChecker;

public class JoinListener implements Listener {

    private final UpdateChecker updateChecker;

    public JoinListener(UpdateChecker updateChecker) {
        this.updateChecker = updateChecker;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission("hyperclock.admin")) return;
        if (!updateChecker.isUpdateAvailable()) return;

        event.getPlayer().sendMessage(
                "§e[HyperClock] Update available! §7New version: §c" + updateChecker.getCurrentVersion() + " §7→ §a" + updateChecker.getLatestVersion()
        );
    }
}
