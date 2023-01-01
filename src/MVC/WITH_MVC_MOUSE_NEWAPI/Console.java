package MVC.WITH_MVC_MOUSE_NEWAPI;/*
SAD
Programació de readline amb capacitats d’edició (Model/View/Controller)

@author Pau Jimenez daban
@software coded through intellj, compiled and ran on linux visual code

Previous part explanation extends to the understanding of this class
This class stops having a method that acts both as Controller and view (as it did on the second part of the project) and now becomes a solely view class
Line calls propertyChange as instructed by EditableBufferedReader, and Console implements the changes required to the printed text on terminal
New implements are restricted to the use of PropertyChange events to implement the mouse properties
This class is used as a View part for the MVC pattern

 */


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Console implements PropertyChangeListener {
    public Console() {
        super();
        System.out.print("\033[1;2'z"); // Enable locator reporting
        System.out.print("\033[?1000h");  //  DECSET Enable Mouse Tracking 
        System.out.print("\033[6n"); //Report cursor position to get row 
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "bell":
                System.out.print("\007");
                break;
            case "str":
                int index = ((Line) evt.getSource()).getIndex();
                System.out.print("\033\133\060\113" + ((String) evt.getNewValue()).substring(index-1) + "\033\133" + (index + 1) + "\107");
                break;                                                                                                   
            case "str_delete":
                System.out.print("\033\133\061\104" + "\033\133\061\120");
                break;
            case "str_supress":
                System.out.print("\033\133\061\120"); 
                break;
            case "index": 
                int newx = (Integer) evt.getNewValue();
                int ox = (Integer) evt.getOldValue();
                if (newx == ox-1) {
                    System.out.print("\033\133\061\104");
                } else if (newx == ox+1) {
                    System.out.print("\033\133\061\103");
                } else if (newx==0) {
                    System.out.print("\033\133\061\107");
                } else {
                    System.out.print("\033\133" + ((Integer)evt.getNewValue() + 1) + "\107");
                }

        }
        
    }
    
}
