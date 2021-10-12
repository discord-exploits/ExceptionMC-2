package net.exceptionmc.runnables;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class LeatherArmorRunnable extends BukkitRunnable {

    Player player;
    ArrayList<String> players;

    int r, g, b, one, two, three, four, five, six;

    public LeatherArmorRunnable(Player player, ArrayList<String> players) {

        this.player = player;
        this.players = players;

        this.r = 255;
        this.g = 0;
        this.b = 0;

        this.one = 0;
        this.two = 0;
        this.three = 0;
        this.four = 0;
        this.five = 0;
        this.six = 0;
    }

    public void run() {

        if (!players.contains(player.getName())) {

            player.getInventory().setBoots(new ItemStack(Material.AIR));
            cancel();
            return;
        }

        if (one <= 17) {

            one++;
            g = (one - 1) * 15;
            player.getInventory().setBoots(getBoots(b, g, r));
        } else if (two <= 17) {

            two++;
            r = 255 - 15 * (two - 1);
            player.getInventory().setBoots(getBoots(b, g, r));
        } else if (three <= 17) {

            three++;
            b = (three - 1) * 15;
            player.getInventory().setBoots(getBoots(b, g, r));
        } else if (four <= 17) {

            four++;
            g = 255 - 15 * (four - 1);
            player.getInventory().setBoots(getBoots(b, g, r));
        } else if (five <= 17) {

            five++;
            r = (five - 1) * 15;
            player.getInventory().setBoots(getBoots(b, g, r));
        } else if (six <= 17) {

            six++;
            b = 255 - 15 * (six - 1);
            player.getInventory().setBoots(getBoots(b, g, r));
        } else {

            one = 0;
            two = 0;
            three = 0;
            four = 0;
            five = 0;
            six = 0;
        }
    }

    public ItemStack getBoots(Integer b, Integer g, Integer r) {

        ItemStack itemStack = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;

        leatherArmorMeta.setDisplayName("§0");
        leatherArmorMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        leatherArmorMeta.addEnchant(Enchantment.DURABILITY, -1, true);
        leatherArmorMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        leatherArmorMeta.setColor(Color.fromBGR(b, g, r));
        itemStack.setItemMeta(leatherArmorMeta);

        return itemStack;
    }
}
