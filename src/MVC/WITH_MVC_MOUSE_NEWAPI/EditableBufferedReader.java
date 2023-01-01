package MVC.WITH_MVC_MOUSE_NEWAPI;
/*
SAD
Programació de readline amb capacitats d’edició (Model/View/Controller)

@author Pau Jimenez daban
@software coded through intellj, compiled and runed on linux visual code

Previous part explanation extends to the understanding of this class
Improved code as we have changed the controller method (readline) now it calls model (who then calls view through an event update) 
Itself instead of runing a method on the view class
Readline filters the result of read method, and calls Line methods
Line then calls eventupdates to Console who prints changes itself
New implements are restricted to modifications on read method to acount for the mouse detection function. Implentation of bell filter

 */



import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;


public class EditableBufferedReader extends BufferedReader {
    Console console;
    int mouseX, mouseY;

    public EditableBufferedReader(Reader in) throws IOException {
        super(in);
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
        if (find("\033\133\103"))return KeyCodes.RIGHT;
        if (find("\033\133\104"))return KeyCodes.LEFT;
        if (find("\033\133\115")) {
            int btt = super.read();
            if((btt & 0b1000011) == 0){ //Check MB1 was pressed
                mouseX = Integer.parseInt(new String(Integer.toString(super.read()).getBytes("UTF-8"),"ISO-8859-1"))-32;
                mouseY = Integer.parseInt(new String(Integer.toString(super.read()).getBytes("UTF-8"),"ISO-8859-1"))-32;
                return(KeyCodes.MSE);
            } 
            super.read(); //Discard 2 following bytes
            super.read();
            return(KeyCodes.BELL);
        }
        if (find("\033\133\110")) return KeyCodes.HOME;
        if (find("\033\133\062\176")) return KeyCodes.INS;
        if (find("\033\133\063\176")) return KeyCodes.SUPR;
        if (find("\033\133\106")) return KeyCodes.END;
        if (find("\177")) return KeyCodes.BKS;
        if (find("\033\133")) return KeyCodes.BELL;
        return super.read();
    }
    public String readLine() throws IOException {
        setRaw();
        Console console = new Console();
        Line line = new Line(console);
        line.setRow(getRow());
        int entry = 0;
        while (entry!= '\015') {
            switch (entry = read()) {
                case KeyCodes.LEFT:
                    line.left();
                    break;
                case KeyCodes.RIGHT:
                    line.right();
                    break;
                case KeyCodes.HOME:
                    line.homex();
                    break;
                case KeyCodes.END:
                    line.endx();
                    break;
                case KeyCodes.INS:
                    line.ins = !line.ins;
                    break;
                case KeyCodes.BKS:
                    line.bks();
                    break;
                case KeyCodes.SUPR:
                    line.supr();
                    break;
                case KeyCodes.MSE:
                    line.mouseIndex(mouseX, mouseY);
                case KeyCodes.BELL:
                case '\015': 
                    break;
                default:
                    line.insert((char) entry, line.ins);
            }
        }
        unsetRaw();
        return line.toString();
    }
    public int getRow() throws IOException{
        readUntil('[');
        int row = Integer.parseInt(readUntil(';'));
        readUntil('R');
        return row;
    }
    private String readUntil(char delim) throws IOException{
        StringBuilder b = new StringBuilder();
        int ch;
        while((ch=super.read()) != delim){
            b.append((char) ch);
        }
        return b.toString();
    }    
}
