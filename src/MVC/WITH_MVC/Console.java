package MVC.WITH_MVC;

public class Console {

    private Line line;

    public Console(Line line){

        this.line = line;
    }

    public void ConsoleOutput(int read){

        int end;

        switch (read) {

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

                System.out.print("\033\133" + line.home() + "\104"); 
                break;

            case KeyCodes.END:

                end = line.end();

                if(end > 0){

                    System.out.print("\033\133" + end + "\103");
                }
                break;

            case KeyCodes.INS:

                line.insert();
                break;

            case KeyCodes.SUPR:

                if (line.supr()) {

                    System.out.print("\033\133\120");
                }
                break;

            case KeyCodes.BKS:

                if (line.bksp()) {

                    System.out.print("\033\133\104");
                    System.out.print("\033\133\120");
                }
                break;

            default:

                System.out.print("\033\133\100");
                System.out.print((char) read);
        }
    }


    public String getConsoleLine(){

        String consoleLine = line.getLine();
        return consoleLine;
    }
}