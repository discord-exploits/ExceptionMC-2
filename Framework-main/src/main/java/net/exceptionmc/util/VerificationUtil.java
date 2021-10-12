package net.exceptionmc.util;

import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@SuppressWarnings("all")
public class VerificationUtil {

    DatabaseUtil databaseUtil =
            new DatabaseUtil("discord", "discord", "OHRoaEx2bFM5QnVoNUZRTHpZaWk=");

    public void openVerificationInventory(Player player) {
        HashMap<Integer, ItemStack> integerItemStackHashMap = Maps.newHashMap();

        integerItemStackHashMap.put(13, new ItemStackUtil().getItem(Material.PAPER, 0, 1,
                new LanguageUtil().getString(player.getUniqueId().toString(), "l1vs7xf7")
                        .replace("$verificationToken",
                                databaseUtil.getString("verificationSessions", "verificationToken",
                                        "uniqueId", player.getUniqueId().toString()))));

        GuiUtil.open3RowInventory(player,
                new LanguageUtil().getString(player.getUniqueId().toString(), "TTOptiw3"), integerItemStackHashMap);
    }

    public boolean isAwaitingVerification(String uuid) {
        return databaseUtil.exists("verificationSessions", "uniqueId", uuid);
    }
}
