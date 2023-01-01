import java.io.*;
import java.net.*;
public class ClientThread extends Thread {

    public MySocket clientsocket;
    public String clientname;
    public boolean alive;

    public ClientThread(MySocket socket) {
        clientsocket=socket;
        alive=true;
    }
    public void run(){

        try{
            clientsocket.start();
            String inputLine;
            boolean free_nick=false;

            while(!free_nick){
                clientsocket.printLine("Desired username: ");
                clientname=clientsocket.readLine();

                if (Server.slist.containsKey(clientname)){
                    clientsocket.printLine(Server.ANSI_RED + "Username taken." + Server.ANSI_RESET);
                }else{
                    System.out.println(Server.ANSI_GREEN + clientname + " is connected." +  Server.ANSI_RESET);
                    Server.slist.put(clientname,clientsocket);
                    clientsocket.username = clientname;
                    free_nick=true;
                }
            }
            Server.tlist.put(clientname,this);

            while ((inputLine = clientsocket.in.readLine()) != null) {
                if (alive){
                    if (inputLine.contains("CLOSE_CONNECTION")){
                        System.out.println(Server.ANSI_RED + clientname +  " has disconnected from server" + Server.ANSI_RESET);
                        for (MySocket cs: Server.slist.values()){
                            if(cs != clientsocket){
                                cs.printLine(Server.ANSI_RED + clientname +  " has disconnected from server" + Server.ANSI_RESET);
                            }
                        }
                        this.alive=false;
                        Server.slist.remove(clientname,clientsocket);
                    }
                    else{
                        System.out.println(Server.ANSI_BLUE + clientsocket.username + ": " + Server.ANSI_RESET + inputLine);
                        for (MySocket cs: Server.slist.values()){
                            if(cs != clientsocket){
                                cs.printLine(Server.ANSI_BLUE + clientsocket.username + ": " + Server.ANSI_RESET + inputLine);
                            }
                        }
                    }
                    
                }
            }

            clientsocket.out.close();
            clientsocket.in.close();
            clientsocket.close();

        }catch(IOException e) {
            System.out.println("Message hasn't been sent");

        }
    }
}
