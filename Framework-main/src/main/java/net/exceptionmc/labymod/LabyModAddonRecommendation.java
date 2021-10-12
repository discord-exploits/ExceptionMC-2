package net.exceptionmc.labymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class LabyModAddonRecommendation {

    private final Player player;
    private final JsonObject jsonObject = new JsonObject();

    public LabyModAddonRecommendation(Player player) {
        this.player = player;
    }

    public LabyModAddonRecommendation recommend(String addonUuid, Boolean isRequired) {
        JsonArray jsonArray = new JsonArray();
        JsonObject addon = new JsonObject();

        addon.addProperty("uuid", addonUuid);
        addon.addProperty("required", isRequired);

        jsonArray.add(addon);

        jsonObject.add("addons", jsonArray);

        return this;
    }

    public void submit() {
        LabyModProtocol.sendLabyModMessage(player, "addon_recommendation", jsonObject);
    }
}
