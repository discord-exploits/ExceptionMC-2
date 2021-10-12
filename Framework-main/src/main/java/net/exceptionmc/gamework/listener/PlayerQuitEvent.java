package net.exceptionmc.gamework.listener;

import net.exceptionmc.gamework.voting.MapVoting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerQuitEvent playerQuitEvent) {

        Player player = playerQuitEvent.getPlayer();

        MapVoting.removeVote(player);
        InventoryClickEvent.inventorySortPlayers.remove(player);
        InventoryClickEvent.previewPlayers.remove(player);
    }
}
