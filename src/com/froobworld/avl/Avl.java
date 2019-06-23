package com.froobworld.avl;

import com.froobworld.avl.metrics.Metrics;
import com.froobworld.avl.tasks.CompatibilityCheckTask;
import com.froobworld.avl.tasks.MainTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Avl extends JavaPlugin {
    private AvlConfiguration config;

    public void onEnable() {
        if(new CompatibilityCheckTask(this).passedCheck()) {
            config = new AvlConfiguration(this, AvlConfiguration.CONFIG_CURRENT_VERSION, "config.yml");
            config.loadFromFile();

            addTasks();

            new Metrics(this);
            logger().info("Successfully enabled.");
        }
    }

    private void addTasks() {
        new MainTask(this);
    }

    public AvlConfiguration getAvlConfig() {
        return config;
    }

    public static Logger logger() {
        return Avl.getPlugin(Avl.class).getLogger();
    }
}
