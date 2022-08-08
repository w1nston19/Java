package bg.uni.sofia.fmi.mjt.Labs.cocktails.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientManager {

    private static final int SERVER_PORT = 6874;
    private static final String SERVER_HOST = "localhost";
    private static final int BUFFER_SIZE = 512;

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

            System.out.println("connected");

            while (true) {
                System.out.println("Enter command");
                String command = scanner.nextLine();

                if ("quit".equals(command)) {
                    break;
                }

                System.out.println("Sending message <" + command + "> to the server...");
                buffer.clear();
                buffer.put(command.getBytes());
                buffer.flip();
                socketChannel.write(buffer);

                buffer.clear();
                socketChannel.read(buffer);
                buffer.flip();

                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String reply = new String(bytes, StandardCharsets.UTF_8); // buffer drain

                // if buffer is a non-direct one, is has a wrapped array and we can get it
                //String reply = new String(buffer.array(), 0, buffer.position(), "UTF-8"); // buffer drain

                System.out.println("The server replied <" + reply + ">");

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
