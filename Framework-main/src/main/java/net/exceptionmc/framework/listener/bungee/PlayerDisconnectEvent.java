package net.exceptionmc.framework.listener.bungee;

import net.exceptionmc.util.OnlineTimeUtil;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@SuppressWarnings("unused")
public class PlayerDisconnectEvent implements Listener {

    @EventHandler
    public void on(net.md_5.bungee.api.event.PlayerDisconnectEvent playerDisconnectEvent) {

        OnlineTimeUtil.stopOnlineTimeCounter(playerDisconnectEvent.getPlayer().getUniqueId().toString(), false);
    }
}
