package net.exceptionmc.framework.listener.spigot;

import net.exceptionmc.util.OnlineTimeUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@SuppressWarnings("unused")
public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerQuitEvent playerQuitEvent) {

        OnlineTimeUtil.stopOnlineTimeCounter(playerQuitEvent.getPlayer().getUniqueId().toString(), true);
    }
}
