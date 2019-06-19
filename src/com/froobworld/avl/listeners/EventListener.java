package com.froobworld.avl.listeners;

import com.froobworld.avl.Avl;
import com.froobworld.avl.data.VillagerMemoryData;
import com.froobworld.avl.data.VillagerMemoryDataType;
import com.froobworld.saml.events.SamlMobFreezeEvent;
import com.froobworld.saml.events.SamlMobUnfreezeEvent;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EventListener implements Listener {
    private Avl avl;

    public EventListener(Avl avl) {
        this.avl = avl;
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSamlMobFreeze(SamlMobFreezeEvent event) {
        for(LivingEntity entity : event.getMobsToFreeze()) {
            if(entity instanceof Villager) {
                Location homeLocation = null;
                Location workLocation = null;
                Location meetingLocation = null;
                if(entity.getMemory(MemoryKey.HOME) == null) {
                    entity.setMemory(MemoryKey.HOME, new Location(entity.getWorld(), 0, 0 , 0));
                    homeLocation = new Location(entity.getWorld(), 0, 0 , 0);
                }
                if(entity.getMemory(MemoryKey.JOB_SITE) == null) {
                    entity.setMemory(MemoryKey.JOB_SITE, new Location(entity.getWorld(), 0, 0 , 0));
                    workLocation = new Location(entity.getWorld(), 0, 0 , 0);
                }
                if(entity.getMemory(MemoryKey.MEETING_POINT) == null) {
                    entity.setMemory(MemoryKey.MEETING_POINT, new Location(entity.getWorld(), 0, 0 , 0));
                    meetingLocation = new Location(entity.getWorld(), 0, 0 , 0);
                }
                entity.getPersistentDataContainer().set(new NamespacedKey(avl, "memoryData"), new VillagerMemoryDataType(), new VillagerMemoryData(homeLocation, workLocation, meetingLocation));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSamlMobUnfreeze(SamlMobUnfreezeEvent event) {
        for(LivingEntity entity : event.getUnfrozenMobs()) {
            if(entity instanceof Villager) {
                VillagerMemoryData memoryData = entity.getPersistentDataContainer().get(new NamespacedKey(avl, "memoryData"), new VillagerMemoryDataType());
                if(memoryData != null) {
                    if(memoryData.getHomeLocation() != null) {
                        Location current = entity.getMemory(MemoryKey.HOME);
                        if(current == null || current.lengthSquared() == 0) {
                            entity.setMemory(MemoryKey.HOME, null);
                        }
                    }
                    if(memoryData.getWorkLocation() != null) {
                        Location current = entity.getMemory(MemoryKey.JOB_SITE);
                        if(current == null || current.lengthSquared() == 0) {
                            entity.setMemory(MemoryKey.JOB_SITE, null);
                        }
                    }
                    if(memoryData.getMeetingLocation() != null) {
                        Location current = entity.getMemory(MemoryKey.MEETING_POINT);
                        if(current == null || current.lengthSquared() == 0) {
                            entity.setMemory(MemoryKey.MEETING_POINT, null);
                        }
                    }
                    entity.getPersistentDataContainer().remove(new NamespacedKey(avl, "memoryData"));
                }
            }
        }
    }

}
