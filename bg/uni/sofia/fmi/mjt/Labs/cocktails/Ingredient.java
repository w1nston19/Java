package bg.uni.sofia.fmi.mjt.Labs.cocktails;

import java.util.Objects;

public record Ingredient(String name, String amount) {
    private static final int NAME_INDEX = 0;
    private static final int AMOUNT_IDX = 1;

    public static Ingredient of(String input) {
        String[] tokens = input.split("=");
        return new Ingredient(tokens[NAME_INDEX], tokens[AMOUNT_IDX]);
    }

    @Override
    public String toString() {
        String format = "{\"name\":\"%s\",\"amount\":\"%s\"}";
        return String.format(format, name, amount);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
