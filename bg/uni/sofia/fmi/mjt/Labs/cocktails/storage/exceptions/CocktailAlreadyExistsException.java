package bg.uni.sofia.fmi.mjt.Labs.cocktails.storage.exceptions;

public class CocktailAlreadyExistsException extends Exception {

    public CocktailAlreadyExistsException() {

    }

    public CocktailAlreadyExistsException(String message) {
        super(message);
    }

    public CocktailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
