package net.exceptionmc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDropItemEvent implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void on(org.bukkit.event.player.PlayerDropItemEvent playerDropItemEvent) {

        playerDropItemEvent.setCancelled(true);
    }
}
