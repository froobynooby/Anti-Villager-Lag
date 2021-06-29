package com.froobworld.villageroptimiser;

import com.froobworld.villageroptimiser.utils.ConfigUpdater;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;

public class VillagerOptimiserConfiguration {
    public static final int CONFIG_CURRENT_VERSION = 1;

    private VillagerOptimiser avl;
    private int currentVersion;
    private String fileName;
    private String fileNameDashed;
    private YamlConfiguration config;
    private boolean loaded;

    public VillagerOptimiserConfiguration(VillagerOptimiser avl, int currentVersion, String fileName) {
        this.avl = avl;
        this.currentVersion = currentVersion;
        this.fileName = fileName;
        this.fileNameDashed = fileName.replaceAll("\\.", "-");
        this.loaded = false;
    }


    public synchronized void loadFromFile() {
        loaded = true;
        VillagerOptimiser.logger().info("Loading " + fileName + "...");
        File configFile = new File(avl.getDataFolder(), fileName);
        if (!configFile.exists()) {
            VillagerOptimiser.logger().info("Couldn't find existing " + fileName + ", copying default from jar...");
            try {
                avl.getDataFolder().mkdirs();
                Files.copy(avl.getResource(fileName), configFile.toPath());
            } catch (IOException e) {
                VillagerOptimiser.logger().warning("There was a problem copying the default " + fileName + ":");
                config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(avl.getResource("resources/" + fileName))));
                e.printStackTrace();
                VillagerOptimiser.logger().info("We may still be able to run...");
                return;
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        VillagerOptimiser.logger().info("Successfully loaded " + fileName + ".");

        if (config.contains("version")) {
            int version = config.getInt("version");
            if (version > currentVersion) {
                VillagerOptimiser.logger().warning("Your're using a " + fileName + " for a higher version. This may lead to some issues.");
                VillagerOptimiser.logger().info("You may wish to regenerate this file by deleting it and reloading.");
            }
            if (version < currentVersion) {
                VillagerOptimiser.logger().info("Your " + fileName + " is out of date. Will attempt to perform upgrades...");
                for (int i = version; i < currentVersion; i++) {
                    if (ConfigUpdater.update(configFile, avl.getResource("resources/" + fileNameDashed + "-updates/" + i), i)) {
                        VillagerOptimiser.logger().info("Applied changes for " + fileName + " version " + i + " to " + (i + 1) + ".");
                    } else {
                        VillagerOptimiser.logger().warning("Failed to apply changes for " + fileName + " version " + i + " to " + (i + 1) + ".");
                        return;
                    }
                }
                VillagerOptimiser.logger().info("Successfully updated " + fileName + "!");
                config = YamlConfiguration.loadConfiguration(configFile);
            }
        } else {
            VillagerOptimiser.logger().warning("Your " + fileName + " either hasn't loaded properly or is not versioned. This may lead to problems.");
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

    public long getLong(String key, long defaultValue) {
        return config.getLong(key, defaultValue);
    }

    public List<String> getStringList(String key) {
        return config.getStringList(key);
    }

}