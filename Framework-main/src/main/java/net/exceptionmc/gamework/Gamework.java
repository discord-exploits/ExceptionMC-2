package net.exceptionmc.gamework;

import net.exceptionmc.framework.FrameworkSpigot;
import net.exceptionmc.gamework.kits.KitManager;
import net.exceptionmc.gamework.listener.InventoryClickEvent;
import net.exceptionmc.gamework.listener.PlayerMoveEvent;
import net.exceptionmc.gamework.listener.PlayerQuitEvent;
import net.exceptionmc.gamework.property.GameProperty;
import net.exceptionmc.gamework.team.TeamManager;
import net.exceptionmc.gamework.voting.MapManager;
import net.exceptionmc.gamework.voting.MapVoting;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

@SuppressWarnings("unused")
public class Gamework {

    public static boolean enabled = false;

    public static boolean isEnabled() {
        return enabled;
    }

    public void loadGameWork(boolean kits, boolean teams, boolean gameProperties) {

        new MapManager().save();
        MapVoting.initVoting();

        if (gameProperties)
            new GameProperty().save();
        if (kits)
            new KitManager().createTables();
        if (teams)
            TeamManager.initTeams();

        registerEvents();

        enabled = true;
    }

    public void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new InventoryClickEvent(), FrameworkSpigot.getInstance());
        pluginManager.registerEvents(new PlayerMoveEvent(), FrameworkSpigot.getInstance());
        pluginManager.registerEvents(new PlayerQuitEvent(), FrameworkSpigot.getInstance());
    }
}
