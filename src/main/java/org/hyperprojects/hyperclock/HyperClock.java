package org.hyperprojects.hyperclock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.hyperprojects.hyperclock.command.StopwatchCommand;
import org.hyperprojects.hyperclock.command.TimerCommand;
import org.hyperprojects.hyperclock.listener.JoinListener;
import org.hyperprojects.hyperclock.manager.*;
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
    @SuppressWarnings("FieldCanBeLocal")
    private ConfigManager configManager;
    @SuppressWarnings("FieldCanBeLocal")
    private LangManager langManager;

    @Override
    public void onEnable() {
        instance = this;

        stopwatchManager = new StopwatchManager();
        timerManager = new TimerManager();

        configManager = new ConfigManager(this);
        configManager.load();

        langManager = new LangManager(this, configManager);
        langManager.load();

        CommandManager commandManager = new CommandManager(this);

        StopwatchCommand stopwatchCommand = new StopwatchCommand(stopwatchManager, configManager, langManager);
        TimerCommand timerCommand = new TimerCommand(timerManager, configManager, langManager);

        commandManager.register("stopwatch", stopwatchCommand, stopwatchCommand);
        commandManager.register("timer", timerCommand, timerCommand);

        if (configManager.getBoolean("updates.auto-update-check")) {
            updateChecker = new UpdateChecker(this, "DiscordSRV/DiscordSRV");
            updateChecker.check();
        }

        if (configManager.getBoolean("timer.enabled")) {
            if (configManager.getString("timer.default-time") != null) {
                boolean result = timerCommand.setDefaultTime();

                if (result) {
                    getLogger().info("Successfully set the timer to the default time.");
                } else {
                    getLogger().warning("Failed to set the timer to the default time.");
                }
            }
        }

        getServer().getPluginManager().registerEvents(
                new JoinListener(updateChecker, configManager),
                this
        );

        // Run on full startup!
        new BukkitRunnable() {
            @Override
            public void run() {
                if (configManager.getBoolean("stopwatch.enabled") && configManager.getBoolean("stopwatch.auto-start")) {
                    stopwatchManager.autoStart();
                    getLogger().info("The stopwatch has started because auto start was enabled.");
                }
                if (configManager.getBoolean("timer.enabled") && configManager.getBoolean("timer.auto-start")) {
                    String defaultTime = configManager.getString("timer.default-time");

                    if (defaultTime == null || defaultTime.isEmpty()) {
                        getLogger().warning("Unable to start the timer because there was no default time found.");
                    }

                    timerManager.autoStart();
                    getLogger().info("The timer has started because auto start was enabled.");
                }
            }
        }.runTask(this);

        // PlaceholderAPI hook
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HyperClockExpansion(stopwatchManager, timerManager).register();
            getLogger().info("PlaceholderAPI detected, placeholders enabled.");
        } else {
            getLogger().info("PlaceholderAPI not detected, placeholders disabled.");
        }

        getLogger().info("HyperClock enabled successfully");
    }

    @Override
    public void onDisable() {
        getLogger().info("HyperClock disabled successfully");
    }

    public static HyperClock getInstance() {
        return instance;
    }
}
