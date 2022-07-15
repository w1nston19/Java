package bg.uni.sofia.fmi.mjt.Spotify.playlist;

import bg.uni.sofia.fmi.mjt.Spotify.exceptions.PlaylistCapacityExceededException;
import bg.uni.sofia.fmi.mjt.Spotify.playable.Playable;

public interface Playlist {

    /**
     * Adds the given playable content to the playlist.
     *
     * @throws PlaylistCapacityExceededException when the playlist has exceeded its predefined capacity of 20 items.
     */
    void add(Playable playable) throws PlaylistCapacityExceededException;

    /**
     * Returns the name of the playlist.
     */
    String getName();

}