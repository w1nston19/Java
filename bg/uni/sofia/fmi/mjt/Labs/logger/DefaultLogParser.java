package bg.uni.sofia.fmi.mjt.Labs.logger;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DefaultLogParser implements LogParser {
    List<Log> allLogs;

    public DefaultLogParser(Path logsFilePath) {

        this.allLogs = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(logsFilePath)) {
            String logLine;
            while ((logLine = reader.readLine()) != null) {
                allLogs.add(toLog(logLine));
            }

        } catch (IOException e) {
            throw new LogException(e.getMessage(), e.getCause());
        }
    }

    private Log toLog(String s) {
        final int levelIdx = 0;
        final int timeStampIdx = 1;
        final int packageIdx = 2;
        final int messageIdx = 3;


        String[] arr = s.split("\\|");
        String level = arr[levelIdx].substring(1, arr[levelIdx].length() - 1);

        return new Log(Level.valueOf(level),
                LocalDateTime.parse(arr[timeStampIdx],
                        DateTimeFormatter.ISO_DATE_TIME),
                arr[packageIdx], arr[messageIdx]);
    }

    @Override
    public List<Log> getLogs(Level level) {
        List<Log> levelLogs = new ArrayList<>();

        for (Log log : allLogs) {
            if (log.level().getLevel() == level.getLevel()) {
                levelLogs.add(log);
            }
        }

        return levelLogs;
    }

    @Override
    public List<Log> getLogs(LocalDateTime from, LocalDateTime to) {
        List<Log> timeLogs = new ArrayList<>();

        for (Log log : allLogs) {
            if (log.timestamp().isAfter(from) && log.timestamp().isBefore(to)) {
                timeLogs.add(log);
            }
        }

        return timeLogs;
    }

    @Override
    public List<Log> getLogsTail(int n) {
        if (n > allLogs.size()) {
            return allLogs;
        }
        return allLogs.subList(allLogs.size() - n, allLogs.size());
    }
}
