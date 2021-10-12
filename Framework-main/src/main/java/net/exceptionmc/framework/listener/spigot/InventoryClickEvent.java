package net.exceptionmc.framework.listener.spigot;

import net.exceptionmc.util.GuiUtil;
import net.exceptionmc.util.LanguageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InventoryClickEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.inventory.InventoryClickEvent inventoryClickEvent) {

        Player player = (Player) inventoryClickEvent.getWhoClicked();

        if (inventoryClickEvent.getCurrentItem() != null) {
            if (inventoryClickEvent.getCurrentItem().getItemMeta() != null) {
                if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName() != null) {
                    if (inventoryClickEvent.getInventory().getTitle().equals(GuiUtil.getInventoryTitle(player))) {

                        inventoryClickEvent.setCancelled(true);

                        int currentPage = GuiUtil.getCurrentPage(player);
                        ArrayList<ItemStack> itemStacks = GuiUtil.getCarousel(player);
                        String title = GuiUtil.getInventoryTitle(player);

                        if (GuiUtil.getInventorySize(player) == 3) {
                            if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
                                    .equals(new LanguageUtil().getString(player.getUniqueId().toString(), "nytd8OAv")))
                                GuiUtil.open3RowCarouselInventory(player, title, itemStacks, currentPage - 1);
                            else if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
                                    .equals(new LanguageUtil().getString(player.getUniqueId().toString(), "Bvs2RlEn")))
                                GuiUtil.open3RowCarouselInventory(player, title, itemStacks, currentPage + 1);
                        } else if (GuiUtil.getInventorySize(player) == 5) {
                            if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
                                    .equals(new LanguageUtil().getString(player.getUniqueId().toString(), "nytd8OAv")))
                                GuiUtil.open5RowCarouselInventory(player, title, itemStacks, currentPage - 1);
                            else if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName()
                                    .equals(new LanguageUtil().getString(player.getUniqueId().toString(), "Bvs2RlEn")))
                                GuiUtil.open5RowCarouselInventory(player, title, itemStacks, currentPage + 1);
                        }
                    }
                }
            }
        }
    }
}
