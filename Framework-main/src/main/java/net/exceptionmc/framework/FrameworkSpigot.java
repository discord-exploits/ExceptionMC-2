package net.exceptionmc.framework;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.wrapper.Wrapper;
import net.exceptionmc.framework.commandexecutor.ShutdownCommand;
import net.exceptionmc.framework.listener.spigot.*;
import net.exceptionmc.util.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FrameworkSpigot extends JavaPlugin {

    /*
     *
     * Directory Structure
     *
     * /Framework/general
     *   - /config
     *     - Contains general configuration files.
     *
     * /Framework/game
     *   - /properties
     *     - Contains game configuration files such as "sw.yml, bw.yml".
     *   - /maps
     *     - /location
     *       - Contains one location file per map.
     *
     */

    private static FrameworkSpigot framework;

    @Override
    public void onEnable() {
        framework = this;

        initialize();
    }

    @Override
    public void onDisable() {
        shutdown();
    }

    // --- //

    public static FrameworkSpigot getInstance() {
        return framework;
    }

    public static File getFolder() {
        return getInstance().getDataFolder();
    }

    // --- //

    private void initialize() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        // Listeners
        pluginManager.registerEvents(new AsyncPlayerChatEvent(), this);
        pluginManager.registerEvents(new InventoryClickEvent(), this);
        pluginManager.registerEvents(new InventoryCloseEvent(), this);
        pluginManager.registerEvents(new PlayerJoinEvent(), this);
        pluginManager.registerEvents(new PlayerLoginEvent(), this);
        pluginManager.registerEvents(new PlayerQuitEvent(), this);

        // CommandExecutors
        getCommand("shutdown").setExecutor(new ShutdownCommand());

        // Database
        OnlineTimeUtil.createTable();

        new ClanUtil().createTables();
        new CoinsUtil().createTable();
        new LanguageUtil().createTable();
        new PrivacyUtil().createTable();
        new UniqueIdFetcher().createTable();

        sendAscii();
    }

    private void shutdown() {
        // Unregister CloudNet Hook
        CloudNetDriver.getInstance().getEventManager().unregisterListeners(this.getClass().getClassLoader());
        Wrapper.getInstance().unregisterPacketListenersByClassLoader(this.getClass().getClassLoader());

        sendAscii();
    }

    public void sendAscii() {
        PluginDescriptionFile pluginDescriptionFile = getDescription();
        String author = pluginDescriptionFile.getAuthors().get(0);
        String version = pluginDescriptionFile.getVersion();

        System.out.println("    ______                                             __  ");
        System.out.println("   / ____/________ _____ ___  ___ _      ______  _____/ /__");
        System.out.println("  / /_  / ___/ __ `/ __ `__ \\/ _ \\ | /| / / __ \\/ ___/ //_/");
        System.out.println(" / __/ / /  / /_/ / / / / / /  __/ |/ |/ / /_/ / /  / ,<   ");
        System.out.println("/_/   /_/   \\__,_/_/ /_/ /_/\\___/|__/|__/\\____/_/  /_/|_|  ");
        System.out.println("v" + version + " by " + author);
        System.out.println(" ");
    }
}
