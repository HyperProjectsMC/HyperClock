package org.hyperprojects.hyperclock.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.hyperprojects.hyperclock.manager.ConfigManager;
import org.hyperprojects.hyperclock.manager.LangManager;
import org.hyperprojects.hyperclock.manager.TimerManager;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TimerCommand implements CommandExecutor, TabCompleter {

    private final TimerManager timer;
    private final ConfigManager configManager;
    private final LangManager langManager;

    public TimerCommand(TimerManager timer, ConfigManager configManager, LangManager langManager) {
        this.timer = timer;
        this.configManager = configManager;
        this.langManager = langManager;
    }

    private long parseTimeToMillis(String input) {
        if (input == null || input.isEmpty()) {
            return -1;
        }

        input = input.toLowerCase();
        long totalMillis = 0;

        Pattern pattern = Pattern.compile("(\\d+)([hms])");
        Matcher matcher = pattern.matcher(input);

        int matches = 0;

        while (matcher.find()) {
            matches++;
            long value = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "h":
                    totalMillis += value * 60 * 60 * 1000;
                    break;
                case "m":
                    totalMillis += value * 60 * 1000;
                    break;
                case "s":
                    totalMillis += value * 1000;
                    break;
            }
        }

        if (matches == 0) {
            try {
                return Long.parseLong(input) * 1000;
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        return totalMillis;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {

        if (!configManager.getBoolean("timer.enabled")) {
            String messageDisabled = langManager.getString("timer-disabled");
            if (messageDisabled == null || messageDisabled.isEmpty()) {
                messageDisabled = "§cThe timer is disabled in the config.";
            }

            sender.sendMessage(messageDisabled);
            return true;
        }

        if (!sender.hasPermission("hyperclock.timer.use")) {
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

            String commandUsage = "/timer <start|stop|set|status>";

            messageUsage = messageUsage.replace("{commandUsage}", commandUsage);

            sender.sendMessage(messageUsage);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                timer.start();

                String messageStart = langManager.getString("timer-started");
                if (messageStart == null || messageStart.isEmpty()) {
                    messageStart = "§aTimer started.";
                }

                sender.sendMessage(messageStart);
                break;

            case "stop":
                timer.stop();
                String messageStop = langManager.getString("timer-stopped");
                if (messageStop == null || messageStop.isEmpty()) {
                    messageStop = "§eTimer stopped.";
                }

                sender.sendMessage(messageStop);
                break;

            case "set":
                if (args.length < 2) {
                    String messageUsage = langManager.getString("usage");
                    if (messageUsage == null || messageUsage.isEmpty()) {
                        messageUsage = "§7Usage: {commandUsage}";
                    }

                    String commandUsage = "/timer set <time>";

                    messageUsage = messageUsage.replace("{commandUsage}", commandUsage);

                    String messageSetExamples = langManager.getString("timer-set-examples");
                    if (messageSetExamples == null || messageSetExamples.isEmpty()) {
                        messageSetExamples = "§7Examples: 30s, 5m, 1h";
                    }

                    String messageSetUsage = messageUsage + "\n" + messageSetExamples;

                    sender.sendMessage(messageSetUsage);
                    break;
                }

                long millis = parseTimeToMillis(args[1]);
                if (millis <= 0) {
                    String messageSetInvalid = langManager.getString("timer-set-invalid");
                    if (messageSetInvalid == null || messageSetInvalid.isEmpty()) {
                        messageSetInvalid = "§cInvalid time format.";
                    }

                    String messageSetExamples = langManager.getString("timer-set-examples");
                    if (messageSetExamples == null || messageSetExamples.isEmpty()) {
                        messageSetExamples = "§7Examples: 30s, 5m, 1h";
                    }

                    String messageSetInvalidExample = messageSetInvalid + "\n" + messageSetExamples;

                    sender.sendMessage(messageSetInvalidExample);
                    break;
                }

                timer.set(millis);
                String messageSet = langManager.getString("timer-set");
                if (messageSet == null || messageSet.isEmpty()) {
                    messageSet = "§eTimer set to §f{time}";
                }

                String time = timer.getFormattedTime();

                messageSet = messageSet.replace("{time}", time);

                sender.sendMessage(messageSet);
                break;

            case "status":
                String messageStatus = langManager.getString("timer-status");
                if (messageStatus == null || messageStatus.isEmpty()) {
                    messageStatus = "§7Status:\n§bTimer time: §f{time}\n§7Running: §f{status}\n§7Finished: §f{finished}";
                }

                String time2 = timer.getFormattedTime();
                String status = String.valueOf(timer.isRunning());
                String finished = String.valueOf(timer.isFinished());

                messageStatus = messageStatus.replace("{time}", time2);
                messageStatus = messageStatus.replace("{status}", status);
                messageStatus = messageStatus.replace("{finished}", finished);

                sender.sendMessage(messageStatus);
                break;

            default:
                String messageUsage = langManager.getString("usage");
                if (messageUsage == null || messageUsage.isEmpty()) {
                    messageUsage = "§7Usage: {commandUsage}";
                }

                String commandUsage = "/timer <start|stop|set|status>";

                messageUsage = messageUsage.replace("{commandUsage}", commandUsage);

                sender.sendMessage(messageUsage);
                break;
        }

        return true;
    }

    public boolean setDefaultTime() {
        String defaultTimeString = configManager.getString("timer.default-time");
        long defaultTimeMillis = parseTimeToMillis(defaultTimeString);

        if (defaultTimeMillis > 0) {
            timer.set(defaultTimeMillis);
            return true;
        } else {
            return false;
        }
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
