package net.exceptionmc.framework.listener.spigot;

import net.exceptionmc.util.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@SuppressWarnings("unused")
public class PlayerLoginEvent implements Listener {

    CoinsUtil coinsUtil = new CoinsUtil();
    LanguageUtil languageUtil = new LanguageUtil();
    PrivacyUtil privacyUtil = new PrivacyUtil();
    UniqueIdFetcher uniqueIdFetcher = new UniqueIdFetcher();

    @EventHandler
    public void on(org.bukkit.event.player.PlayerLoginEvent playerLoginEvent) {

        Player player = playerLoginEvent.getPlayer();
        String uuid = player.getUniqueId().toString();

        OnlineTimeUtil.createPlayer(uuid);

        coinsUtil.createPlayer(uuid);
        languageUtil.createPlayer(uuid);
        privacyUtil.createPlayer(uuid);
        uniqueIdFetcher.createPlayer(player);

        OnlineTimeUtil.startOnlineTimeCounter(player.getUniqueId().toString());
    }
}
