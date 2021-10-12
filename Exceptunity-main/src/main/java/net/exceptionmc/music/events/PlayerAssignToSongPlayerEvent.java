package net.exceptionmc.music.events;

import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAssignToSongPlayerEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final SongPlayer songPlayer;

    public PlayerAssignToSongPlayerEvent(Player player, SongPlayer songPlayer) {

        this.player = player;
        this.songPlayer = songPlayer;
    }

    @Override
    public String getEventName() {

        return super.getEventName();
    }

    @Override
    public HandlerList getHandlers() {

        return handlerList;
    }

    public Player getPlayer() {

        return player;
    }

    public SongPlayer getSongPlayer() {

        return songPlayer;
    }
}
