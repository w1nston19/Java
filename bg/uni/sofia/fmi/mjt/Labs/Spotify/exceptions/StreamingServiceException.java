package bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions;

public class StreamingServiceException extends Exception {
    public StreamingServiceException() {
    }

    public StreamingServiceException(String message) {
        super(message);
    }


    public StreamingServiceException(String message, Exception e) {
        super(message, e);
    }
}
