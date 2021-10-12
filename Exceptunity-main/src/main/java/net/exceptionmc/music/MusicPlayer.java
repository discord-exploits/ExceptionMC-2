package net.exceptionmc.music;

import com.google.common.collect.Maps;
import net.exceptionmc.util.GuiUtil;
import net.exceptionmc.util.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MusicPlayer {

    ItemStackUtil itemStackUtil = new ItemStackUtil();

//    Material.GOLD_RECORD,
//    Material.GREEN_RECORD,
//    Material.RECORD_3,
//    Material.RECORD_4,
//    Material.RECORD_5,
//    Material.RECORD_6,
//    Material.RECORD_7,
//    Material.RECORD_9,
//    Material.RECORD_10,
//    Material.RECORD_12,

    public void openMusicInventory(Player player) {

        HashMap<Integer, ItemStack> integerItemStackHashMap = Maps.newHashMap();

        String tmp = "Supposed to be the ";
        String tmp0 = " item";

        ItemStack random
                = itemStackUtil.getItem(Material.MAGMA_CREAM, 0, 1, tmp + "random" + tmp0);

        ItemStack musicSelection
                = itemStackUtil.getItem(Material.BOOK, 0, 1, tmp + "selector" + tmp0);

        ItemStack stop
                = itemStackUtil.getItem(Material.INK_SACK, 1, 1, tmp + "stop" + tmp0);

        integerItemStackHashMap.put(10, random);
        integerItemStackHashMap.put(13, musicSelection);
        integerItemStackHashMap.put(16, stop);

        GuiUtil.open3RowInventory(player, "Music", integerItemStackHashMap);
    }
}
