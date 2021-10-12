package net.exceptionmc.labymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("unused")
public class LabyModStickerAPI {

    public void playSticker(Player receiver, UUID npcUuid, Short stickerId) {
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("uuid", npcUuid.toString());
        jsonObject.addProperty("sticker_id", stickerId);

        jsonArray.add(jsonArray);

        LabyModProtocol.sendLabyModMessage(receiver, "sticker_api", jsonArray);
    }
}
