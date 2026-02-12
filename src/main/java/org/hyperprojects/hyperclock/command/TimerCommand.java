package org.hyperprojects.hyperclock.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.hyperprojects.hyperclock.manager.TimerManager;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class TimerCommand implements CommandExecutor, TabCompleter {

    private final TimerManager timer;

    public TimerCommand(TimerManager timer) {
        this.timer = timer;
    }

    private long parseTimeToMillis(String input) {
        input = input.toLowerCase();

        try {
            if (input.endsWith("h")) {
                return Long.parseLong(input.replace("h", "")) * 60 * 60 * 1000;
            }
            if (input.endsWith("m")) {
                return Long.parseLong(input.replace("m", "")) * 60 * 1000;
            }
            if (input.endsWith("s")) {
                return Long.parseLong(input.replace("s", "")) * 1000;
            }

            return Long.parseLong(input) * 1000;

        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {

        if (!sender.hasPermission("hyperclock.timer.use")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§7Usage: /timer <start|stop|set|status>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                timer.start();
                sender.sendMessage("§aTimer started.");
                break;

            case "stop":
                timer.stop();
                sender.sendMessage("§cTimer stopped.");
                break;

            case "set":
                if (args.length < 2) {
                    sender.sendMessage("§cUsage: /timer set <time>");
                    sender.sendMessage("§7Examples: 30s, 5m, 1h");
                    break;
                }

                long millis = parseTimeToMillis(args[1]);
                if (millis <= 0) {
                    sender.sendMessage("§cInvalid time format.");
                    break;
                }

                timer.set(millis);
                sender.sendMessage("§eTimer set to §f" + timer.getFormattedTime());
                break;

            case "status":
                sender.sendMessage("§bTimer time: §f" + timer.getFormattedTime());
                sender.sendMessage("§7Running: " + timer.isRunning());
                sender.sendMessage("§7Finished: " + timer.isFinished());
                break;

            default:
                sender.sendMessage("§7Usage: /timer <start|stop|set|status>");
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

        if (!sender.hasPermission("hyperclock.timer.use")) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            return Stream.of("start", "stop", "set", "status")
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            return Stream.of("10s", "30s", "1m", "5m", "10m")
                    .filter(s -> s.startsWith(args[1].toLowerCase()))
                    .toList();
        }

        return Collections.emptyList();
    }
}
