package bg.uni.sofia.fmi.mjt.Labs.wallet.exception;

public class OfferPriceException extends RuntimeException {
    public OfferPriceException() {
    }

    public OfferPriceException(String message) {
        super(message);
    }

    public OfferPriceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
