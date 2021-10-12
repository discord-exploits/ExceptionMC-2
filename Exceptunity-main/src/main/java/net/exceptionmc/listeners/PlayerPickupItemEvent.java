package net.exceptionmc.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPickupItemEvent implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void on(org.bukkit.event.player.PlayerPickupItemEvent playerPickupItemEvent) {

        Player player = playerPickupItemEvent.getPlayer();

        playerPickupItemEvent.setCancelled(!player.getGameMode().equals(GameMode.CREATIVE));
    }
}
