package panel.creator;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The display frame which shows the different backgrounds
 *
 * @author EricGummerson
 */
public class DisplayFrame extends javax.swing.JFrame {

    public BackgroundMain bg; // main panel 
    // Loads panel
    public MainFrame mf;
    public ArrayList<BackgroundRack> rackTabs;
    public BackgroundLoad bgl;
    public Store store;

    /**
     * Creates new form DisplayFrame
     *
     * @param mf
     * @param store
     */
    public DisplayFrame(MainFrame mf, Store store) {
        initComponents();
        this.mf = mf;
        rackTabs = new ArrayList<>();
        bg = new BackgroundMain();
        this.store = store;
        _TabbedPane_Tabs.add("Main", bg);
        for (int i = 1; i <= store.getNumRacks(); i++) {
            BackgroundRack br = new BackgroundRack((i - 1));
            _TabbedPane_Tabs.add("Rack " + i, br);
            rackTabs.add(br);
        }

        bgl = new BackgroundLoad();
        _TabbedPane_Tabs.add("Loads", bgl);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _TabbedPane_Tabs = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Customized Backgrounds");
        setAutoRequestFocus(false);
        setLocation(new java.awt.Point(940, 0));
        setMaximumSize(new java.awt.Dimension(1800, 1200));
        setMinimumSize(new java.awt.Dimension(1200, 900));
        setPreferredSize(new java.awt.Dimension(900, 600));

        _TabbedPane_Tabs.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                _TabbedPane_TabsComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_TabbedPane_Tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_TabbedPane_Tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * resize the display
     *
     * @param evt
     */
    private void _TabbedPane_TabsComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event__TabbedPane_TabsComponentResized
        // TODO add your handling code her
        if (mf != null) {
            mf.updateDisplaySize(this.getSize());
            this.setPreferredSize(this.getSize());
        }
    }//GEN-LAST:event__TabbedPane_TabsComponentResized

    
    
    /**
     * Updates the form with the right information
     *
     * @param store store
     */
    public void updateDisplays(Store store) {

        JFrame t = this;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int selected = _TabbedPane_Tabs.getSelectedIndex();
                int nt = _TabbedPane_Tabs.getTabCount();

                // update the main
                bg.updateRacks(store.getRacks(), store.getNumRacks(), store.getCustomFont(), store.getCustomBorder(),
                        store.getImgStr(), store.getStoreName());

                for (int i = nt - 2; i > store.getNumRacks(); i--) {
                    _TabbedPane_Tabs.remove(i);
                }

                String[] rackNames = store.getRackNames();
                for (int i = 0; i < store.getNumRacks(); i++) {
                    if (rackTabs.size() > i) {                        
                        if (rackTabs.get(i) != null) {
                            rackTabs.get(i).updateRacks(store.getRackIndex(i), store.getNumRacks(), store.getCustomFont(), store.getCustomBorder(), store.getImgStr(), store.getStoreName(), rackNames);
                            _TabbedPane_Tabs.add(rackTabs.get(i), i + 1);
                            _TabbedPane_Tabs.setTitleAt(i + 1, rackNames[i]);
                        }
                    } else {
                        rackTabs.add(new BackgroundRack(i));
                        rackTabs.get(i).updateRacks(store.getRackIndex(i), store.getNumRacks(), store.getCustomFont(), store.getCustomBorder(), store.getImgStr(), store.getStoreName(), rackNames);
                        _TabbedPane_Tabs.add(rackTabs.get(i), i + 1);
                        _TabbedPane_Tabs.setTitleAt(i + 1, rackNames[i]);                        
                    }
                }

                bgl.updateRacks(store.getRacks(), store.getNumRacks(), store.getCustomFont(), store.getCustomBorder(), store.getImgStr(), store.getStoreName());

                if (selected == (nt - 1)) {
                    if (_TabbedPane_Tabs.getTabCount() < nt) {
                        selected--; // loads tab selected
                    } else {
                        selected = _TabbedPane_Tabs.getTabCount() - 1;
                    }
                } else if (selected < (_TabbedPane_Tabs.getTabCount() - 1)) {
                    // good                    
                } else if (selected >= (_TabbedPane_Tabs.getTabCount() - 1)) {
                    selected--;
                }
                _TabbedPane_Tabs.setSelectedIndex(selected);
                t.pack();

            }
        });

    }
    
    public void updateSettings(DisplaySettings ds){
        this.store.setDs(ds);
        
        bg.updateFont(ds.getFont());
        bg.updateBorder(ds.getBorder());
        for (int i = 0; i < store.getNumRacks(); i++) {
            if (rackTabs.get(i) != null) {
                rackTabs.get(i).updateFont(ds.getFont());
                rackTabs.get(i).updateBorder(ds.getBorder());
            }
        }
        bgl.updateFont(ds.getFont());
        bgl.updateBorder(ds.getBorder());
        
    }

    
    /**
     * update the logo
     */
    public void updateLogo() {
        bg.updateImageURL("");
        for (int i = 0; i < store.getNumRacks(); i++) {
            if (rackTabs.get(i) != null) {
                rackTabs.get(i).updateImageURL("");
            }
        }
    }

    /**
     * update the logo
     *
     * @param img String file path
     */
    public void updateLogo(String img) {
        bg.updateImageURL(img);
        for (int i = 0; i < store.getNumRacks(); i++) {
            if (rackTabs.get(i) != null) {
                rackTabs.get(i).updateImageURL(img);
            }
        }
    }

    /**
     * sets the size of the display frame
     *
     * @param width the width of the frame
     * @param height the height of the frame
     */
    public void setNewSize(int width, int height) {
        this.setSize(width, height);

    }

    /**
     * gets the selected panel on the tabbed pane
     *
     * @return component
     */
    public Component getCurrentPane() {
        return _TabbedPane_Tabs.getSelectedComponent();
    }

    public Component[] getPanelPictures() {
        Component[] c = new Component[store.getNumRacks() + 2];

        c[0] = bg;

        for (int i = 1; i <= store.getNumRacks(); i++) {
            c[i] = rackTabs.get(i - 1);
        }

        c[c.length - 1] = bgl;

        return c;

    }

    /**
     * Gets the number of tabs being displayed
     *
     * @return number of tabs
     */
    public int getTabCount() {
        return _TabbedPane_Tabs.getTabCount();
    }

    /**
     * Changes the selected index of the tabbed pane
     *
     * @param index int index to switch to 0 - (tab count - 1)
     */
    public void changeTab(int index) {
        System.out.println("Changing tab to " + index);
        _TabbedPane_Tabs.setSelectedIndex(index);

    }

    /**
     * Gets the current tab selection of the tabbed pane
     *
     * @return int index of the selected index
     */
    public int getTabSelection() {
        return _TabbedPane_Tabs.getSelectedIndex();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane _TabbedPane_Tabs;
    // End of variables declaration//GEN-END:variables
}
