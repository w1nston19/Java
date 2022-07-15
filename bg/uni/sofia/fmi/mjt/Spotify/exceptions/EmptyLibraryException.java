package bg.uni.sofia.fmi.mjt.Spotify.exceptions;

public class EmptyLibraryException extends Exception {
    public EmptyLibraryException() {
    }

    ;

    public EmptyLibraryException(String message) {
        super(message);
    }

    ;

    public EmptyLibraryException(String message, Exception e) {
        super(message, e);
    }
}
