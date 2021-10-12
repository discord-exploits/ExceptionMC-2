package net.exceptionmc.framework.listener.spigot;

import net.exceptionmc.util.GuiUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryCloseEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.inventory.InventoryCloseEvent inventoryCloseEvent) {

        Player player = (Player) inventoryCloseEvent.getPlayer();
        GuiUtil.removeFromCarousel(player);
    }
}
