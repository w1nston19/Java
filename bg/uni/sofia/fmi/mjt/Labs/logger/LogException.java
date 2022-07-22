package bg.uni.sofia.fmi.mjt.Labs.logger;

public class LogException extends RuntimeException {
    public LogException() {
    }

    public LogException(String message) {
        super(message);
    }

    public LogException(String message, Throwable cause) {
        super(message, cause);
    }
}
