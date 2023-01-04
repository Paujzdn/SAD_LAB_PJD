package MVC.NO_MVC;

import java.io.*;
import java.lang.String;

public class EditableBufferedReader extends BufferedReader{

    Line line;

    public EditableBufferedReader(Reader in) {

        super(in);
        line= new Line();
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


    public int read() throws IOException {

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

        int entry=0;
        this.setRaw();

        while (entry != '\015') {
            //Here we print on regx, telling the console what to do in case we have read an edit key.
            // Otherwise it just inserts the usual char on the text atribute of Line class
            switch (entry=read()) {

                case KeyCodes.RIGHT:
                    //Cursor to the right
                    if (line.right()) System.out.print("\033\133\103");
                    break;

                case KeyCodes.LEFT:
                    //Cursor to the left
                    if (line.left()) System.out.print("\033\133\104");
                    break;

                case KeyCodes.HOME:
                    //Cursor to the start of the line
                    System.out.print("\033\133\061\107");
                    break;

                case KeyCodes.END:
                    //Cursor to the end of line
                    line.end();
                    System.out.print("\033[" + (line.getpos()+1) + "\107");
                    break;

                case KeyCodes.INS:
                    //Change ins mode, through a line method
                    line.insert();
                    break;

                case KeyCodes.SUPR:
                    //Del char to the right
                    line.supr();
                    System.out.print("\033\133\061\120");
                    break;

                case KeyCodes.BKS:
                    //Del char to the left
                    line.bksp();
                    System.out.print("\033[1D" + "\033\133\061\120");
                    break;

                default:
                    //Insering default char to the string on line
                    System.out.print("\033\133\060\113" + line.insertChar((char) entry, line.ins) + "\033[" + (line.getpos() +1) + "\107");
            }
        }

        this.unsetRaw();
        return line.getLine();
    }
}
