package MVC.NO_MVC;

import java.util.ArrayList;

public class Line {

    int x;
    boolean ins;
    private ArrayList<Character> text;

    public Line() {

        this.text = new ArrayList<>();
        this.x = 0;
        this.ins = true;
    }

    public boolean setLine(char c) {

        if (ins) {
            text.add(x, c);
            x++;
            return true;
        } else {

            if (x >= text.size() - 1) {
                text.add(x, c);
            } else {
                text.set(x, c);
            }
            x++;
            return false;
        }
    }

    public int home() {

        int cA = x;
        this.x = 0;
        return cA;
    }

    public int getpos(){return x;}

    public String getLine(){

        String line = "";

        for(Character c : text) {

            line += c;
        }

        return line;
    }

    public int end(){

        int cA = x;
        x=text.size();
        return cA;
    }

    public void insert(){this.ins = !ins;}

    public boolean right() {

        if (x < text.size()) {

            x++;
            return true;
        }
        else return false;
    }

    public boolean left() {

        if (x > 0) {

            x--;
            return true;
        }
        else return false;
    }

    public boolean supr() {

        if (x < text.size()) {

            text.remove(x);
            return true;
        }
        else return false;
    }

    public boolean bksp() {

        if (x > 0) {

            text.remove(x - 1);
            x--;
            return true;
        }
        else return false;
    }
}