package net.exceptionmc.gamework.voting;

import net.exceptionmc.framework.FrameworkSpigot;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class MapManager {

    File file;
    YamlConfiguration yamlConfiguration;

    public MapManager() {
        file = new File(FrameworkSpigot.getFolder() + "/game", "maps.yml");
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public boolean exists(String mapName) {
        return yamlConfiguration.get(mapName) != null;
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void createMap(String mapName, String builder) {
        if (!exists(mapName)) {
            yamlConfiguration.set(mapName + ".builder", builder);
            save();

            new MapLocation(mapName).save();
        }
    }

    public String getBuilder(String mapName) {
        if (exists(mapName))
            return yamlConfiguration.getString(mapName + ".builder");

        return null;
    }

    public ArrayList<String> getAllMaps() {
        return new ArrayList<>(yamlConfiguration.getConfigurationSection("").getKeys(false));
    }
}
