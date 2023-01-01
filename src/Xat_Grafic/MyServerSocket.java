package Xat_Grafic;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServerSocket {
    public ServerSocket serverSocket;
    public Socket socket;

    public MyServerSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, e);
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