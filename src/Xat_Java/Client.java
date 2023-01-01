package Xat_Java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    public static final String HOST = "localhost";
    public static final int PORT = 5000;

    public static void main(String[] args) throws IOException {
        MySocket mysocket = new MySocket(HOST, PORT);
        new Thread() {
            public void run() {
                String iline;
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                try {
                    while ((iline = in.readLine()) != null) {
                        if (iline=="Close"){
                            mysocket.printLine("CLOSE_CONNECTION");
                        }else{
                            mysocket.printLine(iline);
                        }
                    }

                    mysocket.close();
                }catch(IOException e) {
                }
            }
        }.start();
        new Thread() {
            public void run() {
                String oline;
                while ((oline = mysocket.readLine()) != null) {
                    System.out.println(oline);
                }
            }
        }.start();

    }
}
