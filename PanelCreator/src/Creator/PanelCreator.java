package Creator;

/**
 * Main
 *
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

        try {
            mf = new MainFrame(this);
            mf.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error caught in main");
            e.printStackTrace();
            System.out.println("Restarting");
            if (mf != null) {
                mf.saveDefault();
            }

            this.start();
        }

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
