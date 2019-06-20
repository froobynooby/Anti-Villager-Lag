package com.froobworld.avl.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class ActivityUtils {
    private final static String NAME = Bukkit.getServer().getClass().getPackage().getName();
    private final static String VERSION = NAME.substring(NAME.lastIndexOf('.') + 1);

    private static Method VILLAGER_GET_HANDLE_METHOD;
    private static Method VILLAGER_GET_BEHAVIOUR_CONTROLLER_METHOD;
    private static Method BEHAVIOUR_CONTROLLER_GET_SCHEDULE_METHOD;
    private static Method CURRENT_ACTIVITY_METHOD;

    private static Field ACTIVITIES_FIELD;

    private static Object ACTIVITY_CORE;
    static {
        try {
            VILLAGER_GET_HANDLE_METHOD =  Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftVillager").getMethod("getHandle");
            VILLAGER_GET_BEHAVIOUR_CONTROLLER_METHOD = Class.forName("net.minecraft.server." + VERSION + ".EntityLiving").getMethod("getBehaviorController");
            BEHAVIOUR_CONTROLLER_GET_SCHEDULE_METHOD = Class.forName("net.minecraft.server." + VERSION + ".BehaviorController").getMethod("getSchedule");
            CURRENT_ACTIVITY_METHOD = Class.forName("net.minecraft.server." + VERSION + ".Schedule").getMethod("a", int.class);

            ACTIVITIES_FIELD = Class.forName("net.minecraft.server." + VERSION + ".BehaviorController").getDeclaredField("g");
            ACTIVITIES_FIELD.setAccessible(true);

            ACTIVITY_CORE = Class.forName("net.minecraft.server." + VERSION + ".Activity").getField("CORE").get(null);
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setActivitiesNormal(Villager villager) {
        try {
            ((Set) ACTIVITIES_FIELD.get(VILLAGER_GET_BEHAVIOUR_CONTROLLER_METHOD.invoke(VILLAGER_GET_HANDLE_METHOD.invoke(villager)))).clear();
            ((Set) ACTIVITIES_FIELD.get(VILLAGER_GET_BEHAVIOUR_CONTROLLER_METHOD.invoke(VILLAGER_GET_HANDLE_METHOD.invoke(villager)))).add(ACTIVITY_CORE);
            Object currentActivity = CURRENT_ACTIVITY_METHOD.invoke(BEHAVIOUR_CONTROLLER_GET_SCHEDULE_METHOD.invoke(VILLAGER_GET_BEHAVIOUR_CONTROLLER_METHOD.invoke(VILLAGER_GET_HANDLE_METHOD.invoke(villager))), (int) villager.getWorld().getTime());
            ((Set) ACTIVITIES_FIELD.get(VILLAGER_GET_BEHAVIOUR_CONTROLLER_METHOD.invoke(VILLAGER_GET_HANDLE_METHOD.invoke(villager)))).add(currentActivity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void setActivitiesEmpty(Villager villager) {
        try {
            ((Set) ACTIVITIES_FIELD.get(VILLAGER_GET_BEHAVIOUR_CONTROLLER_METHOD.invoke(VILLAGER_GET_HANDLE_METHOD.invoke(villager)))).clear();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
