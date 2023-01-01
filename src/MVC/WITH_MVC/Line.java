package MVC.WITH_MVC;/*
SAD
Programació de readline amb capacitats d’edició (Model/View/Controller)

@author Pau Jimenez daban
@software coded through intellj, compiled and runed on linux visual code
The Line class on this part of the project is purely a model class
It has simple methods that get called when edit keys are detected and there is a need to interact or change propreties of Line text
Previous part explanation extends to the understanding of this class
 */


import java.util.ArrayList;
public class Line {
    private int x; 
    private boolean ins; 
    private ArrayList<Character> text;
    public Line() {
        this.x = 0;
        this.ins = true; 
        this.text = new ArrayList<>();
    }
    public String getLine() { 
        String l = "";
        for(Character c : text) { 
            l += c;
        }
        return l;
    }
    public boolean right() {
        if (x < text.size()) {
            x++;
            return true;
        } else {
            return false;
        }
    }

    public boolean left() {
        if (x > 0) {
            x--;
            return true;
        } else {
            return false;
        }
    }
    public int home() {
        int xActual = x;
        this.x = 0;
        return xActual; 
    }

    public int end() {
        int xActual = x;
        this.x = text.size();
        return (text.size() - xActual); 
    }

    public void insert() {
        this.ins = !ins;
    }

    public boolean supr() {
        if (x < text.size()) { 
            text.remove(x);
            return true;
        } else {
            return false;
        }
    }

    public boolean bksp() {
        if (x > 0) {
            text.remove(x - 1); 
            x--; 
            return true;
        } else {
            return false;
        }
    }
}
