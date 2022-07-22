package bg.uni.sofia.fmi.mjt.Labs.logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class DefaultLogger implements Logger {
    private final String LOG_FORMAT = "logs-%d.txt";
    private static int logID = -1;

    LoggerOptions options;

    Path currentFile;

    BufferedWriter writer;


    public DefaultLogger(LoggerOptions options) {
        this.options = options;
        try {
            makePath();
            openFile();
        } catch (IOException e) {
            if (options.shouldThrowErrors()) {
                throw new LogException(e.getMessage(), e.getCause());
            }
        }
    }

    public void makePath() {
        String name = String.format(LOG_FORMAT, ++logID);
        this.currentFile = Path.of(options.getDirectory(), name);
    }

    public void openFile() throws IOException {
        this.writer = Files.newBufferedWriter(currentFile);
    }

    public boolean canWriteToFile() throws IOException {
        return Files.size(currentFile) < options.getMaxFileSizeBytes();
    }

    @Override
    public void log(Level level, LocalDateTime timestamp, String message) {
        validateNotNull(level);
        validateNotNull(timestamp);
        validateNotNull(message);
        validateNotEmpty(message);

        if (level.getLevel() < options.getMinLogLevel().getLevel()) {
            return;
        }

        Log toLog = new Log(level, timestamp, options.getClazz().getPackageName(), message);

        try {
            if (!canWriteToFile()) {
                closeFile();
                makePath();
                openFile();
            }

            writer.write(toLog.toString());
            writer.flush();

        } catch (IOException e) {
            if (getOptions().shouldThrowErrors()) {
                throw new LogException(e.getMessage(), e.getCause());
            }
        }

    }

    public void closeFile() throws IOException {
        writer.close();
    }

    @Override
    public LoggerOptions getOptions() {
        return options;
    }

    @Override
    public Path getCurrentFilePath() {
        return currentFile;
    }

    public void validateNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

    public void validateNotEmpty(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
