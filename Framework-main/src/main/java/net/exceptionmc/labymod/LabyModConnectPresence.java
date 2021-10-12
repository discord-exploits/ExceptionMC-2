package net.exceptionmc.labymod;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class LabyModConnectPresence {

    public void notifyCurrentPlaying(Player player, String gameModeName, Boolean visible) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("gamemode_name", gameModeName);
        jsonObject.addProperty("show_gamemode", visible);

        LabyModProtocol.sendLabyModMessage(player, "server_gamemode", jsonObject);
    }
}
