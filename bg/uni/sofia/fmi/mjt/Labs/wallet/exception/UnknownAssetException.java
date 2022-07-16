package bg.uni.sofia.fmi.mjt.Labs.wallet.exception;

public class UnknownAssetException extends RuntimeException {
    public UnknownAssetException() {
    }

    public UnknownAssetException(String message) {
        super(message);
    }

    public UnknownAssetException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
