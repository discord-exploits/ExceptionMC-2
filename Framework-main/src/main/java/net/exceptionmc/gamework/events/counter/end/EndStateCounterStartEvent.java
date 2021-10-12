package net.exceptionmc.gamework.events.counter.end;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("unused")
public class EndStateCounterStartEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    public EndStateCounterStartEvent() {
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
