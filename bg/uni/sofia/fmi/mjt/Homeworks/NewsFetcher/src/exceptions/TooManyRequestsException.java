package bg.uni.sofia.fmi.mjt.Homeworks.NewsFetcher.src.exceptions;

public class TooManyRequestsException extends Exception {
    public TooManyRequestsException(String message) {
        super(message);
    }
}
