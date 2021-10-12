package net.exceptionmc.commandexecutors;

import net.exceptionmc.util.LocationUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] strings) {
        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if (new LocationUtil().exists("spawn"))
                player.teleport(new LocationUtil().getLocation("spawn"));
        }
        return false;
    }
}
