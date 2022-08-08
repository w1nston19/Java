package bg.uni.sofia.fmi.mjt.Labs.cocktails.server;

import bg.uni.sofia.fmi.mjt.Labs.cocktails.command.CommandCreator;
import bg.uni.sofia.fmi.mjt.Labs.cocktails.command.CommandExecutor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

public class Server {

    private static final int BUFFER_SIZE = 1024;
    private static final String HOST = "localhost";

    private final CommandExecutor commandExecutor;

    private final int port;
    private boolean isServerWorking;

    private ByteBuffer buffer;
    private Selector selector;

    public Server(int port, CommandExecutor commandExecutor) {
        this.port = port;
        this.commandExecutor = commandExecutor;
    }

    public void startServer() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            System.out.println("Server connected");

            selector = Selector.open();

            serverSocketChannel.bind(new InetSocketAddress(HOST, port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
            this.isServerWorking = true;

            while (isServerWorking) {
                try {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) {
                        continue;
                    }

                    Collection<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = keys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isAcceptable()) {
                            accept(selector, key);
                        } else {
                            if (key.isReadable()) {
                                SocketChannel socketChannel = (SocketChannel) key.channel();
                                String clientInput = getInput(socketChannel);
                                System.out.println(clientInput);
                                if (clientInput == null) {
                                    continue;
                                }
                                String output = commandExecutor.executeCommand(
                                        CommandCreator.newCommand(clientInput)
                                );

                                writeOutput(socketChannel, output);
                            }
                        }
                        keyIterator.remove();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        this.isServerWorking = false;
        if (selector.isOpen()) {
            selector.wakeup();
        }
    }

    private void accept(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();

        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
    }


    private String getInput(SocketChannel clientChannel) throws IOException {
        buffer.clear();

        int readBytes = clientChannel.read(buffer);
        if (readBytes < 0) {
            clientChannel.close();
            return null;
        }

        buffer.flip();

        byte[] clientInputBytes = new byte[buffer.remaining()];
        buffer.get(clientInputBytes);

        return new String(clientInputBytes, StandardCharsets.UTF_8);
    }

    private void writeOutput(SocketChannel clientChannel, String output) throws IOException {
        buffer.clear();
        buffer.put(output.getBytes());
        buffer.flip();

        clientChannel.write(buffer);
    }
}
