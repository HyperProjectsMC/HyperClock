package org.hyperprojects.hyperclock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.hyperprojects.hyperclock.command.CommandManager;
import org.hyperprojects.hyperclock.command.StopwatchCommand;
import org.hyperprojects.hyperclock.command.TimerCommand;
import org.hyperprojects.hyperclock.placeholder.HyperClockExpansion;

@SuppressWarnings("unused")
public class HyperClock extends JavaPlugin {

    private static HyperClock instance;
    @SuppressWarnings("FieldCanBeLocal")
    private StopwatchManager stopwatchManager;
    private TimerManager timerManager;

    @Override
    public void onEnable() {
        instance = this;
        stopwatchManager = new StopwatchManager();

        CommandManager commandManager = new CommandManager(this);

        StopwatchCommand stopwatchCommand = new StopwatchCommand(stopwatchManager);
        TimerCommand timerCommand = new TimerCommand(timerManager);

        commandManager.register("stopwatch", stopwatchCommand, stopwatchCommand);
        commandManager.register("timer", timerCommand, timerCommand);

        // PlaceholderAPI hook
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HyperClockExpansion(stopwatchManager).register();
            getLogger().info("PlaceholderAPI detected, placeholders enabled.");
        } else {
            getLogger().info("PlaceholderAPI not detected, placeholders disabled.");
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
