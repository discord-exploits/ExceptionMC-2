package net.exceptionmc.gamework.events.gamestate;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("unused")
public class GameStateChangeEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final Integer gameState;

    public GameStateChangeEvent(Integer gameState) {
        this.gameState = gameState;
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

    public Integer getGameState() {
        return gameState;
    }
}
