/*
SAD
Programació de readline amb capacitats d’edició (Model/View/Controller)

@author Pau Jimenez daban
@software coded through intellj, compiled and runed on linux visual code

For the first part of this project, we need to implement the desired readline editor without proper use of MVC pattern
As a result this class acts both as a controller and view. Reads the input and calls methods on the model (Line) throw readline
But also acts as a view class when it prints the changes to the cursor and text directly into the console

Through this class we use octal notation for regx, to filter keys and edit the console text. The used octals and its equivalences in char are:
033=ESC 133=[ 110=H 062=2 176=~ 063=3 106=F 103=C 104=D 177=del

This class has 5 methods and 1 atribute (line), also extends BufferedReader so has heritatge attributes
@method setRaw, unsetRaw
@param (none)
@returns (none)
@throws IOException when executint the thread
Use of exec to change the console type from and to raw. Needed to read edit keys. Throws exception as needed for exec classes

@method find
@param (string) to compare with console imput
@return (boolean) matches or not

@method read
Uses heritatge method of bufferedReader, to filter the edit keys we want to check and let through regular char
@param (none)
@return (int) returns concrete int from an abstract class for each edit key we want to check

@method readline
@param (none)
@return (string) returns the final text string
@throws IOException
This method uses the read method from this class, and filters the edit keys, when we go through read we can get a default char turned asci or int KeyCodes
Filters the known codes and calls methods on line class to afect it as needed. Otherwise just adds the character to the string attribute on line class
Also, for the cursos index modification keys and text deletion it prints the modified console (index and text changed)
So this is the method that acts as both controller and view
 */

import java.io.*;
import java.lang.String;
public class EditableBufferedReader extends BufferedReader{
    Line line; //We use Line as a class ot interact with the text, it uses string to simulate the text
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
    public String readLine() throws IOException {//We read
        int entry=0;
        this.setRaw();//seting line as raw before reading it (in case we read editor keys)
        while (entry != '\015') {//regx for CR, new line
            switch (entry=read()) {//Here we print on regx, telling the console what to do in case we have read an edit key. Otherwise it just inserts the usual char on the text atribute of Line class
                case KeyCodes.RIGHT:
                    if (line.right()) System.out.print("\033\133\103");//Cursor to the right
                    break;
                case KeyCodes.LEFT:
                    if (line.left()) System.out.print("\033\133\104");//Cursor to the left
                    break;
                case KeyCodes.HOME:
                    System.out.print("\033\133\061\107");//Cursor to the start of the line
                    break;
                case KeyCodes.END:
                    line.end();
                    System.out.print("\033[" + (line.getpos()+1) + "\107");//Cursor to the end of line 
                    break;
                case KeyCodes.INS:
                    line.insert();//Change ins mode, through a line method
                    break;
                case KeyCodes.SUPR://Del char to the right
                    line.supr();
                    System.out.print("\033\133\061\120");
                    break;
                case KeyCodes.BKS://Del char to the left
                    line.bksp();
                    System.out.print("\033[1D" + "\033\133\061\120");
                    break;
                default: //Insering default char to the string on line
                    System.out.print("\033\133\060\113" + line.insertChar((char) entry, line.ins) + "\033[" + (line.getpos() +1) + "\107");
            }
        }
        this.unsetRaw();
        return line.getLine();
    }


}
