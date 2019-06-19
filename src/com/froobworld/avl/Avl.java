package com.froobworld.avl;

import com.froobworld.avl.listeners.EventListener;
import com.froobworld.avl.tasks.CompatibilityCheckTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Avl extends JavaPlugin {

    public void onEnable() {
        new CompatibilityCheckTask(this).run();
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
    }

    public static Logger logger() {
        return Avl.getPlugin(Avl.class).getLogger();
    }
}
