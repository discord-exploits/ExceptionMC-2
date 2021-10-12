package net.exceptionmc.framework.commandexecutor;

import net.exceptionmc.framework.FrameworkSpigot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class ShutdownCommand implements CommandExecutor {

    BukkitTask bukkitTask;
    boolean active = false;
    int sec = 10;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        if (!player.hasPermission("exception.shutdown"))
            return true;

        if (!active) {
            active = true;

            for (Player players : Bukkit.getOnlinePlayers())
                players.sendMessage("SHUTDOWN IN 10 SECONDS");

            bukkitTask = Bukkit.getScheduler().runTaskTimer(FrameworkSpigot.getInstance(), () -> {
                switch (sec) {
                    case 5:
                        for (Player players : Bukkit.getOnlinePlayers())
                            players.sendMessage("SHUTDOWN IN 5 SECONDS");

                        break;
                    case 0:
                        for (Player players : Bukkit.getOnlinePlayers())
                            players.kickPlayer("RESTART");

                        bukkitTask.cancel();
                        active = false;
                        sec = 10;
                        Bukkit.shutdown();
                        break;
                    default:
                        break;
                }

                sec--;
            }, 0L, 20L);
        } else {
            bukkitTask.cancel();
            active = false;
            sec = 10;

            for (Player players : Bukkit.getOnlinePlayers())
                players.sendMessage("SHUTDOWN ABORTED");
        }

        return false;
    }
}
