package MVC.NO_MVC;

import java.io.*;
import java.lang.String;

class TestReadLine {

    public static void main(String[] args) throws IOException{

        BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;

        try {

            str = in.readLine();
        } catch (IOException e) { e.printStackTrace(); }

        System.out.println("\nline is: " + str);
    }
}
