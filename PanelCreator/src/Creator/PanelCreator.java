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
        pc.start(0);
    }

    /**
     * Starts the program
     */
    private void start(int times) {

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
            // Will reset 5 times before not running again
            times++;
            if (times < 5) {
                this.start(times);
            }
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
