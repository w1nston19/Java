package bg.uni.sofia.fmi.mjt.Spotify.exceptions;

public class PlaylistNotFoundException extends Exception {
    public PlaylistNotFoundException() {
    }

    public PlaylistNotFoundException(String message) {
        super(message);
    }

    public PlaylistNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
