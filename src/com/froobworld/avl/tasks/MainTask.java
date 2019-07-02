package com.froobworld.avl.tasks;

import org.bukkit.Bukkit;

import com.froobworld.avl.Avl;

public class MainTask implements Runnable {
    private Avl avl;
    
    private final NormalActivityTask activityTask;
    private final RemoveActivityTask removeTask;

    public MainTask(Avl avl) {
        this.avl = avl;
        
        this.activityTask = new NormalActivityTask();
        this.removeTask = new RemoveActivityTask();
        
        run();
    }

    @Override
    public void run() {
        long ticksPerAllowSearch = avl.getAvlConfig().getLong("ticks-per-allow-search");
        Bukkit.getScheduler().scheduleSyncDelayedTask(avl, this, ticksPerAllowSearch <= 0 ? 600 : ticksPerAllowSearch);

        this.activityTask.run();
        Bukkit.getScheduler().scheduleSyncDelayedTask(avl, this.removeTask, 1);
    }
}
