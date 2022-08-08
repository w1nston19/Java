package bg.uni.sofia.fmi.mjt.Labs.cocktails.command;

import java.util.List;

public class CommandCreator {
    private static final int COMMAND_TOKEN = 0;

    public static Command newCommand(String clientInput) {

        String[] tokens = clientInput.split(" ");

        List<String> allTokens = List.of(tokens);
        return new Command(tokens[COMMAND_TOKEN], allTokens.subList(1, allTokens.size()));
    }
}
