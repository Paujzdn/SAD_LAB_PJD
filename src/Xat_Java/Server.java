package Xat_Java;
import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    public MyServerSocket serverSocket;
    public static ConcurrentHashMap<String, MySocket> slist = new ConcurrentHashMap<>();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static void main(String[] args){
        Server server=new Server();
        server.start(5000);

    }

    public void start(int port) {
        serverSocket = new MyServerSocket(port);
        System.out.println(ANSI_BLUE + "Waiting for users to connect" + ANSI_RESET);
        while (true) {
            new ClientThread(serverSocket.accept()).start();
        }
    }
    public void stop() throws IOException {
        serverSocket.serverSocket.close();
    }

}
