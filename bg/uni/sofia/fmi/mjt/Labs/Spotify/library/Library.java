package bg.uni.sofia.fmi.mjt.Labs.Spotify.library;

import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.EmptyLibraryException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.LibraryCapacityExceededException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.PlaylistNotFoundException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.playlist.Playlist;

public interface Library {

    /**
     * Adds the provided playlist to the library.
     *
     * @throws LibraryCapacityExceededException when the library capacity (fixed to 21) is exceeded.
     */
    void add(Playlist playlist) throws LibraryCapacityExceededException;

    /**
     * Removes the playlist with the given name from the library.
     *
     * @throws IllegalArgumentException  if the given playlist name matches the default "Liked Content" playlist name.
     * @throws EmptyLibraryException     if the library is empty.
     * @throws PlaylistNotFoundException if the playlist with the given name is not present in the library.
     */
    void remove(String name) throws EmptyLibraryException, PlaylistNotFoundException;

    /**
     * Returns the default "Liked Content" playlist from the library.
     */
    Playlist getLiked();

}