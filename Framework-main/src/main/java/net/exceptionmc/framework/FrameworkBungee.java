package net.exceptionmc.framework;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionManagement;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import net.exceptionmc.framework.listener.bungee.PlayerDisconnectEvent;
import net.exceptionmc.framework.listener.bungee.PostLoginEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;

import java.io.File;
import java.util.UUID;

public class FrameworkBungee extends Plugin {

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
     *     - Contains game configuration files such as "sw.yaml, bw.yaml".
     *   - /maps
     *     - /location
     *       - Contains one location file per map.
     *
     */

    private static final CloudNetDriver cloudNetDriver = CloudNetDriver.getInstance();
    private static final IPermissionManagement iPermissionManagement = cloudNetDriver.getPermissionManagement();
    private static FrameworkBungee framework;

    public static FrameworkBungee getInstance() {
        return framework;
    }

    public static File getFolder() {
        return getInstance().getDataFolder();
    }

    // --- //

    public static IPermissionManagement getIPermissionManagement() {
        return iPermissionManagement;
    }

    public static IPermissionUser getIPermissionUser(UUID uuid) {
        if (uuid != null)
            return getIPermissionManagement().getUser(uuid);

        return null;
    }

    @Override
    public void onEnable() {
        framework = this;

        initializeCommandExecutors();
        initializeListeners();

        sendAscii();
    }

    @Override
    public void onDisable() {
        sendAscii();
    }

    // --- //

    private void initializeCommandExecutors() {
    }

    private void initializeListeners() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerDisconnectEvent());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PostLoginEvent());
    }

    // --- //

    public void sendAscii() {
        PluginDescription pluginDescription = getDescription();
        String version = pluginDescription.getVersion();
        String author = pluginDescription.getAuthor();

        System.out.println("    ______                                             __  ");
        System.out.println("   / ____/________ _____ ___  ___ _      ______  _____/ /__");
        System.out.println("  / /_  / ___/ __ `/ __ `__ \\/ _ \\ | /| / / __ \\/ ___/ //_/");
        System.out.println(" / __/ / /  / /_/ / / / / / /  __/ |/ |/ / /_/ / /  / ,<   ");
        System.out.println("/_/   /_/   \\__,_/_/ /_/ /_/\\___/|__/|__/\\____/_/  /_/|_|  ");
        System.out.println("v" + version + " by " + author);
        System.out.println(" ");
    }
}
