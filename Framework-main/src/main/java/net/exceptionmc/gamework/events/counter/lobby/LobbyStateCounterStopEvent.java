package net.exceptionmc.gamework.events.counter.lobby;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("unused")
public class LobbyStateCounterStopEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    public LobbyStateCounterStopEvent() {
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
}
