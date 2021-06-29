package com.froobworld.villageroptimiser;

import com.froobworld.villageroptimiser.metrics.Metrics;
import com.froobworld.villageroptimiser.tasks.CompatibilityCheckTask;
import com.froobworld.villageroptimiser.tasks.MainTask;
import com.froobworld.villageroptimiser.utils.ActivityUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Logger;

//TODO: Make everything horrible not horrible, i.e. rewrite the plugin
public class VillagerOptimiser extends JavaPlugin {
    private static VillagerOptimiser instance;
    private BukkitTask task;
    private VillagerOptimiserConfiguration config;
    private ActivityUtils activityUtils;

    @Override
    public void onEnable() {
        instance = this;

        if (new CompatibilityCheckTask(this).passedCheck()) {
            config = new VillagerOptimiserConfiguration(this, VillagerOptimiserConfiguration.CONFIG_CURRENT_VERSION, "config.yml");
            config.loadFromFile();

            activityUtils = new ActivityUtils();

            startTasks();

            new Metrics(this);
            logger().info("Successfully enabled.");
        }
    }

    private void startTasks() {
        if (this.task != null) {
            this.task.cancel();
        }

        long ticksPerAllowSearch = config.getLong("ticks-per-allow-search",
                600L /* Default value, if config does not contain the entry */);
        this.task = Bukkit.getScheduler().runTaskTimer(this, new MainTask(this), 0L,
                ticksPerAllowSearch <= 0 ? 600 : ticksPerAllowSearch);
    }

    public VillagerOptimiserConfiguration getAvlConfig() {
        return config;
    }

    public static Logger logger() {
        return JavaPlugin.getPlugin(VillagerOptimiser.class).getLogger();
    }

    public ActivityUtils getActivityUtils() {
        return activityUtils;
    }

    public static VillagerOptimiser getInstance() {
        return instance;
    }
}
