package net.exceptionmc.gamework.listener;

import com.google.common.collect.Lists;
import net.exceptionmc.framework.FrameworkSpigot;
import net.exceptionmc.gamework.events.kits.KitInventoryPreviewCloseEvent;
import net.exceptionmc.gamework.events.kits.KitInventorySortEvent;
import net.exceptionmc.gamework.kits.KitManager;
import net.exceptionmc.gamework.voting.MapVoting;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

@SuppressWarnings("all")
public class InventoryClickEvent implements Listener {

    public static final ArrayList<Player> inventorySortPlayers = Lists.newArrayList();
    public static final ArrayList<Player> previewPlayers = Lists.newArrayList();

    @EventHandler
    public void on(org.bukkit.event.inventory.InventoryClickEvent inventoryClickEvent) {

        Player player = (Player) inventoryClickEvent.getWhoClicked();

        if (inventorySortPlayers.contains(player)) {
            if (inventoryClickEvent.getSlot() < 9)
                inventoryClickEvent.setCancelled(false);
            else
                inventoryClickEvent.setCancelled(true);
        }

        if (inventoryClickEvent.getCurrentItem() != null) {
            if (inventoryClickEvent.getCurrentItem().getItemMeta() != null) {
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName() != null) {
                    if (inventoryClickEvent.getInventory().getTitle().equals(
                            new LanguageUtil().getString(player.getUniqueId().toString(), "lg30D3S6"))) {

                        inventoryClickEvent.setCancelled(true);

                        if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().startsWith("§8»")) {
                            String mapName = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
                                    .replace("§8» " + "§" + new LanguageUtil()
                                            .getColor(player.getUniqueId().toString()), "");

                            MapVoting.addVote(player, mapName);

                            player.getOpenInventory().close();
                            player.sendMessage(FrameworkSpigot.getPrefix() +
                                    new LanguageUtil().getString(player.getUniqueId().toString(), "3eJDwnRU")
                                            .replace("$map", mapName)
                                            .replace("$votes", String.valueOf(MapVoting.getVotes(mapName))));
                        }
                    } else if (inventoryClickEvent.getInventory().getTitle().equals(
                            new LanguageUtil().getString(player.getUniqueId().toString(), "QwMxLVpv"))) {

                        inventoryClickEvent.setCancelled(true);

                        String kitName = inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
                                .replace("§8» " + "§" +
                                        new LanguageUtil().getColor(player.getUniqueId().toString()), "");

                        if (new KitManager().getSelectedKit(player).equals(kitName)) {
                            new KitManager().openSortInventory(player, kitName);

                            inventorySortPlayers.add(player);
                        } else if (!new KitManager().hasBought(player, kitName)) {
                            if (new KitManager().kitExists(kitName))
                                new KitManager().openKitBuyInventory(player, kitName);
                        } else {
                            new KitManager().selectKit(player, kitName);

                            player.sendMessage(FrameworkSpigot.getPrefix() +
                                    new LanguageUtil().getString(player.getUniqueId().toString(), "PUZUL1Mw")
                                            .replace("$kit", kitName));

                            player.getOpenInventory().close();
                        }
                    } else {
                        inventoryClickEvent.setCancelled(true);

                        if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals(
                                new LanguageUtil().getString(player.getUniqueId().toString(), "QbT2fsr1"))) {

                            String kitName = inventoryClickEvent.getClickedInventory().getTitle().replace("§" +
                                    new LanguageUtil().getColor(player.getUniqueId().toString()) + "§l", "");

                            new KitManager().buyKit(player, kitName);

                            player.getOpenInventory().close();
                        } else if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals(
                                new LanguageUtil().getString(player.getUniqueId().toString(), "EDAtw7pp"))) {

                            String kitName = inventoryClickEvent.getClickedInventory().getTitle().replace("§" +
                                    new LanguageUtil().getColor(player.getUniqueId().toString()) + "§l", "");

                            new KitManager().openPreviewInventory(player, kitName);

                            previewPlayers.add(player);
                        }

                        if (inventorySortPlayers.contains(player)) {
                            if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals(
                                    new LanguageUtil().getString(player.getUniqueId().toString(), "vsTjFmvY"))) {

                                String kitName = new KitManager().getSelectedKit(player);
                                new KitManager().setKitSort(player, kitName);

                                inventorySortPlayers.remove(player);

                                player.getOpenInventory().close();

                                KitInventorySortEvent kitInventorySortEvent = new KitInventorySortEvent(player);
                                Bukkit.getPluginManager().callEvent(kitInventorySortEvent);
                            } else if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals(
                                    new LanguageUtil().getString(player.getUniqueId().toString(), "3QbjRsPA"))) {

                                inventorySortPlayers.remove(player);

                                player.getOpenInventory().close();

                                KitInventorySortEvent kitInventorySortEvent = new KitInventorySortEvent(player);
                                Bukkit.getPluginManager().callEvent(kitInventorySortEvent);
                            }
                        } else if (previewPlayers.contains(player)) {
                            if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals(
                                    new LanguageUtil().getString(player.getUniqueId().toString(), "R9Ri2CL5"))) {

                                previewPlayers.remove(player);

                                KitInventoryPreviewCloseEvent kitInventoryPreviewCloseEvent =
                                        new KitInventoryPreviewCloseEvent(player);
                                Bukkit.getPluginManager().callEvent(kitInventoryPreviewCloseEvent);

                                player.getOpenInventory().close();
                            }
                        }
                    }
                }
            }
        }
    }
}
