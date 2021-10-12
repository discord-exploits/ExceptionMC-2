package net.exceptionmc.gamework.voting;

import net.exceptionmc.framework.FrameworkSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class MapLocation {

    private final String mapName;
    YamlConfiguration yamlConfiguration;
    File file;

    public MapLocation(String mapName) {
        this.mapName = mapName;
        file = new File(FrameworkSpigot.getInstance().getDataFolder() + "/game/maps", mapName + ".yml");
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setLocation(String locationPath, Location location) {
        if (new MapManager().exists(mapName)) {
            yamlConfiguration.set(locationPath, location.getWorld().getName() + "," + location.getX() + ","
                    + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch());

            save();
        }
    }

    public void setHigh(String locationPath, Location location) {
        if (new MapManager().exists(mapName)) {
            yamlConfiguration.set(locationPath, location.getWorld().getName() + "," + location.getY());

            save();
        }
    }

    public Location getLocation(String locationPath) {
        if (new MapManager().exists(mapName)) {
            String[] strings = yamlConfiguration.getString(locationPath).split(",");
            World world = Bukkit.getWorld(strings[0]);

            double x = Double.parseDouble(strings[1]),
                    y = Double.parseDouble(strings[2]),
                    z = Double.parseDouble(strings[3]);

            float yaw = Float.parseFloat(strings[4]), pitch = Float.parseFloat(strings[5]);

            return new Location(world, x, y, z, yaw, pitch);
        }

        return null;
    }

    public Location getHigh(String locationPath) {
        if (new MapManager().exists(mapName)) {
            String[] strings = yamlConfiguration.getString(locationPath).split(",");
            World world = Bukkit.getWorld(strings[0]);

            double y = Double.parseDouble(strings[1]);

            return new Location(world, 0, y, 0, 0, 0);
        }

        return null;
    }
}
