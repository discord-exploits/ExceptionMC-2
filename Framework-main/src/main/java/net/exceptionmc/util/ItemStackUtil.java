package net.exceptionmc.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@SuppressWarnings("unused")
public class ItemStackUtil {

    ArrayList<String> itemLore = new ArrayList<>();

    public ItemStack getItem(Material material, int sub, Integer amount) {
        return new ItemStack(material, amount, (short) sub);
    }

    public ItemStack getItem(Material material, int sub, Integer amount, String displayName) {
        ItemStack itemStack = new ItemStack(material, amount, (short) sub);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getItem(Material material, int sub, Integer amount, String displayName, String[] lore) {
        itemLore.addAll(Arrays.asList(lore));

        ItemStack itemStack = new ItemStack(material, amount, (short) sub);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack getItem(Material material, int sub, Integer amount, String displayName, String[] lore,
                             Enchantment[] enchantment, Integer[] level, Boolean[] ignoreLevelRestriction) {
        itemLore.addAll(Arrays.asList(lore));

        ItemStack itemStack = new ItemStack(material, amount, (short) sub);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(itemLore);

        for (Enchantment value : enchantment)
            for (int i1 = 0; i1 < enchantment.length; i1++)
                for (int i2 = 0; i2 < enchantment.length; i2++)
                    itemMeta.addEnchant(value, level[i1], ignoreLevelRestriction[i2]);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack getPlayerSkull(String target, Integer amount) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setOwner(target);
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public ItemStack getPlayerSkull(String target, Integer amount, String displayName) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setOwner(target);
        skullMeta.setDisplayName(displayName);
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public ItemStack getPlayerSkull(String target, Integer amount, String displayName, String[] lore) {
        itemLore.addAll(Arrays.asList(lore));

        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setOwner(target);
        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(itemLore);

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public ItemStack getPlayerSkull(String target, Integer amount, String displayName, String[] lore,
                                    Enchantment[] enchantment, Integer[] level, Boolean[] ignoreLevelRestriction) {
        itemLore.addAll(Arrays.asList(lore));

        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setOwner(target);
        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(itemLore);

        for (Enchantment value : enchantment)
            for (int i1 = 0; i1 < enchantment.length; i1++)
                for (int i2 = 0; i2 < enchantment.length; i2++)
                    skullMeta.addEnchant(value, level[i1], ignoreLevelRestriction[i2]);

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public ItemStack getCustomSkull(String textureId, Integer amount) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        Field profileField;

        if (textureId.isEmpty())
            return itemStack;

        String url = "https://textures.minecraft.net/texture/";
        byte[] encodedData = Base64.getEncoder()
                .encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url + textureId).getBytes());

        gameProfile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public ItemStack getCustomSkull(String textureId, Integer amount, String displayName) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        Field profileField;

        if (textureId.isEmpty())
            return itemStack;

        String url = "https://textures.minecraft.net/texture/";
        byte[] encodedData = Base64.getEncoder()
                .encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url + textureId).getBytes());

        gameProfile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        skullMeta.setDisplayName(displayName);
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public ItemStack getCustomSkull(String textureId, Integer amount, String displayName, String[] lore) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        Field profileField;

        if (textureId.isEmpty())
            return itemStack;

        String url = "https://textures.minecraft.net/texture/";
        byte[] encodedData = Base64.getEncoder()
                .encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url + textureId).getBytes());

        gameProfile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(Arrays.asList(lore));

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public ItemStack getCustomSkull(String textureId, Integer amount, String displayName, String[] lore,
                                    Enchantment[] enchantment, Integer[] level, Boolean[] ignoreLevelRestriction) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        Field profileField;

        if (textureId.isEmpty())
            return itemStack;

        String url = "https://textures.minecraft.net/texture/";
        byte[] encodedData = Base64.getEncoder()
                .encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url + textureId).getBytes());

        gameProfile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(Arrays.asList(lore));

        for (Enchantment value : enchantment)
            for (int i1 = 0; i1 < enchantment.length; i1++)
                for (int i2 = 0; i2 < enchantment.length; i2++)
                    skullMeta.addEnchant(value, level[i1], ignoreLevelRestriction[i2]);

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }
}
