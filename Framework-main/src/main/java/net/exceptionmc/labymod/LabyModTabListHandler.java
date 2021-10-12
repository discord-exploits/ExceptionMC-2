package net.exceptionmc.labymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("unused")
public class LabyModTabListHandler {

    public void setBanner(Player player, String imageUrl) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("url", imageUrl);

        LabyModProtocol.sendLabyModMessage(player, "server_banner", jsonObject);
    }

    public void setCountryFlag(Player receiver, UUID uuid, String countryCode) {
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        JsonObject userObject = new JsonObject();

        userObject.addProperty("uuid", uuid.toString());
        userObject.addProperty("code", countryCode); // The country code (e.g. "us", "de")

        jsonArray.add(userObject);

        jsonObject.add("users", jsonArray);

        LabyModProtocol.sendLabyModMessage(receiver, "language_flag", jsonObject);
    }
}
