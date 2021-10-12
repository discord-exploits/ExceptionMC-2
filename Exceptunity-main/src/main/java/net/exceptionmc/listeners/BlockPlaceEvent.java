package net.exceptionmc.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockPlaceEvent implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void on(org.bukkit.event.block.BlockPlaceEvent blockPlaceEvent) {

        Player player = blockPlaceEvent.getPlayer();

        blockPlaceEvent.setCancelled(!player.getGameMode().equals(GameMode.CREATIVE));
    }
}
