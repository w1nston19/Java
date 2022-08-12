package bg.uni.sofia.fmi.mjt.Homeworks.GameRecommender.analyzer;

import bg.uni.sofia.fmi.mjt.Homeworks.GameRecommender.BoardGame;

import java.util.*;

public class BoardGamesStatisticsAnalyzer implements StatisticsAnalyzer{
    Collection<BoardGame> games;
    Map<String, Integer> categoriesCount;
    Map<String, Double> categoriesPlayingTime;

    BoardGamesStatisticsAnalyzer(Collection<BoardGame> games){
        this.games = games;
        categoriesCount = new HashMap<>();
        categoriesPlayingTime = new HashMap<>();
        calculateStatisticsCategories();
    }

    @Override
    public List<String> getNMostPopularCategories(int n) {
        return categoriesCount.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public double getAverageMinAge() {
        int minAge = 0;
        for(BoardGame game : games){
            minAge += game.minAge();
        }
        return (double) minAge / games.size();
    }

    @Override
    public double getAveragePlayingTimeByCategory(String category) {
        return games.stream()
                .filter((boardGame -> boardGame.categories().contains(category)))
                .mapToDouble(BoardGame::playingTimeMins)
                .average()
                .orElse(0.0);
    }

    @Override
    public Map<String, Double> getAveragePlayingTimeByCategory() {
        return this.categoriesPlayingTime;
    }

    private void calculateStatisticsCategories(){
        for(BoardGame game : games){
            for(String category : game.categories()){
                categoriesCount.putIfAbsent(category, 0);
                categoriesCount.put(category, categoriesCount.get(category) + 1);
                categoriesPlayingTime.putIfAbsent(category, 0.0);
                categoriesPlayingTime.put(category, categoriesPlayingTime.get(category) + game.playingTimeMins());
            }
        }

        categoriesPlayingTime.replaceAll(
                (c, v) -> categoriesPlayingTime.get(c) / categoriesCount.get(c));
    }
}
