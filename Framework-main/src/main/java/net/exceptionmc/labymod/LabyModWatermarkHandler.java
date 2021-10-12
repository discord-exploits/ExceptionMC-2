package net.exceptionmc.labymod;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class LabyModWatermarkHandler {

    public void whyTheHeckDoYouEvenWantToTurnThisCrapOn(Player player, Boolean visible) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("visible", visible);

        LabyModProtocol.sendLabyModMessage(player, "watermark", jsonObject);
    }
}
