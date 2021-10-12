package net.exceptionmc.framework.listener.spigot;

import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import net.exceptionmc.framework.FrameworkSpigot;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@SuppressWarnings("unused")
public class AsyncPlayerChatEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.AsyncPlayerChatEvent asyncPlayerChatEvent) {

        String message = asyncPlayerChatEvent.getMessage().replace("%", "%%");

        if (asyncPlayerChatEvent.getPlayer().hasPermission("exception.color"))
            message = ChatColor.translateAlternateColorCodes('&', message);

        if (ChatColor.stripColor(message).trim().isEmpty())
            asyncPlayerChatEvent.setCancelled(true);

        // - CloudNet Chat - //
        IPermissionUser iPermissionUser
                = FrameworkSpigot.getIPermissionManagement().getUser(asyncPlayerChatEvent.getPlayer().getUniqueId());

        assert iPermissionUser != null;
        IPermissionGroup iPermissionGroup
                = FrameworkSpigot.getIPermissionManagement().getHighestPermissionGroup(iPermissionUser);

        asyncPlayerChatEvent.setFormat(iPermissionGroup.getDisplay()
                + asyncPlayerChatEvent.getPlayer().getName() + " §8» §7" + message);
    }
}
