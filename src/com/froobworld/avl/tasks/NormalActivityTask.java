package com.froobworld.avl.tasks;

import com.froobworld.avl.utils.ActivityUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;

public class NormalActivityTask implements Runnable {
    @Override
    public void run() {
        for(World world : Bukkit.getWorlds()) {
            for(LivingEntity entity : world.getLivingEntities()) {
                if(entity instanceof Villager) {
                    ActivityUtils.setScheduleNormal((Villager) entity);
                    ActivityUtils.setActivitiesNormal((Villager) entity);
                    ActivityUtils.clearPlaceholderMemories((Villager) entity);
                }
            }
        }
    }
}
