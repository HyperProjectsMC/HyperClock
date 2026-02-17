package org.hyperprojects.hyperclock.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hyperprojects.hyperclock.manager.ConfigManager;
import org.hyperprojects.hyperclock.manager.LangManager;
import org.hyperprojects.hyperclock.util.UpdateChecker;

public class JoinListener implements Listener {

    private final UpdateChecker updateChecker;
    private final ConfigManager configManager;
    private final LangManager langManager;

    public JoinListener(UpdateChecker updateChecker, ConfigManager configManager, LangManager langManager) {
        this.updateChecker = updateChecker;
        this.configManager = configManager;
        this.langManager = langManager;
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

        String messageUpdate = langManager.getString("update-available");
        if (messageUpdate == null || messageUpdate.isEmpty()) {
            messageUpdate = "§eUpdate available! §7Version: §c{version}";
        }

        String version = updateChecker.getCurrentVersion() + " §7→ §a" + updateChecker.getLatestVersion();

        messageUpdate = messageUpdate.replace("{version}", version);

        event.getPlayer().sendMessage(messageUpdate);
    }
}
