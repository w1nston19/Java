package bg.uni.sofia.fmi.mjt.Labs.wallet.exception;

public class WalletException extends Exception {
    public WalletException() {
    }

    public WalletException(String message) {
        super(message);
    }

    public WalletException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
