package bg.uni.sofia.fmi.mjt.Spotify.playlist;

import bg.uni.sofia.fmi.mjt.Spotify.exceptions.PlaylistCapacityExceededException;
import bg.uni.sofia.fmi.mjt.Spotify.playable.Playable;


public class UserPlaylist implements Playlist {
    final static int CAPACITY = 20;

    String name;

    Playable[] playables;
    int numItems;

    public UserPlaylist(String name) {
        this.name = name;
        playables = new Playable[CAPACITY];
        numItems = 0;
    }

    @Override
    public void add(Playable playable) throws PlaylistCapacityExceededException {
        if (numItems == CAPACITY) {
            throw new PlaylistCapacityExceededException("Cannot add playable; playlist is full");
        }
        playables[numItems] = playable;
        numItems += 1;
    }

    @Override
    public String getName() {
        return name;
    }
}
