package bg.uni.sofia.fmi.mjt.Homeworks.GameRecommender;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record BoardGame(int id, String name, String description, int maxPlayers, int minAge, int minPlayers,
                        int playingTimeMins, Collection<String> categories, Collection<String> mechanics) {

    private static final int ID_IDX = 0;
    private static final int NAME_IDX = 4;
    private static final int DESCRIPTION_IDX = 8;
    private static final int MAX_PLAYERS_IDX = 1;
    private static final int MIN_AGE_IDX = 2;
    private static final int MIN_PLAYERS_IDX = 3;
    private static final int PLAYING_TIME_IDX = 5;
    private static final int CATEGORIES_IDX = 6;
    private static final int MECHANICS_IDX = 7;

    public static BoardGame of(String input){

        validateNotNull(input);
        validateNotEmpty(input);
        String[] tokens = input.split(";");

        for(String token : tokens){
            validateNotEmpty(token);
        }


        Collection<String> cats = List.of(tokens[CATEGORIES_IDX].split(","));
        Collection<String> mechanics = List.of(tokens[MECHANICS_IDX].split(","));

        return new BoardGame(
                Integer.parseInt(tokens[ID_IDX]),
                tokens[NAME_IDX], tokens[DESCRIPTION_IDX],
                Integer.parseInt(tokens[MAX_PLAYERS_IDX]),
                Integer.parseInt(tokens[MIN_AGE_IDX]),
                Integer.parseInt(tokens[MIN_PLAYERS_IDX]),
                Integer.parseInt(tokens[PLAYING_TIME_IDX]),
                cats, mechanics
                );
    }

    static void validateNotNull(Object o){
        if(o == null){
            throw new IllegalArgumentException();
        }
    }
    
    static void validateNotEmpty(String s){
        if(s.isEmpty()){
            throw new IllegalArgumentException();
        }
    }

    public  double getDistance(BoardGame other){
        return numericArgumentDistance(other) + getDiff(other);
    }

    public  double numericArgumentDistance(BoardGame other) {
        double sum = (Math.pow((other.playingTimeMins - this.playingTimeMins),2) +
                Math.pow((other.maxPlayers - this.maxPlayers),2) +
        Math.pow((other.minAge - this.minAge),2) +
                Math.pow((other.minPlayers - this.minPlayers),2));
        return Math.sqrt(sum);
    }

    public double getDiff(BoardGame other) {
        Set<String> tmpCat = new HashSet<>();
        tmpCat.addAll(categories);
        tmpCat.addAll(other.categories);
        
        Set<String> intersectionCat = new HashSet<>(this.categories);
        intersectionCat.retainAll(other.categories);
        
        double diffCat = tmpCat.size() - intersectionCat.size();

        Set<String> tmpMechanics = new HashSet<>();
        tmpMechanics.addAll(this.mechanics);
        tmpMechanics.addAll(other.mechanics);

        Set<String> intersectionMechanics = new HashSet<>(this.mechanics);
        intersectionMechanics.retainAll(other.mechanics);

        double diffMechanics = tmpMechanics.size() - intersectionMechanics.size();

        return diffMechanics + diffCat;
    }


}