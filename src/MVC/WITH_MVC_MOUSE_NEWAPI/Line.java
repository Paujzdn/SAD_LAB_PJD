package MVC.WITH_MVC_MOUSE_NEWAPI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Line{

    int x;
    String str;
    boolean ins;
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    int cR;

    public Line(PropertyChangeListener pcl) {

        x = 0;
        str = "";
        ins = true;
        pcs.addPropertyChangeListener(pcl);
    }


    public void setRow(int row){cR=row;}


    public int getIndex() {return x;}


    public String toString() {return str;}


    public void insert(char cr, Boolean insert) {

        String str_au = str;

        if (insert) {

            String queue = str.substring(x);
            str = str.substring(0, x) + cr + queue;
            x += 1;
        }
        else {

            if (x == str.length()) {

                str = str + cr;
                x += 1;

            }
            else {

                String queue = str.substring(x + 1);
                str = str.substring(0, x) + cr + queue;
                x += 1;
            }
        }
        pcs.firePropertyChange(new PropertyChangeEvent(this, "str", str_au, str));
    }


    public void right(){

        if (x < str.length()) {

            pcs.firePropertyChange(new PropertyChangeEvent(this, "index", x, x+=1)); 
        }
        else {

            pcs.firePropertyChange(new PropertyChangeEvent(this, "bell", null, null));
        }
    }


    public void left(){

        if (x > 0) {

            pcs.firePropertyChange(new PropertyChangeEvent(this, "index", x, x-=1));
        }
        else {

            pcs.firePropertyChange(new PropertyChangeEvent(this, "bell", null, null));
        }
    }


    public void homex(){

        pcs.firePropertyChange(new PropertyChangeEvent(this, "index", x, x=0));
    }


    public void endx(){

        pcs.firePropertyChange(new PropertyChangeEvent(this, "index", x, x=str.length()));
    }


    public void bks() {

        String str_au = str;

        if (x > 0) {

            String queue = str.substring(x);
            str = str.substring(0, x - 1) + queue;
            x -= 1;
            pcs.firePropertyChange(new PropertyChangeEvent(this, "str_delete", str_au, str));
        }
        else {

            pcs.firePropertyChange(new PropertyChangeEvent(this, "bell", null, null));
        }
    }


    public void supr() {

        String str_au = str;

        if (x < str.length()) {

            String queue = str.substring(x + 1);
            str = str.substring(0, x) + queue;
            pcs.firePropertyChange(new PropertyChangeEvent(this, "str_supress", str_au, str));
        }
        else {

            pcs.firePropertyChange(new PropertyChangeEvent(this, "bell", null, null));
        }
    }


    public void mouseIndex(int mouseX, int mouseY){

        int x_aux = x;

        if (((mouseX-1) <= str.length()) && (mouseX-1>=0) && mouseY==cR) {

            x =  mouseX-1;
        }
        pcs.firePropertyChange(new PropertyChangeEvent(this, "index", x_aux, x));
    }
}
