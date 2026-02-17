package org.hyperprojects.hyperclock.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class UpdateChecker {

    private final JavaPlugin plugin;
    private final String repo;

    private boolean updateAvailable = false;
    private String currentVersion = "";
    private String latestVersion = "";

    public UpdateChecker(JavaPlugin plugin, String repo) {
        this.plugin = plugin;
        this.repo = repo;
    }

    public void check() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = URI.create(
                        "https://api.github.com/repos/" + repo + "/releases/latest"
                ).toURL();

                currentVersion = plugin.getPluginMeta().getVersion();
                latestVersion = getString(url);

                if (latestVersion.startsWith("v")) {
                    latestVersion = latestVersion.substring(1);
                }

                if (!latestVersion.equalsIgnoreCase(currentVersion)) {
                    updateAvailable = true;

                    plugin.getLogger().warning("Update available: " + currentVersion + " → " + latestVersion);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Couldn't check if there was a update.");
            }
        });
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    private static String getString(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "MinecraftPlugin");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        reader.close();

        return json.toString().split("\"tag_name\":\"")[1].split("\"")[0];
    }
}
