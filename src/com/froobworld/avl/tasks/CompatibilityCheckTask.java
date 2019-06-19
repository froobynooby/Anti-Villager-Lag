package com.froobworld.avl.tasks;

import com.froobworld.avl.Avl;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.memory.CraftMemoryMapper;

public class CompatibilityCheckTask implements Runnable {
    private Avl avl;

    public CompatibilityCheckTask(Avl avl) {
        this.avl = avl;
    }

    @Override
    public void run() {
        try {
            org.bukkit.entity.memory.MemoryKey.class.getClass();
            org.bukkit.persistence.PersistentDataHolder.class.getClass();
        } catch (NoClassDefFoundError e) {
            disablePlugin();
            return;
        }
        try {
            CraftMemoryMapper.toNms(null);
        } catch (UnsupportedOperationException e) {
            disablePlugin();
            return;
        }
    }

    private void disablePlugin() {
        Avl.logger().warning("Sorry, Anti-Villager Lag is not compatible with the version you are using.");
        Bukkit.getPluginManager().disablePlugin(avl);
    }
}
