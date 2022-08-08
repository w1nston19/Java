package bg.uni.sofia.fmi.mjt.Labs.cocktails.command;

import java.util.List;

public record Command(String name, List<String> args) {
}
