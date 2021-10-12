package net.exceptionmc.util;

import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoStereoMode;
import com.xxmicloxx.NoteBlockAPI.songplayer.PositionSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class NotePlayerUtil {

    public static HashMap<UUID, SongPlayer> uuidSongPlayerHashMap = new HashMap<>();

    public void playByRadioSongPlayer(Player player, File songFile) {
        if (!uuidSongPlayerHashMap.containsKey(player.getUniqueId())) {
            RadioSongPlayer radioSongPlayer = new RadioSongPlayer(NBSDecoder.parse(songFile));

            radioSongPlayer.addPlayer(player);
            radioSongPlayer.setChannelMode(new MonoStereoMode());
            radioSongPlayer.setPlaying(true);

            uuidSongPlayerHashMap.put(player.getUniqueId(), radioSongPlayer);
        }
    }

    public void playByPositionSongPlayer(Player player, File songFile, Location targetLocation, Integer distance) {
        if (!uuidSongPlayerHashMap.containsKey(player.getUniqueId())) {
            PositionSongPlayer positionSongPlayer = new PositionSongPlayer(NBSDecoder.parse(songFile));

            positionSongPlayer.addPlayer(player);
            positionSongPlayer.setTargetLocation(targetLocation);
            positionSongPlayer.setDistance(distance);
            positionSongPlayer.setPlaying(true);

            uuidSongPlayerHashMap.put(player.getUniqueId(), positionSongPlayer);
        }
    }

    public void stop(Player player) {
        SongPlayer songPlayer = uuidSongPlayerHashMap.get(player.getUniqueId());
        songPlayer.destroy();

        uuidSongPlayerHashMap.remove(player.getUniqueId());
    }
}
