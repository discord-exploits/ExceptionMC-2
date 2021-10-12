package net.exceptionmc;

import net.exceptionmc.commandexecutors.SetCommand;
import net.exceptionmc.commandexecutors.SpawnCommand;
import net.exceptionmc.framework.FrameworkSpigot;
import net.exceptionmc.listeners.*;
import net.exceptionmc.listeners.external.RegionEnterEvent;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Exceptunity extends JavaPlugin {

    public static Exceptunity exceptunity;
    public static HashMap<UUID, String> uuidStringHashMap = new HashMap<>();

    @Override
    public void onEnable() {

        exceptunity = this;

        initializeCommandExecutors();
        initializeListeners();
        initializeWorlds();
    }

    @Override
    public void onDisable() {
    }

    // -- //

    private void initializeCommandExecutors() {

        getCommand("set").setExecutor(new SetCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
    }

    private void initializeListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new BlockBreakEvent(), this);
        pluginManager.registerEvents(new BlockPlaceEvent(), this);
        pluginManager.registerEvents(new EntityDamageEvent(), this);
        pluginManager.registerEvents(new PlayerDropItemEvent(), this);
        pluginManager.registerEvents(new PlayerInteractEvent(), this);
        pluginManager.registerEvents(new PlayerJoinEvent(), this);
        pluginManager.registerEvents(new PlayerPickupItemEvent(), this);
        pluginManager.registerEvents(new PlayerQuitEvent(), this);

        // initialize external listeners
        pluginManager.registerEvents(new RegionEnterEvent(), this);
    }

    private void initializeWorlds() {
        for (World world : Bukkit.getWorlds()) {

            world.setGameRuleValue("doFireTick", "false");
            world.setGameRuleValue("mobGriefing", "false");
            world.setGameRuleValue("keepInventory", "true");
            world.setGameRuleValue("doMobSpawning", "false");
            world.setGameRuleValue("doMobLoot", "false");
            world.setGameRuleValue("doTileDrop", "false");
            world.setGameRuleValue("commandBlockOutput", "false");
            world.setGameRuleValue("naturalRegeneration", "false");
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("logAdminCommands", "false");
            world.setGameRuleValue("showDeathMessages", "false");
            world.setGameRuleValue("randomTickSpeed", "0");
            world.setGameRuleValue("sendCommandFeedback", "false");
            world.setGameRuleValue("reducedDebugInfo", "false");

            world.setDifficulty(Difficulty.PEACEFUL);

            world.setFullTime(6000);
            world.setWeatherDuration(0);
            world.setStorm(false);
            world.setThundering(false);
        }
    }

    public static Exceptunity getExceptunity() {

        return exceptunity;
    }

    public static String getPrefix() {

        return FrameworkSpigot.getPrefix();
    }
}
