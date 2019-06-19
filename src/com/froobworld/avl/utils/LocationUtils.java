package com.froobworld.avl.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static Location deserialiseLocation(String string) {
        if(string == null) {
            return null;
        }
        String[] split = string.split(";");
        String worldName = split[0];
        double x = Double.valueOf(split[1]);
        double y = Double.valueOf(split[1]);
        double z = Double.valueOf(split[1]);

        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    public static String serialiseLocation(Location location) {
        if(location == null || location.getWorld() == null) {
            return null;
        }
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ();
    }
}
