/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Main frame is the main frame containing links to all other frames w
 *
 * @author EricGummerson
 */
public class MainFrame extends JFrame {

    private final PanelCreator main;
    public DisplayFrame displayFrame;    
    public ControlsPanel controlPanel;
    public SettingsPanel settingsPanel;
    public NameGeneratorPanel ngPanel;
    public Store store;

    /**
     * Creates new form MainFrame
     *
     * @param main
     */
    public MainFrame(PanelCreator main) {
        this.main = main;
        store = new Store("Store");
        initComponents();
        initPanels();
        
    }

    private void initPanels() {

        controlPanel = new ControlsPanel(this, store);
        displayFrame = new DisplayFrame(this, store);
        settingsPanel = new SettingsPanel(this);
        ngPanel = new NameGeneratorPanel(this);
        
        // Load the main panel        
        //controlPanel.setVisible(true);
        displayFrame.setVisible(true);
        controlPanel.updateDisplay();
        ngPanel.loadGroups();
        // add it to the frame           
        _TabbedPane_Tabs.add("Controls", controlPanel);
        _TabbedPane_Tabs.add("Settings", settingsPanel);
        _TabbedPane_Tabs.add("Name Generator", ngPanel);

    }

    public void updateDisplaySize(Dimension d) {
        if (settingsPanel != null) {
            settingsPanel.setDim(d);
        }
    }

    public void updateDisplaySize(int width, int height) {
        //displayFrame.setNewSize(width, height);
        displayFrame.setSize(width, height);
    }

    public void updateFont(Font f) {
        displayFrame.updateFont(f);
    }

    public void updateBorder(Border b) {
        displayFrame.updateBorder(b);
    }

    public void displayPanel(int width, int height) {
        displayFrame.setNewSize(width, height);
        if (!displayFrame.isVisible()) {
            controlPanel.updateDisplay();
            displayFrame.setVisible(true);
        } else {
            displayFrame.setVisible(false);
        }
    }

    public void updateDisplay(Store store) {
        Font f = settingsPanel.getFontData();
        Border b = settingsPanel.getBorder();        
        displayFrame.updateDisplays(store, f, b);
    }
    
    
    public void updateVarNames(ArrayList<String> storeStr, ArrayList<String> rackStr,
                                ArrayList<String> condStr, ArrayList<String> sgStr,
                                ArrayList<String> compStr, ArrayList<String>  sysStr,
                                ArrayList<String> extraStr){
        store.setStoreStr(storeStr);
        store.setRackStr(rackStr);
        store.setCondStr(condStr);
        store.setSgStr(sgStr);
        store.setCompStr(compStr);
        store.setSysStr(sysStr);
        store.setExtraStr(extraStr);
        
        
    }
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _FileChooser_LoadLogo = new javax.swing.JFileChooser();
        _FileChooser_SavePicture = new javax.swing.JFileChooser();
        _FileChooser_SaveStore = new javax.swing.JFileChooser();
        _FileChooser_LoadStore = new javax.swing.JFileChooser();
        _FileChooser_SaveIntoFolder = new javax.swing.JFileChooser();
        _FileChooser_SaveCSV = new javax.swing.JFileChooser();
        _TabbedPane_Tabs = new javax.swing.JTabbedPane();
        _MenuBar_Menus = new javax.swing.JMenuBar();
        _Menu_File = new javax.swing.JMenu();
        _MenuItem_SaveCurrentDisplay = new javax.swing.JMenuItem();
        _MenuItem_SaveAllDisplays = new javax.swing.JMenuItem();
        _MenuItem_PrintVarNames = new javax.swing.JMenuItem();
        _MenuItem_OpenStore = new javax.swing.JMenuItem();
        _MenuItem_SaveStore = new javax.swing.JMenuItem();
        _MenuItem_NewStore = new javax.swing.JMenuItem();
        _MenuItem_Close = new javax.swing.JMenuItem();
        _Menu_View = new javax.swing.JMenu();
        _MenuItem_ViewPanel = new javax.swing.JMenuItem();
        _MenuItem_changedisplay = new javax.swing.JMenuItem();
        _Menu_Image = new javax.swing.JMenu();
        _MenuItem_OpenImage = new javax.swing.JMenuItem();
        _MenuItem_RemoveImage = new javax.swing.JMenuItem();

        _FileChooser_LoadLogo.setApproveButtonText("Open");
        _FileChooser_LoadLogo.setApproveButtonToolTipText("Open the logo file");
        _FileChooser_LoadLogo.setCurrentDirectory(null);
        _FileChooser_LoadLogo.setDialogTitle("Open image file for the store logo");
        _FileChooser_LoadLogo.setFileFilter(new panel.creator.FilterImage());

        _FileChooser_SavePicture.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        _FileChooser_SavePicture.setApproveButtonText("Save");
        _FileChooser_SavePicture.setApproveButtonToolTipText("Save Picture");
        _FileChooser_SavePicture.setCurrentDirectory(null);
        _FileChooser_SavePicture.setDialogTitle("Directory to save a picture");
        _FileChooser_SavePicture.setFileFilter(new FileNameExtensionFilter("Image files (.png, .jpg, .gif)", new String[]{"jpg", "gif", "png"}));

        _FileChooser_SaveStore.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        _FileChooser_SaveStore.setApproveButtonText("Save");
        _FileChooser_SaveStore.setApproveButtonToolTipText("Save Store");
        _FileChooser_SaveStore.setCurrentDirectory(null);
        _FileChooser_SaveStore.setDialogTitle("Directory to save store");
        _FileChooser_SaveStore.setFileFilter(new FileNameExtensionFilter("Store file .sto", new String[]{"sto"}));

        _FileChooser_LoadStore.setApproveButtonText("Open Store");
        _FileChooser_LoadStore.setApproveButtonToolTipText("Loads the store file");
        _FileChooser_LoadStore.setCurrentDirectory(null);
        _FileChooser_LoadStore.setDialogTitle("Directory to open Store file");
        _FileChooser_LoadStore.setFileFilter(new panel.creator.FilterStore());

        _FileChooser_SaveIntoFolder.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        _FileChooser_SaveIntoFolder.setApproveButtonText("Save Here");
        _FileChooser_SaveIntoFolder.setApproveButtonToolTipText("Saves the displays into this directory");
        _FileChooser_SaveIntoFolder.setCurrentDirectory(null);
        _FileChooser_SaveIntoFolder.setDialogTitle("Directory to save pictures");
        _FileChooser_SaveIntoFolder.setFileFilter(new panel.creator.FilterFolder());
        _FileChooser_SaveIntoFolder.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        _FileChooser_SaveCSV.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        _FileChooser_SaveCSV.setApproveButtonText("Save");
        _FileChooser_SaveCSV.setApproveButtonToolTipText("Save CSV");
        _FileChooser_SaveCSV.setCurrentDirectory(null);
        _FileChooser_SaveCSV.setDialogTitle("Directory to save a picture");
        _FileChooser_SaveCSV.setFileFilter(new FileNameExtensionFilter("CSV (.csv)", new String[]{"csv"}));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Image Creator");
        setMinimumSize(new java.awt.Dimension(1045, 629));
        setResizable(false);

        _Menu_File.setText("File");

        _MenuItem_SaveCurrentDisplay.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_SaveCurrentDisplay.setText("Save Current Display");
        _MenuItem_SaveCurrentDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_SaveCurrentDisplayActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_SaveCurrentDisplay);

        _MenuItem_SaveAllDisplays.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_SaveAllDisplays.setText("Save All Displays");
        _MenuItem_SaveAllDisplays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_SaveAllDisplaysActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_SaveAllDisplays);

        _MenuItem_PrintVarNames.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_PrintVarNames.setText("Print Variable Names");
        _MenuItem_PrintVarNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_PrintVarNamesActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_PrintVarNames);

        _MenuItem_OpenStore.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_OpenStore.setText("Open Store");
        _MenuItem_OpenStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_OpenStoreActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_OpenStore);

        _MenuItem_SaveStore.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_SaveStore.setText("Save Store");
        _MenuItem_SaveStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_SaveStoreActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_SaveStore);

        _MenuItem_NewStore.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_NewStore.setText("New Store");
        _MenuItem_NewStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_NewStoreActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_NewStore);

        _MenuItem_Close.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_Close.setText("Exit");
        _MenuItem_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_CloseActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_Close);

        _MenuBar_Menus.add(_Menu_File);

        _Menu_View.setText("View");

        _MenuItem_ViewPanel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_ViewPanel.setText("View Panels");
        _MenuItem_ViewPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_ViewPanelActionPerformed(evt);
            }
        });
        _Menu_View.add(_MenuItem_ViewPanel);

        _MenuItem_changedisplay.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_changedisplay.setText("Change display tab");
        _MenuItem_changedisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_changedisplayActionPerformed(evt);
            }
        });
        _Menu_View.add(_MenuItem_changedisplay);

        _MenuBar_Menus.add(_Menu_View);

        _Menu_Image.setText("Image");

        _MenuItem_OpenImage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_OpenImage.setText("Open Image File");
        _MenuItem_OpenImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_OpenImageActionPerformed(evt);
            }
        });
        _Menu_Image.add(_MenuItem_OpenImage);

        _MenuItem_RemoveImage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_RemoveImage.setText("Remove Image");
        _MenuItem_RemoveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_RemoveImageActionPerformed(evt);
            }
        });
        _Menu_Image.add(_MenuItem_RemoveImage);

        _MenuBar_Menus.add(_Menu_Image);

        setJMenuBar(_MenuBar_Menus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_TabbedPane_Tabs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 969, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_TabbedPane_Tabs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _MenuItem_ViewPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_ViewPanelActionPerformed
        // TODO add your handling code here:
        int width = settingsPanel.getDisplayWidth();
        int height = settingsPanel.getDisplayHeight();
        displayPanel(width, height);
    }//GEN-LAST:event__MenuItem_ViewPanelActionPerformed

    private void _MenuItem_OpenImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_OpenImageActionPerformed
        // TODO add your handling code here:
        int returnVal = _FileChooser_LoadLogo.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = _FileChooser_LoadLogo.getSelectedFile();
            store.setImgStr(file.getAbsolutePath());
            displayFrame.updateLogo(store.getImgStr());
            controlPanel.updateStoreLogo(store.getImgStr());

        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event__MenuItem_OpenImageActionPerformed

    private void _MenuItem_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_CloseActionPerformed
        // TODO add your handling code here:
        main.close();
    }//GEN-LAST:event__MenuItem_CloseActionPerformed

    private void _MenuItem_SaveCurrentDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_SaveCurrentDisplayActionPerformed
        // TODO add your handling code here:

        int returnVal = _FileChooser_SavePicture.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fn = _FileChooser_SavePicture.getSelectedFile().toString();
            if (!fn.endsWith(".png") || !fn.endsWith(".jpg") || !fn.endsWith(".gif")) {
                fn += ".png";
            }
            // What to do with the file, e.g. display it in a TextArea

            try {
                BufferedImage bi = ScreenImage.createImage(displayFrame.getCurrentPane());
                ScreenImage.writeImage(bi, fn);
                //ScreenImage.createImage();
            } catch (AWTException | IOException e) {
                System.out.println("Error " + e.getMessage());
            }

        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event__MenuItem_SaveCurrentDisplayActionPerformed

    private void _MenuItem_RemoveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_RemoveImageActionPerformed
        // TODO add your handling code here:

        displayFrame.updateLogo();
        controlPanel.removeStoreLogo();

    }//GEN-LAST:event__MenuItem_RemoveImageActionPerformed

    private void _MenuItem_SaveStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_SaveStoreActionPerformed
        // TODO add your handling code here:
        int returnVal = _FileChooser_SaveStore.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fn = _FileChooser_SaveStore.getSelectedFile().toString();
            if (!fn.endsWith(".sto")) {
                fn += ".sto";
            }
            // What to do with the file, e.g. display it in a TextArea

            try {
                FileOutputStream fos = new FileOutputStream(fn);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(controlPanel.store); 
                oos.close();
                fos.close();
                System.out.println("Store saved");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("File access cancelled by user.");
        }


    }//GEN-LAST:event__MenuItem_SaveStoreActionPerformed

    private void _MenuItem_OpenStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_OpenStoreActionPerformed

        int returnVal = _FileChooser_LoadStore.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser_LoadStore.getSelectedFile();

            // What to do with the file, e.g. display it in a TextArea
            //System.out.println("File: " + file.getAbsolutePath());
            String filePath = file.getAbsolutePath();

            try {
                FileInputStream fis = new FileInputStream(filePath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.store = (Store) ois.readObject();                
                ois.close();
                fis.close();                
                System.out.println("Store read properly");
                controlPanel.loadStore(store);                
                ngPanel.loadStore(store);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("Class not found");
                c.printStackTrace();
            } 
            /*
            System.out.println("Store Load Debug\nRack count: " + controlPanel.store.getNumRacks());
            for (Rack r : controlPanel.store.getRacks()) {
                System.out.println(r);
            }*/
            
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event__MenuItem_OpenStoreActionPerformed

    private void _MenuItem_SaveAllDisplaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_SaveAllDisplaysActionPerformed
        // TODO add your handling code here:

        
        int returnVal = _FileChooser_SaveIntoFolder.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            String filePath = _FileChooser_SaveIntoFolder.getSelectedFile().toString() + "\\";
            //System.out.println("FP: " + filePath);
            String[] fileNames = controlPanel.getFileNames(filePath, displayFrame.bg.getSize());
            int numDisplays = displayFrame.getTabCount();
            this.store.writeCSV(filePath + " " + store.getStoreName() + "-IO Names.csv");
            BufferedImage bi;
            System.out.println("Num displays: " + numDisplays + "\tfilenames length: " + fileNames.length);
            
            for (int i = 0; i < numDisplays; i++) {
                //System.out.println(i + ": " + fileNames[i]);
                try {
                    if(i == 0){
                        bi = ScreenImage.createImage(displayFrame.bg);
                    }else if(i == (numDisplays-1)){
                        bi = ScreenImage.createImage(displayFrame.bgl);
                    }else {
                        bi = ScreenImage.createImage(displayFrame.rackTabs.get(i-1));
                    }
                    
                    ScreenImage.writeImage(bi, fileNames[i]);
                    //ScreenImage.createImage();
                    
                } catch (IOException e) {
                    System.out.println("Error " + e.getMessage());
                }
            }
        } else {
            System.out.println("File access cancelled by user.");
        }
       
    }//GEN-LAST:event__MenuItem_SaveAllDisplaysActionPerformed

    private void _MenuItem_changedisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_changedisplayActionPerformed
        // TODO add your handling code here:
        int d = displayFrame.getTabCount();
        int r = (int) (Math.random() * (d));
        displayFrame.changeTab(r);
        System.out.println("Changed to " + r);
    }//GEN-LAST:event__MenuItem_changedisplayActionPerformed

    private void _MenuItem_NewStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_NewStoreActionPerformed
        // TODO add your handling code here:
        displayFrame.dispose();
        _TabbedPane_Tabs.removeAll();
        this.store = new Store("New Store");
        initPanels();
        
        
    }//GEN-LAST:event__MenuItem_NewStoreActionPerformed

    private void _MenuItem_PrintVarNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_PrintVarNamesActionPerformed
        // TODO add your handling code here:
        
                       
        int returnVal = _FileChooser_SaveCSV.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser_SaveCSV.getSelectedFile();            
            //System.out.println("File: " + file.getAbsolutePath());
            String filePath = file.getAbsolutePath();
            
            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }
            
            
            this.store.writeCSV(filePath);
           
           
        } else {
            System.out.println("File access cancelled by user.");
        }
        
    }//GEN-LAST:event__MenuItem_PrintVarNamesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser _FileChooser_LoadLogo;
    private javax.swing.JFileChooser _FileChooser_LoadStore;
    private javax.swing.JFileChooser _FileChooser_SaveCSV;
    private javax.swing.JFileChooser _FileChooser_SaveIntoFolder;
    private javax.swing.JFileChooser _FileChooser_SavePicture;
    private javax.swing.JFileChooser _FileChooser_SaveStore;
    private javax.swing.JMenuBar _MenuBar_Menus;
    private javax.swing.JMenuItem _MenuItem_Close;
    private javax.swing.JMenuItem _MenuItem_NewStore;
    private javax.swing.JMenuItem _MenuItem_OpenImage;
    private javax.swing.JMenuItem _MenuItem_OpenStore;
    private javax.swing.JMenuItem _MenuItem_PrintVarNames;
    private javax.swing.JMenuItem _MenuItem_RemoveImage;
    private javax.swing.JMenuItem _MenuItem_SaveAllDisplays;
    private javax.swing.JMenuItem _MenuItem_SaveCurrentDisplay;
    private javax.swing.JMenuItem _MenuItem_SaveStore;
    private javax.swing.JMenuItem _MenuItem_ViewPanel;
    private javax.swing.JMenuItem _MenuItem_changedisplay;
    private javax.swing.JMenu _Menu_File;
    private javax.swing.JMenu _Menu_Image;
    private javax.swing.JMenu _Menu_View;
    private javax.swing.JTabbedPane _TabbedPane_Tabs;
    // End of variables declaration//GEN-END:variables
}
