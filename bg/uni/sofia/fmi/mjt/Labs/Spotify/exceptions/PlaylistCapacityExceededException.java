package bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions;

public class PlaylistCapacityExceededException extends Exception {
    public PlaylistCapacityExceededException() {
    }

    public PlaylistCapacityExceededException(String message) {
        super(message);
    }

    public PlaylistCapacityExceededException(String message, Exception e) {
        super(message, e);
    }
}
