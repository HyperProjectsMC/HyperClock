package org.hyperprojects.hyperclock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.hyperprojects.hyperclock.command.StopwatchCommand;
import org.hyperprojects.hyperclock.command.TimerCommand;
import org.hyperprojects.hyperclock.listener.JoinListener;
import org.hyperprojects.hyperclock.manager.CommandManager;
import org.hyperprojects.hyperclock.manager.ConfigManager;
import org.hyperprojects.hyperclock.manager.StopwatchManager;
import org.hyperprojects.hyperclock.manager.TimerManager;
import org.hyperprojects.hyperclock.placeholder.HyperClockExpansion;
import org.hyperprojects.hyperclock.util.UpdateChecker;

@SuppressWarnings("unused")
public class HyperClock extends JavaPlugin {

    private static HyperClock instance;
    @SuppressWarnings("FieldCanBeLocal")
    private StopwatchManager stopwatchManager;
    @SuppressWarnings("FieldCanBeLocal")
    private TimerManager timerManager;

    @SuppressWarnings("FieldCanBeLocal")
    private UpdateChecker updateChecker;

    @Override
    public void onEnable() {
        instance = this;

        stopwatchManager = new StopwatchManager();
        timerManager = new TimerManager();

        CommandManager commandManager = new CommandManager(this);

        StopwatchCommand stopwatchCommand = new StopwatchCommand(stopwatchManager);
        TimerCommand timerCommand = new TimerCommand(timerManager);

        commandManager.register("stopwatch", stopwatchCommand, stopwatchCommand);
        commandManager.register("timer", timerCommand, timerCommand);

        updateChecker = new UpdateChecker(this, "HyperProjects/HyperClock");
        updateChecker.check();

        getServer().getPluginManager().registerEvents(
                new JoinListener(updateChecker),
                this
        );

        ConfigManager.getInstance().Load();

        // PlaceholderAPI hook
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HyperClockExpansion(stopwatchManager, timerManager).register();
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
