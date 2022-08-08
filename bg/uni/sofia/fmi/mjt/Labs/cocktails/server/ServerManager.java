package bg.uni.sofia.fmi.mjt.Labs.cocktails.server;

import bg.uni.sofia.fmi.mjt.Labs.cocktails.command.CommandExecutor;
import bg.uni.sofia.fmi.mjt.Labs.cocktails.storage.DefaultCocktailStorage;

public class ServerManager {
    public static void main(String[] args) {
        Server server = new Server(6874, new CommandExecutor(new DefaultCocktailStorage()));
        server.startServer();
    }
}
