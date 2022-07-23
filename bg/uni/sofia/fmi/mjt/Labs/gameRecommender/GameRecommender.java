package bg.uni.sofia.fmi.mjt.Labs.gameRecommender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameRecommender {

    List<Game> games;

    /**
     * Loads the dataset from the given {@code dataInput} stream.
     *
     * @param dataInput java.io.Reader input stream from which the dataset can be read
     */
    public GameRecommender(Reader dataInput) {
        games = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(dataInput)) {
            String gameInfo;

            while ((gameInfo = bf.readLine()) != null) {
                games.add(Game.of(gameInfo));
            }

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

    }

    /**
     * @return All games from the dataset as an unmodifiable copy.
     * If the dataset is empty, return an empty collection
     */
    public List<Game> getAllGames() {
        return List.copyOf(games);
    }

    /**
     * Returns all games in the dataset released after the specified {@code date} as an unmodifiable list.
     * If no games have been released after the given date, returns an empty list.
     *
     * @param date
     * @return a list of all games released after {@code date}, in an undefined order.
     */
    public List<Game> getGamesReleasedAfter(LocalDate date) {
        return games.stream()
                .filter((game) -> game.release_date().isAfter(date))
                .toList();
    }

    /**
     * Returns the top {@code n} games by user review score.
     *
     * @param n maximum number of games to return
     *          If {@code n} exceeds the total number of games in the dataset, return all games.
     * @return unmodifiable list of the games sorted by user review score in descending order
     * @throws IllegalArgumentException in case {@code n} is a negative number.
     */
    public List<Game> getTopNUserRatedGames(int n) {
        return games.stream()
                .sorted(Comparator.comparing(Game::user_review))
                .limit(n)
                .toList();
    }

    /**
     * Returns a list (without repetitions) of all years in which at least one game with meta score
     * {@code minimalScore} or higher has been released. The order of the years in the result is undefined.
     * If there are no such years, return an empty list.
     *
     * @param minimalScore
     * @return the years when a game with at least {@code minimalScore} meta score has been released
     */
    public List<Integer> getYearsWithTopScoringGames(int minimalScore) {
        return games.stream()
                .filter((Game game) -> game.meta_score() >= minimalScore)
                .map(game -> game.release_date().getYear())
                .toList();
    }

    /**
     * Returns the names of all games in the dataset released in {@code year} as a comma-separated String.
     * Each comma in the result must be followed by a space. The order of the game names in the result is undefined.
     * If no games have been released in the given year, returns an empty String.
     *
     * @param year
     * @return a comma-separated String containing all game names released in {@code year}
     */
    public String getAllNamesOfGamesReleasedIn(int year) {
        return games.stream()
                .filter(game -> game.release_date().getYear() == year)
                .map(Game::name)
                .collect(Collectors.joining(","));
    }

    /**
     * Returns the game for the specified {@code platform} with the highest user review score.
     *
     * @param platform the name of the platform
     * @return the game for the specified {@code platform} with the highest review score
     * @throws NoSuchElementException if there is no game at all released for the specified platform,
     *                                or if {@code platform} is null or an empty String.
     */
    public Game getHighestUserRatedGameByPlatform(String platform) {
        return games.stream()
                .filter(game -> game.platform().equals(platform))
                .max(Comparator.comparing(Game::user_review))
                .orElseThrow(NoSuchElementException::new);

    }

    /**
     * Returns all games by platform. The result should map a platform name to the set of all games
     * released for this platform.
     *
     * @return all games by platform
     */
    public Map<String, Set<Game>> getAllGamesByPlatform() {
        return games.stream()
                .collect(Collectors.groupingBy(Game::platform, Collectors.toSet()));
    }

    /**
     * Returns the number of years a game platform has been live.
     * The lifecycle of a platform is assumed to start and end with the release year of the oldest and newest game
     * released for this platform (the exact date is not significant).
     * In case all games for a given platform have been released in a single year, return 1.
     * In case {@code platform} is null, blank or unknown in the dataset, return 0.
     *
     * @return the number of years a game platform has been live
     */
    public int getYearsActive(String platform) {
        IntStream is = games.stream()
                .filter(game -> game.platform().equals(platform))
                .mapToInt(game -> game.release_date().getYear())
                .sorted();

        OptionalInt first = is.min();
        OptionalInt second = is.max();

        if (first == second) {
            return 1;
        }

        if (first.isEmpty() || second.isEmpty()) {
            return 0;
        }

        return second.getAsInt() - first.getAsInt();
    }

    /**
     * Returns the games whose summary contains all {@code keywords} specified, as an unmodifiable list.
     * <p>
     * If there are no such games, return an empty list.
     * In case no keywords are specified, or any of the keywords is null or blank, the result is undefined.
     *
     * @param keywords the keywords to search for in the game summary
     * @return the games whose summary contains the specified keywords
     */
    public List<Game> getGamesSimilarTo(String... keywords) {
        return games.stream()
                .filter(game -> containsAll(game.summary(), keywords))
                .toList();
    }

    private boolean containsAll(String base, String... keywords) {
        long numWords = Arrays.stream(keywords)
                .filter(base::contains).count();

        return numWords == keywords.length;
    }
}