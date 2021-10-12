package net.exceptionmc.listeners;

import net.exceptionmc.Exceptunity;
import net.exceptionmc.music.MusicPlayer;
import net.exceptionmc.util.LocationUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import java.util.UUID;

public class PlayerInteractEvent implements Listener {

    LocationUtil locationUtil = new LocationUtil();

    @EventHandler
    @SuppressWarnings("unused")
    public void on(org.bukkit.event.player.PlayerInteractEvent playerInteractEvent) {

        Player player = playerInteractEvent.getPlayer();
        UUID playerUuid = player.getUniqueId();
        Action playerAction = playerInteractEvent.getAction();
        Block clickedBlock = playerInteractEvent.getClickedBlock();

        if (playerAction.equals(Action.RIGHT_CLICK_BLOCK) || playerAction.equals(Action.RIGHT_CLICK_AIR)) {

            if (clickedBlock.getType().equals(Material.WOOD_BUTTON)
                    || clickedBlock.getType().equals(Material.STONE_BUTTON)
                    || clickedBlock.getType().equals(Material.LEVER)) {

                if (clickedBlock.getLocation().equals(new LocationUtil().getLocation("btn.music"))) {
                    if (!Exceptunity.uuidStringHashMap.containsKey(playerUuid)) {

                        new MusicPlayer().openMusicInventory(player);
                    } else {

                        locationUtil.setBlockLocation(Exceptunity.uuidStringHashMap.get(playerUuid), clickedBlock);
                        Exceptunity.uuidStringHashMap.remove(playerUuid);
                    }
                }
            } else {

                playerInteractEvent.setCancelled(true);
            }
        }
    }
}
