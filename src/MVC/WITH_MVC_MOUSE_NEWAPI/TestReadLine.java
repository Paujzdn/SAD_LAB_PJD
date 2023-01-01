package MVC.WITH_MVC_MOUSE_NEWAPI;
import java.io.*;

class TestReadLine {
    public static void main(String[] args) throws IOException{
        BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;
        int i = 0; //llegir
        try {
            str = in.readLine(); 
            //i = in.read();
        } catch (IOException e) { e.printStackTrace();}
        System.out.println("\nline is: " + str); //i
    }
}