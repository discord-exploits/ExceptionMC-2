package net.exceptionmc.gamework.counter;

import net.exceptionmc.framework.FrameworkSpigot;
import net.exceptionmc.gamework.events.counter.lobby.LobbyStateCounterStartEvent;
import net.exceptionmc.gamework.events.counter.lobby.LobbyStateCounterStopEvent;
import net.exceptionmc.gamework.interfaces.GameCounter;
import net.exceptionmc.gamework.state.GameState;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public class LobbyStateCounter implements GameCounter {

    private static BukkitTask bukkitTask;
    private static Integer seconds = 60;

    public static void start() {

        call(new LobbyStateCounterStartEvent());
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(FrameworkSpigot.getInstance(), () -> {

            switch (seconds) {

                case 60:
                case 30:
                case 15:
                case 10:
                case 5:
                case 3:
                case 2:

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        player.sendMessage(FrameworkSpigot.getPrefix() +
                                new LanguageUtil().getString(player.getUniqueId().toString(), "RwzL7vJ4")
                                        .replace("$seconds", String.valueOf(seconds)));
                    }
                    break;
                case 1:

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        player.sendMessage(FrameworkSpigot.getPrefix() +
                                new LanguageUtil().getString(player.getUniqueId().toString(), "iisNw95Z")
                                        .replace("$second", String.valueOf(seconds)));
                    }
                    break;
                case 0:

                    GameState.setCurrentGameState(1);
                    bukkitTask.cancel();
                default:
                    break;
            }

            seconds--;
        }, 0L, 20L);
    }

    public static void stop() {
        if (isCounting()) {

            call(new LobbyStateCounterStopEvent());
            bukkitTask.cancel();
        }
    }

    public static boolean isCounting() {

        return bukkitTask != null;
    }

    public static Integer getSeconds() {
        if (isCounting()) {

            return seconds;
        }

        return null;
    }

    public static void setSeconds(Integer seconds) {

        LobbyStateCounter.seconds = seconds;
    }

    private static void call(Event event) {

        Bukkit.getPluginManager().callEvent(event);
    }
}
