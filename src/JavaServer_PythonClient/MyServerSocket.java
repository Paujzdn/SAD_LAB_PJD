import java.io.IOException;
import java.net.ServerSocket;

public class MyServerSocket {
    public ServerSocket serverSocket;

    public MyServerSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MySocket accept() {
        try {
            MySocket socket = new MySocket(this.serverSocket.accept());
            return  socket;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
