package com.froobworld.villageroptimiser.tasks;

import com.froobworld.villageroptimiser.VillagerOptimiser;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;

public class NormalActivityTask implements Runnable {
    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (entity instanceof Villager) {
                    if (!VillagerOptimiser.getInstance().getActivityUtils().wouldBeBadActivity((Villager) entity) && !VillagerOptimiser.getInstance().getActivityUtils().isScheduleNormal((Villager) entity)) {
                        VillagerOptimiser.getInstance().getActivityUtils().setScheduleNormal((Villager) entity);
                        VillagerOptimiser.getInstance().getActivityUtils().setActivitiesNormal((Villager) entity);
                    }
                    VillagerOptimiser.getInstance().getActivityUtils().clearPlaceholderMemories((Villager) entity);
                }
            }
        }
    }
}
