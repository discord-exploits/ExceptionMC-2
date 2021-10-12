package net.exceptionmc.util;

import com.google.common.collect.Maps;
import net.exceptionmc.framework.FrameworkSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({"unused", "deprecation"})
public class GuiUtil {

    private static final ItemStack whiteStainedGlass
            = new ItemStackUtil().getItem(Material.STAINED_GLASS_PANE, 0, 1, "§0");
    private static final ItemStack grayStainedGlass
            = new ItemStackUtil().getItem(Material.STAINED_GLASS_PANE, 7, 1, "§0");
    private static final ItemStack blueStainedGlass
            = new ItemStackUtil().getItem(Material.STAINED_GLASS_PANE, 11, 1, "§0");

    private static final HashMap<Player, ArrayList<ItemStack>> carouselPlayers = Maps.newHashMap();
    private static final HashMap<Player, Integer> carouselCurrentPageOfPlayer = Maps.newHashMap();
    private static final HashMap<Player, String> carouselTitleOfInventory = Maps.newHashMap();
    private static final HashMap<Player, Integer> carouselInventorySize = Maps.newHashMap();

    public static Inventory open3RowInventory(Player player, String title, HashMap<Integer, ItemStack> items) {

        Inventory inventory = Bukkit.createInventory(null, 9 * 3, title);
        player.openInventory(inventory);
        inventory.setItem(0, items.getOrDefault(0, grayStainedGlass));

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(1, items.getOrDefault(1, grayStainedGlass));
            inventory.setItem(9, items.getOrDefault(9, blueStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 2);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(2, items.getOrDefault(2, blueStainedGlass));
            inventory.setItem(10, items.getOrDefault(10, whiteStainedGlass));
            inventory.setItem(18, items.getOrDefault(18, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 4);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(3, items.getOrDefault(3, whiteStainedGlass));
            inventory.setItem(11, items.getOrDefault(11, grayStainedGlass));
            inventory.setItem(19, items.getOrDefault(19, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 6);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(4, items.getOrDefault(4, grayStainedGlass));
            inventory.setItem(12, items.getOrDefault(12, grayStainedGlass));
            inventory.setItem(20, items.getOrDefault(20, blueStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 8);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(5, items.getOrDefault(5, whiteStainedGlass));
            inventory.setItem(13, items.getOrDefault(13, blueStainedGlass));
            inventory.setItem(21, items.getOrDefault(21, whiteStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 10);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(6, items.getOrDefault(6, blueStainedGlass));
            inventory.setItem(14, items.getOrDefault(14, grayStainedGlass));
            inventory.setItem(22, items.getOrDefault(22, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 12);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(7, items.getOrDefault(7, grayStainedGlass));
            inventory.setItem(15, items.getOrDefault(15, grayStainedGlass));
            inventory.setItem(23, items.getOrDefault(23, whiteStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 14);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(8, items.getOrDefault(8, grayStainedGlass));
            inventory.setItem(16, items.getOrDefault(16, whiteStainedGlass));
            inventory.setItem(24, items.getOrDefault(24, blueStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 16);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(17, items.getOrDefault(17, blueStainedGlass));
            inventory.setItem(25, items.getOrDefault(25, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 18);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(26, items.getOrDefault(26, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 20);

        return inventory;
    }

    public static Inventory open5RowInventory(Player player, String title, HashMap<Integer, ItemStack> items) {

        Inventory inventory = Bukkit.createInventory(null, 9 * 5, title);
        player.openInventory(inventory);
        inventory.setItem(0, items.getOrDefault(0, grayStainedGlass));

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(1, items.getOrDefault(1, grayStainedGlass));
            inventory.setItem(9, items.getOrDefault(9, blueStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 2);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(2, items.getOrDefault(2, blueStainedGlass));
            inventory.setItem(10, items.getOrDefault(10, whiteStainedGlass));
            inventory.setItem(18, items.getOrDefault(18, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 4);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(3, items.getOrDefault(3, whiteStainedGlass));
            inventory.setItem(11, items.getOrDefault(11, grayStainedGlass));
            inventory.setItem(19, items.getOrDefault(19, grayStainedGlass));
            inventory.setItem(27, items.getOrDefault(27, blueStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 6);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(4, items.getOrDefault(4, grayStainedGlass));
            inventory.setItem(12, items.getOrDefault(12, grayStainedGlass));
            inventory.setItem(20, items.getOrDefault(20, blueStainedGlass));
            inventory.setItem(28, items.getOrDefault(28, whiteStainedGlass));
            inventory.setItem(36, items.getOrDefault(36, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 8);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(5, items.getOrDefault(5, whiteStainedGlass));
            inventory.setItem(13, items.getOrDefault(13, blueStainedGlass));
            inventory.setItem(21, items.getOrDefault(21, whiteStainedGlass));
            inventory.setItem(29, items.getOrDefault(29, grayStainedGlass));
            inventory.setItem(37, items.getOrDefault(37, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 10);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(6, items.getOrDefault(6, blueStainedGlass));
            inventory.setItem(14, items.getOrDefault(14, grayStainedGlass));
            inventory.setItem(22, items.getOrDefault(22, grayStainedGlass));
            inventory.setItem(30, items.getOrDefault(30, grayStainedGlass));
            inventory.setItem(38, items.getOrDefault(38, blueStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 12);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(7, items.getOrDefault(7, grayStainedGlass));
            inventory.setItem(15, items.getOrDefault(15, grayStainedGlass));
            inventory.setItem(23, items.getOrDefault(23, whiteStainedGlass));
            inventory.setItem(31, items.getOrDefault(31, blueStainedGlass));
            inventory.setItem(39, items.getOrDefault(39, whiteStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 14);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(8, items.getOrDefault(8, grayStainedGlass));
            inventory.setItem(16, items.getOrDefault(16, whiteStainedGlass));
            inventory.setItem(24, items.getOrDefault(24, blueStainedGlass));
            inventory.setItem(32, items.getOrDefault(32, grayStainedGlass));
            inventory.setItem(40, items.getOrDefault(40, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 16);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(17, items.getOrDefault(17, blueStainedGlass));
            inventory.setItem(25, items.getOrDefault(25, grayStainedGlass));
            inventory.setItem(33, items.getOrDefault(33, grayStainedGlass));
            inventory.setItem(41, items.getOrDefault(41, whiteStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 18);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(26, items.getOrDefault(26, grayStainedGlass));
            inventory.setItem(34, items.getOrDefault(34, whiteStainedGlass));
            inventory.setItem(42, items.getOrDefault(42, blueStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 20);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(35, items.getOrDefault(35, blueStainedGlass));
            inventory.setItem(43, items.getOrDefault(43, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 22);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            inventory.setItem(44, items.getOrDefault(44, grayStainedGlass));

            player.playSound(player.getLocation(), Sound.CLICK, 1, 0);
        }, 24);

        return inventory;
    }

    public static boolean isInCarousel(Player player) {
        return carouselPlayers.containsKey(player);
    }

    public static void addToCarousel(Player player, ArrayList<ItemStack> itemStacks, Integer page, String title, Integer inventorySize) {
        carouselPlayers.put(player, itemStacks);
        carouselCurrentPageOfPlayer.put(player, page);
        carouselTitleOfInventory.put(player, title);
        carouselInventorySize.put(player, inventorySize);
    }

    public static void removeFromCarousel(Player player) {
        carouselPlayers.remove(player);
        carouselCurrentPageOfPlayer.remove(player);
        carouselTitleOfInventory.remove(player);
        carouselInventorySize.remove(player);
    }

    public static ArrayList<ItemStack> getCarousel(Player player) {
        return carouselPlayers.get(player);
    }

    public static Integer getCurrentPage(Player player) {
        return carouselCurrentPageOfPlayer.get(player);
    }

    public static String getInventoryTitle(Player player) {
        return carouselTitleOfInventory.get(player);
    }

    public static Integer getInventorySize(Player player) {
        return carouselInventorySize.get(player);
    }

    public static void open3RowCarouselInventory(Player player, String title, ArrayList<ItemStack> itemStacks, Integer page) {
        player.getOpenInventory().close();
        addToCarousel(player, itemStacks, page, title, 3);
        HashMap<Integer, ItemStack> itemStacksOfPage = Maps.newHashMap();

        ItemStack arrowLeft = new ItemStackUtil().getPlayerSkull("MHF_arrowleft", 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "nytd8OAv"));
        ItemStack arrowRight = new ItemStackUtil().getPlayerSkull("MHF_arrowright", 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "Bvs2RlEn"));

        int itemStackCount = 0;
        int firstValidItemStack = page * 4;
        int positionOfItemStack = 10;

        for (ItemStack currentItemStack : itemStacks) {
            if (itemStackCount == firstValidItemStack || itemStackCount == firstValidItemStack + 1
                    || itemStackCount == firstValidItemStack + 2 || itemStackCount == firstValidItemStack + 3) {

                itemStacksOfPage.put(positionOfItemStack, itemStacks.get(itemStackCount));
                positionOfItemStack = positionOfItemStack + 2;
            }

            itemStackCount++;
        }

        if (3 < firstValidItemStack)
            itemStacksOfPage.put(18, arrowLeft);
        if (itemStackCount > firstValidItemStack + 4)
            itemStacksOfPage.put(26, arrowRight);

        open3RowInventory(player, title, itemStacksOfPage);
    }

    public static void open5RowCarouselInventory(Player player, String title, ArrayList<ItemStack> itemStacks, Integer page) {
        player.getOpenInventory().close();
        addToCarousel(player, itemStacks, page, title, 5);

        HashMap<Integer, ItemStack> itemStacksOfPage = Maps.newHashMap();

        ItemStack arrowLeft = new ItemStackUtil().getPlayerSkull("MHF_arrowleft", 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "nytd8OAv"));
        ItemStack arrowRight = new ItemStackUtil().getPlayerSkull("MHF_arrowright", 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "Bvs2RlEn"));

        int itemStackCount = 0;
        int firstValidItemStack = page * 11;
        int positionOfItemStack = 10;

        for (ItemStack currentItemStack : itemStacks) {
            if (itemStackCount == firstValidItemStack || itemStackCount == firstValidItemStack + 1 ||
                    itemStackCount == firstValidItemStack + 2 || itemStackCount == firstValidItemStack + 3 ||
                    itemStackCount == firstValidItemStack + 4 || itemStackCount == firstValidItemStack + 5 ||
                    itemStackCount == firstValidItemStack + 6 || itemStackCount == firstValidItemStack + 7 ||
                    itemStackCount == firstValidItemStack + 8 || itemStackCount == firstValidItemStack + 9 ||
                    itemStackCount == firstValidItemStack + 10) {

                itemStacksOfPage.put(positionOfItemStack, itemStacks.get(itemStackCount));

                if (positionOfItemStack != 16 && positionOfItemStack != 24)
                    positionOfItemStack = positionOfItemStack + 2;
                else
                    positionOfItemStack = positionOfItemStack + 4;
            }

            itemStackCount++;
        }

        if (10 < firstValidItemStack)
            itemStacksOfPage.put(36, arrowLeft);
        if (itemStackCount > firstValidItemStack + 10)
            itemStacksOfPage.put(44, arrowRight);

        open5RowInventory(player, title, itemStacksOfPage);
    }
}
