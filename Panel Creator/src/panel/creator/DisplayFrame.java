package panel.creator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
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
    public ArrayList<BackgroundRackNew> rackTabs;
    public BackgroundLoad bgl;
    public ControlSettings cs;
    public DisplaySettings ds;

    /**
     * Creates new form DisplayFrame
     *
     * @param mf
     * @param css
     * @param dss
     */
    public DisplayFrame(MainFrame mf, ControlSettings css, DisplaySettings dss) {
        initComponents();
        this.mf = mf;
        this.cs = css;
        this.ds = dss;
        rackTabs = new ArrayList<>();
        bg = new BackgroundMain(this);
        _TabbedPane_Tabs.add("Main", bg);
        for (int i = 1; i <= this.cs.getNumRacks(); i++) {
            BackgroundRackNew br = new BackgroundRackNew(this, (i - 1));
            _TabbedPane_Tabs.add("Rack " + i, br);
            rackTabs.add(br);
        }

        bgl = new BackgroundLoad(this);
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
                        
            mf.updateDisplaySettingsSize(this.getSize());
            this.setPreferredSize(this.getSize());
            if (bg != null) {
                int w = bg.getWidth();
                int h = bg.getHeight();
                this.setTitle("Customized Backgrounds " + w + "x" + h);
            }

        }
    }//GEN-LAST:event__TabbedPane_TabsComponentResized

    public void canClick(int panelIndex, boolean b){
        int nt = _TabbedPane_Tabs.getTabCount();
        if(panelIndex == 0){
            bg.setCanClick(b);
        }else if(panelIndex == (nt-1)){
            bgl.setCanClick(b);
        }else {
            panelIndex--;
            rackTabs.get(panelIndex).setCanClick(b);
        }        
    }
    
    public void returnClick(Point point){
        mf.returnClick(point);
    }
    
    public void setTab(int tab){
        if(_TabbedPane_Tabs.getTabCount() > tab){
            _TabbedPane_Tabs.setSelectedIndex(tab);
        }
    }
    /**
     * Updates the form with the right information
     *
     * @param css
     * @param dss
     */
    public void updateDisplays(ControlSettings css, DisplaySettings dss) {
        this.cs = css;
        this.ds = dss;

        DisplayFrame t = this;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int selected = _TabbedPane_Tabs.getSelectedIndex();
                int nt = _TabbedPane_Tabs.getTabCount();                
                // update the main
                bg.updateRacks(cs.getRacks(), cs.getNumRacks(), ds.getFont(), ds.getBorder(),
                        cs.getImgStr(), cs.getStoreName());

                for (int i = nt - 2; i > cs.getNumRacks(); i--) {
                    _TabbedPane_Tabs.remove(i);
                }

                String[] rackNames = cs.getRackNames();
                for (int i = 0; i < cs.getNumRacks(); i++) {
                    if (rackTabs.size() > i) {
                        if (rackTabs.get(i) != null) {
                            rackTabs.get(i).updateRacks(cs.getRackIndex(i), cs.getNumRacks(), ds.getFont(), ds.getBorder(), cs.getImgStr(), cs.getStoreName(), rackNames);
                            _TabbedPane_Tabs.add(rackTabs.get(i), i + 1);
                            _TabbedPane_Tabs.setTitleAt(i + 1, rackNames[i]);
                        }
                    } else {
                        rackTabs.add(new BackgroundRackNew(t, i));
                        rackTabs.get(i).updateRacks(cs.getRackIndex(i), cs.getNumRacks(), ds.getFont(), ds.getBorder(), cs.getImgStr(), cs.getStoreName(), rackNames);
                        _TabbedPane_Tabs.add(rackTabs.get(i), i + 1);
                        _TabbedPane_Tabs.setTitleAt(i + 1, rackNames[i]);
                    }
                }

                bgl.updateRacks(cs.getRacks(), cs.getNumRacks(), ds.getFont(), ds.getBorder(), cs.getImgStr(), cs.getStoreName());

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
                int w = bg.getWidth();
                int h = bg.getHeight();
                t.setTitle("Customized Backgrounds " + w + "x" + h);

            }

        });

    }

    public void updateSettings(DisplaySettings dss) {
        this.ds = dss;

        setNewSize(ds.getDisplayWidth(), ds.getDisplayHeight());
        bg.updateFont(ds.getFont());
        bg.updateBorder(ds.getBorder());
        for (int i = 0; i < cs.getNumRacks(); i++) {
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
        for (int i = 0; i < cs.getNumRacks(); i++) {
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
        for (int i = 0; i < cs.getNumRacks(); i++) {
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
        this.setPreferredSize(new Dimension(width, height));

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
        Component[] c = new Component[cs.getNumRacks() + 2];

        c[0] = bg;

        for (int i = 1; i <= cs.getNumRacks(); i++) {
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
