package bg.uni.sofia.fmi.mjt.Labs.cocktails.command;

import bg.uni.sofia.fmi.mjt.Labs.cocktails.Cocktail;
import bg.uni.sofia.fmi.mjt.Labs.cocktails.Ingredient;
import bg.uni.sofia.fmi.mjt.Labs.cocktails.storage.CocktailStorage;
import bg.uni.sofia.fmi.mjt.Labs.cocktails.storage.exceptions.CocktailNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CommandExecutor {
    private static final String CREATE = "create";
    private static final String GET = "get";
    private static final String DISCONNECT = "disconnect";

    private static final String GET_COCKTAILS = "{\"status\":\"OK\",\"cocktails\":[%s]}";
    private static final String GET_COCKTAIL_BY_NAME = "{\"status\":\"OK\",\"cocktail\":[%s]}";

    private static final String GET_UNSUCCESSFUL =
            "{\"status\":\"ERROR\",\"errorMessage\":\"cocktail %s doesn't exist\"}";
    private static final String CREATE_SUCCESSFUL = "{\"status\":\"CREATED\"}";
    private static final String CREATE_UNSUCCESSFUL =
            "{\"status\":\"ERROR\",\"errorMessage\":\"cocktail %s already exists\"}";
    private static final String INVALID_ARGS_COUNT_MESSAGE_FORMAT =
            "Invalid count of arguments: \"%s\" expects \"%s\"  arguments.";

    private final CocktailStorage storage;

    public CommandExecutor(CocktailStorage storage) {
        this.storage = storage;
    }

    public String executeCommand(Command command) {
        return switch (command.name()) {
            case CREATE -> create(command.args());
            case GET -> get(command.args());
            case DISCONNECT -> "Disconnected from the server";
            default -> "Unknown command";
        };
    }

    private String create(List<String> cocktailArgs) {
        if (cocktailArgs.size() < 2) {
            throw new RuntimeException(String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, "CREATE", "at least 2"));
        }
        String cocktailName = cocktailArgs.get(0);
        Set<Ingredient> ingredients = new HashSet<>();

        for (int i = 1; i < cocktailArgs.size(); i++) {
            ingredients.add(Ingredient.of(cocktailArgs.get(i)));
        }

        Cocktail result = new Cocktail(cocktailName, ingredients);

        try {
            this.storage.createCocktail(result);
        } catch (Exception e) {
            return String.format(CREATE_UNSUCCESSFUL, cocktailName);
        }
        return CREATE_SUCCESSFUL;
    }

    private String get(List<String> cocktailArgs) {
        if (cocktailArgs.isEmpty()) {
            throw new RuntimeException(String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, "GET", "at least 1"));
        }

        String getInfo = cocktailArgs.get(0);
        return switch (getInfo) {
            case "all" -> getALL();
            case "by-name" -> getByName(cocktailArgs.subList(1, cocktailArgs.size()));
            case "by-ingredient" -> getByIngredient(cocktailArgs.subList(1, cocktailArgs.size()));
            default -> "Unknown command";
        };
    }

    private String getALL() {
        List<Cocktail> cocktails = this.storage.getCocktails().stream().toList();
        return buildResult(cocktails);
    }

    private String getByName(List<String> args) {
        if (args.isEmpty()) {
            throw new RuntimeException(String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, "GET BY-NAME", "1"));
        }

        Cocktail result;
        try {
            result = storage.getCocktail(args.get(0));
        } catch (CocktailNotFoundException e) {
            return String.format(GET_UNSUCCESSFUL, args.get(0));
        }

        return String.format(GET_COCKTAIL_BY_NAME, result.toString());
    }

    private String getByIngredient(List<String> args) {
        if (args.isEmpty()) {
            throw new RuntimeException(String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, "GET BY-NAME", "1"));
        }

        List<Cocktail> cocktails = storage.getCocktailsWithIngredient(args.get(0)).stream().toList();

        return buildResult(cocktails);
    }

    private String buildResult(List<Cocktail> cocktails) {
        StringBuilder builder = new StringBuilder();

        for (Cocktail cocktail : cocktails) {
            builder.append(cocktail.toString()).append(',');
        }
        if (!builder.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return String.format(GET_COCKTAILS, builder);
    }


}
