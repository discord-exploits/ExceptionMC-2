package net.exceptionmc.labymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("unused")
public class LabyModEmoteAPI {

    public void playEmote(Player player, UUID npcUuid, Integer emoteId) {
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("uuid", npcUuid.toString());
        jsonObject.addProperty("emote_id", emoteId);

        jsonArray.add(jsonObject);

        LabyModProtocol.sendLabyModMessage(player, "emote_api", jsonArray);
    }
}
