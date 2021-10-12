package net.exceptionmc.labymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class LabyModActionMenuHandler {

    private final Player player;
    private final JsonArray jsonArray = new JsonArray();
    public LabyModActionMenuHandler(Player player) {
        this.player = player;
    }

    public LabyModActionMenuHandler addEntry(String displayName, EnumActionType enumActionType, String value) {
        JsonObject entry = new JsonObject();

        entry.addProperty("displayName", displayName);
        entry.addProperty("type", enumActionType.name());
        entry.addProperty("value", value); // {name} will be replaced with the players name

        jsonArray.add(entry);

        return this;
    }

    public void submit() {
        LabyModProtocol.sendLabyModMessage(player, "user_menu_actions", jsonArray);
    }

    public enum EnumActionType {
        NONE,
        CLIPBOARD,
        RUN_COMMAND,
        SUGGEST_COMMAND,
        OPEN_BROWSER
    }
}
