package net.exceptionmc.labymod;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class LabyModCinescopes {

    public void setCoveragePercent(Player player, Integer coveragePercent, Long duration) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("coverage", coveragePercent);
        jsonObject.addProperty("duration", duration);

        LabyModProtocol.sendLabyModMessage(player, "cinescopes", jsonObject);
    }
}
