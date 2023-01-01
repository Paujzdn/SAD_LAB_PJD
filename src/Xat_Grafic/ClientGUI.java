package Xat_Grafic;
import javax.swing.SwingUtilities;

public class ClientGUI {

    static ClientFrame client;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                client = new ClientFrame(args[0], Integer.parseInt(args[1]));
            }
        });
    }
}
