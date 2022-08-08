package bg.uni.sofia.fmi.mjt.Labs.cocktails;

import java.util.*;

public record Cocktail(String name, Set<Ingredient> ingredients) {

    public String getName() {
        return name;
    }

    public boolean contains(String ingredientName) {
        return this.ingredients.stream()
                .map(Ingredient::name)
                .toList().contains(ingredientName);
    }


    @Override
    public String toString() {
        final String format = "{\"name\":\"%s\",\"ingredients\":[%s]}";

        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : this.ingredients) {
            builder.append(ingredient.toString()).append(",");
        }

        int lastIndex = builder.length() - 1;

        if (builder.charAt(lastIndex) == ',') {
            builder.deleteCharAt(lastIndex);
        }

        return String.format(format, name, new String(builder));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cocktail cocktail = (Cocktail) o;
        return name.equals(cocktail.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
