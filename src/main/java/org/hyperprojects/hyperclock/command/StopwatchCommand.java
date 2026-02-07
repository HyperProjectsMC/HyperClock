package org.hyperprojects.hyperclock.command;

import org.bukkit.command.TabCompleter;
import org.hyperprojects.hyperclock.StopwatchManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class StopwatchCommand implements CommandExecutor, TabCompleter {

    private final StopwatchManager stopwatch;

    public StopwatchCommand(StopwatchManager stopwatch) {
        this.stopwatch = stopwatch;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {

        if (!sender.hasPermission("hyperclock.stopwatch.use")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§7Usage: /stopwatch <start|stop|reset|status>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                stopwatch.start();
                sender.sendMessage("§aStopwatch started.");
                break;

            case "stop":
                stopwatch.stop();
                sender.sendMessage("§cStopwatch stopped.");
                break;

            case "reset":
                stopwatch.reset();
                sender.sendMessage("§eStopwatch reset.");
                break;

            case "status":
                sender.sendMessage("§bStopwatch time: §f" + stopwatch.getFormattedTime());
                sender.sendMessage("§7Running: " + stopwatch.isRunning());
                break;

            default:
                sender.sendMessage("§7Usage: /stopwatch <start|stop|reset|status>");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(
            @NonNull CommandSender sender,
            @NonNull Command command,
            @NonNull String alias,
            @NonNull String @NonNull [] args
    ) {

        if (!sender.hasPermission("hyperclock.stopwatch.use")) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            return Stream.of("start", "stop", "reset", "status")
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        return Collections.emptyList();
    }
}
