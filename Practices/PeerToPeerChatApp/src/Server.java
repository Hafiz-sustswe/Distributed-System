import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void startServer()
    {
        while(!serverSocket.isClosed())
        {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("A new client is connected !");
                ClientHandler clientHandler = new ClientHandler(socket);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void closeServerSocket()
    {
        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
