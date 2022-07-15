package bg.uni.sofia.fmi.mjt.Spotify.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {
    }

    ;

    public AccountNotFoundException(String message) {
        super(message);
    }

    ;

    public AccountNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
