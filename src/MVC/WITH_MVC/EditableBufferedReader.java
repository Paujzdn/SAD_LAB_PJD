package MVC.WITH_MVC;


/*
SAD
Programació de readline amb capacitats d’edició (Model/View/Controller)

@author Pau Jimenez daban
@software coded through intellj, compiled and runed on linux visual code

For the second part of this project we are asked to modify our original code implementing an aditional class and separating the MVC pattern between classes
This class stop being a Control-view class and becomes a Controll only class
Reading the console imput and instructing console to call line methods or to print changes on output

Through this class we use octal notation for regx, to filter keys and edit the console text. The used octals and its equivalences in char are:
033=ESC 133=[ 110=H 062=2 176=~ 063=3 106=F 103=C 104=D 177=del 015=CR (carrel return)

This class has 5 methods and 1 atribute (line), also extends BufferedReader so has heritatge attributes
@method setRaw, unsetRaw
@param (none)
@returns (none)
@throws IOException when executint the thread
Use of exec to change the console type from and to raw. Needed to read edit keys. Throws exception as needed for exec classes

@method find
@param (string) to compare with console input
@return (boolean) matches or not

@method read
Uses heritatge method of bufferedReader, to filter the edit keys we want to check and let through regular char
@param (none)
@return (int) returns concrete int from an abstract class for each edit key we want to check

@method readline
@param (none)
@return (string) returns the final text string
@throws IOException
Constatly reads the console input throw this class's read method and instructs console to filter and print changes on console
Previous part explanation extends to the understanding of this class
 */


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
