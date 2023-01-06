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

    @Override
    public int read() throws IOException {
        int read;

        switch (read = super.read()) {
            case '\033':
                super.read(); //Discarding the \133, as all codes have it

                switch (super.read()) {
                    case '\103':
                        return KeyCodes.RIGHT;
                    case '\104':
                        return KeyCodes.LEFT;
                    case '\110':
                        return KeyCodes.HOME;
                    case '\106':
                        return KeyCodes.END;
                    case '\062':
                        super.read();
                        return KeyCodes.INS;
                    case '\063':
                        super.read();
                        return KeyCodes.SUPR;
                }

            default:
                return read;

        }
    }

    @Override

    public String readLine() throws IOException {

        int readline;
        this.setRaw();

        while ((readline = this.read()) != KeyCodes.ENTER) {

            switch (readline) {

                case KeyCodes.RIGHT:

                    if (line.right()) {

                        System.out.print("\033\133\103");
                    }
                    break;

                case KeyCodes.LEFT:

                    if (line.left()) {

                        System.out.print("\033\133\104");
                    }
                    break;

                case KeyCodes.HOME:

                    System.out.print("\033[" + line.home() + "D");
                    break;

                case KeyCodes.END:

                    if(line.end() > 0){

                        System.out.print("\033[" + line.end() + "C");
                    }
                    break;

                case KeyCodes.INS:

                    line.insert();
                    break;

                case KeyCodes.SUPR:

                    if (line.supr()) {

                        System.out.print("\033\133\061\120");
                    }
                    break;

                case KeyCodes.BKSP:

                    if (line.bksp()) {

                        System.out.print("\033[1D" + "\033\133\061\120");
                    }
                    break;

                default:
                    System.out.print((char) readline);

            }
        }

        this.unsetRaw();
        return line.getLine();

    }
}
