package net.exceptionmc.gamework.property;

import net.exceptionmc.framework.FrameworkSpigot;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class GameProperty {

    File file = new File(FrameworkSpigot.getInstance().getDataFolder() + "/game", "properties.yml");
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public void save() {
        if (!yamlConfiguration.contains("gameSize"))
            yamlConfiguration.set("gameSize", "2x1");

        try {
            yamlConfiguration.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public int getAmountOfTeams() {
        return Integer.parseInt(yamlConfiguration.getString("gameSize").split("x")[0]);
    }

    public void setAmountOfTeams(int amountOfTeams) {
        int amountOfPlayersPerTeam = Integer.parseInt(yamlConfiguration.getString("gameSize").split("x")[1]);
        yamlConfiguration.set("gameSize", amountOfTeams + "x" + amountOfPlayersPerTeam);

        save();
    }

    public int getAmountOfPlayersPerTeam() {
        return Integer.parseInt(yamlConfiguration.getString("gameSize").split("x")[1]);
    }

    public void setAmountOfPlayersPerTeam(int amountOfPlayersPerTeam) {
        int amountOfTeams = Integer.parseInt(yamlConfiguration.getString("gameSize").split("x")[0]);
        yamlConfiguration.set("gameSize", amountOfTeams + "x" + amountOfPlayersPerTeam);

        save();
    }
}
