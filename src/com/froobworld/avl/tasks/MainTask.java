package com.froobworld.avl.tasks;

import com.froobworld.avl.Avl;
import org.bukkit.Bukkit;

public class MainTask implements Runnable {
    private Avl avl;

    public MainTask(Avl avl) {
        this.avl = avl;
        run();
    }

    @Override
    public void run() {
        long ticksPerAllowSearch = avl.getAvlConfig().getLong("ticks-per-allow-search");
        System.out.println(ticksPerAllowSearch);
        Bukkit.getScheduler().scheduleSyncDelayedTask(avl, this, ticksPerAllowSearch <= 0 ? 600 : ticksPerAllowSearch);

        new NormalActivityTask().run();
        Bukkit.getScheduler().scheduleSyncDelayedTask(avl, new RemoveActivityTask(), 1);
    }
}
