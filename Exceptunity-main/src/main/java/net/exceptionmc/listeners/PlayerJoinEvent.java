package net.exceptionmc.listeners;

import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionManagement;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import net.exceptionmc.Exceptunity;
import net.exceptionmc.framework.FrameworkSpigot;
import net.exceptionmc.runnables.LeatherArmorRunnable;
import net.exceptionmc.util.*;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void on(org.bukkit.event.player.PlayerJoinEvent playerJoinEvent) {

        Player player = playerJoinEvent.getPlayer();

        playerJoinEvent.setJoinMessage("");

        player.setGameMode(GameMode.ADVENTURE);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setExp(0);
        player.setLevel(new GregorianCalendar().get(Calendar.YEAR));
        player.setAllowFlight(false);
        player.setFlying(false);

        sendScoreboard(player);
        setBoots(player);

        if (new LocationUtil().exists("spawn"))
            player.teleport(new LocationUtil().getLocation("spawn"));
    }

    private void sendScoreboard(Player player) {

        UUID uuid = player.getUniqueId();

        IPermissionManagement iPermissionManagement = FrameworkSpigot.getIPermissionManagement();
        IPermissionUser iPermissionUser = FrameworkSpigot.getIPermissionUser(uuid);
        IPermissionGroup iPermissionGroup = iPermissionManagement.getHighestPermissionGroup(iPermissionUser);

        int coins = new CoinsUtil().getCoins(uuid.toString());

        long onlineTimeMillis = OnlineTimeUtil.getOnlineTimeMillis(uuid.toString());

        /* Do you like declaring a phenomenal amount of strings? I sure do! */
        String preferredColor = new LanguageUtil().getColor(uuid.toString());
        String printCoins = preferredColor + coins;
        String printRank = iPermissionGroup.getColor() + iPermissionGroup.getName();
        String printOnlineTime = OnlineTimeUtil.formatOnlineTime(uuid.toString(), onlineTimeMillis, false);
        String printClan = fetchClan(player);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("§0");
        arrayList.add("§8× §7Coins");
        arrayList.add("§8➥ §" + printCoins);
        arrayList.add("§0 ");
        arrayList.add("§8× §7Rank");
        arrayList.add("§8➥ " + printRank);
        arrayList.add("§0  ");
        arrayList.add("§8× §7OnlineTime");
        arrayList.add("§8➥ §9" + printOnlineTime);
        arrayList.add("§0  ");
        arrayList.add("§8× §7Clan");
        arrayList.add("§8➥ §9" + printClan);

        new ScoreboardUtil().sendScoreboard(player, "§8× §" + preferredColor + "§lExceptionMC §8×", arrayList);
    }

    private String fetchClan(Player player) {

        /* Do you like declaring a phenomenal amount of strings? I sure do! */
        String uuid = player.getUniqueId().toString();
        String preferredColor = new LanguageUtil().getColor(uuid);
        String clan = new ClanUtil().getClan(uuid);
        String clanTag = new ClanUtil().getClanTag(clan);
        String clanColor = new ClanUtil().getClanColor(clan);
        String printClan = "§" + preferredColor + "/";

        boolean isInClan = new ClanUtil().isInClan(uuid);

        if (isInClan)
            printClan = "§" + clanColor + clanTag;

        return printClan;
    }

    private void getLeatherBoots(Player player, Integer r, Integer g, Integer b) {

        ItemStack itemStack = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();

        leatherArmorMeta.setDisplayName("§0");
        leatherArmorMeta.setColor(Color.fromRGB(r, g, b));

        itemStack.setItemMeta(leatherArmorMeta);

        player.getInventory().setBoots(itemStack);
    }

    private void setBoots(Player player) {

        UUID uuid = player.getUniqueId();

        IPermissionManagement iPermissionManagement = FrameworkSpigot.getIPermissionManagement();
        IPermissionUser iPermissionUser = FrameworkSpigot.getIPermissionUser(uuid);
        IPermissionGroup iPermissionGroup = iPermissionManagement.getHighestPermissionGroup(iPermissionUser);

        if (iPermissionGroup.getName().equalsIgnoreCase("admin")) {
            getLeatherBoots(player, 170, 0, 0);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("manager")) {
            getLeatherBoots(player, 255, 85, 85);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("srDev")) {
            getLeatherBoots(player, 85, 85, 255);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("dev")) {
            getLeatherBoots(player, 85, 85, 255);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("srContent")) {
            getLeatherBoots(player, 85, 255, 255);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("content")) {
            getLeatherBoots(player, 85, 255, 255);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("srBuild")) {
            getLeatherBoots(player, 255, 255, 85);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("build")) {
            getLeatherBoots(player, 255, 255, 85);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("srStaff")) {
            getLeatherBoots(player, 85, 255, 85);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("staff")) {
            getLeatherBoots(player, 85, 255, 85);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("creator")) {
            getLeatherBoots(player, 255, 85, 255);
        } else if (iPermissionGroup.getName().equalsIgnoreCase("premium")) {
            getLeatherBoots(player, 255, 170, 0);
        } else {
            getLeatherBoots(player, 170, 170, 170);
        }

        // This code is monument protected and may not be removed. ;)
        if (player.getUniqueId().equals(UUID.fromString("85414edf-54cb-485f-883f-cfada401a17a")) //MeowAdorian
                || player.getUniqueId().equals(UUID.fromString("a280d456-ec11-4dbb-89d1-6d1b2c699062"))) { //Me^9ow

            ArrayList<String> stringArrayList = new ArrayList<>();
            stringArrayList.add(player.getName());

            new LeatherArmorRunnable(player, stringArrayList)
                    .runTaskTimerAsynchronously(Exceptunity.getExceptunity(), 0L, 1L);
        }
    }
}
