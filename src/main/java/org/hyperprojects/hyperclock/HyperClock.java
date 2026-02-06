package org.hyperprojects.hyperclock;


import org.hyperprojects.hyperclock.command.StopwatchCommand;
import org.hyperprojects.hyperclock.placeholder.HyperClockExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@SuppressWarnings("unused")
public class HyperClock extends JavaPlugin {

    private static HyperClock instance;
    @SuppressWarnings("FieldCanBeLocal")
    private StopwatchManager stopwatchManager;

    @Override
    public void onEnable() {
        instance = this;
        stopwatchManager = new StopwatchManager();

        Objects.requireNonNull(getCommand("stopwatch")).setExecutor(new StopwatchCommand(stopwatchManager));

        // PlaceholderAPI hook
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HyperClockExpansion(stopwatchManager).register();
            getLogger().info("PlaceholderAPI detected, placeholders enabled.");
        }

        getLogger().info("HyperClock enabled successfully");
    }

    public static HyperClock getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().info("HyperClock disabled successfully");
    }
}
