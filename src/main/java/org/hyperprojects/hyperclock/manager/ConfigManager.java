package org.hyperprojects.hyperclock.manager;

import org.bukkit.configuration.file.YamlConfiguration;
import org.hyperprojects.hyperclock.HyperClock;

import java.io.File;

public class ConfigManager {

    private final static ConfigManager instance = new ConfigManager();

    private ConfigManager() {
    }

    private File file;
    private YamlConfiguration config;

    @SuppressWarnings("all")
    public void Load() {
        file = new File(HyperClock.getInstance().getDataFolder(), "config/config.yml");

        if (!file.exists())
            HyperClock.getInstance().saveResource("config/config.yml", false);

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public void Save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public void Set(String path, Object value) {
        config.set(path, value);

        Save();
    }

    public static ConfigManager getInstance() {
        return instance;
    }

}
