package bg.uni.sofia.fmi.mjt.Labs.cocktails.storage.exceptions;

public class CocktailNotFoundException extends Exception {
    public CocktailNotFoundException() {

    }

    public CocktailNotFoundException(String message) {
        super(message);
    }

    public CocktailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
