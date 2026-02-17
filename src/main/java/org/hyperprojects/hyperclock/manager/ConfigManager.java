package org.hyperprojects.hyperclock.manager;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.hyperprojects.hyperclock.HyperClock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.jar.JarFile;

public class ConfigManager {

    private final JavaPlugin plugin;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private File file;
    private YamlConfiguration config;

    @SuppressWarnings("all")
    public void load() {
        File dataFolder = HyperClock.getInstance().getDataFolder();

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
            plugin.getLogger().info("No config folder detected, created one right now.");
        }

        try {
            // Pak de JAR van de plugin
            File jarFile = new File(
                    HyperClock.getInstance()
                            .getClass()
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            );

            // There will be a lot of dutch commands right here because I still don't seem to understand everything I created but it works!
            try (JarFile jar = new JarFile(jarFile)) {
                // Zoek naar alle bestanden die in de config-map zitten (nu direct in de root van de plugin)
                jar.stream()
                        .filter(entry -> entry.getName().startsWith("config/")) // Alle bestanden uit de 'config' map worden gefiltert.
                        .forEach(entry -> {
                            if (entry.isDirectory()) return; // overslaan van directories

                            String entryName = entry.getName();  // volledige padnaam binnen de JAR
                            // We plaatsen de bestanden direct in de hoofdmap van de plugin
                            File outFile = new File(dataFolder, entryName.replace("config/", ""));  // Verwijder 'config/' uit de padnaam

                            // Maak de directory aan als die nog niet bestaat
                            if (!outFile.getParentFile().exists()) {
                                outFile.getParentFile().mkdirs();
                            }

                            // Kopieer het bestand naar de doelfolder als het nog niet bestaat
                            if (!outFile.exists()) {
                                try (InputStream in = jar.getInputStream(entry);
                                     FileOutputStream out = new FileOutputStream(outFile)) {

                                    byte[] buffer = new byte[1024];
                                    int length;
                                    while ((length = in.read(buffer)) > 0) {
                                        out.write(buffer, 0, length);
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
            }

        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().warning("An error occurred while creating the configuration files.");
        }

        // Laad daarna config.yml uit plugin root
        file = new File(dataFolder, "config.yml");
        config = YamlConfiguration.loadConfiguration(file);
        config.options().parseComments(true);
    }

    @SuppressWarnings("all")
    public void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            plugin.getLogger().warning("An error occurred while trying to save the configuration files");
        }
    }

    public boolean getBoolean(String path) {
        Object value = config.get(path);
        if (value == null) {
            return false;
        }
        return (Boolean) value;
    }

    public String getString(String path) {
        Object value = config.get(path);
        if (value == null) {
            return null;
        }
        return (String) value;
    }
}
