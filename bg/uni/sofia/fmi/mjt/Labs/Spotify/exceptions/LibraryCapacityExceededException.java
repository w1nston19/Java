package bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions;

public class LibraryCapacityExceededException extends Exception {
    public LibraryCapacityExceededException() {
    }

    public LibraryCapacityExceededException(String message) {
        super(message);
    }

    public LibraryCapacityExceededException(String message, Exception e) {
        super(message, e);
    }
}
