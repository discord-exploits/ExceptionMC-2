package net.exceptionmc.gamework.kits;

import com.google.common.collect.Maps;
import net.exceptionmc.framework.FrameworkSpigot;
import net.exceptionmc.util.*;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class KitManager {

    File file;
    YamlConfiguration yamlConfiguration;
    DatabaseUtil databaseUtil;

    public KitManager() {
        String db = KitDatabase.kitDatabase, user = KitDatabase.kitUser, encPw = KitDatabase.kitEncodedPassword;

        this.file = new File(FrameworkSpigot.getInstance().getDataFolder() + "/game", "kits.yml");
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        this.databaseUtil = new DatabaseUtil(db, user, encPw);
    }

    public void createTables() {
        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS boughtKits" +
                "(uniqueId VARCHAR(255), kitName VARCHAR(255), selected INT)");

        databaseUtil.executeUpdate("CREATE TABLE IF NOT EXISTS inventorySort" +
                "(uniqueId VARCHAR(255), kitName VARCHAR(255), sort VARCHAR(1020))");
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void createPlayer(Player player, String defaultKit) {
        if (!databaseUtil.exists("boughtKits", "uniqueId", player.getUniqueId().toString())) {
            databaseUtil.create("boughtKits", "uniqueId", player.getUniqueId().toString());

            databaseUtil.setString("boughtKits", "kitName", defaultKit,
                    "uniqueId", player.getUniqueId().toString());

            databaseUtil.setInteger("boughtKits", "selected", 1,
                    "uniqueId", player.getUniqueId().toString());
        }

        if (!databaseUtil.exists("inventorySort", "uniqueId", player.getUniqueId().toString())) {
            databaseUtil.create("inventorySort", "uniqueId", player.getUniqueId().toString());

            databaseUtil.setString("inventorySort", "kitName", defaultKit,
                    "uniqueId", player.getUniqueId().toString());

            databaseUtil.setString("inventorySort", "sort",
                    "0,1,2,3,4,5,6,7,8", "uniqueId", player.getUniqueId().toString());
        }
    }

    public void setKitSort(Player player, String kit) {
        PlayerInventory playerInventory = player.getInventory();
        StringBuilder stringBuilder = new StringBuilder();
        Integer[] newSort = new Integer[9];

        for (int slot = 0; slot < 9; slot++) {
            if (playerInventory.getItem(slot) != null) {
                String material = playerInventory.getItem(slot).getType().toString();
                Integer subId = (int) playerInventory.getItem(slot).getDurability();
                newSort[slot] = new KitContent(kit).getSlotOfMaterial(material, subId);
            } else {
                newSort[slot] = new KitContent(kit).getSlotOfMaterial("AIR", 0);
            }
        }

        for (int position = 0; position < 9; position++) {
            if (position != 8)
                stringBuilder.append(newSort[position]).append(",");
            else
                stringBuilder.append(newSort[position]);
        }

        databaseUtil.setString("inventorySort", "sort", stringBuilder.toString(),
                "uniqueId", player.getUniqueId().toString(), "kitName", kit);
    }

    public String[] getKitSort(Player player, String kitName) {
        return databaseUtil.getString("inventorySort", "sort",
                "uniqueId", player.getUniqueId().toString(), "kitName", kitName)
                .split(",");
    }

    public String getSelectedKit(Player player) {
        return databaseUtil.getString("boughtKits", "kitName",
                "uniqueId", player.getUniqueId().toString(), "selected", 1);
    }

    public Boolean hasBought(Player player, String kitName) {
        return databaseUtil.exists("boughtKits",
                "uniqueId", player.getUniqueId().toString(), "kitName", kitName);
    }

    public void selectKit(Player player, String kitName) {
        databaseUtil.setInteger("boughtKits", "selected", 0,
                "uniqueId", player.getUniqueId().toString(), "kitName", getSelectedKit(player));

        databaseUtil.setInteger("boughtKits", "selected", 1,
                "uniqueId", player.getUniqueId().toString(), "kitName", kitName);
    }

    public void createKit(Player player, String kitName, long price) {
        yamlConfiguration.set(kitName + ".price", price);
        save();

        new KitContent(kitName).save();
        new KitContent(kitName).setKitContent(player);
    }

    public boolean kitExists(String kitName) {
        return yamlConfiguration.contains(kitName);
    }

    public Integer getPrice(String kitName) {
        return yamlConfiguration.getInt(kitName + ".price");
    }

    public void buyKit(Player player, String kitName) {
        Integer currentCoins = new CoinsUtil().getCoins(player.getUniqueId().toString());

        if (currentCoins >= getPrice(kitName)) {

            new CoinsUtil().subtractCoins(player.getUniqueId().toString(), getPrice(kitName));

            databaseUtil.create("boughtKits", "uniqueId", player.getUniqueId().toString(),
                    "kitName", kitName);
            databaseUtil.create("inventorySort", "uniqueId", player.getUniqueId().toString(),
                    "kitName", kitName);
            databaseUtil.setString("inventorySort", "sort",
                    "0,1,2,3,4,5,6,7,8",
                    "uniqueId", player.getUniqueId().toString(),
                    "kitName", kitName);

            selectKit(player, kitName);

            player.sendMessage(FrameworkSpigot.getPrefix() +
                    new LanguageUtil().getString(player.getUniqueId().toString(), "HsvhzVbp")
                            .replace("$kitName", kitName));
        } else {
            player.sendMessage(FrameworkSpigot.getPrefix() +
                    new LanguageUtil().getString(player.getUniqueId().toString(), "jJK88oi4"));
        }
    }

    public void openKitSelectionInventory(Player player) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        for (String currentKit : yamlConfiguration.getConfigurationSection("").getKeys(false)) {
            String[] lore = new String[]{"§8» " +
                    new LanguageUtil().getString(player.getUniqueId().toString(), "5b3yZv0X")};

            if (getSelectedKit(player).equals(currentKit))
                lore[0] = "§8» " + new LanguageUtil().getString(player.getUniqueId().toString(), "23qg6bFX");
            else if (hasBought(player, currentKit))
                lore[0] = "§8» " + new LanguageUtil().getString(player.getUniqueId().toString(), "y90vS5gw");

            itemStacks.add(new ItemStackUtil().getItem(Material.IRON_CHESTPLATE, 0, 1,
                    "§8» §" + new LanguageUtil().getColor(player.getUniqueId().toString()) + currentKit,
                    lore));
        }

        GuiUtil.open3RowCarouselInventory(player,
                new LanguageUtil().getString(player.getUniqueId().toString(), "QwMxLVpv"), itemStacks, 0);
    }

    public void openKitBuyInventory(Player player, String kitName) {
        HashMap<Integer, ItemStack> itemStackHashMap = Maps.newHashMap();
        String[] lore = new String[]{new LanguageUtil().getString(player.getUniqueId().toString(), "lbdRAPkx") +
                " §8» §" + new LanguageUtil().getColor(player.getUniqueId().toString()) + getPrice(kitName)};

        itemStackHashMap.put(11,
                new ItemStackUtil().getItem(Material.COOKIE, 0, 1,
                        new LanguageUtil().getString(player.getUniqueId().toString(), "QbT2fsr1"), lore));

        itemStackHashMap.put(15,
                new ItemStackUtil().getItem(Material.GLASS, 0, 1,
                        new LanguageUtil().getString(player.getUniqueId().toString(), "EDAtw7pp")));

        GuiUtil.open3RowInventory(player,
                "§" + new LanguageUtil()
                        .getColor(player.getUniqueId().toString()) + "§l" + kitName, itemStackHashMap);
    }

    public void openSortInventory(Player player, String kitName) {
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();

        player.getOpenInventory().close();

        ItemStack whiteStainedGlass
                = new ItemStackUtil().getItem(Material.STAINED_GLASS_PANE, 0, 1, "§0");
        ItemStack grayStainedGlass
                = new ItemStackUtil().getItem(Material.STAINED_GLASS_PANE, 7, 1, "§0");
        ItemStack blueStainedGlass
                = new ItemStackUtil().getItem(Material.STAINED_GLASS_PANE, 11, 1, "§0");

        playerInventory.clear();
        playerInventory.setItem(9, grayStainedGlass);
        playerInventory.setItem(10, grayStainedGlass);
        playerInventory.setItem(11, blueStainedGlass);
        playerInventory.setItem(12, whiteStainedGlass);
        playerInventory.setItem(13, grayStainedGlass);
        playerInventory.setItem(14, whiteStainedGlass);
        playerInventory.setItem(15, blueStainedGlass);
        playerInventory.setItem(16, grayStainedGlass);
        playerInventory.setItem(17, grayStainedGlass);
        playerInventory.setItem(18, blueStainedGlass);
        playerInventory.setItem(19, whiteStainedGlass);

        playerInventory.setItem(20, new ItemStackUtil().getItem(Material.STAINED_CLAY, 13, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "vsTjFmvY")));

        playerInventory.setItem(21, grayStainedGlass);
        playerInventory.setItem(22, blueStainedGlass);
        playerInventory.setItem(23, grayStainedGlass);

        playerInventory.setItem(24, new ItemStackUtil().getItem(Material.STAINED_CLAY, 14, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "3QbjRsPA")));

        playerInventory.setItem(25, whiteStainedGlass);
        playerInventory.setItem(26, blueStainedGlass);
        playerInventory.setItem(27, grayStainedGlass);
        playerInventory.setItem(28, grayStainedGlass);
        playerInventory.setItem(29, blueStainedGlass);
        playerInventory.setItem(30, whiteStainedGlass);
        playerInventory.setItem(31, grayStainedGlass);
        playerInventory.setItem(32, whiteStainedGlass);
        playerInventory.setItem(33, blueStainedGlass);
        playerInventory.setItem(34, grayStainedGlass);
        playerInventory.setItem(35, grayStainedGlass);

        new KitContent(kitName).setKitContentInPlayerInventory(player);
    }

    public void openPreviewInventory(Player player, String kitName) {
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();

        player.getOpenInventory().close();

        ItemStack whiteStainedGlass
                = new ItemStackUtil().getItem(Material.STAINED_GLASS_PANE, 0, 1, "§0");
        ItemStack grayStainedGlass
                = new ItemStackUtil().getItem(Material.STAINED_GLASS_PANE, 7, 1, "§0");
        ItemStack blueStainedGlass
                = new ItemStackUtil().getItem(Material.STAINED_GLASS_PANE, 11, 1, "§0");

        playerInventory.setItem(9, grayStainedGlass);
        playerInventory.setItem(10, grayStainedGlass);
        playerInventory.setItem(11, blueStainedGlass);
        playerInventory.setItem(12, whiteStainedGlass);
        playerInventory.setItem(13, grayStainedGlass);
        playerInventory.setItem(14, whiteStainedGlass);
        playerInventory.setItem(15, blueStainedGlass);
        playerInventory.setItem(16, grayStainedGlass);
        playerInventory.setItem(17, grayStainedGlass);
        playerInventory.setItem(18, blueStainedGlass);
        playerInventory.setItem(19, whiteStainedGlass);
        playerInventory.setItem(20, blueStainedGlass);
        playerInventory.setItem(21, grayStainedGlass);

        playerInventory.setItem(22, new ItemStackUtil().getItem(Material.STAINED_CLAY, 14, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "R9Ri2CL5")));

        playerInventory.setItem(23, grayStainedGlass);
        playerInventory.setItem(24, blueStainedGlass);
        playerInventory.setItem(25, whiteStainedGlass);
        playerInventory.setItem(26, blueStainedGlass);
        playerInventory.setItem(27, grayStainedGlass);
        playerInventory.setItem(28, grayStainedGlass);
        playerInventory.setItem(29, blueStainedGlass);
        playerInventory.setItem(30, whiteStainedGlass);
        playerInventory.setItem(31, grayStainedGlass);
        playerInventory.setItem(32, whiteStainedGlass);
        playerInventory.setItem(33, blueStainedGlass);
        playerInventory.setItem(34, grayStainedGlass);
        playerInventory.setItem(35, grayStainedGlass);

        new KitContent(kitName).setKitContentInPlayerInventory(player);
    }
}
