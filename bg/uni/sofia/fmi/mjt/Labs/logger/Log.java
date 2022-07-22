package bg.uni.sofia.fmi.mjt.Labs.logger;

import java.time.LocalDateTime;

public record Log(Level level, LocalDateTime timestamp, String packageName, String message) {
    private static final String FORMAT = "[%s]|%s|%s|%s" + System.lineSeparator();

    @Override
    public String toString() {
        return String.format(FORMAT, level, timestamp, packageName, message);
    }
}