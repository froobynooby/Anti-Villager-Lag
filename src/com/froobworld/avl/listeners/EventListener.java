package com.froobworld.avl;

import com.froobworld.saml.events.SamlMobFreezeEvent;
import com.froobworld.saml.events.SamlMobUnfreezeEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
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

            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSamlMobUnfreeze(SamlMobUnfreezeEvent event) {
        for(LivingEntity entity : event.getUnfrozenMobs()) {
            if(entity instanceof Villager) {

            }
        }
    }

}
