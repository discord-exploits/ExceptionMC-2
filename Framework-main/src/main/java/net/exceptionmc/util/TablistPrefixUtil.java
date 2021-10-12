package net.exceptionmc.util;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionManagement;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class TablistPrefixUtil {

    private static final HashMap<Player, String> suffixHashMap = new HashMap<>();
    private static final HashMap<Player, String> prePrefixHashMap = new HashMap<>();

    private static final CloudNetDriver cloudNetDriver = CloudNetDriver.getInstance();

    public static IPermissionUser getIPermissionUser(UUID uuid) {
        IPermissionManagement iPermissionManagement = cloudNetDriver.getPermissionManagement();

        if (uuid != null)
            return iPermissionManagement.getUser(uuid);

        return null;
    }

    public static void registerTeams(Player player) {
        Scoreboard playerScoreboard = player.getScoreboard();

        for (Player players : Bukkit.getOnlinePlayers()) {
            Scoreboard playersScoreboard = players.getScoreboard();

            if (playersScoreboard.getTeam(getGroupSortId(player) + "?"
                    + player.getUniqueId().toString().substring(1, 8)) == null) {

                Team playersTeam = playersScoreboard.registerNewTeam(getGroupSortId(player) + "?"
                        + player.getUniqueId().toString().substring(1, 8));

                playersTeam.addEntry(player.getName());
            }

            if (player != players) {
                if (playerScoreboard.getTeam(getGroupSortId(players) + "?"
                        + players.getUniqueId().toString().substring(1, 8)) == null) {

                    Team playerTeam = playerScoreboard.registerNewTeam(getGroupSortId(players) + "?"
                            + players.getUniqueId().toString().substring(1, 8));

                    playerTeam.addEntry(players.getName());
                }
            }
        }
    }

    public static void setPrefix(Player player) {
        for (Player players : Bukkit.getOnlinePlayers())
            loadPrefix(players, player);
    }

    @SuppressWarnings("unused")
    public static void setPrePrefix(Player player, String prePrefix) {
        if (prePrefixHashMap.containsKey(player))
            prePrefixHashMap.replace(player, prePrefix);
        else
            prePrefixHashMap.put(player, prePrefix);

        for (Player players : Bukkit.getOnlinePlayers())
            loadPrefix(players, player);
    }

    public static String getGroupPrefix(Player player) {
        IPermissionManagement iPermissionManagement = cloudNetDriver.getPermissionManagement();
        IPermissionGroup playerIPermissionGroup = iPermissionManagement
                .getHighestPermissionGroup(Objects.requireNonNull(getIPermissionUser(player.getUniqueId())));

        return playerIPermissionGroup.getPrefix();
    }

    public static Integer getGroupSortId(Player player) {
        IPermissionManagement playerIPermissionManagement = cloudNetDriver.getPermissionManagement();
        IPermissionGroup playerIPermissionGroup = playerIPermissionManagement
                .getHighestPermissionGroup(Objects.requireNonNull(getIPermissionUser(player.getUniqueId())));

        return playerIPermissionGroup.getSortId();
    }

    public static void loadPrefix(Player player) {
        Scoreboard playerScoreboard = player.getScoreboard();

        for (Player players : Bukkit.getOnlinePlayers()) {
            String prePrefix = "";

            if (prePrefixHashMap.containsKey(players))
                prePrefix = prePrefixHashMap.get(players);

            Team playersTeam = playerScoreboard.getTeam(getGroupSortId(players) + "?"
                    + players.getUniqueId().toString().substring(1, 8));

            playersTeam.setPrefix(prePrefix + getGroupPrefix(players));
        }
    }

    public static void loadPrefix(Player player, Player ofPlayer) {
        Scoreboard playerScoreboard = player.getScoreboard();
        String prePrefix = "";

        if (prePrefixHashMap.containsKey(player))
            prePrefix = prePrefixHashMap.get(ofPlayer);

        Team playerTeam = playerScoreboard.getTeam(getGroupSortId(ofPlayer) + "?"
                + ofPlayer.getUniqueId().toString().substring(1, 8));

        playerTeam.setPrefix(prePrefix + getGroupPrefix(ofPlayer));
    }

    public static void setSuffix(Player player, String suffix) {
        if (suffixHashMap.containsKey(player))
            suffixHashMap.replace(player, suffix);
        else
            suffixHashMap.put(player, suffix);

        for (Player players : Bukkit.getOnlinePlayers())
            loadSuffix(players, player);
    }

    public static void loadSuffix(Player player) {
        Scoreboard playerScoreboard = player.getScoreboard();

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (suffixHashMap.containsKey(players)) {
                IPermissionManagement playersIPermissionManagement = cloudNetDriver.getPermissionManagement();

                IPermissionGroup playersIPermissionGroup = playersIPermissionManagement
                        .getHighestPermissionGroup(Objects.requireNonNull(getIPermissionUser(players.getUniqueId())));

                Team playerTeam = playerScoreboard.getTeam(playersIPermissionGroup.getSortId() + "?"
                        + players.getUniqueId().toString().substring(1, 8));

                playerTeam.setSuffix(suffixHashMap.get(players));
            }
        }
    }

    public static void loadSuffix(Player player, Player ofPlayer) {
        Scoreboard playerScoreboard = player.getScoreboard();

        if (suffixHashMap.containsKey(player)) {
            Team playerTeam = playerScoreboard.getTeam(getGroupSortId(ofPlayer) + "?"
                    + ofPlayer.getUniqueId().toString().substring(1, 8));

            playerTeam.setSuffix(suffixHashMap.get(ofPlayer));
        }
    }
}
