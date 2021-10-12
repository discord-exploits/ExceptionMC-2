package net.exceptionmc.util;

import net.exceptionmc.framework.FrameworkSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class LocationUtil {

    File file = new File(FrameworkSpigot.getFolder() + "/general", "locations.yaml");
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public boolean exists(String string) {
        return yamlConfiguration.get(string) != null;
    }

    public void setLocation(String string, Location location) {
        String world = location.getWorld().getName();

        double coordinateX = location.getX();
        double coordinateY = location.getY();
        double coordinateZ = location.getZ();

        float yaw = location.getYaw();
        float pitch = location.getPitch();

        yamlConfiguration.set(string, world + "," + coordinateX + "," + coordinateY + "," + coordinateZ + "," + yaw
                + "," + pitch);

        try {
            yamlConfiguration.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setBlockLocation(String string, Block block) {
        Location location = block.getLocation();
        String world = location.getWorld().getName();

        double coordinateX = location.getX();
        double coordinateY = location.getY();
        double coordinateZ = location.getZ();

        float yaw = location.getYaw();
        float pitch = location.getPitch();

        yamlConfiguration.set("btn." + string, world + "," + coordinateX + "," + coordinateY + "," + coordinateZ
                + "," + yaw + "," + pitch);

        try {
            yamlConfiguration.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public Location getLocation(String string) {
        if (exists(string)) {
            String[] strings = yamlConfiguration.getString(string).split(",");

            String world = strings[0];

            double coordinateX = Double.parseDouble(strings[1]);
            double coordinateY = Double.parseDouble(strings[2]);
            double coordinateZ = Double.parseDouble(strings[3]);

            float yaw = Float.parseFloat(strings[4]);
            float pitch = Float.parseFloat(strings[5]);

            Location location = new Location(Bukkit.getWorld(world), coordinateX, coordinateY, coordinateZ);

            location.setYaw(yaw);
            location.setPitch(pitch);

            return location;
        }

        return null;
    }
}
