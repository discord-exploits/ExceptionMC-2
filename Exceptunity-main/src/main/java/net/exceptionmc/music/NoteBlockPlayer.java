package net.exceptionmc.music;

import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoStereoMode;
import com.xxmicloxx.NoteBlockAPI.songplayer.PositionSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import net.exceptionmc.music.events.PlayerAlreadyAssignedSongPlayerEvent;
import net.exceptionmc.music.events.PlayerAssignToSongPlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public class NoteBlockPlayer {

    /**
     * This HashMap is required to get the current SongPlayer
     * of the player if you want to destroy the current playing.
     */
    private static final HashMap<UUID, SongPlayer> currentSongPlayerAssignments = new HashMap<>();

    /**
     * Call up all currently assigned SongPlayer#s with the matching unique id of the assigned player.
     *
     * @return currentSongPlayerAssignments as uuidSongPlayerHashMap
     */
    public static HashMap<UUID, SongPlayer> getCurrentSongPlayerAssignments() {

        return currentSongPlayerAssignments;
    }

    /**
     * The RadioSongPlayer will play the provided song to the player at a constant volume
     * regardless of how far the player is away from the start location of the RadioSongPlayer.
     *
     * @param player The song will be played to this player.
     * @param song   This song will be played to this player.
     */
    public void startRadioSongPlayer(Player player, File song) {
        if (isAlreadyAssignedToSongPlayer(player)) {

            RadioSongPlayer radioSongPlayer = new RadioSongPlayer(NBSDecoder.parse(song));
            radioSongPlayer.addPlayer(player);
            radioSongPlayer.setChannelMode(new MonoStereoMode());
            radioSongPlayer.setPlaying(true);

            currentSongPlayerAssignments.put(player.getUniqueId(), radioSongPlayer);

            callEvent(new PlayerAssignToSongPlayerEvent(player, radioSongPlayer));
        } else {

            SongPlayer songPlayer = currentSongPlayerAssignments.get(player.getUniqueId());
            callEvent(new PlayerAlreadyAssignedSongPlayerEvent(player, songPlayer));
        }
    }

    /**
     * The PositionSongPlayer will play the provided song to the player,
     * provided that he is within the range (distance) of the target location.
     *
     * @param player   The song will be played to this player.
     * @param song     This song will be played to the player.
     * @param location The song will be played to the player,
     *                 provided that he is within the range
     *                 of the target Location.
     * @param distance This value defines the range of the
     *                 music to be played measured from the
     *                 target Location.
     */
    public void startPositionSongPlayer(Player player, File song, Location location, Integer distance) {
        if (!isAlreadyAssignedToSongPlayer(player)) {
            PositionSongPlayer positionSongPlayer = new PositionSongPlayer(NBSDecoder.parse(song));
            positionSongPlayer.addPlayer(player);
            positionSongPlayer.setTargetLocation(location);
            positionSongPlayer.setDistance(distance);
            positionSongPlayer.setPlaying(true);

            currentSongPlayerAssignments.put(player.getUniqueId(), positionSongPlayer);

            callEvent(new PlayerAssignToSongPlayerEvent(player, positionSongPlayer));
        } else {

            SongPlayer songPlayer = currentSongPlayerAssignments.get(player.getUniqueId());
            callEvent(new PlayerAlreadyAssignedSongPlayerEvent(player, songPlayer));
        }
    }

    /**
     * Inquire whether the player has been already assigned to a
     * SongPlayer otherwise call the PlayerAlreadyAssignedSongPlayerEvent
     *
     * @param player This player is searched for in the currentSongPlayerAssignments list.
     * @return true if the player got already assigned to a SongPlayer
     */
    public boolean isAlreadyAssignedToSongPlayer(Player player) {

        return currentSongPlayerAssignments.containsKey(player.getUniqueId());
    }

    /**
     * This function destroys the player's SongPlayer regardless of which SongPlayer it is assigned to.
     *
     * @param player That player's SongPlayer will be destroyed.
     */
    public void stop(Player player) {
        if (isAlreadyAssignedToSongPlayer(player)) {
            SongPlayer songPlayer = currentSongPlayerAssignments.get(player.getUniqueId());
            songPlayer.destroy();

            currentSongPlayerAssignments.remove(player.getUniqueId());
        }
    }

    private void callEvent(Event event) {

        Bukkit.getPluginManager().callEvent(event);
    }
}
