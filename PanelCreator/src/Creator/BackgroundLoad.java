package Creator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * BackgroundLoad is the JPanel which is a simple table which has 10 columns
 * plus the one column for the system names. It has X many rows, where x is the
 * number of systems found over all racks
 *
 * @author EricGummerson
 */
public class BackgroundLoad extends javax.swing.JPanel {

    public DisplayFrame df;
    public int numRacks;            // num of racks
    public Rack rack;   // the list of racks
    public Font font;               // global font
    public Border border;           // global border
    public String img;              // global img string for the logo
    public String storeName;        // global string for the store name
    private Map<String, Component> widgetComponents;
    public int rackNum;
    public String[] rackNames;

    /**
     * Creates new form BackgroundLoads Initalizes the arraylist to avoid null
     * pointer exceptions, as well as the logo
     *
     * @param df
     * @param rackNum
     */
    public BackgroundLoad(DisplayFrame df, int rackNum) {
        initComponents();
        this.df = df;
        rack = new Rack();
        this.img = "";
        this.widgetComponents = new TreeMap<>();
        this.rackNum = rackNum;
        this.rackNames = new String[]{"Main", "Rack {}", "Loads", "Financial"};
    }

    
    
    
    
    /**
     * Updates the form with the right information
     *
     * @param racks rack list
     * @param numRacks number of racks to read from the list
     * @param font global font
     * @param border global border
     * @param img global img string for the logo
     * @param storeName global string for the store name
     */
    public void updateRack(Rack r, int numRacks, Font font, Border border, String img, String storeName, String[] rackNames) {

        this.rack = r;
        this.numRacks = numRacks;
        this.font = font;
        this.border = border;
        this.img = img;
        this.storeName = storeName;
        this.widgetComponents = new TreeMap<>();
        this.rackNames = rackNames;
        this.updateView();
    }

    public void updateDisplaySettings(DisplaySettings ds) {
        this.border = ds.getBorder();
        this.font = ds.getFont();
    }

    /**
     * updates the storename
     *
     * @param storeName string of the store name
     */
    public void updateStoreName(String storeName) {
        this.storeName = storeName;
        this.updateView();
    }

    /**
     * updates the image url for the logo
     *
     * @param img string file path of the logo
     */
    public void updateImageURL(String img) {
        this.img = img;
        this.updateView();
    }

    /**
     * updates the font selected from the settings panel
     *
     * @param font Font
     */
    public void updateFont(Font font) {
        this.font = font;
        this.updateView();
    }

    /**
     * Updates the border
     *
     * @param border Border
     */
    public void updateBorder(Border border) {
        this.border = border;
        this.updateView();
    }

    /**
     * Updates the font and bother
     *
     * @param font Font
     * @param border Border
     */
    public void updateFontBorder(Font font, Border border) {
        this.font = font;
        this.border = border;
        this.updateView();
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _ScrollPane_Panel = new javax.swing.JScrollPane();
        _Panel_MainPanel = new javax.swing.JPanel();

        _Panel_MainPanel.setBackground(new java.awt.Color(0, 0, 0));
        _Panel_MainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                _Panel_MainPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout _Panel_MainPanelLayout = new javax.swing.GroupLayout(_Panel_MainPanel);
        _Panel_MainPanel.setLayout(_Panel_MainPanelLayout);
        _Panel_MainPanelLayout.setHorizontalGroup(
            _Panel_MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 998, Short.MAX_VALUE)
        );
        _Panel_MainPanelLayout.setVerticalGroup(
            _Panel_MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
        );

        _ScrollPane_Panel.setViewportView(_Panel_MainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_ScrollPane_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_ScrollPane_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _Panel_MainPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event__Panel_MainPanelMousePressed
        df.returnClick(evt.getPoint());
    }//GEN-LAST:event__Panel_MainPanelMousePressed

    /**
     * update the view of the panel
     */
    public void updateView() {

        int gridXPos, gridYPos, gridWidth, gridHeight;
        // Used to keep track of the panels positions                                                       
        int maxGridWidth = 30;
        JPanel panel;
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        // Make sure to remove all components from previous
        _Panel_MainPanel.removeAll();
        _Panel_MainPanel.setLayout(gbl);
        widgetComponents.clear();

        // Store panel info at top
        //===========================================================
        // Positioning
        gridXPos = 0;
        gridYPos = 0;
        gridWidth = maxGridWidth;
        gridHeight = 5;
        // Constraints               
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0; // No space between bottom and below row?        
        c.gridx = gridXPos;
        c.gridy = gridYPos;
        c.gridwidth = gridWidth;
        c.gridheight = gridHeight;
        //c.ipady = 100;
        //c.ipady = 0;                
        // End of Constraints
        //===========================================================  
        panel = panelTop(img, storeName);
        _Panel_MainPanel.add(panel, c);

        gridXPos = 0;
        gridYPos += gridHeight;
        gridHeight = 15;
        // Main Area
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridXPos;
        c.gridy = gridYPos;
        c.weightx = 1;
        c.weighty = 0;
        c.gridwidth = 30;
        c.gridheight = gridHeight;

        panel = panelLoads();

        _Panel_MainPanel.add(panel, c);
        
        panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.BLACK);
        c.weighty = 1;
        gridYPos += gridHeight;
        c.gridy = gridYPos;
        _Panel_MainPanel.add(panel, c);

        //==============================================================
        // make labels white
        setLabels(_Panel_MainPanel, Colours.White.getCol());
        // do it before last panel

        // Bottom Panel
        //===========================================================
        // Constraints        
        c.fill = GridBagConstraints.BOTH;
        //c.weightx = 1;
        c.weighty = 0; // No space between bottom and below row?          
        gridXPos = 0;
        gridYPos += gridHeight;
        gridWidth = maxGridWidth;
        gridHeight = 5; // 5 per row for performance 
        c.gridx = gridXPos;
        c.gridy = gridYPos;

        c.gridwidth = gridWidth;
        c.gridheight = 5;
        //c.ipady = 100;
        //c.ipady = 0;                  
        // We dont setup next position because we are adding suction groups still

        //gridYPos += gridHeight;
        // End of Constraints
        //===========================================================
        //panel = panelBottom(this.numRacks);
        panel = df.panelBottom(numRacks + rackNum + 1);
        _Panel_MainPanel.add(panel, c);

        _Panel_MainPanel.revalidate();
        _Panel_MainPanel.repaint();

    }

    /**
     * Makes the labels a certain colour
     *
     * @param p1 the container to change
     * @param c colour to change to
     */
    public void setLabels(Container p1, Color c) {

        for (Component p : p1.getComponents()) {
            if (p instanceof JLabel) {
                ((JLabel) p).setForeground(Color.white);
            } else {
                if (p instanceof JPanel) {
                    setLabels((Container) p, c);
                }
            }
        }
    }
    
    public JScrollPane getScrollPane(){
        return _ScrollPane_Panel;
    }

    /**
     * Creates the loads panel
     *
     * @return JPanel
     */
    public JPanel panelLoads() {

        SuctionGroup sg;
        JLabel label;
        GridBagLayout gbl = new GridBagLayout();
        JPanel panel = new JPanel(gbl);
        GridBagConstraints c = new GridBagConstraints();

        //===========================================================
        // Constraints        
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0; // No space between bottom and below row?                
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        //c.ipady = 100;
        //c.ipady = 0;                  
        // We dont setup next position because we are adding suction groups still        
        // End of Constraints
        //===========================================================

        label = new JLabel("");
        label.setOpaque(true);
        label.setBackground(Colours.BlueDark.getCol());
        label.setBorder(border);
        label.setFont(font);
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel.add(label, c);

        String[] titles = new String[]{"SETPOINT", "  TEMP  ", " EEPR% ", "<HTML>EEPR%<br>PREDICTED</HTML>",
            "CAPACITY", "    kW    ", " FAULT "};

        c.gridx += 1;
        for (int i = 0; i < titles.length; i++) {

            label = new JLabel(titles[i]);
            label.setOpaque(true);
            // Set the colours
            label.setBackground(Colours.BlueDark.getCol());

            label.setBorder(border);
            label.setFont(font.deriveFont(Font.BOLD, 20));
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            panel.add(label, c);
            c.gridx += 1;
        }

        int numSystems = 0;

        //ArrayList<String> systemNames = new ArrayList<>();
        ArrayList<String[]> widgetInfo = new ArrayList<>();
        //String sgName;

        for (int j = 0; j < rack.getNumSuctionGroups(); j++) {
            sg = rack.getSuctionGroupIndex(j);
            numSystems += sg.getNumSystems();
            for (int k = 0; k < sg.getNumSystems(); k++) {
                widgetInfo.add(new String[]{rack.getName(), sg.getName(), sg.getSystemNameIndex(k)});
                //systemNames.add(sg.getSystemNameIndex(k));
            }
        }

        //System.out.println("Num systems " + numSystems);
        //System.out.println("System names\n" + systemNames);
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 10;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        String[] tooltip = new String[]{
            "System Setpoint `%rackname` `%sgname` `%sysname`",
            "System Temp `%rackname` `%sgname` `%sysname`",
            "System EEPR `%rackname` `%sgname` `%sysname`",
            "System EEPR Predicted `%rackname` `%sgname` `%sysname`",
            "System Capacity `%rackname` `%sgname` `%sysname`",
            "System kW `%rackname` `%sgname` `%sysname`",
            "System Status `%rackname` `%sgname` `%sysname`"
        };
        
        
        for (int i = 0; i < numSystems; i++) {

            // add the system name
            label = new JLabel(" " + widgetInfo.get(i)[2] + " ");
            label.setOpaque(true);
            if (i % 2 == 0) {
                label.setBackground(Colours.BlueLight.getCol());
            } else {
                label.setBackground(Colours.BlueLightest.getCol());
            }

            label.setBorder(border);
            label.setFont(font.deriveFont(Font.BOLD, 18));
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            panel.add(label, c);

            c.gridx += 1;

            for (int j = 0; j < tooltip.length; j++) {
                label = new JLabel("");
                label.setOpaque(true);

                if (i % 2 == 0) {
                    label.setBackground(Colours.BlueLight.getCol());
                } else {
                    label.setBackground(Colours.BlueLightest.getCol());
                }

                label.setBorder(border);
                label.setFont(font);
                try {
                    widgetComponents.put(tooltip[j].replace("`%rackname`", widgetInfo.get(i)[0])
                            .replace("`%sgname`", widgetInfo.get(i)[1])
                            .replace("`%sysname`", widgetInfo.get(i)[2]), label);
                } catch (NullPointerException e) {
                    //System.out.println(e.getMessage());
                }
                panel.add(label, c);
                c.gridx += 1;

            }

            c.gridx = 0;
            c.gridy += 1;
        }

        panel.setBackground(Color.black);
        return panel;
    }

    /**
     * Gets the widget positions of the current panel These positions are stored
     * in the widgetComponents map which contains the component and a string
     * identifying the component
     *
     * @return Map<String, Rectangle> Key, Rectangle of the spot
     */
    public Map<String, Rectangle> positions() {
        //public void positions() {
        //System.out.println("Positions " + rack.getName());
        Map<String, Rectangle> ioPoints = new TreeMap<>();

        if (widgetComponents.isEmpty()) {
            return null;
        }
        for (Map.Entry<String, Component> entry : widgetComponents.entrySet()) {

            Component p = entry.getValue();
            try {
                //System.out.println("Tooltip: " + ((JLabel) p).getToolTipText());
                if (p instanceof JLabel) {
                    //if (((JLabel) p).getToolTipText() != null) {
                    //System.out.println("Has a tooltip:" + ((JLabel) p).getToolTipText());
                    Rectangle r = p.getBounds();
                    Component par = p;
                    while (par.getParent() != _Panel_MainPanel) {
                        par = par.getParent();
                    }
                    r = SwingUtilities.convertRectangle(par, r, _Panel_MainPanel);
                    //((JLabel) p).setText("x=" + r.getX() + ", y=" + r.getY());

                    Rectangle oldRect = ioPoints.put(entry.getKey(), r);
                    if (oldRect != null) {
                        System.out.println("Replaced " + ((JLabel) p).getToolTipText()
                                + ".\nOld rectangle " + oldRect.toString()
                                + "\nNew rectangle: " + r.toString());
                    }

                }

            } catch (NullPointerException | IllegalComponentStateException e) {
                System.out.println("Error with " + ((JLabel) p).getName());
            }

        }
        
        return ioPoints;

    }

    /**
     * Top panel
     *
     * @param imgUrl string location of the picture to be used as a logo
     * @param storeName name of the store
     * @return JPanel
     */
    public JPanel panelTop(String imgUrl, String storeName) {

        JLabel label;
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(gbl);

        //===========================================================
        // Constraints        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 0; // No space between bottom and below row?          
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 10;
        c.gridheight = 2;
        //c.ipady = 100;
        //c.ipady = 0; 
        // End of Constraints
        //===========================================================
        //===========================
        // store logo 
        //===========================       
        label = new JLabel();
        if (!"".equals(imgUrl)) {
            ImageIcon icon = new ImageIcon(imgUrl);
            label.setText("");
            label.setIcon(icon);
        } else {
            label.setText("NO LOGO SELECTED");
        }
        //label.setBorder(border);        
        label.setAlignmentX((Component.LEFT_ALIGNMENT));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel.add(label, c);

        //===========================================================
        // Constraints        
        //c.fill = GridBagConstraints.HORIZONTAL;        
        c.weightx = 1;
        //c.weighty = 0; // No space between bottom and below row?          
        c.gridx = 10;
        //c.gridy = 0;
        c.gridwidth = 20;
        //c.gridheight = 2;
        //c.ipady = 100;
        //c.ipady = 0; 
        // End of Constraints
        //===========================================================

        // Store
        label = new JLabel();
        //label.setBorder(border);      
        label.setOpaque(true);
        label.setBackground(Colours.Gray.getCol());
        label.setFont(font.deriveFont(Font.BOLD, 22));
        label.setAlignmentX((Component.RIGHT_ALIGNMENT));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel.add(label, c);
               

        //===========================================================
        // Constraints        
        //c.fill = GridBagConstraints.HORIZONTAL;        
        c.weightx = 0;
        //c.weighty = 0; // No space between bottom and below row?          
        c.gridx = 30;
        //c.gridy = 1;
        c.gridwidth = 10;
        //c.gridheight = 2;
        //c.ipady = 100;
        //c.ipady = 0; 
        // End of Constraints
        //===========================================================

        // Store
        label = new JLabel("CIRCUIT STATUS                 " + storeName);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setFont(font.deriveFont(Font.BOLD, 22));
        label.setAlignmentX((Component.RIGHT_ALIGNMENT));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.add(label, c);

        panel.setBackground(Colours.Gray.getCol());
        panel.setBorder(border);
        return panel;

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel _Panel_MainPanel;
    private javax.swing.JScrollPane _ScrollPane_Panel;
    // End of variables declaration//GEN-END:variables
}
