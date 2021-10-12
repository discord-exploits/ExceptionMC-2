package net.exceptionmc.gamework.events.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KitInventoryPreviewCloseEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final Player player;

    public KitInventoryPreviewCloseEvent(Player player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }
}
