package org.hyperprojects.hyperclock.manager;

import org.bukkit.plugin.java.JavaPlugin;
import org.hyperprojects.hyperclock.HyperClock;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LangManager {

    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private JSONObject languageData;

    public LangManager(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @SuppressWarnings("all")
    public void load() {
        String lang = configManager.getString("lang");
        if (lang == null || lang.isEmpty()) {
            lang = "en";
        }

        File langFile = new File(plugin.getDataFolder(), "lang/" + lang + ".json");

        if (!langFile.exists()) {
            plugin.getLogger().warning("Language file for '" + lang + "' not found. Falling back to 'en.json'.");
            langFile = new File(HyperClock.getInstance().getDataFolder(), "lang/en.json");
        }

        try (FileReader reader = new FileReader(langFile)) {
            int fileLength = (int) langFile.length();
            char[] buffer = new char[fileLength];

            int charactersRead = reader.read(buffer);

            if (charactersRead != -1) {
                String content = new String(buffer, 0, charactersRead);
                languageData = new JSONObject(content);
            } else {
                plugin.getLogger().severe("No content read from language file: " + langFile.getName());
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load language file: " + langFile.getName());
            e.printStackTrace();
        }

    }

    public String getString(String key) {
        if (languageData == null) {
            return null;
        }
        return configManager.getString("general.prefix") + languageData.optString(key, key);
    }
}
