package org.hyperprojects.hyperclock.command;

import org.bukkit.command.TabCompleter;
import org.hyperprojects.hyperclock.manager.ConfigManager;
import org.hyperprojects.hyperclock.manager.LangManager;
import org.hyperprojects.hyperclock.manager.StopwatchManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class StopwatchCommand implements CommandExecutor, TabCompleter {

    private final StopwatchManager stopwatch;
    private final ConfigManager configManager;
    private final LangManager langManager;

    public StopwatchCommand(StopwatchManager stopwatch, ConfigManager configManager, LangManager langManager) {
        this.stopwatch = stopwatch;
        this.configManager = configManager;
        this.langManager = langManager;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {

        if (!configManager.getBoolean("stopwatch.enabled")) {
            String messageDisabled = langManager.getString("stopwatch-disabled");
            if (messageDisabled == null || messageDisabled.isEmpty()) {
                messageDisabled = "§cThe stopwatch is disabled in the config.";
            }

            sender.sendMessage(messageDisabled);
            return true;
        }

        if (!sender.hasPermission("hyperclock.stopwatch.use")) {
            String messagePerm = langManager.getString("no-permission");
            if (messagePerm == null || messagePerm.isEmpty()) {
                messagePerm = "§cYou do not have permission to use this command.";
            }

            sender.sendMessage(messagePerm);
            return true;
        }

        if (args.length == 0) {
            String messageUsage = langManager.getString("usage");
            if (messageUsage == null || messageUsage.isEmpty()) {
                messageUsage = "§7Usage: {commandUsage}";
            }

            String commandUsage = "/stopwatch <start|stop|reset|status>";

            messageUsage = messageUsage.replace("{commandUsage}", commandUsage);

            sender.sendMessage(messageUsage);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                stopwatch.start();

                String messageStart = langManager.getString("stopwatch-started");
                if (messageStart == null || messageStart.isEmpty()) {
                    messageStart = "§aStopwatch started.";
                }

                sender.sendMessage(messageStart);
                break;

            case "stop":
                stopwatch.stop();

                String messageStop = langManager.getString("stopwatch-stopped");
                if (messageStop == null || messageStop.isEmpty()) {
                    messageStop = "§eStopwatch stopped.";
                }

                sender.sendMessage(messageStop);
                break;

            case "reset":
                stopwatch.reset();

                String messageReset = langManager.getString("stopwatch-reset");
                if (messageReset == null || messageReset.isEmpty()) {
                    messageReset = "§eStopwatch reset.";
                }

                sender.sendMessage(messageReset);
                break;

            case "status":
                String messageStatus = langManager.getString("stopwatch-status");
                if (messageStatus == null || messageStatus.isEmpty()) {
                    messageStatus = "§7Status:\n§bStopwatch time: §f{time}\n§7Running: §f{status}";
                }

                String time = stopwatch.getFormattedTime();
                String status = String.valueOf(stopwatch.isRunning());

                messageStatus = messageStatus.replace("{time}", time);
                messageStatus = messageStatus.replace("{status}", status);

                sender.sendMessage(messageStatus);
                break;

            default:
                String messageUsage = langManager.getString("usage");
                if (messageUsage == null || messageUsage.isEmpty()) {
                    messageUsage = "§7Usage: {commandUsage}";
                }

                String commandUsage = "/stopwatch <start|stop|reset|status>";

                messageUsage = messageUsage.replace("{commandUsage}", commandUsage);

                sender.sendMessage(messageUsage);
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
