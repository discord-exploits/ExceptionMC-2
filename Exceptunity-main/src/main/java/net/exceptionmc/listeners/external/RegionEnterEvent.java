package net.exceptionmc.listeners.external;

import net.exceptionmc.handler.ActionbarHandler;
import net.exceptionmc.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.text.DecimalFormat;

public class RegionEnterEvent implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void on(com.mewin.WGRegionEvents.events.RegionEnterEvent regionEnterEvent) {

        Player player = regionEnterEvent.getPlayer();

        if (!player.hasPermission("exception.stage")) {
            if (regionEnterEvent.getRegion().getId().equalsIgnoreCase("stage")) {

                player.sendMessage("teleport to stageLoc");

                if (new LocationUtil().exists("stage"))
                    player.teleport(new LocationUtil().getLocation("stage"));
            }

            if (regionEnterEvent.getRegion().getId().equalsIgnoreCase("entrance0")
                    || regionEnterEvent.getRegion().getId().equalsIgnoreCase("gate0")) {

                player.sendMessage("teleport to gateLoc0");

                if (new LocationUtil().exists("gate0"))
                    player.teleport(new LocationUtil().getLocation("gate0"));
            }

            if (regionEnterEvent.getRegion().getId().equalsIgnoreCase("entrance1")
                    || regionEnterEvent.getRegion().getId().equalsIgnoreCase("gate1")) {

                player.sendMessage("teleport to gateLoc1");

                if (new LocationUtil().exists("gate1"))
                    player.teleport(new LocationUtil().getLocation("gate1"));
            }
        }
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void on(PlayerMoveEvent playerMoveEvent) {

        Player player = playerMoveEvent.getPlayer();
        Location eyeLocation = player.getEyeLocation();

        double coordinateX = Double.parseDouble(round(eyeLocation.getX()));
        double coordinateY = Double.parseDouble(round(eyeLocation.getY()));
        double coordinateZ = Double.parseDouble(round(eyeLocation.getZ()));

        float yaw = Float.parseFloat(round(eyeLocation.getYaw()));
        float pitch = Float.parseFloat(round(eyeLocation.getYaw()));

        new ActionbarHandler().sendActionbarPacket(player,
                "§fX: §a" + coordinateX + " §fY: §b" + coordinateY + " §fZ: §c" + coordinateZ
                        + " §fYaw: §d" + yaw + " §fPitch: §e" + pitch);
    }

    private String round(Double doubleInput) {

        return new DecimalFormat("###.##").format(doubleInput);
    }

    private String round(Float floatInput) {

        return new DecimalFormat("###.##").format(floatInput);
    }
}
