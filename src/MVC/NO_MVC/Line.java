/*
SAD
Programació de readline amb capacitats d’edició (Model/View/Controller)

@author Pau Jimenez daban
@software coded through intellj, compiled and runed on linux visual code

This class has 10 methods that interact and return attributes around a text
This class has 3 attributes necessary to work with the desired text: line string, the pointer x, and a boolean to know if we are using insert mode

@method getpos
@param (none)
@return (int) where is the cursor

@method home
@param (none)
@return (none)
puts index at the begining of line

@method end
@param (none)
@return (none)
puts index at the end of line

@method getline
@param (none)
@return (String) returns text string

@method home
@param (none)
@return (none)
changes insertmode boolean

@method left, right, supr, bks
@param (none)
@return (none)
Move cursor 1 char to left or right, or del char before/after cursor

@method insertChar
@param (character, boolean) the character we want to insert, and the condition of being in or out insertmode
@returns (String) string with inserted character

 */

public class Line {
    int x;
    boolean ins;
    String text;

    public Line() {
        this.text = "";
        this.x = 0;
        this.ins = true;
    }
    //
    public int getpos(){return x;}
    public void home(){x=0;}
    public String getLine(){return text;}
    public void end(){x=text.length();}
    public void insert(){this.ins = !ins;}

    public String insertChar(char cr,boolean i) {//
        if (i) {
            String que = text.substring(x);
            text = text.substring(0,x)+cr+que;
            x++;
        } else {
            if (x == text.length()) {
                text=text+cr;
                x++;
            } else {
                String que = text.substring(x+1);
                text=text.substring(0,x)+cr+que;
                x++;
            }
        }
        return text.substring(x-1);
    }
    public boolean right() {
        if (x < text.length()) {
            x++;
            return true;
        } else return false;
    }
    public boolean left() {
        if (x > 0) {
            x--;
            return true;
        } else {
            return false;
        }
    }
    public void supr() {
        if (x < text.length()) {
            String que = text.substring(x+1);
            text=text.substring(0,x)+que;
        } else {
            System.out.println("\007");
        }
    }
    public void bksp() {
        if (x > 0) {
            String que = text.substring(x);
            text=text.substring(0,x-1)+que;
            x--;
        } else {
            System.out.println("\007");
        }
    }
}