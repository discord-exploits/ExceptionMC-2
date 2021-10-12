package net.exceptionmc.commandexecutors;

import net.exceptionmc.Exceptunity;
import net.exceptionmc.util.LanguageUtil;
import net.exceptionmc.util.LocationUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class SetCommand implements CommandExecutor {

    String usage = "§9/set §7<§9(loc)§7, §9button§7> §7<(§9spawn§7, §9gate0§7, §9gate1§7, §9stage§7) " +
            "[§9pointless§7, §9effects§7, §9music§7, §9light§7]>";

    ArrayList<String> allowedButton = new ArrayList<>(Arrays.asList("pointless", "effects", "music", "light"));
    ArrayList<String> allowedWarps = new ArrayList<>(Arrays.asList("spawn", "gate0", "gate1", "stage"));

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] strings) {
        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if (player.hasPermission("exception.admin")) {
                if (string.equalsIgnoreCase("set")) {
                    if (strings.length == 2) {
                        if (strings[0].equalsIgnoreCase("loc")) {
                            if (allowedWarps.contains(strings[1])) {

                                new LocationUtil().setLocation(strings[0], player.getLocation());

                                player.sendMessage("");
                            }
                        } else if (strings[0].equalsIgnoreCase("button")) {
                            if (allowedButton.contains(strings[1])) {

                                Exceptunity.uuidStringHashMap.remove(player.getUniqueId());
                                Exceptunity.uuidStringHashMap.put(player.getUniqueId(), strings[1]);
                                player.sendMessage("Please click the button " + strings[1]);
                            }
                        }
                    } else {

                        player.sendMessage(Exceptunity.getPrefix() +
                                new LanguageUtil().getString(player.getUniqueId().toString(), "J8BxpV7y") + usage);
                    }
                }
            }
        }

        return false;
    }
}
