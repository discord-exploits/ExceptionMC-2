package net.exceptionmc.gamework.kits;

import com.google.common.collect.Lists;
import net.exceptionmc.framework.FrameworkSpigot;
import net.exceptionmc.util.DatabaseUtil;
import net.exceptionmc.util.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KitContent {

    File file;
    YamlConfiguration yamlConfiguration;
    String kitName;

    public KitContent(String kit) {
        this.file = new File(FrameworkSpigot.getInstance().getDataFolder() + "/game/kits", kit + ".yml");
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        this.kitName = kit;
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setKitContent(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        int current;

        for (int slot = 0; slot < 4; slot++) {
            ItemStack itemStack = playerInventory.getArmorContents()[slot];
            String armorType = itemStack.getType().toString().split("_")[1].toLowerCase();

            yamlConfiguration.set("armor." + armorType + ".item", itemStack.getType().toString() + ","
                    + itemStack.getDurability());

            current = 0;

            for (Enchantment enchantment : itemStack.getEnchantments().keySet())
                yamlConfiguration.set("armor." + armorType + ".enchant." + current, enchantment.getName() + "," +
                        itemStack.getItemMeta().getEnchantLevel(enchantment));
        }

        for (int slot = 0; slot < 9; slot++) {
            ItemStack itemStack = playerInventory.getItem(slot);

            if (itemStack != null) {
                yamlConfiguration.set("items.slot." + slot + ".item", itemStack.getType().toString() + "," +
                        itemStack.getDurability());

                yamlConfiguration.set("items.slot." + slot + ".amount", itemStack.getAmount());

                current = 0;

                for (Enchantment enchantment : itemStack.getEnchantments().keySet())
                    yamlConfiguration.set("items.slot." + slot + ".enchant." + current, enchantment.getName() + ","
                            + itemStack.getEnchantmentLevel(enchantment));

            } else {
                yamlConfiguration.set("items.slot." + slot + ".item", "AIR," + 0);
                yamlConfiguration.set("items.slot." + slot + ".amount", 0);
            }
        }

        save();
    }

    public void setKitContentInPlayerInventory(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack[] itemStacks = new ItemStack[4];

        int count = 0;

        for (String current : yamlConfiguration.getConfigurationSection("armor").getKeys(false)) {

            int amountOfEnchants;

            Enchantment[] enchantments = new Enchantment[0];
            Integer[] integers = new Integer[0];
            Boolean[] booleans = new Boolean[0];

            if (yamlConfiguration.contains("armor." + current + ".enchant")) {
                amountOfEnchants = yamlConfiguration.getConfigurationSection("armor." + current + ".enchant")
                        .getKeys(false).size();

                enchantments = new Enchantment[amountOfEnchants];
                integers = new Integer[amountOfEnchants];
                booleans = new Boolean[amountOfEnchants];

                for (String currentEnchantment :
                        yamlConfiguration.getConfigurationSection("armor." + current + ".enchant")
                                .getKeys(false)) {

                    int currentEnchantmentInt = Integer.parseInt(currentEnchantment);

                    String[] strings = yamlConfiguration
                            .getString("armor." + current + ".enchant." + currentEnchantmentInt).split(",");

                    enchantments[currentEnchantmentInt] = Enchantment.getByName(strings[0]);
                    integers[currentEnchantmentInt] = Integer.valueOf(strings[1]);
                    booleans[currentEnchantmentInt] = true;
                }
            }

            String[] material = yamlConfiguration.getString("armor." + current + ".item").split(",");

            if (!material[0].equals("AIR"))
                itemStacks[count] = new ItemStackUtil().getItem(Material.valueOf(material[0]),
                        Integer.parseInt(material[1]), 1, "", new String[]{},
                        enchantments, integers, booleans);

            count++;
        }

        playerInventory.setArmorContents(itemStacks);
        String[] sort = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8"};
        String uniqueId = player.getUniqueId().toString();

        if (new DatabaseUtil(KitDatabase.kitDatabase, KitDatabase.kitUser, KitDatabase.kitEncodedPassword).exists(
                "inventorySort", "uniqueId", uniqueId, "kitName", kitName))

            sort = new KitManager().getKitSort(player, kitName);

        int slotCount = 0;

        for (String currentSlot : sort) {

            Enchantment[] enchantments = new Enchantment[0];
            Integer[] integers = new Integer[0];
            Boolean[] booleans = new Boolean[0];

            if (yamlConfiguration.contains("items.slot." + currentSlot + ".enchant")) {
                int amountOfEnchants =
                        yamlConfiguration.getConfigurationSection("items.slot." + currentSlot + ".enchant")
                                .getKeys(false).size();

                enchantments = new Enchantment[amountOfEnchants];
                integers = new Integer[amountOfEnchants];
                booleans = new Boolean[amountOfEnchants];

                for (String currentEnchantment : yamlConfiguration.getConfigurationSection(
                        "items.slot." + currentSlot + ".enchant").getKeys(false)) {

                    int currentEnchantmentInt = Integer.parseInt(currentEnchantment);

                    String[] strings =
                            yamlConfiguration.getString("items.slot." + currentSlot + ".enchant." +
                                    currentEnchantmentInt).split(",");

                    enchantments[currentEnchantmentInt] = Enchantment.getByName(strings[0]);
                    integers[currentEnchantmentInt] = Integer.valueOf(strings[1]);
                    booleans[currentEnchantmentInt] = true;
                }
            }

            String[] material
                    = yamlConfiguration.getString("items.slot." + currentSlot + ".item").split(",");

            if (!material[0].equals("AIR")) {
                ItemStack itemStack
                        = new ItemStackUtil().getItem(Material.valueOf(material[0]), Integer.parseInt(material[1]),
                        yamlConfiguration.getInt("items.slot." + currentSlot + ".amount"), "",
                        new String[]{}, enchantments, integers, booleans);

                playerInventory.setItem(slotCount, itemStack);
            }

            slotCount++;
        }
    }

    public Integer getSlotOfMaterial(String material, Integer subId) {
        ArrayList<Integer> alreadyAdded = Lists.newArrayList();

        for (int slot = 0; slot < 9; slot++) {
            if (!alreadyAdded.contains(slot)) {
                if (yamlConfiguration.getString("items.slot." + slot + ".item").equals(material + "," + subId)) {
                    alreadyAdded.add(slot);
                    return slot;
                }
            }
        }

        return null;
    }
}
