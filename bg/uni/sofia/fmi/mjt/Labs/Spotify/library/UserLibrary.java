package bg.uni.sofia.fmi.mjt.Labs.Spotify.library;

import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.EmptyLibraryException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.LibraryCapacityExceededException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.PlaylistNotFoundException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.playlist.Playlist;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.playlist.UserPlaylist;

public class UserLibrary implements Library {
    final static int CAPACITY = 21;

    Playlist[] playlists;
    int num;

    public UserLibrary() {
        num = 1;
        playlists = new Playlist[CAPACITY];
        playlists[0] = new UserPlaylist("Liked Content");
    }

    @Override
    public void add(Playlist playlist) throws LibraryCapacityExceededException {
        if (num == CAPACITY) {
            throw new LibraryCapacityExceededException("Lib full");
        }
        for (int i = 0; i < CAPACITY - 1; i++) {
            if (playlists[i] == null) {
                playlists[i] = playlist;
            }
        }
        num += 1;
    }

    @Override
    public void remove(String name) throws EmptyLibraryException, PlaylistNotFoundException {
        if (name.equals("Liked Content")) {
            throw new IllegalArgumentException("Cant delete liked songs");
        }

        if (num == 1) {
            throw new EmptyLibraryException("Lib is empty");
        }

        for (Playlist playlist : playlists) {
            if (playlist != null && playlist.getName().equals(name)) {
                playlist = null;
                num = num - 1;
            }
        }

        throw new PlaylistNotFoundException("No playlist with this name");
    }

    @Override
    public Playlist getLiked() {
        return playlists[0];
    }
}
