package bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions;

public class EmptyLibraryException extends Exception {
    public EmptyLibraryException() {
    }

    public EmptyLibraryException(String message) {
        super(message);
    }

    public EmptyLibraryException(String message, Exception e) {
        super(message, e);
    }
}
