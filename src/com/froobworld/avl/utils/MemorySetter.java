package com.froobworld.avl.utils;

import com.froobworld.avl.Avl;
import com.froobworld.avl.data.VillagerMemoryData;
import com.froobworld.avl.data.VillagerMemoryDataType;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;

public class MemorySetter {

    public static void setMissingMemories(Avl avl, Villager villager) {
        Location homeLocation = null;
        Location workLocation = null;
        Location meetingLocation = null;
        if(villager.getMemory(MemoryKey.HOME) == null) {
            villager.setMemory(MemoryKey.HOME, new Location(villager.getWorld(), 0, 0 , 0));
            homeLocation = new Location(villager.getWorld(), 0, 0 , 0);
        }
        if(villager.getMemory(MemoryKey.JOB_SITE) == null) {
            villager.setMemory(MemoryKey.JOB_SITE, new Location(villager.getWorld(), 0, 0 , 0));
            workLocation = new Location(villager.getWorld(), 0, 0 , 0);
        }
        if(villager.getMemory(MemoryKey.MEETING_POINT) == null) {
            villager.setMemory(MemoryKey.MEETING_POINT, new Location(villager.getWorld(), 0, 0 , 0));
            meetingLocation = new Location(villager.getWorld(), 0, 0 , 0);
        }
        villager.getPersistentDataContainer().set(new NamespacedKey(avl, "memoryData"), new VillagerMemoryDataType(), new VillagerMemoryData(homeLocation, workLocation, meetingLocation));
    }

    public static void unsetMissingMemories(Avl avl, Villager villager) {
        VillagerMemoryData memoryData = villager.getPersistentDataContainer().get(new NamespacedKey(avl, "memoryData"), new VillagerMemoryDataType());
        if(memoryData != null) {
            if(memoryData.getHomeLocation() != null) {
                Location current = villager.getMemory(MemoryKey.HOME);
                if(current == null || current.lengthSquared() == 0) {
                    villager.setMemory(MemoryKey.HOME, null);
                }
            }
            if(memoryData.getWorkLocation() != null) {
                Location current = villager.getMemory(MemoryKey.JOB_SITE);
                if(current == null || current.lengthSquared() == 0) {
                    villager.setMemory(MemoryKey.JOB_SITE, null);
                }
            }
            if(memoryData.getMeetingLocation() != null) {
                Location current = villager.getMemory(MemoryKey.MEETING_POINT);
                if(current == null || current.lengthSquared() == 0) {
                    villager.setMemory(MemoryKey.MEETING_POINT, null);
                }
            }
            villager.getPersistentDataContainer().remove(new NamespacedKey(avl, "memoryData"));
        }
    }

    public static boolean isMissingMemories(Villager villager){
        return villager.getMemory(MemoryKey.HOME) == null || villager.getMemory(MemoryKey.JOB_SITE) == null || villager.getMemory(MemoryKey.MEETING_POINT) == null;
    }
}
