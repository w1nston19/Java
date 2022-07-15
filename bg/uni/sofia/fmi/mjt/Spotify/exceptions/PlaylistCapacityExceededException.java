package bg.uni.sofia.fmi.mjt.Spotify.exceptions;

public class PlaylistCapacityExceededException extends Exception {
    public PlaylistCapacityExceededException() {
    }

    ;

    public PlaylistCapacityExceededException(String message) {
        super(message);
    }

    ;

    public PlaylistCapacityExceededException(String message, Exception e) {
        super(message, e);
    }
}
