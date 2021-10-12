package net.exceptionmc.gamework.team;

import net.exceptionmc.gamework.property.GameProperty;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TeamManager {

    private static final ArrayList<String> teams = new ArrayList<>();
    private static final HashMap<String, Integer> amountOfPlayersPerTeam = new HashMap<>();
    private static final HashMap<Player, String> teamOfPlayers = new HashMap<>();

    public static void initTeams() {
        if (new GameProperty().getAmountOfTeams() > 0)
            teams.add("red");
        if (new GameProperty().getAmountOfTeams() > 1)
            teams.add("yellow");
        if (new GameProperty().getAmountOfTeams() > 2)
            teams.add("green");
        if (new GameProperty().getAmountOfTeams() > 3)
            teams.add("blue");
        if (new GameProperty().getAmountOfTeams() > 4)
            teams.add("purple");
        if (new GameProperty().getAmountOfTeams() > 5)
            teams.add("orange");
        if (new GameProperty().getAmountOfTeams() > 6)
            teams.add("white");
        if (new GameProperty().getAmountOfTeams() > 7)
            teams.add("gray");

        Collections.shuffle(teams);

        for (String team : teams)
            amountOfPlayersPerTeam.put(team, 0);
    }

    public static void setTeam(Player player, String team) {
        unsetTeam(player);

        if (teamOfPlayers.containsKey(player))
            teamOfPlayers.replace(player, team);
        else
            teamOfPlayers.put(player, team);

        amountOfPlayersPerTeam.replace(team, amountOfPlayersPerTeam.get(team) + 1);
    }

    public static void unsetTeam(Player player) {
        if (teamOfPlayers.containsKey(player)) {
            amountOfPlayersPerTeam.replace(teamOfPlayers.get(player),
                    amountOfPlayersPerTeam.get(teamOfPlayers.get(player)) - 1);

            teamOfPlayers.remove(player);
        }
    }

    public static void setTeamWithLowestPlayers(Player player) {
        String teamWithLowestPlayers = teams.get(0);

        for (String team : teams)
            if (amountOfPlayersPerTeam.get(team) < amountOfPlayersPerTeam.get(teamWithLowestPlayers))
                teamWithLowestPlayers = team;

        setTeam(player, teamWithLowestPlayers);
    }

    public static boolean playable() {
        ArrayList<String> arrayList = new ArrayList<>();

        for (String team : teams)
            if (amountOfPlayersPerTeam.get(team) > 0)
                arrayList.add(team);

        return arrayList.size() > 1;
    }

    public static String getTeam(Player player) {
        return teamOfPlayers.get(player);
    }

    public static Integer getAmountOfPlayersPerTeam(String team) {
        return amountOfPlayersPerTeam.get(team);
    }
}
