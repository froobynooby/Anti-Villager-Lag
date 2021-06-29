package com.froobworld.villageroptimiser.tasks;

import com.froobworld.villageroptimiser.VillagerOptimiser;
import org.bukkit.Bukkit;

public class MainTask implements Runnable {
    private VillagerOptimiser avl;

    private final NormalActivityTask activityTask;
    private final RemoveActivityTask removeTask;

    public MainTask(VillagerOptimiser avl) {
        this.avl = avl;

        this.activityTask = new NormalActivityTask();
        this.removeTask = new RemoveActivityTask();

        run();
    }

    @Override
    public void run() {
        this.activityTask.run();
        Bukkit.getScheduler().scheduleSyncDelayedTask(avl, this.removeTask, 1);
    }
}
