import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.Scanner;

public class ChatApp {
    private static final int portNumber = 12345;
    private static final int maxClients = 10;
    private static int nextUserId = 1;

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(maxClients);

        try (ServerSocket server = new ServerSocket(portNumber)) {
            System.out.println("Chat server running on port " + portNumber);

            // Scanner for reading console input
            Scanner consoleInput = new Scanner(System.in);

            while (true) {
                Socket client = server.accept();
                System.out.println("New client connected: " + client);

                // Assign a unique user ID
                int userId = nextUserId++;

                // Create handler and start it
                ClientHandler handler = new ClientHandler(client, userId, consoleInput);
                threadPool.execute(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}

class ClientHandler implements Runnable {

    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private int userId;
    private Scanner console;

    public ClientHandler(Socket client, int userId, Scanner console) {
        this.client = client;
        this.userId = userId;
        this.console = console;
    }

    public void run() {
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from User " + userId + ": " + message);

                System.out.print("Enter message to send to User " + userId + ": ");
                String reply = console.nextLine();
                out.println(reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}