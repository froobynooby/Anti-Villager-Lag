package com.froobworld.avl;

import com.froobworld.avl.listeners.EventListener;
import com.froobworld.avl.tasks.CompatibilityCheckTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Avl extends JavaPlugin {
    private AvlConfiguration config;

    public void onEnable() {
        if(new CompatibilityCheckTask(this).passedCheck()) {
            config = new AvlConfiguration(this, AvlConfiguration.CONFIG_CURRENT_VERSION, "config.yml");
            Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        }
    }

    public AvlConfiguration getAvlConfig() {
        return config;
    }

    public static Logger logger() {
        return Avl.getPlugin(Avl.class).getLogger();
    }
}
