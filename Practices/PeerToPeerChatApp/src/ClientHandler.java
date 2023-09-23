import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public  static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public ClientHandler(Socket socket) {
        try{
            this.socket = socket;
        }
        catch(Exception e) {
        }
    }

    @Override
    public void run() {

    }
}
