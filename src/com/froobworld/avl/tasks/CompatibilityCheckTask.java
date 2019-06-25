package com.froobworld.avl.tasks;

import com.froobworld.avl.Avl;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

public class CompatibilityCheckTask implements Runnable {
    private final static String NAME = Bukkit.getServer().getClass().getPackage().getName();
    private final static String VERSION = NAME.substring(NAME.lastIndexOf('.') + 1);

    private Avl avl;
    private String[] supportedVersions = new String[]{"v1_14_R1"};

    private boolean pass;

    public CompatibilityCheckTask(Avl avl) {
        this.avl = avl;
        run();
    }

    @Override
    public void run() {
        try {
            Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.memory.CraftMemoryMapper").getMethod("toNms", Object.class).invoke(null, (Object) null);
        } catch (InvocationTargetException e) {
            pass = false;
            disablePlugin("You need to be using a more recent build of Craftbukkit/Spigot/Paper!");
            return;
        } catch (IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
            pass = false;
            disablePlugin("This plugin is not compatible with the version of Minecraft you are using.");
            return;
        }
        for(String string : supportedVersions) {
            if(string.equals(VERSION)) {
                pass = true;
                break;
            }
            pass = false;
        }
        if(!pass) {
            disablePlugin("This plugin is not compatible with the version of Minecraft you are using.");
        }
    }

    private void disablePlugin(String message) {
        Avl.logger().warning(message);
        Bukkit.getPluginManager().disablePlugin(avl);
    }

    public boolean passedCheck() {
        return pass;
    }
}
