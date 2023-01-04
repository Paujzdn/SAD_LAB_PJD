package MVC.NO_MVC;

public class Line {

    int x;
    boolean ins;
    String text;

    public Line() {

        this.text = "";
        this.x = 0;
        this.ins = true;
    }

    public int getpos(){return x;}

    public void home(){x=0;}

    public String getLine(){return text;}

    public void end(){x=text.length();}

    public void insert(){this.ins = !ins;}

    public String insertChar(char cr,boolean i) {

        if (i) {

            String que = text.substring(x);
            text = text.substring(0,x)+cr+que;
            x++;

        } else {

            if (x == text.length()) {

                text=text+cr;
                x++;
            }

            else {

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

        }

        else return false;
    }

    public boolean left() {

        if (x > 0) {

            x--;
            return true;
        }

        else {

            return false;
        }
    }

    public void supr() {

        if (x < text.length()) {

            String que = text.substring(x+1);
            text=text.substring(0,x)+que;
        }

        else {

            System.out.println("\007");
        }
    }

    public void bksp() {

        if (x > 0) {

            String que = text.substring(x);
            text=text.substring(0,x-1)+que;
            x--;
        }

        else {

            System.out.println("\007");
        }
    }
}