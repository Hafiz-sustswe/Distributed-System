import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class PeerToPeerChatServer {
    private static final int PORT = 12356;
    private static final int MAX_CLIENTS = 2;
    private static final Socket[] clientConnections = new Socket[MAX_CLIENTS];
    private static final PrintWriter[] clientWriters = new PrintWriter[MAX_CLIENTS];

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_CLIENTS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Peer-to-Peer Chat server is running on port " + PORT);

            for (int i = 0; i < MAX_CLIENTS; i++) {
                clientConnections[i] = serverSocket.accept();
                System.out.println("Client " + (i + 1) + " connected: " + clientConnections[i]);
                clientWriters[i] = new PrintWriter(clientConnections[i].getOutputStream(), true);

                // Create a new client handler thread for each client and start it
                ClientHandler clientHandler = new ClientHandler(i);
                executor.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    static class ClientHandler implements Runnable {
        private int clientIndex;

        public ClientHandler(int clientIndex) {
            this.clientIndex = clientIndex;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientConnections[clientIndex].getInputStream()));

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    System.out.println("Received from Client " + (clientIndex + 1) + ": " + clientMessage);

                    // Determine the recipient client
                    int recipientIndex = (clientIndex == 0) ? 1 : 0;

                    // Forward the message to the recipient client
                    forwardMessage("Client " + (clientIndex + 1) + ": " + clientMessage, recipientIndex);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientConnections[clientIndex].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private synchronized void forwardMessage(String message, int recipientIndex) {
            if (recipientIndex >= 0 && recipientIndex < MAX_CLIENTS && clientWriters[recipientIndex] != null) {
                clientWriters[recipientIndex].println(message);
            }
 }
}
}