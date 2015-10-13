/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

/**
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

    
    private void start() {

        mf = new MainFrame(this);
        mf.setVisible(true);
        // Show the main panel

    }

    public void close() {
        mf.dispose();
        System.exit(0);
    }

}
