package net.exceptionmc.labymod;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class LabyModServerSwitchingPacket {

    public void playOutServerSwitchPacket(Player player, String title, String address, Boolean preview) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("title", title);
        jsonObject.addProperty("address", address);
        jsonObject.addProperty("preview", preview);

        LabyModProtocol.sendLabyModMessage(player, "server_switch", jsonObject);
    }
}
