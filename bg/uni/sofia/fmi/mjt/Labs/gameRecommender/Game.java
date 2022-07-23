package bg.uni.sofia.fmi.mjt.Labs.gameRecommender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public record Game(String name, String platform,
                   LocalDate release_date, String summary,
                   int meta_score, double user_review) {


    private static final int nameIdx = 0;
    private static final int platformIdx = 1;
    private static final int release_dateIdx = 2;
    private static final int summaryIdx = 3;
    private static final int meta_scoreIdx = 4;
    private static final int user_reviewIdx = 5;


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public static Game of(String input) {
        validateNotNull(input);
        validateNotEmpty(input);

        String[] data = input.split(",");

        if (data.length != 6) {
            throw new IllegalArgumentException();
        }
        for (String str : data) {
            validateNotNull(input);
            validateNotEmpty(str);
        }

        LocalDate date = LocalDate.parse(data[release_dateIdx], DATE_FORMATTER);

        return new Game(data[nameIdx], data[platformIdx],
                date,
                data[summaryIdx], Integer.parseInt(data[meta_scoreIdx]),
                Double.parseDouble(data[user_reviewIdx]));
    }


    private static void validateNotNull(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateNotEmpty(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
