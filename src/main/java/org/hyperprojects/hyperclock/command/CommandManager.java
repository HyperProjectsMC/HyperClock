package org.hyperprojects.hyperclock.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class CommandManager {

    private final JavaPlugin plugin;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(
            String name,
            CommandExecutor executor,
            TabCompleter tabCompleter
    ) {
        var command = Objects.requireNonNull(
                plugin.getCommand(name),
                "Command not found in plugin.yml: " + name
        );

        command.setExecutor(executor);
        command.setTabCompleter(tabCompleter);
    }
}
