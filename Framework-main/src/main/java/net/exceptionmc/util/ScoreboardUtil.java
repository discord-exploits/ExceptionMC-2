package net.exceptionmc.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ScoreboardUtil {

    public void sendScoreboard(Player player, String title, ArrayList<String> content) {
        Collections.reverse(content);

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("aaa", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);

        int count = 0;

        for (String current : content) {
            Team team = scoreboard.registerNewTeam("team" + count);
            team.addEntry(getScoreEntry(count));
            team.setPrefix(current);
            objective.getScore(getScoreEntry(count)).setScore(count);

            count++;
        }

        player.setScoreboard(scoreboard);
    }

    public void update(Player player, HashMap<Integer, String> content) {

        Scoreboard scoreboard = player.getScoreboard();
        for (int slot : content.keySet()) {

            Team team = scoreboard.getTeam("team" + slot);
            team.setPrefix(content.get(slot));
        }
    }

    private String getScoreEntry(Integer entry) {
        switch (entry) {
            case 0:
                return "§0";
            case 1:
                return "§1";
            case 2:
                return "§2";
            case 3:
                return "§3";
            case 4:
                return "§4";
            case 5:
                return "§5";
            case 6:
                return "§6";
            case 7:
                return "§7";
            case 8:
                return "§8";
            case 9:
                return "§9";
            case 10:
                return "§a";
            case 11:
                return "§b";
            case 12:
                return "§c";
            case 13:
                return "§d";
            case 14:
                return "§e";
            case 15:
                return "§f";
            case 16:
                return "§n";
            case 17:
                return "§m";
            default:
                return null;
        }
    }
}
