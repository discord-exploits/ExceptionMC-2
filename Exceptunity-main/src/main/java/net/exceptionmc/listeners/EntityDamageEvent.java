package net.exceptionmc.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageEvent implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void on(org.bukkit.event.entity.EntityDamageEvent entityDamageEvent) {

        Entity entity = entityDamageEvent.getEntity();

        if (entity instanceof Player)
            entityDamageEvent.setCancelled(true);
    }
}
