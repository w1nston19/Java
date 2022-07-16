package bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions;

public class PlayableNotFoundException extends Exception {
    public PlayableNotFoundException() {
    }

    public PlayableNotFoundException(String message) {
        super(message);
    }

    public PlayableNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
