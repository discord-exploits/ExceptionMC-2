package net.exceptionmc.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void on(org.bukkit.event.block.BlockBreakEvent blockBreakEvent) {

        Player player = blockBreakEvent.getPlayer();

        blockBreakEvent.setCancelled(!player.getGameMode().equals(GameMode.CREATIVE));
    }
}
