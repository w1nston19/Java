package bg.uni.sofia.fmi.mjt.Homeworks.GameRecommender.recommender;

import bg.uni.sofia.fmi.mjt.Homeworks.GameRecommender.BoardGame;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class BoardGamesRecommender implements Recommender {
    private final String regex = "[\\p{IsPunctuation}\\p{IsWhite_Space}]+";
    List<BoardGame> games;
    List<String> stopWords;

    Map<String, Set<Integer>> index;

    Map<Integer, BoardGame> idMap;

    BoardGamesRecommender(Path datasetZipFile, String datasetFileName, Path stopwordsFile){
        initializeStructures();
        try (ZipFile zipFile = new ZipFile(datasetZipFile.toFile())){
            ZipEntry entry = zipFile.getEntry(datasetFileName);
            Path pathToDataset = Path.of(entry.getName());
            generateGames(Files.newBufferedReader(pathToDataset));

        }catch (Exception e){
            e.printStackTrace();
        }


        try (BufferedReader bufferedReader = Files.newBufferedReader(stopwordsFile)){
            generateStopWords(bufferedReader);
        }catch (Exception e){
            e.printStackTrace();
        }

        generateIndex();
    }

    BoardGamesRecommender(Reader dataset, Reader stopwords){
        initializeStructures();
        try(BufferedReader br = new BufferedReader(dataset)){
            generateGames(br);
        }catch (Exception e){
            e.printStackTrace();
        }

        try(BufferedReader br = new BufferedReader(stopwords)){
            generateStopWords(br);
        }catch (Exception e){
            e.printStackTrace();
        }
        generateIndex();
    }

    private void initializeStructures(){
        this.games = new ArrayList<>();
        this.stopWords = new ArrayList<>();
        this.idMap = new HashMap<>();
        this.index = new HashMap<>();
    }
    private void generateStopWords(BufferedReader bf) throws IOException {
        String word;
        while((word = bf.readLine()) != null){
            this.stopWords.add(word);
        }
    }

    private void generateGames(BufferedReader bf) throws IOException {
        String game;
        boolean header = false;
        while((game = bf.readLine()) != null){
            if(!header){
                header = true;
                continue;
            }
            this.games.add(BoardGame.of(game));
        }

        for(BoardGame boardGame : games){
            idMap.put(boardGame.id(), boardGame);
        }
    }

    private void generateIndex() {
        Set<String> allWords = new HashSet<>();
        games.stream()
                .map(BoardGame::description)
                .forEach(description -> allWords.addAll(Arrays.stream(description.split(regex)).toList()));

        this.index = new HashMap<>();

        allWords.stream()
                .filter(word -> !stopWords.contains(word))
                .forEach(this::addToIndex);

    }

    private void addToIndex(String word){
        this.index.put(word,games.stream()
                .filter(boardGame -> boardGame.description().contains(word))
                .map(BoardGame::id).collect(Collectors.toSet()));

    }

    @Override
    public Collection<BoardGame> getGames() {
        return Collections.unmodifiableCollection(this.games);
    }

    @Override
    public List<BoardGame> getSimilarTo(BoardGame game, int n) {

        Map<BoardGame, Double> distances = new HashMap<>();
        for(BoardGame boardGame : games){
            if(boardGame.id() == game.id()){
                continue;
            }
            distances.put(boardGame, boardGame.getDistance(game));
        }

        return distances.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }


    @Override
    public List<BoardGame> getByDescription(String... keywords) {
        Map<Integer, Integer> gamesKeyword = new HashMap<>();
        List<String> clearKeywords = new ArrayList<>();
        for(String key : keywords){
            if(!stopWords.contains(key)){
                clearKeywords.add(key);
            }
        }

        for(String word : clearKeywords){
            var contained = index.get(word);

            if(contained != null){
                for(Integer gameId : contained){
                    gamesKeyword.putIfAbsent(gameId,0);
                    gamesKeyword.put(gameId, gamesKeyword.get(gameId) + 1);
                }
            }

        }

        List<Integer> sortedIds = gamesKeyword.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Map.Entry::getKey).toList();

        return sortedIds.stream()
                .map(id -> idMap.get(id))
                .toList();
    }

    @Override
    public void storeGamesIndex(Writer writer) {
        final String format = "%s: %s\n";

        for(String word : index.keySet()){
            String ids = index.get(word)
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            try{
                writer.write(String.format(format, word, ids));
                writer.flush();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
