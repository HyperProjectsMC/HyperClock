package org.hyperprojects.hyperclock.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.hyperprojects.hyperclock.manager.StopwatchManager;
import org.hyperprojects.hyperclock.manager.TimerManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HyperClockExpansion extends PlaceholderExpansion {

    private final StopwatchManager stopwatch;
    private final TimerManager timer;

    public HyperClockExpansion(StopwatchManager stopwatch, TimerManager timer) {
        this.stopwatch = stopwatch;
        this.timer = timer;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "hyperclock";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Timor";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        if (params.equalsIgnoreCase("stopwatch_time")) {
            return stopwatch.getFormattedTime();
        }

        if (params.equalsIgnoreCase("stopwatch_running")) {
            return String.valueOf(stopwatch.isRunning());
        }

        if (params.equalsIgnoreCase("timer_time")) {
            return String.valueOf(timer.getFormattedTime());
        }

        if (params.equalsIgnoreCase("timer_running")) {
            return String.valueOf(timer.isRunning());
        }

        if (params.equalsIgnoreCase("timer_finished")) {
            return String.valueOf(timer.isFinished());
        }

        return null;
    }
}
