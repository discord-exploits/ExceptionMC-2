package net.exceptionmc.framework.listener.spigot;

import net.exceptionmc.framework.FrameworkSpigot;
import net.exceptionmc.util.ClanUtil;
import net.exceptionmc.util.TablistPrefixUtil;
import net.exceptionmc.util.UniqueIdFetcher;
import net.exceptionmc.util.VerificationUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@SuppressWarnings({"unused", "deprecation"})
public class PlayerJoinEvent implements Listener {

    ClanUtil clanUtil = new ClanUtil();
    UniqueIdFetcher uniqueIdFetcher = new UniqueIdFetcher();
    VerificationUtil verificationUtil = new VerificationUtil();

    @EventHandler
    public void on(org.bukkit.event.player.PlayerJoinEvent playerJoinEvent) {

        Player player = playerJoinEvent.getPlayer();

        if (uniqueIdFetcher.playerExists(player.getUniqueId().toString()))
            if (!uniqueIdFetcher.getLastUsedName(player.getUniqueId().toString()).equals(player.getName()))
                uniqueIdFetcher.setLastUsedName(player.getUniqueId().toString(), player.getName());

        Bukkit.getScheduler().scheduleAsyncDelayedTask(FrameworkSpigot.getInstance(), () -> {

            //  Prefix-System
            TablistPrefixUtil.registerTeams(player);
            TablistPrefixUtil.setPrefix(player);

            new ClanUtil().setSuffix(player);

            TablistPrefixUtil.loadPrefix(player);
            TablistPrefixUtil.loadSuffix(player);

            //  Verification-System
            if (verificationUtil.isAwaitingVerification(player.getUniqueId().toString()))
                verificationUtil.openVerificationInventory(player);
        }, 1);
    }
}
