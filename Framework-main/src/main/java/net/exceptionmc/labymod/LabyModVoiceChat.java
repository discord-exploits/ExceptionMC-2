package net.exceptionmc.labymod;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("unused")
public class LabyModVoiceChat {

    public void disableVoiceChat(Player player) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("allowed", false);

        LabyModProtocol.sendLabyModMessage(player, "voicechat", jsonObject);
    }

    public void mutePlayerTo(Player player, UUID mutedPlayer, Boolean muted) {
        JsonObject voicechatObject = new JsonObject();
        JsonObject mutePlayerObject = new JsonObject();

        mutePlayerObject.addProperty("mute", muted);
        mutePlayerObject.addProperty("target", mutedPlayer.toString());

        voicechatObject.add("mute_player", mutePlayerObject);

        LabyModProtocol.sendLabyModMessage(player, "voicechat", voicechatObject);
    }

    public void setSettings(Player player, Boolean keepSettings, Boolean required, Boolean forceEnabled,
                            Boolean continuousTransmission, Integer microphoneVolume, Integer surroundVolume,
                            Integer surroundRange) {

        JsonObject voicechatObject = new JsonObject();
        JsonObject requestSettingsObject = new JsonObject();
        JsonObject settingsObject = new JsonObject();

        settingsObject.addProperty("enabled", forceEnabled);
        settingsObject.addProperty("surroundRange", surroundRange); // (5 - 18, Default: 10)
        settingsObject.addProperty("surroundVolume", surroundVolume); //(0 - 10, Default: 10)
        settingsObject.addProperty("microphoneVolume", microphoneVolume); //(0 - 10, Default 10)
        settingsObject.addProperty("continuousTransmission", continuousTransmission);

        requestSettingsObject.addProperty("required", required);
        requestSettingsObject.add("settings", settingsObject);

        voicechatObject.addProperty("keep_settings_on_server_switch", keepSettings);

        voicechatObject.add("request_settings", requestSettingsObject);

        LabyModProtocol.sendLabyModMessage(player, "voicechat", voicechatObject);
    }
}
