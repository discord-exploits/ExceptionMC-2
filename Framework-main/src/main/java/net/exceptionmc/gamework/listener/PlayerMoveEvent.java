package net.exceptionmc.gamework.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMoveEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerMoveEvent playerMoveEvent) {
        Player player = playerMoveEvent.getPlayer();

        if (InventoryClickEvent.inventorySortPlayers.contains(player)
                || InventoryClickEvent.previewPlayers.contains(player))
            player.teleport(player.getLocation());
    }
}
