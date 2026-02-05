package org.hyperprojects.hyperclock.command;

import org.hyperprojects.hyperclock.StopwatchManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StopwatchCommand implements CommandExecutor {

    private final StopwatchManager stopwatch;

    public StopwatchCommand(StopwatchManager stopwatch) {
        this.stopwatch = stopwatch;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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
}
