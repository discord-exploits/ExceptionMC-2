package net.exceptionmc.labymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("unused")
public class LabyModSubtitleHandler {

    public void setSubtitle(Player receiver, UUID subtitlePlayer, String value) {
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("uuid", subtitlePlayer.toString());
        jsonObject.addProperty("size", 0.8d); // Range is 0.8 - 1.6 (1.6 is Minecraft default)

        if (value != null)
            jsonObject.addProperty("value", value);

        jsonArray.add(jsonObject);

        LabyModProtocol.sendLabyModMessage(receiver, "account_subtitle", jsonArray);
    }
}
