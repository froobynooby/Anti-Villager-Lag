package com.froobworld.avl.tasks;

import com.froobworld.avl.Avl;
import org.bukkit.Bukkit;

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
            com.froobworld.saml.events.SamlMobUnfreezeEvent.class.getClass();
        } catch (NoClassDefFoundError e) {
            pass = false;
            disablePlugin("Either this plugin is not compatible with the version of SAML you are using, or you don't have SAML.");
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
