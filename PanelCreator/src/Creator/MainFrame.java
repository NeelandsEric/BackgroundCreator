/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

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
    public ModbusPanel mbPanel;
    public WidgetPanel wgPanel;
    public TaskManagerPanel tmPanel;
    public Store store;
    public XMLParser xmlParser;
    private final String homeDirectory;
    public int stationId;

    /**
     * Creates new form MainFrame
     *
     * @param main
     */
    public MainFrame(PanelCreator main) {
        this.main = main;
        homeDirectory = System.getProperty("user.home") + "/PanelCreator";
        xmlParser = new XMLParser();

        boolean storeLoaded = loadDefaultStore();
        if (!storeLoaded) {
            this.store = new Store();
        }

        // Attempt to load the last stored store        
        initComponents();
        initPanels();

    }

    private void initPanels() {

        controlPanel = new ControlsPanel(this, store.getCs());
        settingsPanel = new SettingsPanel(this, store.getDs());
        ngPanel = new NameGeneratorPanel(this, store.getIoNames());
        mbPanel = new ModbusPanel(this, store.getMb());
        wgPanel = new WidgetPanel(this, store.getCs(), store.getWidgetSettings(), store.getWidgetSettings().getWpl());
        tmPanel = new TaskManagerPanel(this, store.getCs());
        displayFrame = new DisplayFrame(this, store.getCs(), store.getDs());
        displayFrame.setStopUpdate(true);

        //displayFrame.setVisible(true);
        mbPanel.initalizeMeters();
        controlPanel.updateDisplay();
        ngPanel.loadGroups();
        wgPanel.loadWidgetCode();

        displayFrame.updateSettings(this.store.getDs());
        settingsPanel.loadSettings(this.store.getDs());
        controlPanel.loadControlSettings(this.store.getCs());
        ngPanel.loadStore(this.store.getIoNames());
        mbPanel.loadStore(this.store.getMb());
        wgPanel.loadControlSettings(this.store.getCs(), this.store.getWidgetSettings());

        displayFrame.updateDisplays(this.store.getCs(), this.store.getDs());

        // add it to the frame           
        _TabbedPane_Tabs.add("Controls", controlPanel);
        _TabbedPane_Tabs.add("Settings", settingsPanel);
        _TabbedPane_Tabs.add("Name Generator", ngPanel);
        _TabbedPane_Tabs.add("Modbus Generator", mbPanel);
        _TabbedPane_Tabs.add("Widget Creator", wgPanel);
        _TabbedPane_Tabs.add("Task Manager", tmPanel);
        displayFrame.setStopUpdate(false);

    }

    private boolean loadDefaultStore() {

        if (!(new File(homeDirectory).mkdirs())) {
            // Directory exists, check if the Store exists
            String filePath = homeDirectory + "/DefaultStore.xml";
            if (new File(filePath).exists()) {
                try {
                    this.store = xmlParser.readFile(filePath);

                    if (store != null) {
                        if (controlPanel != null) {
                            controlPanel.writeToLog("Store " + this.store.getStoreName() + " read properly");
                        }
                        return true;
                    } else {
                        if (controlPanel != null) {
                            controlPanel.writeToLog("Error opening " + filePath + "\nMaking a new store!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public DefaultWidgets loadDefaultWidgets() {
        if (!(new File(homeDirectory).mkdirs())) {
            // Directory exists, check if the Store exists
            String filePath = homeDirectory + "/DefaultWidgets.xml";
            if (new File(filePath).exists()) {
                try {
                    DefaultWidgets dw = xmlParser.readWidgetsFile(filePath);

                    if (dw == null) {
                        System.out.println("Default widgets null");
                    } else {
                        System.out.println("Default widgets from home directory read and returned");
                        return dw;
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            } else {
                // File doesnt exist in home directory, use the file in the jar
                String path = "/Creator/textFiles/DefaultWidgets.xml";

                try (InputStream loc = this.getClass().getResourceAsStream(path)) {
                    DefaultWidgets dw = xmlParser.readWidgetsFile(loc);

                    if (dw == null) {
                        System.out.println("Default widgets null");
                    } else {
                        System.out.println("Default widgets from JAR read and returned");
                        return dw;
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }

            }
        }
        return null;

    }

    public IoNames loadDefaultIoNames() {
        if (!(new File(homeDirectory).mkdirs())) {
            // Directory exists, check if the Store exists
            String filePath = homeDirectory + "/DefaultIoNames.xml";
            if (new File(filePath).exists()) {
                try {
                    IoNames names = xmlParser.readIoNamesFile(filePath);

                    if (names == null) {
                        System.out.println("Default io names null");
                    } else {
                        System.out.println("Default names read and returned");
                        return names;
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
        return null;

    }

    public void loadImportedIos(Map<String, Integer> importedIos, int caller, int stationId) {
        this.stationId = stationId;
        if (caller == 1) { // Widget panel call
            tmPanel.setImportedIoVariables(importedIos, stationId);
            mbPanel.setImportedIoVariables(importedIos, stationId);

        } else if (caller == 2) { // task manager call
            wgPanel.setImportedIoVariables(importedIos, stationId);
            mbPanel.setImportedIoVariables(importedIos, stationId);

        } else if (caller == 3) { // modbus call
            wgPanel.setImportedIoVariables(importedIos, stationId);
            tmPanel.setImportedIoVariables(importedIos, stationId);

        }
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getStationId() {
        return stationId;
    }

    public List<String[]> getStringsNoParams(boolean addHeader) {

        return this.store.ioNames.formatStringsNoParams(this.store.getCs(), addHeader);

    }

    public Map<String, List> getMapFullStrings() {

        return this.store.ioNames.mapFullStrings(this.store.getCs());

    }

    public void updateWidgetSettings(WidgetSettings ws) {
        //System.out.println("before updating widget links\n" + this.store.getWidgetSettings());
        if (ws.equals(this.store.getWidgetSettings())) {
            //System.out.println("Update Widget Settings seems to be the same one");
        }
        this.store.setWidgetSettings(ws);
        //System.out.println("-------------------\nafter updating widget links\n" + this.store.getWidgetSettings());
    }

    public void updateModbusSettings(ModbusSettings mb) {
        this.store.setMb(mb);
    }

    public void updateDisplaySettingsSize(Dimension d) {
        if (settingsPanel != null) {
            settingsPanel.setDim(d);
        }
    }

    public void updateDisplaySize(int width, int height) {
        displayFrame.setNewSize(width, height);
    }

    public void updateSettings(DisplaySettings ds) {

        if (this.store.ds.equals(ds)) {
            this.store.setDs(ds);
            displayFrame.updateSettings(ds);
        }
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

    public void updateDisplay(ControlSettings cs) {

        if (this.store.cs.equals(cs)) {
            displayFrame.setStopUpdate(true);
            this.store.setCs(cs);
            store.getMb().updateModbusSettings(cs);
            mbPanel.loadModels();
            wgPanel.setCs(cs);
            tmPanel.setCs(cs);
            displayFrame.setStopUpdate(false);
            displayFrame.updateDisplays(this.store.getCs(), this.store.getDs());
        }

    }

    public void updateVarNames(IoNames ioNames) {
        store.setIoNames(ioNames);
    }

    public void returnClick(Point point) {
        wgPanel.returnClick(point);        
    }
    
    public void returnClick(Point point, String buttonName, int buttonX, int buttonWidth){
        wgPanel.buttonClick(point, buttonName, buttonX, buttonWidth);
    }

    public void saveDefault() {
        if (xmlParser != null) {
            if (xmlParser.writeOut(this.store, homeDirectory + "/DefaultStore.xml")) {
                System.out.println("Store " + this.store.getStoreName() + " saved");
            } else {
                System.out.println("Store " + this.store.getStoreName() + " had a problem saving");
            }

        } else {
            System.out.println("Problem with the XMLParser");
        }
    }

    public void saveDefaultWidgets() {
        if (xmlParser != null) {
            if (xmlParser.writeOutDefaultWidgets(this.wgPanel.getDefaultWidgets(), homeDirectory + "/DefaultWidgets.xml")) {
                System.out.println("Default Widgets saved");
            } else {
                System.out.println("Default Widgets had a problem saving");
            }

        } else {
            System.out.println("Problem with the XMLParser");
        }
    }

    public void saveDefaultIoNames(IoNames ioNames) {
        if (xmlParser != null) {
            DefaultIoNames names = new DefaultIoNames(ioNames);
            if (xmlParser.writeOutDefaultIoNames(names, homeDirectory + "/DefaultIoNames.xml")) {
                System.out.println("Default Io Names saved");
            } else {
                System.out.println("Default Io Names had a problem saving");
            }

        } else {
            System.out.println("Problem with the XMLParser");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _FileChooser = new javax.swing.JFileChooser();
        _TabbedPane_Tabs = new javax.swing.JTabbedPane();
        _MenuBar_Menus = new javax.swing.JMenuBar();
        _Menu_File = new javax.swing.JMenu();
        _MenuItem_SaveCurrentDisplay = new javax.swing.JMenuItem();
        _MenuItem_SaveAllDisplays = new javax.swing.JMenuItem();
        _MenuItem_PrintVarNamesX = new javax.swing.JMenuItem();
        _MenuItem_PrintVarNamesCsv = new javax.swing.JMenuItem();
        _MenuItem_PrintVarNamesText = new javax.swing.JMenuItem();
        _MenuItem_NewStore = new javax.swing.JMenuItem();
        _MenuItem_OpenStore = new javax.swing.JMenuItem();
        _MenuItem_SaveStore = new javax.swing.JMenuItem();
        _MenuItem_SaveAll = new javax.swing.JMenuItem();
        _MenuItem_Close = new javax.swing.JMenuItem();
        _Menu_View = new javax.swing.JMenu();
        _MenuItem_ViewPanel = new javax.swing.JMenuItem();
        _MenuItem_changedisplay = new javax.swing.JMenuItem();
        _Menu_Image = new javax.swing.JMenu();
        _MenuItem_OpenImage = new javax.swing.JMenuItem();
        _MenuItem_RemoveImage = new javax.swing.JMenuItem();

        _FileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        _FileChooser.setApproveButtonText("Save");
        _FileChooser.setApproveButtonToolTipText("Save in this folder");
        _FileChooser.setCurrentDirectory(null);
        _FileChooser.setDialogTitle("");
        _FileChooser.setFileHidingEnabled(true);
        _FileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Panel Creator");
        setMinimumSize(new java.awt.Dimension(1045, 640));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeFrame(evt);
            }
        });

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

        _MenuItem_PrintVarNamesX.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_PrintVarNamesX.setText("Print Variable Names to .xlsx");
        _MenuItem_PrintVarNamesX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_PrintVarNamesXActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_PrintVarNamesX);

        _MenuItem_PrintVarNamesCsv.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_PrintVarNamesCsv.setText("Print Variable Names to .csv");
        _MenuItem_PrintVarNamesCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_PrintVarNamesCsvActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_PrintVarNamesCsv);

        _MenuItem_PrintVarNamesText.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_PrintVarNamesText.setText("Print Variable Names to .txt");
        _MenuItem_PrintVarNamesText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_PrintVarNamesTextActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_PrintVarNamesText);

        _MenuItem_NewStore.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_NewStore.setText("New Store");
        _MenuItem_NewStore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_NewStoreActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_NewStore);

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

        _MenuItem_SaveAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_SaveAll.setText("Save Everything");
        _MenuItem_SaveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_SaveAllActionPerformed(evt);
            }
        });
        _Menu_File.add(_MenuItem_SaveAll);

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

        _MenuItem_ViewPanel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_ViewPanel.setText("View Panels");
        _MenuItem_ViewPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_ViewPanelActionPerformed(evt);
            }
        });
        _Menu_View.add(_MenuItem_ViewPanel);

        _MenuItem_changedisplay.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        _MenuItem_changedisplay.setText("Next Display Tab");
        _MenuItem_changedisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _MenuItem_changedisplayActionPerformed(evt);
            }
        });
        _Menu_View.add(_MenuItem_changedisplay);

        _MenuBar_Menus.add(_Menu_View);

        _Menu_Image.setText("Image");

        _MenuItem_OpenImage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
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
            .addComponent(_TabbedPane_Tabs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_TabbedPane_Tabs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _MenuItem_ViewPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_ViewPanelActionPerformed

        int width = store.getDs().getDisplayWidth();
        int height = store.getDs().getDisplayHeight();
        displayPanel(width, height);
    }//GEN-LAST:event__MenuItem_ViewPanelActionPerformed

    private void _MenuItem_OpenImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_OpenImageActionPerformed

        File oldFileLoc = _FileChooser.getCurrentDirectory();
        _FileChooser.setDialogTitle("Load Logo Image");
        _FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser.setFileFilter(new FilterImage());
        _FileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        _FileChooser.setApproveButtonText("Open image file");
        _FileChooser.setApproveButtonToolTipText("Open");

        int returnVal = _FileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = _FileChooser.getSelectedFile();
            store.getCs().setImgStr(file.getAbsolutePath());
            controlPanel.updateStoreLogo(store.getCs().getImgStr());

        } else {
            System.out.println("File access cancelled by user.");
        }

        _FileChooser.setCurrentDirectory(oldFileLoc);
    }//GEN-LAST:event__MenuItem_OpenImageActionPerformed

    private void _MenuItem_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_CloseActionPerformed

        tmPanel.closeConn();
        if (xmlParser != null) {
            if (xmlParser.writeOut(this.store, homeDirectory + "/DefaultStore.xml")) {
                controlPanel.writeToLog("Store " + this.store.getStoreName() + " saved");
            } else {
                controlPanel.writeToLog("Store " + this.store.getStoreName() + " had a problem saving");
            }
        } else {
            System.out.println("Problem with the XMLParser");
        }

        main.close();
    }//GEN-LAST:event__MenuItem_CloseActionPerformed

    private void _MenuItem_SaveCurrentDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_SaveCurrentDisplayActionPerformed

        _FileChooser.setDialogTitle("Save Current Panel");
        _FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser.setFileFilter(new FileNameExtensionFilter("Image files (.png, .jpg)", new String[]{"png", "jpg"}));
        _FileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser.setApproveButtonText("Save image file");
        _FileChooser.setApproveButtonToolTipText("Save");

        int returnVal = _FileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fn = _FileChooser.getSelectedFile().toString();
            if (!fn.endsWith(".png")) {
                fn += ".png";
            }
            // What to do with the file, e.g. display it in a TextArea

            try {
                BufferedImage bi = ScreenImage.createImage(displayFrame.getCurrentPane());
                ScreenImage.writeImage(bi, fn);
                //ScreenImage.createImage();
            } catch (AWTException | IOException e) {
                controlPanel.writeToLog("Error saving current display as a picture" + e.getMessage());
            }
            // SVG working but not used atm
            /*
             try{ 
             BufferedImage bi = ScreenImage.createImage(displayFrame.getCurrentPane());
                
             SVGGraphics2D g = new SVGGraphics2D(bi.getWidth(), bi.getHeight());
             BufferedImageOp op = new AffineTransformOp(new AffineTransform(), AffineTransformOp.TYPE_BILINEAR);
                
             g.drawImage(bi, op, 0, 0);
             File f = new File(fn.replace(".png", ".svg"));
             SVGUtils.writeToSVG(f, g.getSVGElement());                
                
             //ScreenImage.createImage();
             } catch (AWTException | IOException e) {
             controlPanel.writeToLog("Error saving current display as a picture" + e.getMessage());
             }*/

        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event__MenuItem_SaveCurrentDisplayActionPerformed

    private void _MenuItem_RemoveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_RemoveImageActionPerformed

        controlPanel.removeStoreLogo();

    }//GEN-LAST:event__MenuItem_RemoveImageActionPerformed

    private void _MenuItem_SaveStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_SaveStoreActionPerformed

        _FileChooser.setDialogTitle("Save Store (XML)");
        _FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser.setFileFilter(new FileNameExtensionFilter("XML File .xml", new String[]{"xml"}));
        _FileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser.setApproveButtonText("Save Store file");
        _FileChooser.setApproveButtonToolTipText("Save");

        int returnVal = _FileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fn = _FileChooser.getSelectedFile().toString();
            if (!fn.endsWith(".xml")) {
                fn += ".xml";
            }
            // What to do with the file, e.g. display it in a TextArea

            if (xmlParser != null) {
                if (xmlParser.writeOut(this.store, fn)) {
                    controlPanel.writeToLog("Store " + this.store.getStoreName() + " saved");
                } else {
                    controlPanel.writeToLog("Store " + this.store.getStoreName() + " had a problem saving");
                }
            } else {
                System.out.println("Problem with the XMLParser");
            }

        } else {
            System.out.println("File access cancelled by user.");
        }


    }//GEN-LAST:event__MenuItem_SaveStoreActionPerformed

    private void _MenuItem_OpenStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_OpenStoreActionPerformed

        _FileChooser.setDialogTitle("Open Store File (XML)");
        _FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser.setFileFilter(new FilterStore());
        _FileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        _FileChooser.setApproveButtonText("Open Store (XML) file");
        _FileChooser.setApproveButtonToolTipText("Open");

        int returnVal = _FileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser.getSelectedFile();

            // What to do with the file, e.g. display it in a TextArea
            //System.out.println("File: " + file.getAbsolutePath());
            String filePath = file.getAbsolutePath();

            this.store = xmlParser.readFile(filePath);

            if (store == null) {
                controlPanel.writeToLog("Error opening " + filePath);
            } else {
                displayFrame.updateSettings(this.store.getDs());
                settingsPanel.loadSettings(this.store.getDs());
                controlPanel.loadControlSettings(this.store.getCs());
                ngPanel.loadStore(this.store.getIoNames());
                mbPanel.loadStore(this.store.getMb());
                wgPanel.loadControlSettings(this.store.getCs(), this.store.getWidgetSettings());
                controlPanel.writeToLog("Store " + this.store.getStoreName() + " read properly");

                displayFrame.updateDisplays(this.store.getCs(), this.store.getDs());
            }

        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event__MenuItem_OpenStoreActionPerformed

    private void _MenuItem_SaveAllDisplaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_SaveAllDisplaysActionPerformed

        _FileChooser.setDialogTitle("Save pictures into a folder");
        _FileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //_FileChooser.setFileFilter(null);
        _FileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser.setApproveButtonText("Save Files Here");
        _FileChooser.setApproveButtonText("Save");

        int returnVal = _FileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            String filePath = _FileChooser.getSelectedFile().toString() + "\\";
            //System.out.println("FP: " + filePath);
            String[] fileNames = controlPanel.getFileNames(filePath, displayFrame.bg.getSize());
            int numDisplays = displayFrame.getTabCount();
            BufferedImage bi;
            Component[] comps = displayFrame.getPanelPictures();

            int numRacks = store.cs.getNumRacks();

            for (int i = 0; i < numDisplays; i++) {
                //System.out.println(i + ": " + fileNames[i]);

                try {
                    /*
                     if (i == 0) {
                     bi = ScreenImage.createImage(displayFrame.bg);
                     } else if (i > 0 && i <= numRacks) {
                     bi = ScreenImage.createImage(displayFrame.rackTabs.get(i - 1));
                     } else if (i > numRacks && i <= (numRacks * 2)) {
                     bi = ScreenImage.createImage(displayFrame.loadTabs.get(i - numRacks - 1), true);
                     } else if (i == (numDisplays - 3)) {
                     bi = ScreenImage.createImage(displayFrame.bgf);
                     } else if (i == (numDisplays - 2)) {
                     bi = ScreenImage.createImage(displayFrame.bge);
                     } else if (i == (numDisplays - 1)) {
                     bi = ScreenImage.createImage(displayFrame.bgg);
                     } else {                        
                     bi = ScreenImage.createImage(displayFrame.bg);
                     }*/
                    if (i > numRacks && i <= (numRacks * 2)) {
                        bi = ScreenImage.createImage((JScrollPane) displayFrame.getPanelAtIndex(i, numRacks, numDisplays));

                    } else {
                        bi = ScreenImage.createImage((JPanel) displayFrame.getPanelAtIndex(i, numRacks, numDisplays));
                    }
                    //bi = ScreenImage.createImage(comps[i]);

                    ScreenImage.writeImage(bi, fileNames[i]);
                    //ScreenImage.createImage();

                } catch (IOException e) {
                    controlPanel.writeToLog("Error writing csv file" + e.getMessage());
                }
            }
        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event__MenuItem_SaveAllDisplaysActionPerformed

    private void _MenuItem_changedisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_changedisplayActionPerformed

        int count = displayFrame.getTabCount();
        int curr = displayFrame.getTabSelection();
        if ((curr + 1) < count) {
            curr += 1;
        } else {
            curr = 0;
        }
        displayFrame.changeTab(curr);
    }//GEN-LAST:event__MenuItem_changedisplayActionPerformed

    private void _MenuItem_NewStoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_NewStoreActionPerformed

        displayFrame.dispose();
        _TabbedPane_Tabs.removeAll();
        this.store = new Store();
        initPanels();


    }//GEN-LAST:event__MenuItem_NewStoreActionPerformed

    private void _MenuItem_PrintVarNamesCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_PrintVarNamesCsvActionPerformed

        _FileChooser.setDialogTitle("Save IO Imports As CSV File");
        _FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser.setFileFilter(new FileNameExtensionFilter("Comma Seperated Values (.csv)", new String[]{"csv"}));
        _FileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser.setApproveButtonText("Save CSV file");
        _FileChooser.setApproveButtonToolTipText("Save");

        int returnVal = _FileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser.getSelectedFile();
            //System.out.println("File: " + file.getAbsolutePath());
            String filePath = file.getAbsolutePath();
            String filePath2 = filePath;
            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }
            if (!filePath2.endsWith(".csv")) {
                filePath2 += "-NOPARAMS.csv";
            } else {
                filePath2 = filePath2.replace(".csv", "-NOPARAMS.csv");
            }
            this.store.writeCSV(filePath);
            this.store.writeCSVNoParams(filePath2);

        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event__MenuItem_PrintVarNamesCsvActionPerformed

    private void _MenuItem_PrintVarNamesTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_PrintVarNamesTextActionPerformed

        _FileChooser.setDialogTitle("Save IO Imports As Text File");
        _FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser.setFileFilter(new FileNameExtensionFilter("Text file (.txt)", new String[]{"txt"}));
        _FileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser.setApproveButtonText("Save Text file");
        _FileChooser.setApproveButtonToolTipText("Save");

        int returnVal = _FileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser.getSelectedFile();
            //System.out.println("File: " + file.getAbsolutePath());
            String filePath = file.getAbsolutePath();
            String fp = filePath + "OnlyNames";
            if (!filePath.endsWith(".txt")) {
                filePath += ".txt";
            }
            if (!fp.endsWith(".txt")) {
                fp += ".txt";
            }
            this.store.writeCSV(filePath);
            this.store.writeNames(fp);

        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event__MenuItem_PrintVarNamesTextActionPerformed

    private void _MenuItem_PrintVarNamesXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_PrintVarNamesXActionPerformed

        _FileChooser.setDialogTitle("Save IO Imports As Excel File");
        _FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser.setFileFilter(new FileNameExtensionFilter("Excel workbook (.xlsx)", new String[]{"xlsx"}));
        _FileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser.setApproveButtonText("Save Excel file");
        _FileChooser.setApproveButtonToolTipText("Save");

        int returnVal = _FileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser.getSelectedFile();
            //System.out.println("File: " + file.getAbsolutePath());
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try {
                Workbook wb = new XSSFWorkbook();
                FileOutputStream fileOut = new FileOutputStream(filePath);

                List<String[]> list = store.formatStrings();
                int rowNum = 0;
                Sheet sheet = wb.createSheet("Var Names");

                for (String[] r : list) {
                    // Create a row and put some cells in it. Rows are 0 based.
                    Row row = sheet.createRow(rowNum);
                    // Create a cell and put a value in it.
                    for (int i = 0; i < r.length; i++) {
                        Cell cell = row.createCell(i);

                        // If the string is a number, write it as a number
                        if (r[i].equals("")) {
                            // Empty field, do nothing

                        } else if (isStringNumeric(r[i])) {
                            cell.setCellValue(Double.parseDouble(r[i].replace("\"", "")));
                        } else {
                            cell.setCellValue(r[i]);
                        }

                    }

                    rowNum++;

                }

                wb.write(fileOut);
                fileOut.close();
            } catch (Exception e) {
                controlPanel.writeToLog("Error with creating excel file " + e.getMessage());
            }

        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event__MenuItem_PrintVarNamesXActionPerformed

    private void closeFrame(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeFrame

        tmPanel.closeConn();
        if (xmlParser != null) {
            if (xmlParser.writeOut(this.store, homeDirectory + "/DefaultStore.xml")) {
                controlPanel.writeToLog("Store " + this.store.getStoreName() + " saved");
            } else {
                controlPanel.writeToLog("Store " + this.store.getStoreName() + " had a problem saving");
            }
        } else {
            System.out.println("Problem with the XMLParser");
        }
        main.close();
    }//GEN-LAST:event_closeFrame

    private void _MenuItem_SaveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__MenuItem_SaveAllActionPerformed

        _FileChooser.setDialogTitle("Save everything into a folder");
        _FileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        _FileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser.setApproveButtonText("Save Here");
        _FileChooser.setApproveButtonToolTipText("Save");

        int returnVal = _FileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePathORIGINAL = _FileChooser.getSelectedFile().toString();
            String fn = filePathORIGINAL + "\\" + this.store.getStoreName() + ".xml";

            // What to do with the file, e.g. display it in a TextArea
            if (xmlParser != null) {
                if (xmlParser.writeOut(this.store, fn)) {
                    controlPanel.writeToLog("Store " + this.store.getStoreName() + " saved");
                } else {
                    controlPanel.writeToLog("Store " + this.store.getStoreName() + " had a problem saving");
                }
            } else {
                System.out.println("Problem with the XMLParser");
            }

            // -------------------- Save all displays --------------------------
            String filePath = filePathORIGINAL + "\\Displays\\";

            if (!new File(filePath).mkdir()) {
                filePath = filePath.replace("Displays\\", "");
            }
            //System.out.println("FP: " + filePath);
            String[] fileNames = controlPanel.getFileNames(filePath, displayFrame.bg.getSize());
            int numDisplays = displayFrame.getTabCount();
            BufferedImage bi;

            int numRacks = store.cs.getNumRacks();
            for (int i = 0; i < numDisplays; i++) {
                //System.out.println(i + ": " + fileNames[i]);
                try {
                    if (i == 0) {
                        bi = ScreenImage.createImage(displayFrame.bg);
                    } else if (i > 0 && i <= numRacks) {
                        bi = ScreenImage.createImage(displayFrame.rackTabs.get(i - 1));
                    } else if (i > numRacks && i <= (numRacks * 2)) {
                        bi = ScreenImage.createImage(displayFrame.loadTabs.get(i - (numRacks + 1)));
                    } else if (i == (numDisplays - 3)) {
                        bi = ScreenImage.createImage(displayFrame.bgf);
                    } else if (i == (numDisplays - 2)) {
                        bi = ScreenImage.createImage(displayFrame.bge);
                    } else if (i == (numDisplays - 1)) {
                        bi = ScreenImage.createImage(displayFrame.bgg);
                    } else {
                        System.out.println("Screen Print else on i = " + i);
                        bi = ScreenImage.createImage(displayFrame.bg);
                    }

                    ScreenImage.writeImage(bi, fileNames[i]);
                    //ScreenImage.createImage();

                } catch (IOException e) {
                    controlPanel.writeToLog("Error writing image file" + e.getMessage());
                }
            }

            // -------------------------- Save XLSX --------------------
            File file = new File(filePathORIGINAL + "\\" + this.store.getStoreName() + "-IOVariables.xlsx");
            //System.out.println("File: " + file.getAbsolutePath());
            String excelPath = file.getAbsolutePath();

            try {
                Workbook wb = new XSSFWorkbook();
                FileOutputStream fileOut = new FileOutputStream(excelPath);

                List<String[]> list = store.formatStrings();
                int rowNum = 0;
                Sheet sheet = wb.createSheet("Var Names");

                for (String[] r : list) {
                    // Create a row and put some cells in it. Rows are 0 based.
                    Row row = sheet.createRow(rowNum);
                    // Create a cell and put a value in it.
                    for (int i = 0; i < r.length; i++) {
                        Cell cell = row.createCell(i);

                        // If the string is a number, write it as a number
                        if (r[i].equals("")) {
                            // Empty field, do nothing

                        } else if (isStringNumeric(r[i])) {
                            cell.setCellValue(Double.parseDouble(r[i].replace("\"", "")));
                        } else {
                            cell.setCellValue(r[i]);
                        }

                    }

                    rowNum++;

                }

                wb.write(fileOut);
                fileOut.close();
            } catch (NumberFormatException | IOException e) {
                controlPanel.writeToLog("Error with creating excel file " + e.getMessage());
            }

        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event__MenuItem_SaveAllActionPerformed

    public static boolean isStringNumeric(String str) {
        DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
        char localeMinusSign = currentLocaleSymbols.getMinusSign();

        if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != localeMinusSign) {
            return false;
        }

        boolean isDecimalSeparatorFound = false;
        char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

        for (char c : str.substring(1).toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == localeDecimalSeparator && !isDecimalSeparatorFound) {
                    isDecimalSeparatorFound = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser _FileChooser;
    private javax.swing.JMenuBar _MenuBar_Menus;
    private javax.swing.JMenuItem _MenuItem_Close;
    private javax.swing.JMenuItem _MenuItem_NewStore;
    private javax.swing.JMenuItem _MenuItem_OpenImage;
    private javax.swing.JMenuItem _MenuItem_OpenStore;
    private javax.swing.JMenuItem _MenuItem_PrintVarNamesCsv;
    private javax.swing.JMenuItem _MenuItem_PrintVarNamesText;
    private javax.swing.JMenuItem _MenuItem_PrintVarNamesX;
    private javax.swing.JMenuItem _MenuItem_RemoveImage;
    private javax.swing.JMenuItem _MenuItem_SaveAll;
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
