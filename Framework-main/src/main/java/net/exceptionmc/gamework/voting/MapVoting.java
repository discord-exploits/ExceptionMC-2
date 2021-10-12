package net.exceptionmc.gamework.voting;

import com.google.common.collect.Maps;
import net.exceptionmc.util.GuiUtil;
import net.exceptionmc.util.ItemStackUtil;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SuppressWarnings("unused")
public class MapVoting {

    private static HashMap<String, Integer> mapVotes = new HashMap<>();
    private static HashMap<Player, String> playerVote = new HashMap<>();
    private static ArrayList<String> votingMaps = new ArrayList<>();

    private static String oldWinnerMap = "none";

    public static void clearVoting() {
        mapVotes = new HashMap<>();
        playerVote = new HashMap<>();
        votingMaps = new ArrayList<>();

        initVoting();
    }

    public static void initVoting() {
        if (new MapManager().getAllMaps().size() > 2)
            setVotingMaps();
    }

    public static boolean alreadyVoted(Player player) {
        return playerVote.containsKey(player);
    }

    public static void removeVote(Player player) {
        if (alreadyVoted(player)) {
            mapVotes.replace(playerVote.get(player), mapVotes.get(playerVote.get(player)) - 1);
            playerVote.remove(player);
        }
    }

    public static void addVote(Player player, String mapName) {
        removeVote(player);
        mapVotes.replace(mapName, mapVotes.get(mapName) + 1);
        playerVote.put(player, mapName);
    }

    public static Integer getVotes(String mapName) {
        return mapVotes.get(mapName);
    }

    public static String getWinnerMap() {
        String winnerMap = Collections.max(mapVotes.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        oldWinnerMap = winnerMap;

        return winnerMap;
    }

    public static void setVotingMaps() {
        ArrayList<String> allMaps = new MapManager().getAllMaps();
        Collections.shuffle(allMaps);

        for (String currentMap : allMaps)
            if (votingMaps.size() < 2)
                if (allMaps.size() > 1)
                    if (!currentMap.equals(oldWinnerMap))
                        votingMaps.add(currentMap);
                    else
                        votingMaps.add(currentMap);

        Collections.shuffle(votingMaps);

        for (String mapName : votingMaps)
            mapVotes.put(mapName, 0);
    }

    public static Inventory openVotingInventory(Player player) {
        String votingMap1 = votingMaps.get(0);
        String votingMap2 = votingMaps.get(1);
        String colorCode = "§" + new LanguageUtil().getColor(player.getUniqueId().toString());

        HashMap<Integer, ItemStack> itemStackHashMap = Maps.newHashMap();

        itemStackHashMap.put(11, new ItemStackUtil().getItem(Material.EMPTY_MAP, 0, 1,
                "§8» " + colorCode + votingMap1,
                new String[]{new LanguageUtil().getString(player.getUniqueId().toString(), "L6Vdqh6Z")
                        .replace("$map", String.valueOf(getVotes(votingMap1))),
                        new LanguageUtil().getString(player.getUniqueId().toString(), "i3LYiNop")
                                .replace("$builder", new MapManager().getBuilder(votingMap1))}));

        itemStackHashMap.put(15, new ItemStackUtil().getItem(Material.EMPTY_MAP, 0, 1,
                "§8» " + colorCode + votingMap2,
                new String[]{new LanguageUtil().getString(player.getUniqueId().toString(), "L6Vdqh6Z")
                        .replace("$map", String.valueOf(getVotes(votingMap2))),
                        new LanguageUtil().getString(player.getUniqueId().toString(), "i3LYiNop")
                                .replace("$builder", new MapManager().getBuilder(votingMap2))}));

        return GuiUtil.open3RowInventory(player,
                new LanguageUtil().getString(player.getUniqueId().toString(), "lg30D3S6"), itemStackHashMap);
    }
}
