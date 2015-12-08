package panel.creator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * BackgroundLoad is the JPanel which is a simple table which has 10 columns
 * plus the one column for the system names. It has X many rows, where x is the
 * number of systems found over all racks
 *
 * @author EricGummerson
 */
public class BackgroundLoad extends javax.swing.JPanel implements Background{

    public DisplayFrame df;
    public int numRacks;            // num of racks
    public ArrayList<Rack> racks;   // the list of racks
    public Font font;               // global font
    public Border border;           // global border
    public String img;              // global img string for the logo
    public String storeName;        // global string for the store name
    public boolean canClick;
    /**
     * Creates new form BackgroundLoads Initalizes the arraylist to avoid null
     * pointer exceptions, as well as the logo
     *
     * @param df
     */
    public BackgroundLoad(DisplayFrame df) {
        initComponents();
        this.df = df;
        racks = new ArrayList<>();
        this.img = "";
        this.canClick = false;
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
    @Override
    public void updateRacks(ArrayList<Rack> racks, int numRacks, Font font, Border border, String img, String storeName) {

        this.racks = racks;
        this.numRacks = numRacks;
        this.font = font;
        this.border = border;
        this.img = img;
        this.storeName = storeName;
        this.updateView();
    }

    /**
     * updates the storename
     *
     * @param storeName string of the store name
     */
    @Override
    public void updateStoreName(String storeName) {
        this.storeName = storeName;
        this.updateView();
    }

    /**
     * updates the image url for the logo
     *
     * @param img string file path of the logo
     */
    @Override
    public void updateImageURL(String img) {
        this.img = img;
        this.updateView();
    }

    /**
     * updates the font selected from the settings panel
     *
     * @param font Font
     */
    @Override
    public void updateFont(Font font) {
        this.font = font;
        this.updateView();
    }

    /**
     * Updates the border
     *
     * @param border Border
     */
    @Override
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
    @Override
    public void updateFontBorder(Font font, Border border) {
        this.font = font;
        this.border = border;
        this.updateView();
    }

    private void buttonClick(){

        if(canClick){
            Point p = this.getMousePosition();
            df.returnClick(p);
        }
    }
    
    @Override
    public boolean canClick() {
        return canClick;
    }

    @Override
    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        _Panel_MainPanelLayout.setVerticalGroup(
            _Panel_MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_Panel_MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_Panel_MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _Panel_MainPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event__Panel_MainPanelMousePressed
         if(canClick){
            //System.out.println("Load click " + evt.getPoint());
            df.returnClick(evt.getPoint());
        }
    }//GEN-LAST:event__Panel_MainPanelMousePressed

    /**
     * update the view of the panel
     */
    @Override
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
        c.weighty = 1;
        c.gridwidth = 30;
        c.gridheight = gridHeight;

        panel = panelLoads();

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
        panel = panelBottom(this.numRacks);
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
    @Override
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

    /**
     * Creates the loads panel
     *
     * @return JPanel
     */
    public JPanel panelLoads() {

        Rack r;
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

        String[] titles = new String[]{" SETPOINT ", "TEMPERATURE", " EEPR% ", "PREDICTED EEPR%",
            "EEPR% DIFFERENCE", "CAPACITY", "    KW    ", "KW DIFFERENCE",
            "COST DIFFERENCE", " FAULT "};

        c.gridx += 1;
        for (int i = 0; i < titles.length; i++) {

            label = new JLabel(titles[i]);
            label.setOpaque(true);
            // Set the colours
            if (i == 8) {
                label.setBackground(Colours.GreenDark.getCol());
            } else {
                label.setBackground(Colours.BlueDark.getCol());
            }

            label.setBorder(border);
            label.setFont(font);
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            panel.add(label, c);
            c.gridx += 1;
        }

        int numSystems = 0;
        ArrayList<String> systemNames = new ArrayList<>();
        String sgName;
        for (int i = 0; i < numRacks; i++) {
            for (int j = 0; j < racks.get(i).getNumSuctionGroups(); j++) {
                sg = racks.get(i).getSuctionGroupIndex(j);
                sgName = sg.getName();
                numSystems += sg.getNumSystems();
                for (int k = 0; k < sg.getNumSystems(); k++) {
                    systemNames.add(sgName + ": " + sg.getSystemNameIndex(k));
                }
            }
        }

        //System.out.println("Num systems " + numSystems);
        //System.out.println("System names\n" + systemNames);
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < numSystems; i++) {

            // add the system name
            label = new JLabel(" " + systemNames.get(i) + " ");
            label.setOpaque(true);
            if (i % 2 == 0) {
                label.setBackground(Colours.BlueLight.getCol());
            } else {
                label.setBackground(Colours.BlueLightest.getCol());
            }

            label.setBorder(border);
            label.setFont(font);
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            panel.add(label, c);

            c.gridx += 1;

            for (int j = 0; j < 10; j++) {
                label = new JLabel("");
                label.setOpaque(true);
                if (j == 8) {
                    if (i % 2 == 0) {
                        label.setBackground(Colours.GreenLight.getCol());
                    } else {
                        label.setBackground(Colours.GreenLightest.getCol());
                    }
                } else {
                    if (i % 2 == 0) {
                        label.setBackground(Colours.BlueLight.getCol());
                    } else {
                        label.setBackground(Colours.BlueLightest.getCol());
                    }
                }
                label.setBorder(border);
                label.setFont(font);
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
     * Creates the bottom panel
     *
     * @param numRacks needs to know how many rack buttons will be listed on the
     * buttons
     * @return JPanel
     */
    public JPanel panelBottom(int numRacks) {

        JButton button;
        JLabel label;
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(gbl);

        //===========================================================
        // Constraints        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0; // No space between bottom and below row?          
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 2;
        //c.ipady = 100;
        //c.ipady = 0; 
        // End of Constraints
        //===========================================================
        //===========================
        // Powered by label
        //===========================
        label = new JLabel("Powered by N.O.E.L");
        label.setForeground(Colours.White.getCol());
        //label.setBorder(border);
        label.setFont(font.deriveFont(Font.BOLD, 20));
        label.setAlignmentX((Component.LEFT_ALIGNMENT));
        label.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        panel.add(label, c);
        //===========================================================
        // Constraints        
        //c.fill = GridBagConstraints.HORIZONTAL;        
        c.weightx = 0;
        //c.weighty = 0; // No space between bottom and below row?          
        c.gridx = 5;
        //c.gridy = 0;
        c.gridwidth = 1;
        //c.gridheight = 2;
        //c.ipady = 100;
        //c.ipady = 0; 
        // End of Constraints
        //===========================================================

        //==========================================================
        //                  Buttons
        //==========================================================
        // Main button
        //button = new JButton("<html><font color = green>Main</font></html>");        
        button = new JButton("Main");
        button.setFont(font.deriveFont(Font.BOLD, 20));
        button.setAlignmentX((Component.CENTER_ALIGNMENT));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                buttonClick();
            }
        }); 
        panel.add(button, c);
        //===========================================================
        // Constraints        
        //c.fill = GridBagConstraints.HORIZONTAL;        
        //c.weightx = 0;
        //c.weighty = 0; // No space between bottom and below row?          
        c.gridx = 6;
        //c.gridy = 0;
        //c.gridwidth = 1;
        //c.gridheight = 2;
        //c.ipady = 100;
        //c.ipady = 0; 
        // End of Constraints
        //===========================================================
        // Rack buttons
        for (int i = 0; i < numRacks; i++) {
            c.gridx += 1;
            button = new JButton(racks.get(i).getName());
            button.setFont(font.deriveFont(Font.BOLD, 20));
            button.setAlignmentX((Component.CENTER_ALIGNMENT));
            button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                buttonClick();
            }
        }); 
            panel.add(button, c);
        }

        //===========================================================
        // Constraints        
        //c.fill = GridBagConstraints.HORIZONTAL;        
        //c.weightx = 0;
        //c.weighty = 0; // No space between bottom and below row?          
        c.gridx += 1;
        //c.gridy = 0;
        //c.gridwidth = 1;
        //c.gridheight = 2;
        //c.ipady = 100;
        //c.ipady = 0; 
        // End of Constraints
        //===========================================================
        // Load Button
        button = new JButton("Loads");
        button.setFont(font.deriveFont(Font.BOLD, 20));
        button.setAlignmentX((Component.CENTER_ALIGNMENT));
        button.setEnabled(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                buttonClick();
            }
        }); 
        panel.add(button, c);
        
        // Financial Button
        c.gridx += 1;
        button = new JButton("Financial");
        button.setFont(font.deriveFont(Font.BOLD, 20));
        button.setAlignmentX((Component.CENTER_ALIGNMENT));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                buttonClick();
            }
        });
        panel.add(button, c);

        //===========================================================
        // Constraints        
        //c.fill = GridBagConstraints.HORIZONTAL;        
        c.weightx = 1;
        //c.weighty = 0; // No space between bottom and below row?          
        c.gridx += 1;
        //c.gridy = 0;
        c.gridwidth = 5;
        //c.gridheight = 2;
        //c.ipady = 100;
        //c.ipady = 0; 
        // End of Constraints
        //===========================================================

        // Map Label
        label = new JLabel("Map");
        label.setFont(font.deriveFont(Font.BOLD, 20));
        label.setAlignmentX((Component.RIGHT_ALIGNMENT));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panel.add(label, c);

        panel.setBackground(Colours.Gray.getCol());
        panel.setBorder(border);
        return panel;

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
        label = new JLabel("");
        //label.setBorder(border);        
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
    // End of variables declaration//GEN-END:variables
}
