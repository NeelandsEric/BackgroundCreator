package Creator;

/**
 * Main 
 * @author EricGummerson
 */
public class PanelCreator {

    MainFrame mf;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        PanelCreator pc = new PanelCreator();
        pc.start();        
    }

    /**
     * Starts the program
     */
    private void start() {        
        
        mf = new MainFrame(this);
        mf.setVisible(true);
        // Show the main panel

    }

    /**
     * Closes the program
     */
    public void close() {
        mf.dispose();
        System.exit(0);
    }

}
