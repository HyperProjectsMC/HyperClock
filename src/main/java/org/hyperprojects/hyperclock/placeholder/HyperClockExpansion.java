package org.hyperprojects.hyperclock.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.hyperprojects.hyperclock.StopwatchManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HyperClockExpansion extends PlaceholderExpansion {

    private final StopwatchManager stopwatch;

    public HyperClockExpansion(StopwatchManager stopwatch) {
        this.stopwatch = stopwatch;
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

        return null;
    }
}
