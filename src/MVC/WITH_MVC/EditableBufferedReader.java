package MVC.WITH_MVC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader {

        Line line;
        Console console;

    public EditableBufferedReader (Reader in) {

        super(in);
        line = new Line ();
        console = new Console(line);

    }


    public void setRaw(){

        try{

            String[] cmd = {"/bin/sh","-c", "stty -echo raw </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch(IOException e) { System.out.println("Error setRaw()");}
    }


    public void unsetRaw(){

        try{

            String[] cmd = {"/bin/sh","-c", "stty echo cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch(IOException e) { System.out.println("Error unsetRaw()");}
    }


    private boolean find(String editkey) throws IOException{

        mark(editkey.length());
        int keycd;

        for(int i = 0; i<editkey.length(); i++){

            keycd = super.read();

            if (keycd == -1 || keycd != editkey.charAt(i)){

                reset(); 
                return false;
            }
        }
        return true;
    }


    public int read() throws IOException{

        if (find("\033\133\110")) return KeyCodes.HOME;

        if (find("\033\133\062\176")) return KeyCodes.INS;

        if (find("\033\133\063\176")) return KeyCodes.SUPR;

        if (find("\033\133\106")) return KeyCodes.END;

        if (find("\033\133\103")) return KeyCodes.RIGHT;

        if (find("\033\133\104")) return KeyCodes.LEFT;

        if (find("\177")) return KeyCodes.BKS;

        return super.read();
    }


    public String readLine() throws IOException {

        int read;  
        this.setRaw();

        while((read = this.read())!='\015'){

            console.ConsoleOutput(read);
        }

        this.unsetRaw();
        return console.getConsoleLine();
    }
}
