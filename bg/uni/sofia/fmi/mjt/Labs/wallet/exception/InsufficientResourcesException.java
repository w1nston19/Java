package bg.uni.sofia.fmi.mjt.Labs.wallet.exception;

public class InsufficientResourcesException extends RuntimeException {
    public InsufficientResourcesException() {
    }

    public InsufficientResourcesException(String message) {
        super(message);
    }

    public InsufficientResourcesException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
