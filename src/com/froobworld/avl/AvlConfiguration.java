package com.froobworld.avl;

import com.froobworld.saml.utils.ConfigUpdater;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;

public class AvlConfiguration {
    public static final int CONFIG_CURRENT_VERSION = 1;

    private Avl avl;
    private int currentVersion;
    private String fileName;
    private String fileNameDashed;
    private YamlConfiguration config;
    private boolean loaded;

    public AvlConfiguration(Avl avl, int currentVersion, String fileName) {
        this.avl = avl;
        this.currentVersion = currentVersion;
        this.fileName = fileName;
        this.fileNameDashed = fileName.replaceAll("\\.", "-");
        this.loaded = false;
    }


    public synchronized void loadFromFile() {
        loaded = true;
        Avl.logger().info("Loading " + fileName + "...");
        File configFile = new File(avl.getDataFolder(), fileName);
        if(!configFile.exists()) {
            Avl.logger().info("Couldn't find existing " + fileName + ", copying default from jar...");
            try {
                avl.getDataFolder().mkdirs();
                Files.copy(avl.getResource("resources/" + fileName), configFile.toPath());
            } catch (IOException e) {
                Avl.logger().warning("There was a problem copying the default " + fileName + ":");
                config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(avl.getResource("resources/" + fileName))));
                e.printStackTrace();
                Avl.logger().info("We may still be able to run...");
                return;
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        Avl.logger().info("Successfully loaded " + fileName + ".");

        if(config.contains("version")) {
            int version = config.getInt("version");
            if (version > currentVersion) {
                Avl.logger().warning("Your're using a " + fileName + " for a higher version. This may lead to some issues.");
                Avl.logger().info("You may wish to regenerate this file by deleting it and reloading.");
            }
            if (version < currentVersion) {
                Avl.logger().info("Your " + fileName + " is out of date. Will attempt to perform upgrades...");
                for (int i = version; i < currentVersion; i++) {
                    if (ConfigUpdater.update(configFile, avl.getResource("resources/" + fileNameDashed + "-updates/" + i), i)) {
                        Avl.logger().info("Applied changes for " + fileName + " version " + i + " to " + (i + 1) + ".");
                    } else {
                        Avl.logger().warning("Failed to apply changes for " + fileName + " version " + i + " to " + (i + 1) + ".");
                        return;
                    }
                }
                Avl.logger().info("Successfully updated " + fileName + "!");
                config = YamlConfiguration.loadConfiguration(configFile);
            }
        } else {
            Avl.logger().warning("Your " + fileName + " either hasn't loaded properly or is not versioned. This may lead to problems.");
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean keyExists(String key) {
        return config.contains(key);
    }

    public String getString(String key) {
        return config.getString(key);
    }

    public Boolean getBoolean(String key) {
        return config.getBoolean(key);
    }

    public Double getDouble(String key) {
        return config.getDouble(key);
    }

    public Long getLong(String key) {
        return config.getLong(key);
    }

    public List<String> getStringList(String key) {
        return config.getStringList(key);
    }

}
