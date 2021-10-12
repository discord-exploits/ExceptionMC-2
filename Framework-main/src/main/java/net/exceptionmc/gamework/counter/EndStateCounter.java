package net.exceptionmc.gamework.counter;

import net.exceptionmc.framework.FrameworkSpigot;
import net.exceptionmc.gamework.events.counter.end.EndStateCounterStartEvent;
import net.exceptionmc.gamework.events.counter.end.EndStateCounterStopEvent;
import net.exceptionmc.gamework.interfaces.GameCounter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("unused")
public class EndStateCounter implements GameCounter {

    private static BukkitTask bukkitTask;
    private static Integer integer;

    public static void start(Integer seconds) {
        call(new EndStateCounterStartEvent());

        integer = seconds;
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(FrameworkSpigot.getInstance(), () -> {

            switch (integer) {
                case 10:
                case 5:
                case 3:
                case 2:
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage("1");
                    break;
                case 1:
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendMessage("2");
                    break;
                case 0:
                    for (Player player : Bukkit.getOnlinePlayers()) {

                        player.sendMessage("3");
                        bukkitTask.cancel();
                    }
                default:
                    break;
            }

            integer--;
        }, 0L, 20L);
    }

    public static void stop() {
        if (isCounting()) {
            call(new EndStateCounterStopEvent());
            bukkitTask.cancel();
        }
    }

    public static boolean isCounting() {
        return bukkitTask != null;
    }

    public static Integer getSeconds() {
        if (isCounting())
            return integer;

        return null;
    }

    public static void setSeconds(Integer seconds) {
        integer = seconds;
    }

    private static void call(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}
