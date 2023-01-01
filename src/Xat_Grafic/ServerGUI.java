
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerGUI {

    public static final ConcurrentHashMap<String, MySocket> chm = new ConcurrentHashMap<String, MySocket>();

    public static void main(String[] args) {

        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
        ExecutorService pool = Executors.newFixedThreadPool(50);

        while(true) {
            pool.execute(new Task(ss.accept()));
        }

    }

    public static class Task implements Runnable {

        private MySocket sc;

        private String name;
        private String msg;

        public Task(MySocket s) {
            sc = s;
        }

        @Override
        public void run() {
            while(true) {
                sc.printLine("Enter username: ");
                name = sc.readLine();
                if (!chm.containsKey(name)) {
                    chm.put(name, sc);
                    System.out.println("USER: " + name);
                    for(MySocket bs : chm.values()) {
                        bs.printLine("--- " + name + " has joined the chat" + " ---");
                    }
                    break;
                } else {
                    sc.printLine("Username already taken. Try again with a different one.");
                }
            }
            while(sc != null) {
                while((msg = sc.readLine()) != null) {
                    for(MySocket bs : chm.values()) {
                        if (sc != bs) {
                            bs.printLine(name + ": " + msg);
                        } else {
                            bs.printLine("*" + chm.keySet().toString() + "*");
                        }
                    }
                }
                break;
            }
            System.out.println("DISCONNECTED USER: " + name);
            chm.remove(name);
            for(MySocket bs : chm.values()) {
                if (sc != bs) {
                    bs.printLine("--- " + name + " has left the chat" + " ---");
                }
            }
        }
    }

}
