package net.exceptionmc.gamework.state;

import net.exceptionmc.gamework.events.gamestate.GameStateChangeEvent;
import org.bukkit.Bukkit;

@SuppressWarnings("unused")
public class GameState {

    public static int lobbyState = 0, inGameState = 1, endingState = 2;
    private static int currentGameState = 0;

    public static Integer getCurrentGameState() {
        return currentGameState;
    }

    public static void setCurrentGameState(Integer newGameState) {
        currentGameState = newGameState;
        Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(newGameState));
    }
}
