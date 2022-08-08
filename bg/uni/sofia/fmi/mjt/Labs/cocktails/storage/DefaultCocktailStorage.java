package bg.uni.sofia.fmi.mjt.Labs.cocktails.storage;

import bg.uni.sofia.fmi.mjt.Labs.cocktails.Cocktail;
import bg.uni.sofia.fmi.mjt.Labs.cocktails.storage.exceptions.CocktailAlreadyExistsException;
import bg.uni.sofia.fmi.mjt.Labs.cocktails.storage.exceptions.CocktailNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DefaultCocktailStorage implements CocktailStorage {
    private List<Cocktail> cocktails;

    public DefaultCocktailStorage() {
        this.cocktails = new ArrayList<>();
    }


    @Override
    public void createCocktail(Cocktail cocktail) throws CocktailAlreadyExistsException {
        if (cocktails.contains(cocktail)) {
            throw new CocktailAlreadyExistsException();
        }
        cocktails.add(cocktail);
    }

    @Override
    public Collection<Cocktail> getCocktails() {
        if (cocktails.isEmpty()) {
            return Collections.emptyList();
        } else return List.copyOf(cocktails);
    }

    @Override
    public Collection<Cocktail> getCocktailsWithIngredient(String ingredientName) {
        return this.cocktails.stream()
                .filter(cocktail -> cocktail.contains(ingredientName))
                .toList();
    }

    @Override
    public Cocktail getCocktail(String name) throws CocktailNotFoundException {
        Cocktail wantedCocktail = null;
        for (Cocktail cocktail : cocktails) {
            if (cocktail.getName().equals(name)) {
                wantedCocktail = cocktail;
                break;
            }
        }
        if (wantedCocktail == null) {
            throw new CocktailNotFoundException();
        }
        return wantedCocktail;
    }
}
