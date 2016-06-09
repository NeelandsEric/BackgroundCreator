/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.security.CodeSource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.Highlighter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author EricGummerson
 */
public class WidgetPanel extends javax.swing.JPanel {

    private MainFrame mf;                                   // main frame    
    private ControlSettings cs;                             // control settings    
    private WidgetSettings ws;                              // Widget settings
    private DefaultListModel listModelWidgetsVars;          // List model for the default widgets vars used on displays
    private DefaultListModel listModelCodeWidgets;          // List model for the code widgets
    private DefaultListModel listModelMasterMap;            // List model for the master map variables
    private Map<String, WidgetCode> widgetList;             // WidgetName,WidgetCode Variable
    private Map<String, List<String>> groupNamesWidget;     // GroupName,List of vars for the group (Store [Temp, Humidity, ..])
    private Map<String, Integer> importedIOVariables;       // io_name,io_id
    private Map<String, Map<String, Rectangle>> masterMap;  // list of variables relating to each view's variables and locations on the panel
    private Map<String, JTextField> widgetCodeSettings;      // custom values for widget code settings
    private int stationID;
    private TreeModel treeModel;
    private DBConn db;

    long holderNum;                                         // Used to change the holder variables

    private static String widgetCodeName = "Panel1 Link";

    private static String deleteSingleQuery = "delete from panel_content where panel_id = %s;";
    private static String deleteAllQuery = "delete from panel_content where panel_id in (%s);";
    private static String insertQuery = "insert into panel_content (panel_id, widget_class_id, posx, posy, content)"
            + " values %s;";

    /**
     * Creates new form WidgetPanel
     *
     * @param mf
     * @param cs
     * @param ws
     */
    public WidgetPanel(MainFrame mf, ControlSettings cs, WidgetSettings ws) {
        this.mf = mf;
        this.cs = cs;
        this.widgetList = new TreeMap<>();
        this.ws = ws;
        holderNum = Instant.now().toEpochMilli();
        if (this.ws.wpl != null) {
            this.ws.wpl = ws.wpl;
        } else {
            this.ws.wpl = new WidgetPanelLinks();
        }
        this.listModelWidgetsVars = new DefaultListModel();
        this.listModelCodeWidgets = new DefaultListModel();
        this.listModelMasterMap = new DefaultListModel();
        this.stationID = -1;
        initComponents();
        loadComboBoxPanels();
        _ComboBox_Panels.setSelectedIndex(0);

    }

    public void loadControlSettings(ControlSettings cs, WidgetSettings ws) {
        this.cs = cs;
        this.ws = ws;
        this.widgetList = new TreeMap<>();

        updateDisplay();
        // Load defaults if no links
        if (ws.getWidgetLinks().isEmpty()) {
            DefaultWidgets dw = mf.loadDefaultWidgets();
            if (dw != null) {
                ws.setWidgetLinks(dw.getWidgetLinks());
            }
        }
        loadWidgetCode();
        loadTree();
    }

    public DBConn newDBConn() {
        if (db == null) {
            return new DBConn();
        } else if (!db.isConnOpened()) {
            return new DBConn();
        } else {
            return db;
        }
    }

    public void closeConn() {
        if (db != null) {
            db.closeConn();
            db = null;
        }
    }

    public WidgetPanelLinks getWpl() {
        return ws.wpl;
    }

    public void setWpl(WidgetPanelLinks wpl) {
        this.ws.wpl = wpl;
    }

    public void setImportedIoVariables(Map<String, Integer> newIo, int stationId) {
        if (importedIOVariables != null && !importedIOVariables.isEmpty()) {
            importedIOVariables.clear();
        }
        this.stationID = stationId;
        importedIOVariables = newIo;
        _Button_CreateImports.setEnabled(true);
        _Label_Loaded.setText("Loaded File!");
    }

    public Map<String, Integer> getImportedIoVariables() {
        return importedIOVariables;
    }

    private void loadComboBoxPanels() {

        String[] tabs = new String[cs.getNumRacks() * 2 + 4 + 1]; // racks * 2 (loads/rack) + 4 (panels) + map
        tabs[0] = "Main";
        for (int i = 0; i < cs.getNumRacks(); i++) {
            tabs[i + 1] = cs.getRackName(i);
        }
        for (int i = 0; i < cs.getNumRacks(); i++) {
            tabs[i + cs.getNumRacks() + 1] = cs.getRackName(i).replace("Rack", "Load");
        }
        tabs[tabs.length - 4] = "Financial";
        tabs[tabs.length - 3] = "Energy";
        tabs[tabs.length - 2] = "Glycol";
        tabs[tabs.length - 1] = "Map";

        _ComboBox_Panels.setModel(new javax.swing.DefaultComboBoxModel(tabs));

    }

    public ControlSettings getCs() {
        return cs;
    }

    public void setCs(ControlSettings cs) {
        if (this.cs != cs) {
            this.cs = cs;
        }

        loadComboBoxPanels();
        updateDisplay();
    }

    public WidgetSettings getWs() {
        return ws;
    }

    public WidgetCode getWidgetCode(String widgetName) {
        if (widgetList.containsKey(widgetName)) {
            return widgetList.get(widgetName);
        } else {
            return null;
        }
    }

    public void setWs(WidgetSettings ws) {
        this.ws = ws;
        updateDisplay();
    }

    public void updateDisplay() {

        String[] tabs = new String[cs.getNumRacks() * 2 + 4];
        tabs[0] = "Main";
        for (int i = 0; i < cs.getNumRacks(); i++) {
            tabs[i + 1] = "R: " + cs.getRackName(i);
        }
        for (int i = 0; i < cs.getNumRacks(); i++) {
            tabs[i + cs.getNumRacks() + 1] = "L: " + cs.getRackName(i).replace("Rack", "Load");
        }
        tabs[tabs.length - 3] = "Financial";
        tabs[tabs.length - 2] = "Energy";
        tabs[tabs.length - 1] = "Glycol";

        _ComboBox_DisplayPanel.setModel(new javax.swing.DefaultComboBoxModel(tabs));

        if (masterMap != null) {
            loadMasterMapList();
        }

    }

    public void loadWidgetCode() {

        boolean ide = true;
        String pathWidgetCode = "textFiles/widgets/";
        ArrayList<String> results = new ArrayList<>();
        String dirCode = new File("").getAbsolutePath() + "/src/Creator/" + pathWidgetCode;

        try {
            getWidgetFiles(dirCode, results);

            results.stream().forEach((s) -> {
                readCodeFile(s);
            });

            updateWidgetCode();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            ide = false;
        }

        if (!ide) {
            CodeSource src = WidgetPanel.class.getProtectionDomain().getCodeSource();
            try {
                List<String> list = new ArrayList<>();
                if (src != null) {
                    URL jar = src.getLocation();
                    ZipInputStream zip = new ZipInputStream(jar.openStream());
                    ZipEntry ze = null;

                    while ((ze = zip.getNextEntry()) != null) {

                        String entryName = ze.getName();
                        if (entryName.startsWith("Creator/textFiles/widgets") && entryName.endsWith(".txt")) {

                            list.add("/" + entryName);
                            //System.out.println("Added name: " + entryName);
                        }
                    }
                    list.stream().forEach((s) -> {
                        readJarFile(s);
                    });

                    updateWidgetCode();

                } else {
                    System.out.println("Src null");
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        readWidgetVars();
    }

    public void returnClick(Point p) {
        //System.out.println("WidgetCode click at " + p);
        clicked(p);

    }

    public void buttonClick(Point p, String buttonName, int buttonX, int buttonWidth) {

        Point np = new Point();
        if (buttonName.startsWith("Rack")) {
            // Rack
            np.x = buttonX + 10;
            np.y = p.y + 6;
            //np.y = p.y + 

        } else if (buttonName.startsWith("Load")) {
            // Load
            np.x = buttonX + 10;
            np.y = p.y + 6;

        } else if (buttonName.startsWith("Financial")) {
            // Financial
            np.x = buttonX + 7;
            np.y = p.y + 6;

        } else if (buttonName.startsWith("Energy")) {
            // Energy 
            np.x = buttonX + 10;
            np.y = p.y + 6;

        } else if (buttonName.startsWith("Glycol")) {
            // Glycol
            np.x = buttonX + 10;
            np.y = p.y + 6;

        } else if (buttonName.startsWith("Main")) {
            // Main
            np.x = buttonX + 15;
            np.y = p.y + 6;

        } else {
            np.x = buttonX + 10;
            np.y = p.y + 6;
            // Rack just probably named wierd

        }

        _FTF_XPOS.setText(String.valueOf(np.x));
        _FTF_YPOS.setText(String.valueOf(np.y));

    }

    public void clicked(Point p) {
        _FTF_WigetParam_xPos.setText(String.valueOf(p.x));
        _FTF_WigetParam_yPos.setText(String.valueOf(p.y));

        if (!_List_MasterMapVariables.isSelectionEmpty()) {
            // Check to see if the selected item contains the clicked point
            String varName = _List_MasterMapVariables.getSelectedValue().toString();
            varName = varName.substring(0, varName.indexOf(":"));
            String index = _ComboBox_DisplayPanel.getSelectedItem().toString();

            if (index.startsWith("R:")) {
                //index = "Rack";
            }

            if (masterMap.get(index).containsKey(varName)) {
                Rectangle r = masterMap.get(index).get(varName);
                if (r.contains(p)) {

                    Point per = percentage(p, r);
                    _FTF_WigetParam_xPosPer.setValue(per.x);
                    _FTF_WigetParam_yPosPer.setValue(per.y);
                } else {
                    _FTF_WigetParam_xPosPer.setValue(-2);
                    _FTF_WigetParam_yPosPer.setValue(-2);
                }

            }

        }
    }

    public void updateWidgetCode() {

        listModelCodeWidgets.removeAllElements();

        widgetList.values().stream().forEach((widget) -> {

            listModelCodeWidgets.addElement(widget.getWidgetName());
        });

    }

    /**
     * Calculates the percentage of the point inside the rectangle
     *
     * @param p point inside the rectangle
     * @param r rectangle containing point
     * @return point - percentage values 0-100 for x,y
     */
    public Point percentage(Point p, Rectangle r) {

        try {
            int xP = (int) (((double) (p.x - r.x) / (double) r.width) * 100.0);
            int yP = (int) (((double) (p.y - r.y) / (double) r.height) * 100.0);
            return new Point(xP, yP);
        } catch (Exception e) {
            System.out.println("Error finding percentage, probably divide by 0 problem");
            System.out.println("Rectangle: " + r + "\nPoint: " + p);
            return new Point(-1, -1);
        }

    }

    public DefaultWidgets getDefaultWidgets() {
        return new DefaultWidgets(ws.getWidgetLinks());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _FileChooser_IoFile = new javax.swing.JFileChooser();
        _ComboBox_DisplayPanel = new javax.swing.JComboBox();
        _ScrollPane_VariableNames = new javax.swing.JScrollPane();
        _List_WidgetVars = new javax.swing.JList();
        _Label_VarNames = new javax.swing.JLabel();
        _Button_LoadXls = new javax.swing.JButton();
        _Label_Loaded = new javax.swing.JLabel();
        _Panel_WidgetParams = new javax.swing.JPanel();
        _Label_WidgetParams = new javax.swing.JLabel();
        _Label_WigetParam_yPos = new javax.swing.JLabel();
        _FTF_WigetParam_xPos = new javax.swing.JFormattedTextField();
        _FTF_WigetParam_yPos = new javax.swing.JFormattedTextField();
        _Label_WigetParam_xPos = new javax.swing.JLabel();
        _FTF_WigetParam_xPosPer = new javax.swing.JFormattedTextField();
        _Label_WigetParam_xPos1 = new javax.swing.JLabel();
        _Label_WigetParam_yPos1 = new javax.swing.JLabel();
        _FTF_WigetParam_yPosPer = new javax.swing.JFormattedTextField();
        _Button_GenerateWidgetLink = new javax.swing.JButton();
        _ScrollPane_Log = new javax.swing.JScrollPane();
        _TextArea_Log = new javax.swing.JTextArea();
        _ScrollPane_WidgetSettings = new javax.swing.JScrollPane();
        _Panel_WidgetSettings = new javax.swing.JPanel();
        _Button_LoadDefaults = new javax.swing.JButton();
        _Button_LoadDefaults1 = new javax.swing.JButton();
        _ScrollPane_WidgetNames = new javax.swing.JScrollPane();
        _List_WidgetCodeList = new javax.swing.JList();
        _Label_Widget = new javax.swing.JLabel();
        _Button_widgetPositions = new javax.swing.JButton();
        _ComboBox_Subgroup = new javax.swing.JComboBox();
        _ScrollPane_MasterMap = new javax.swing.JScrollPane();
        _List_MasterMapVariables = new javax.swing.JList();
        _Label_VarsOnPanel = new javax.swing.JLabel();
        _Button_ClearSelection = new javax.swing.JButton();
        _Button_CreateImports = new javax.swing.JButton();
        _Button_ClearLinks = new javax.swing.JButton();
        _ScrollPane_Tree = new javax.swing.JScrollPane();
        _JTree_WidgetLinks = new javax.swing.JTree();
        _Panel_LinkPanel = new javax.swing.JPanel();
        _Button_GenerateLinks = new javax.swing.JButton();
        _ComboBox_Panels = new javax.swing.JComboBox();
        _FTF_PanelID = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        _TF_PanelName = new javax.swing.JTextField();
        _Button_Save = new javax.swing.JButton();
        _FTF_XPOS = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        _FTF_YPOS = new javax.swing.JFormattedTextField();
        _Button_ClearCurrent = new javax.swing.JButton();
        _Button_ClearAll = new javax.swing.JButton();
        _Button_AddCurrent = new javax.swing.JButton();
        _Button_AddAll = new javax.swing.JButton();

        _FileChooser_IoFile.setApproveButtonText("Open");
        _FileChooser_IoFile.setApproveButtonToolTipText("Open a xls file");
        _FileChooser_IoFile.setCurrentDirectory(new java.io.File("C:\\Users\\EricGummerson\\Documents\\Background Creator Files"));
        _FileChooser_IoFile.setDialogTitle("Open a XLS File");
        _FileChooser_IoFile.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XLS files", "xls"));

        setMinimumSize(new java.awt.Dimension(969, 544));
        setPreferredSize(new java.awt.Dimension(1031, 620));

        _ComboBox_DisplayPanel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_DisplayPanel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Main" }));
        _ComboBox_DisplayPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_DisplayPanelActionPerformed(evt);
            }
        });

        _List_WidgetVars.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _List_WidgetVars.setModel(listModelWidgetsVars);
        _List_WidgetVars.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        _List_WidgetVars.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                _List_WidgetVarsValueChanged(evt);
            }
        });
        _ScrollPane_VariableNames.setViewportView(_List_WidgetVars);

        _Label_VarNames.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_VarNames.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_VarNames.setText("Widget Vars");

        _Button_LoadXls.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_LoadXls.setText("Load Export File (.xls)");
        _Button_LoadXls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_LoadXlsActionPerformed(evt);
            }
        });

        _Label_Loaded.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Label_Loaded.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Loaded.setText("XLS File Not loaded");

        _Label_WidgetParams.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WidgetParams.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WidgetParams.setText("Widget Parameters");

        _Label_WigetParam_yPos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WigetParam_yPos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WigetParam_yPos.setText("Positon Y");

        _FTF_WigetParam_xPos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FTF_WigetParam_xPos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_WigetParam_xPos.setText("10");

        _FTF_WigetParam_yPos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FTF_WigetParam_yPos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_WigetParam_yPos.setText("10");

        _Label_WigetParam_xPos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WigetParam_xPos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WigetParam_xPos.setText("Positon X");

        _FTF_WigetParam_xPosPer.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FTF_WigetParam_xPosPer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_WigetParam_xPosPer.setText("0");

        _Label_WigetParam_xPos1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WigetParam_xPos1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WigetParam_xPos1.setText("Percentage X");

        _Label_WigetParam_yPos1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WigetParam_yPos1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WigetParam_yPos1.setText("Percentage Y");

        _FTF_WigetParam_yPosPer.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FTF_WigetParam_yPosPer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_WigetParam_yPosPer.setText("0");

        _Button_GenerateWidgetLink.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        _Button_GenerateWidgetLink.setText("Generate");
        _Button_GenerateWidgetLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_GenerateWidgetLinkActionPerformed(evt);
            }
        });

        _TextArea_Log.setEditable(false);
        _TextArea_Log.setColumns(20);
        _TextArea_Log.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        _TextArea_Log.setLineWrap(true);
        _TextArea_Log.setRows(5);
        _ScrollPane_Log.setViewportView(_TextArea_Log);

        _ScrollPane_WidgetSettings.setPreferredSize(new java.awt.Dimension(232, 265));

        javax.swing.GroupLayout _Panel_WidgetSettingsLayout = new javax.swing.GroupLayout(_Panel_WidgetSettings);
        _Panel_WidgetSettings.setLayout(_Panel_WidgetSettingsLayout);
        _Panel_WidgetSettingsLayout.setHorizontalGroup(
            _Panel_WidgetSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 493, Short.MAX_VALUE)
        );
        _Panel_WidgetSettingsLayout.setVerticalGroup(
            _Panel_WidgetSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 362, Short.MAX_VALUE)
        );

        _ScrollPane_WidgetSettings.setViewportView(_Panel_WidgetSettings);

        _Button_LoadDefaults.setFont(new java.awt.Font("Arial", 0, 8)); // NOI18N
        _Button_LoadDefaults.setText("Load Defaults");
        _Button_LoadDefaults.setToolTipText("Loads the default widget panel links from the /Home/ Directory");
        _Button_LoadDefaults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_LoadDefaultsActionPerformed(evt);
            }
        });

        _Button_LoadDefaults1.setFont(new java.awt.Font("Arial", 0, 8)); // NOI18N
        _Button_LoadDefaults1.setText("Save Defaults");
        _Button_LoadDefaults1.setToolTipText("Save the current set of widget links to the /Home/ directory");
        _Button_LoadDefaults1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_LoadDefaults1ActionPerformed(evt);
            }
        });

        _List_WidgetCodeList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _List_WidgetCodeList.setModel(listModelCodeWidgets);
        _List_WidgetCodeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        _List_WidgetCodeList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                _List_WidgetCodeListValueChanged(evt);
            }
        });
        _ScrollPane_WidgetNames.setViewportView(_List_WidgetCodeList);

        _Label_Widget.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_Widget.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Widget.setText("Widget Code");

        javax.swing.GroupLayout _Panel_WidgetParamsLayout = new javax.swing.GroupLayout(_Panel_WidgetParams);
        _Panel_WidgetParams.setLayout(_Panel_WidgetParamsLayout);
        _Panel_WidgetParamsLayout.setHorizontalGroup(
            _Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_WidgetParamsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_ScrollPane_WidgetSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(_ScrollPane_WidgetNames, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(_Label_Widget, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Label_WidgetParams, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_WidgetParamsLayout.createSequentialGroup()
                            .addComponent(_Button_GenerateWidgetLink, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(_Button_LoadDefaults, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(_Button_LoadDefaults1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_WidgetParamsLayout.createSequentialGroup()
                            .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(_Label_WigetParam_yPos, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(_Label_WigetParam_xPos, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(_FTF_WigetParam_xPos)
                                .addComponent(_FTF_WigetParam_yPos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_WidgetParamsLayout.createSequentialGroup()
                            .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(_Label_WigetParam_xPos1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(_Label_WigetParam_yPos1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(_FTF_WigetParam_yPosPer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(_FTF_WigetParam_xPosPer, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(_ScrollPane_Log, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        _Panel_WidgetParamsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {_FTF_WigetParam_xPos, _FTF_WigetParam_yPos, _FTF_WigetParam_yPosPer, _Label_WigetParam_xPos, _Label_WigetParam_xPos1, _Label_WigetParam_yPos, _Label_WigetParam_yPos1});

        _Panel_WidgetParamsLayout.setVerticalGroup(
            _Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                        .addComponent(_Label_Widget, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_ScrollPane_WidgetNames))
                    .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(_FTF_WigetParam_xPos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                                .addComponent(_Label_WigetParam_xPos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_FTF_WigetParam_yPos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Label_WigetParam_yPos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_WigetParam_xPos1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FTF_WigetParam_xPosPer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_FTF_WigetParam_yPosPer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Label_WigetParam_yPos1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                                .addComponent(_Button_LoadDefaults, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_Button_LoadDefaults1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(_Button_GenerateWidgetLink, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                        .addComponent(_Label_WidgetParams, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_ScrollPane_WidgetSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(_ScrollPane_Log, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _Panel_WidgetParamsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_Button_LoadDefaults, _Button_LoadDefaults1});

        _Panel_WidgetParamsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_FTF_WigetParam_xPos, _FTF_WigetParam_xPosPer, _FTF_WigetParam_yPos, _FTF_WigetParam_yPosPer, _Label_WigetParam_xPos, _Label_WigetParam_xPos1, _Label_WigetParam_yPos, _Label_WigetParam_yPos1});

        _Button_widgetPositions.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_widgetPositions.setText("Get Positions");
        _Button_widgetPositions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_widgetPositionsActionPerformed(evt);
            }
        });

        _ComboBox_Subgroup.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_Subgroup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Store" }));
        _ComboBox_Subgroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_SubgroupActionPerformed(evt);
            }
        });

        _List_MasterMapVariables.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _List_MasterMapVariables.setModel(listModelMasterMap);
        _ScrollPane_MasterMap.setViewportView(_List_MasterMapVariables);

        _Label_VarsOnPanel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_VarsOnPanel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_VarsOnPanel.setText("Variables on Panel");

        _Button_ClearSelection.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_ClearSelection.setText("Clear Selection");
        _Button_ClearSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_ClearSelectionActionPerformed(evt);
            }
        });

        _Button_CreateImports.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_CreateImports.setText("Create Imports");
        _Button_CreateImports.setEnabled(false);
        _Button_CreateImports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_CreateImportsActionPerformed(evt);
            }
        });

        _Button_ClearLinks.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_ClearLinks.setText("Delete All Widgets");
        _Button_ClearLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_ClearLinksActionPerformed(evt);
            }
        });

        _JTree_WidgetLinks.setModel(treeModel);
        _JTree_WidgetLinks.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                _JTree_WidgetLinksValueChanged(evt);
            }
        });
        _ScrollPane_Tree.setViewportView(_JTree_WidgetLinks);

        _Button_GenerateLinks.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_GenerateLinks.setText("Create Links");
        _Button_GenerateLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_GenerateLinksActionPerformed(evt);
            }
        });

        _ComboBox_Panels.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_Panels.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Main" }));
        _ComboBox_Panels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_PanelsActionPerformed(evt);
            }
        });

        _FTF_PanelID.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FTF_PanelID.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Panel Names");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Panel ID");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Panel Name");

        _TF_PanelName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _TF_PanelName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _TF_PanelName.setText("Main");

        _Button_Save.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_Save.setText("Save & Next");
        _Button_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_SaveActionPerformed(evt);
            }
        });

        _FTF_XPOS.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FTF_XPOS.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("X Position");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Y Position");

        _FTF_YPOS.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FTF_YPOS.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout _Panel_LinkPanelLayout = new javax.swing.GroupLayout(_Panel_LinkPanel);
        _Panel_LinkPanel.setLayout(_Panel_LinkPanelLayout);
        _Panel_LinkPanelLayout.setHorizontalGroup(
            _Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_LinkPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_ComboBox_Panels, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(_Panel_LinkPanelLayout.createSequentialGroup()
                        .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                            .addComponent(_FTF_PanelID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_TF_PanelName)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)))
                    .addComponent(_Button_Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(_Panel_LinkPanelLayout.createSequentialGroup()
                        .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FTF_XPOS, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_FTF_YPOS, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(_Button_GenerateLinks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _Panel_LinkPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {_FTF_XPOS, _FTF_YPOS});

        _Panel_LinkPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, jLabel7});

        _Panel_LinkPanelLayout.setVerticalGroup(
            _Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_LinkPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_Panel_LinkPanelLayout.createSequentialGroup()
                        .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_ComboBox_Panels, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                            .addComponent(_FTF_PanelID)
                            .addComponent(_TF_PanelName)))
                    .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(_Panel_LinkPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_FTF_YPOS, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FTF_XPOS, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_Panel_LinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_Button_Save, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(_Button_GenerateLinks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _Panel_LinkPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel5, jLabel7});

        _Button_ClearCurrent.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_ClearCurrent.setText("Clear Main Panel");
        _Button_ClearCurrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_ClearCurrentActionPerformed(evt);
            }
        });

        _Button_ClearAll.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_ClearAll.setText("Clear All Panels");
        _Button_ClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_ClearAllActionPerformed(evt);
            }
        });

        _Button_AddCurrent.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_AddCurrent.setText("Add Widgets Main Panel");
        _Button_AddCurrent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_AddCurrentActionPerformed(evt);
            }
        });

        _Button_AddAll.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_AddAll.setText("Add Widgets All");
        _Button_AddAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_AddAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Panel_WidgetParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(_Label_VarNames, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_Button_ClearSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(_ComboBox_Subgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_ScrollPane_VariableNames, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_Button_ClearCurrent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_Button_ClearAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_Button_AddCurrent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_Button_AddAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Button_CreateImports, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(_Button_ClearLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(_Label_Loaded, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(_Button_LoadXls, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_ComboBox_DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Button_widgetPositions, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_Label_VarsOnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(_ScrollPane_Tree)
                    .addComponent(_ScrollPane_MasterMap)
                    .addComponent(_Panel_LinkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(_Label_VarNames, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(_ScrollPane_VariableNames, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(_Label_VarsOnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(_Button_ClearSelection, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                    .addComponent(_Button_widgetPositions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(_ScrollPane_MasterMap, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_ComboBox_DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_ComboBox_Subgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_ScrollPane_Tree, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_Panel_LinkPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_Button_ClearCurrent, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(_Button_LoadXls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Button_ClearAll)
                            .addComponent(_Label_Loaded, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Button_AddCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(_Button_AddAll)
                                .addGap(55, 55, 55))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(_Button_CreateImports, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(_Button_ClearLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(_Panel_WidgetParams, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_Button_AddAll, _Button_AddCurrent, _Button_ClearAll, _Button_ClearCurrent});

    }// </editor-fold>//GEN-END:initComponents

    private void _Button_LoadXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_LoadXlsActionPerformed

        _FileChooser_IoFile.setDialogTitle("Load XLS File");
        _FileChooser_IoFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser_IoFile.setDialogType(JFileChooser.OPEN_DIALOG);
        _FileChooser_IoFile.setApproveButtonText("Open XLS File");

        int returnVal = _FileChooser_IoFile.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser_IoFile.getSelectedFile();

            String filePath = file.getAbsolutePath();
            readXFile(filePath);
            _Button_CreateImports.setEnabled(true);
            _Label_Loaded.setText("Loaded File!");

        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event__Button_LoadXlsActionPerformed

    private void _ComboBox_DisplayPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_DisplayPanelActionPerformed

        int index = _ComboBox_DisplayPanel.getSelectedIndex();
        mf.displayFrame.setTab(index);

        if (masterMap != null) {
            loadMasterMapList();
        }
    }//GEN-LAST:event__ComboBox_DisplayPanelActionPerformed

    private void _Button_widgetPositionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_widgetPositionsActionPerformed

        masterMap = mf.displayFrame.getWidgetPositions();
        loadMasterMapList();
    }//GEN-LAST:event__Button_widgetPositionsActionPerformed

    private void _ComboBox_SubgroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_SubgroupActionPerformed

        loadWidgetVars();

    }//GEN-LAST:event__ComboBox_SubgroupActionPerformed

    private void _List_WidgetVarsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event__List_WidgetVarsValueChanged

        if (!evt.getValueIsAdjusting()) {
            if (masterMap != null) {
                loadMasterMapList();
            }
        }
    }//GEN-LAST:event__List_WidgetVarsValueChanged

    private void _Button_ClearSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_ClearSelectionActionPerformed

        _List_WidgetVars.clearSelection();
        if (masterMap != null && masterMap.size() > 0) {
            loadMasterMapList();
        }
    }//GEN-LAST:event__Button_ClearSelectionActionPerformed

    private void _Button_GenerateWidgetLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_GenerateWidgetLinkActionPerformed

        // Make sure we have a selection for the widget type, the widget variable type
        if (!_List_WidgetVars.isSelectionEmpty() && !_List_WidgetCodeList.isSelectionEmpty()) {
            String panelName = _ComboBox_DisplayPanel.getSelectedItem().toString();
            String panelType;

            if (panelName.startsWith("R:")) {
                panelType = "Rack";
            } else if (panelName.startsWith("L:")) {
                panelType = "Load";
            } else {
                panelType = panelName;
            }

            String subGroup = _ComboBox_Subgroup.getSelectedItem().toString();
            String varType = _List_WidgetVars.getSelectedValue().toString();
            String key = panelType + "-" + varType;

            String widgetCodeStr = _List_WidgetCodeList.getSelectedValue().toString();

            // WidgetCode removed from widget link
            //WidgetCode wc = widgetList.get(widgetCodeStr + ".txt");
            Map<String, String> wcVariables = new HashMap<>();
            boolean emptyParams = false;
            if (!widgetCodeSettings.isEmpty()) {

                for (Entry<String, JTextField> entry : widgetCodeSettings.entrySet()) {

                    if (emptyParams) {
                        break;
                    }
                    if (entry.getValue().getText() != null && !entry.getValue().getText().equals("")) {
                        wcVariables.put(entry.getKey(), entry.getValue().getText());
                    } else {
                        emptyParams = true;
                    }
                }
                if (emptyParams) {
                    JOptionPane.showMessageDialog(null, "Parameters must have values", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }

            if (!emptyParams) {

                //System.out.println(widgetCodeStr + " gives -> " + wc);
                Point per = new Point(Integer.parseInt(_FTF_WigetParam_xPosPer.getText()), Integer.parseInt(_FTF_WigetParam_yPosPer.getText()));

                WidgetLink wl = new WidgetLink(widgetCodeStr, per, panelType, subGroup, wcVariables);

                if (ws.add(key, wl) != null) {
                    _TextArea_Log.append("The key {" + key + "} already exists. It has now been overwritten\n");
                } else {
                    _TextArea_Log.append("Added {" + key + "} with WidgetVar {" + widgetCodeStr + "}\n");
                }
                loadTree();
            } else {
                System.out.println("Didnt add the link");
            }

        } else {
            _TextArea_Log.append("Selection is required for Widget Variable and Widget Code.\n");

        }
    }//GEN-LAST:event__Button_GenerateWidgetLinkActionPerformed

    private void _Button_ClearLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_ClearLinksActionPerformed

        //System.out.println("Size Before: " + mf.store.ws.widgetLinks.size());
        ws.clear();
        widgetList.clear();
        loadWidgetCode();
        //System.out.println("Size After: " + mf.store.ws.widgetLinks.size());

    }//GEN-LAST:event__Button_ClearLinksActionPerformed

    private void _List_WidgetCodeListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event__List_WidgetCodeListValueChanged

        // _ScrollPane_WidgetSettings
        if (!evt.getValueIsAdjusting()) {

            // Load the variables of the widget
            String widgetCodeStr;
            WidgetCode wc = null;
            WidgetLink wl = null;
            if (!_JTree_WidgetLinks.isSelectionEmpty()) {

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) _JTree_WidgetLinks.getLastSelectedPathComponent();

                if (node == null) //Nothing is selected.  
                {
                    return;
                }

                if (node.getParent() != null) {
                    String s = node.getParent().toString() + "-" + node.getUserObject().toString();

                    if (ws.containsKey(s)) {
                        wl = ws.get(s);
                        wc = widgetList.get(wl.getWidgetCodeName());
                    }
                }
            }

            if (wc == null) {
                // No selected item on the JTree
                widgetCodeStr = _List_WidgetCodeList.getSelectedValue().toString();
                wc = widgetList.get(widgetCodeStr);
            } else {

                // Make sure the item selected matches the code in the widget link
                // This makes selecting 
                if (!wc.getWidgetName().equals(_List_WidgetCodeList.getSelectedValue().toString())) {

                    widgetCodeStr = _List_WidgetCodeList.getSelectedValue().toString();
                    wc = widgetList.get(widgetCodeStr);
                }

            }

            GridBagLayout gbl = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1;
            c.weighty = 0;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.ipadx = 0;
            c.ipady = 5;

            if (widgetCodeSettings == null) {
                widgetCodeSettings = new HashMap<>();
            } else {
                widgetCodeSettings.clear();
            }

            _Panel_WidgetSettings.removeAll();
            _Panel_WidgetSettings.setLayout(gbl);

            for (String name : wc.getVariables()) {
                JLabel label = new JLabel(name);
                label.setFont(new Font("Arial", Font.BOLD, 13));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                JTextField textfield = new JTextField("");
                textfield.setHorizontalAlignment(SwingConstants.CENTER);
                if (wl != null) {
                    textfield.setText(wl.getVariables().get(name));
                }

                textfield.setFont(new Font("Arial", Font.BOLD, 15));

                widgetCodeSettings.put(label.getText(), textfield);
                _Panel_WidgetSettings.add(label, c);
                c.gridy += 1;
                _Panel_WidgetSettings.add(textfield, c);
                c.gridy += 1;
            }

            _ScrollPane_WidgetSettings.revalidate();
            _ScrollPane_WidgetSettings.repaint();

        }


    }//GEN-LAST:event__List_WidgetCodeListValueChanged

    public void loadTree() {

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Widget Links");
        treeModel = new DefaultTreeModel(root, true);

        Map<String, DefaultMutableTreeNode> treeMap = new TreeMap<>();

        for (Entry<String, WidgetLink> entry : ws.getWidgetLinkEntrySet()) {

            String[] entryString = entry.getKey().split("-");

            if (!treeMap.containsKey(entryString[0])) {
                DefaultMutableTreeNode folder = new DefaultMutableTreeNode(entryString[0], true);
                treeMap.put(entryString[0], folder);
            }

            DefaultMutableTreeNode child = new DefaultMutableTreeNode(entryString[1], true);

            // Add the fields of the widget link
            for (String s : entry.getValue().getTreeStrings()) {
                MutableTreeNode childStats = new DefaultMutableTreeNode(s, false);
                child.add(childStats);
            }

            //MutableTreeNode child = new DefaultMutableTreeNode(entry.getValue().getTreeStrings(entryString[1]), false);
            treeMap.get(entryString[0]).add(child);
        }

        for (Entry<String, DefaultMutableTreeNode> entry : treeMap.entrySet()) {
            root.add(entry.getValue());
        }

        _JTree_WidgetLinks.setModel(treeModel);

    }


    private void _Button_CreateImportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_CreateImportsActionPerformed

        if (masterMap == null) {
            masterMap = mf.displayFrame.getWidgetPositions();
            loadMasterMapList();
        }
        Map<String, List<String>> mappings = generateExportStrings();
        writeOutExports(mappings);


    }//GEN-LAST:event__Button_CreateImportsActionPerformed

    private void _Button_LoadDefaults1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_LoadDefaults1ActionPerformed

        mf.saveDefaultWidgets();
    }//GEN-LAST:event__Button_LoadDefaults1ActionPerformed

    private void _Button_LoadDefaultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_LoadDefaultsActionPerformed

        DefaultWidgets dw = mf.loadDefaultWidgets();
        if (dw != null) {
            _TextArea_Log.setText("Loaded default widgets!");
            ws.setWidgetLinks(dw.getWidgetLinks());
            loadTree();
        } else {
            _TextArea_Log.append("Didn't load default widgets, error locating the file.");
        }

    }//GEN-LAST:event__Button_LoadDefaultsActionPerformed

    private void _JTree_WidgetLinksValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event__JTree_WidgetLinksValueChanged
        // TODO add your handling code here:
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) _JTree_WidgetLinks.getLastSelectedPathComponent();

        if (node == null) //Nothing is selected.  
        {
            return;
        }

        if (node.getParent() != null) {
            String s = node.getParent().toString() + "-" + node.getUserObject().toString();

            if (ws.containsKey(s)) {
                WidgetLink wl = ws.get(s);
                //System.out.println(wl); 

                // Select the Widget, IO name
                _ComboBox_Subgroup.setSelectedItem(wl.getSubGroup());

                String panelName = node.getParent().toString();

                if (panelName.startsWith("Rack")) {
                    _ComboBox_DisplayPanel.setSelectedIndex(1);
                } else if (panelName.startsWith("Load")) {
                    _ComboBox_DisplayPanel.setSelectedIndex(1 + cs.getNumRacks());
                } else {
                    _ComboBox_DisplayPanel.setSelectedItem(panelName);
                }

                _List_WidgetCodeList.setSelectedValue(wl.getWidgetCodeName(), true);
                _List_WidgetVars.setSelectedValue(node.getUserObject().toString(), true);
                _FTF_WigetParam_xPosPer.setValue(wl.getPositionPercentage().getX());
                _FTF_WigetParam_yPosPer.setValue(wl.getPositionPercentage().getY());

            }

        }

    }//GEN-LAST:event__JTree_WidgetLinksValueChanged

    private void _Button_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_SaveActionPerformed
        // TODO add your handling code here:
        if (!_FTF_PanelID.getText().equals("") && !_TF_PanelName.getText().equals("")) {
            ws.wpl.addLink(_ComboBox_Panels.getSelectedItem().toString(),
                    Integer.valueOf(_FTF_PanelID.getText()),
                    _TF_PanelName.getText(),
                    Integer.valueOf(_FTF_XPOS.getText()),
                    Integer.valueOf(_FTF_YPOS.getText())
            );

            if (_ComboBox_Panels.getSelectedIndex() >= (_ComboBox_Panels.getModel().getSize() - 1)) {
                _ComboBox_Panels.setSelectedIndex(0);
            } else {
                _ComboBox_Panels.setSelectedIndex(_ComboBox_Panels.getSelectedIndex() + 1);
            }

        }

        mf.store.ws.setWpl(ws.wpl);
    }//GEN-LAST:event__Button_SaveActionPerformed

    private void _ComboBox_PanelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_PanelsActionPerformed

        if (_ComboBox_Panels.getSelectedItem() != null) {
            String pn = _ComboBox_Panels.getSelectedItem().toString();
            _Button_ClearCurrent.setText("Clear " + pn + " Panel");
            _Button_AddCurrent.setText("Add Widgets " + pn + " Panel");
            LinkInfo li = ws.wpl.getLinkInfo(pn);
            if (li != null) {

                // Load data into the fields
                _FTF_XPOS.setValue(li.getXPos());
                _FTF_YPOS.setValue(li.getYPos());
                _FTF_PanelID.setValue(li.getPanelID());
                _TF_PanelName.setText(li.getPanelName());
            } else {
                _FTF_PanelID.setText("");
                _TF_PanelName.setText(pn);

            }
        }
    }//GEN-LAST:event__ComboBox_PanelsActionPerformed

    private void _Button_GenerateLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_GenerateLinksActionPerformed
        // TODO add your handling code here:

        if (ws.wpl.links.isEmpty()) {
            return;
        }

        _FileChooser_IoFile.setDialogTitle("Save Text Exports");
        _FileChooser_IoFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        _FileChooser_IoFile.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser_IoFile.setApproveButtonText("Save Here");

        int returnVal = _FileChooser_IoFile.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser_IoFile.getSelectedFile();
            //System.out.println("File: " + file.getAbsolutePath());
            String filepath = file.getAbsolutePath();

            String outputCode = "[";

            WidgetCode wc = mf.wgPanel.getWidgetCode(widgetCodeName);
            for (Map.Entry<String, LinkInfo> entry : ws.wpl.getLinks().entrySet()) {

                // For each entry, format a code string based on the default positions
                // and the given panel name and ID's
                String panelID = String.valueOf(entry.getValue().getPanelID());
                String panelName = entry.getValue().getPanelName();

                String xPos = String.valueOf(entry.getValue().getXPos());
                String yPos = String.valueOf(entry.getValue().getYPos());

                String w_id = wc.getFullWidgetText().split("\"widget_subclass\": \"")[1].split("\",")[0];
                System.out.println("Widget ID: " + w_id);
                String newCode = wc.getFullWidgetText()
                        .replace("`%XPOS%`", xPos)
                        .replace("`%YPOS%`", yPos)
                        .replace("`%PANELID%`", panelID)
                        .replace("`%PANELNAME%`", panelName);

                outputCode += newCode + ",";

            }

            outputCode = outputCode.substring(0, outputCode.length() - 1) + "]";

            String fp = filepath + "\\WidgetExports-Links.txt";
            //System.out.println("Writing " + fp);

            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fp), "utf-8"))) {
                writer.write(outputCode);
                //System.out.println("Writing " + fp + " Completed");
            } catch (Exception e) {
                System.out.println("Failed writing to: " + fp);
            }

            System.out.println(outputCode);

        } else {
            System.out.println("Saving widgets exports cancelled.");
        }
    }//GEN-LAST:event__Button_GenerateLinksActionPerformed

    private void _Button_ClearCurrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_ClearCurrentActionPerformed
        // TODO add your handling code here:
        db = newDBConn();

        // First check to see if the panel ID exists for the current panel
        // from the combo box selection
        String panelName = _ComboBox_Panels.getSelectedItem().toString();

        if (ws.wpl.hasLink(panelName)) {
            LinkInfo li = ws.wpl.getLinkInfo(panelName);

            String query = String.format(deleteSingleQuery, String.valueOf(li.getPanelID()));
            System.out.println(query);

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete all widgets from " + li.getPanelName() + " ID: " + li.getPanelID(), "Confirm Delete", dialogButton);
            if (dialogResult == 0) {
                db.executeQuery(query);
                System.out.println("Deleted content from Panel ID: " + li.getPanelID());

            } else {
                System.out.println("Not deleting content");
            }

        }

        db.closeConn();

    }//GEN-LAST:event__Button_ClearCurrentActionPerformed

    private void _Button_ClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_ClearAllActionPerformed
        // TODO add your handling code here:

        db = newDBConn();

        // First check to see if the panel ID exists for the current panel
        // from the combo box selection
        String info = "";
        String ids = "";
        for (Map.Entry<String, LinkInfo> entry : ws.wpl.getLinks().entrySet()) {

            if (entry.getValue().getPanelID() != -1) {
                if (!ids.equals("")) {
                    ids += ",";
                }

                info += "ID: " + entry.getValue().getPanelID() + " Panel Name: " + entry.getValue().getPanelName() + "\n";
                ids += entry.getValue().getPanelID();
            }
        }

        String query = String.format(deleteAllQuery, ids);
        System.out.println(query);

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete all widgets from the follow panels:\n" + info, "Confirm Delete All", dialogButton);

        if (dialogResult == 0) {
            db.executeQuery(query);
            System.out.println("Deleted content from:\n" + info);

        } else {
            System.out.println("Not deleting content");
        }

        db.closeConn();


    }//GEN-LAST:event__Button_ClearAllActionPerformed

    private void _Button_AddCurrentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_AddCurrentActionPerformed
        // TODO add your handling code here:
        db = newDBConn();

        // Add the widgets for the current selected panel in the combo box
        // First check to see if the panel ID exists for the current panel
        // from the combo box selection
        String panelName = _ComboBox_Panels.getSelectedItem().toString();

        if (ws.wpl.hasLink(panelName)) {
            LinkInfo li = ws.wpl.getLinkInfo(panelName);

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this,
                    "Would you like to delete all widgets from " + li.getPanelName() + " ID: " + li.getPanelID(), "Confirm Delete All", dialogButton);

            if (dialogResult == 0) {
                String query = String.format(deleteSingleQuery, String.valueOf(li.getPanelID()));
                db.executeQuery(query);
                System.out.println("Deleted content from Panel ID: " + li.getPanelID());

            } else {
                System.out.println("Not deleting content");
            }

            String values = generateWidgetImport(panelName, li.getPanelID());

            String query = String.format(insertQuery, values);
            System.out.println("Attempting to import widgets to " + panelName + " ID: " + li.getPanelID());
            db.executeQuery(query);
            

            // Import Tasks
            db.closeConn();
        }


    }//GEN-LAST:event__Button_AddCurrentActionPerformed

    private void _Button_AddAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_AddAllActionPerformed
        // TODO add your handling code here:
        db = newDBConn();

        // Add the widgets for the current selected panel in the combo box
        // First check to see if the panel ID exists for the current panel
        // from the combo box selection
        String panelName = "";

        for (Entry<String, LinkInfo> entry : ws.wpl.getLinks().entrySet()) {

            panelName = entry.getValue().getPanelName();

            LinkInfo li = entry.getValue();

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this,
                    "Would you like to delete all widgets from " + li.getPanelName() + " ID: " + li.getPanelID(), "Confirm Delete All", dialogButton);

            if (dialogResult == 0) {
                String query = String.format(deleteSingleQuery, String.valueOf(li.getPanelID()));
                db.executeQuery(query);
                System.out.println("Deleted content from Panel ID: " + li.getPanelID());
            } else {
                System.out.println("Not deleting content");
            }

            String values = generateWidgetImport(panelName, li.getPanelID());

            String query = String.format(insertQuery, values);
            System.out.println("Attempting to import widgets to " + panelName + " ID: " + li.getPanelID());
            db.executeQuery(query);
            //System.out.println(query);

            // Import Tasks
            db.closeConn();

        }
    }//GEN-LAST:event__Button_AddAllActionPerformed

    public String generateWidgetImport(String panelName, int panelID) {

        String importCode = "";
        String template = "(%s, %s, %s, %s, %s)";
        String htmlLinks = getHtmlLinks(panelID);
        List<String> l = getSpecificExports(panelName);

        Pattern p = Pattern.compile("\\d+");
        Matcher m;

        for (String s : l) {

            String w_id = "", w_x = "", w_y = "", content = "", p_id = String.valueOf(panelID);
            // widget_subclass": "
            w_id = s.split("widget_subclass\": \"")[1].split("\",")[0];
            w_x = s.split("w_x\": \"")[1].split("\",")[0];
            w_y = s.split("w_y\": \"")[1].split("\",")[0];
            content = s.split("code\": \"")[1];
            if (content.contains("holder_")) {
                String oldHolderID = content.substring(content.indexOf("holder_"), content.indexOf("holder_") + 30);
                m = p.matcher(oldHolderID);
                if (m.find()) {
                    oldHolderID = "holder_" + m.group();
                }

                String newHolderID = "holder_" + holderNum;
                holderNum++;
                content = content.replace(oldHolderID, newHolderID);
            }

            // Replace the holder numbers
            content = StringEscapeUtils.unescapeJava(content);

            content = "$$" + content.substring(0, content.length() - 2) + "$$";

            if (!importCode.equals("")) {
                importCode += ",";
            }

            importCode += String.format(template, p_id, w_id, w_x, w_y, content);

        }

        importCode += "," + htmlLinks;

        return importCode;

    }

    public String getHtmlLinks(int pid) {

        String returnString = "";

        String template = "(%s, %s, %s, %s, %s)";

        Pattern p = Pattern.compile("\\d+");
        Matcher m;

        WidgetCode wc = mf.wgPanel.getWidgetCode(widgetCodeName);

        for (Map.Entry<String, LinkInfo> entry : ws.wpl.getLinks().entrySet()) {

                // For each entry, format a code string based on the default positions
            // and the given panel name and ID's
            String panelID = String.valueOf(entry.getValue().getPanelID());
            String panelName = entry.getValue().getPanelName();

            String xPos = String.valueOf(entry.getValue().getXPos());
            String yPos = String.valueOf(entry.getValue().getYPos());
            String newCode = wc.getFullWidgetText()
                    .replace("`%XPOS%`", xPos)
                    .replace("`%YPOS%`", yPos)
                    .replace("`%PANELID%`", panelID)
                    .replace("`%PANELNAME%`", panelName);

            String w_id = "", w_x = "", w_y = "", content = "", p_id = String.valueOf(pid);
            // widget_subclass": "
            w_id = newCode.split("widget_subclass\": \"")[1].split("\",")[0];
            w_x = newCode.split("w_x\": \"")[1].split("\",")[0];
            w_y = newCode.split("w_y\": \"")[1].split("\",")[0];
            content = newCode.split("code\": \"")[1];
            if (content.contains("holder_")) {
                String oldHolderID = content.substring(content.indexOf("holder_"), content.indexOf("holder_") + 30);
                //ystem.out.println("Before Holder ID: " + oldHolderID);

                m = p.matcher(oldHolderID);
                if (m.find()) {
                    oldHolderID = "holder_" + m.group();
                }

                String newHolderID = "holder_" + holderNum;
                holderNum++;
                content = content.replace(oldHolderID, newHolderID);
            }

            // Replace the holder numbers
            content = StringEscapeUtils.unescapeJava(content);
            content = "$$" + content.substring(0, content.length() - 2) + "$$";

            if (!returnString.equals("")) {
                returnString += ",";
            }

            returnString += String.format(template, p_id, w_id, w_x, w_y, content);

        }

        return returnString;

    }

    public List<String> getSpecificExports(String panelName) {

        return generateExportStrings().get(panelName);
    }

    public Map<String, List<String>> generateExportStrings() {

        if (masterMap == null) {
            masterMap = mf.displayFrame.getWidgetPositions();
            loadMasterMapList();
        }

        // Generate the code (Save to Files later)
        // Contains a List of JSON code for each panel (Main, Rack(s), Loads, Financial)
        Map<String, List<String>> exportStringMap = new TreeMap<>();

        // For each type of entry in the widgetlinks, try to find a match to an io id
        for (Entry<String, WidgetLink> entry : ws.getWidgetLinkEntrySet()) {

            // Panel type will be like "Main" "Rack" etc
            String panelType = entry.getValue().getPanelType();
            // Actual panel name - u
            String panelName = panelType;
            boolean rackEntry = panelType.equals("Rack");
            boolean loadEntry = panelType.equals("Load");

            // Generate the same widgets for each rack as they will all be the same
            // This will be used to keep all racks used
            List<String> usedRacks = new ArrayList<>();
            List<String> usedLoads = new ArrayList<>();

            // 
            do {

                // We want to generate widget links for every rack panel when
                // the widget link is for a "Rack"
                // Every do loop will check to see if the rack name is part of
                // usedRack list. If it is, the widget has been generated for 
                // that rack, so we continue to find the next rack
                // If all the racks have been used, rackEntry is set to false
                // And we break out of the do loop
                // We use a do loop so the code will be executed at least 1 time
                // for the main,loads,financial widgets
                if (panelType.equals("Rack")) {
                    String[] rackNames = cs.getRackNames();
                    rackEntry = false;
                    for (String rn : rackNames) {
                        if (!usedRacks.contains(rn)) {
                            usedRacks.add(rn);
                            panelName = rn;
                            rackEntry = true;
                            break;
                        }
                    }
                    if (!rackEntry) {
                        break;
                    }
                }

                // Same for loads
                if (panelType.equals("Load")) {
                    String[] loadNames = cs.getLoadNames();
                    loadEntry = false;
                    for (String ln : loadNames) {
                        if (!usedLoads.contains(ln)) {
                            usedLoads.add(ln);
                            panelName = ln;
                            loadEntry = true;
                            break;
                        }
                    }
                    if (!loadEntry) {
                        break;
                    }
                }

                // Generate a new arraylist for the export Mappings only if it
                // doesnt exist. This prevents adding strings to the non existant list
                if (!exportStringMap.containsKey(panelName)) {
                    //System.out.println("Created list for " + panelName);
                    exportStringMap.put(panelName, new ArrayList<>());
                }

                // For each entry, we want to generate the code that applies to all variables
                // Split the widget link into the two parts
                // Rack-Cond Outlet Pressure `%rackname`
                // orgKey = Cond Outlet Pressure `%rackname`
                String orgKey = entry.getKey().substring(entry.getKey().indexOf("-") + 1);

                while (orgKey.contains("&")) {

                    int beginIndex = orgKey.indexOf("&");
                    int endIndex = orgKey.indexOf("&", beginIndex + 1) + 1;
                    String replace = " " + orgKey.substring(beginIndex, endIndex);
                    orgKey = orgKey.replace(replace, "");
                }

                // selectVar = [Cond Outlet Pressure , %rackname]
                String[] selectedVar = orgKey.split("`");
                String index = entry.getValue().getPanelType();

                if (rackEntry) {
                    // We add this before the name as the master map keys follow this format
                    index = "R: " + panelName;
                }
                if (loadEntry) {
                    index = "L: " + panelName;
                }

                //System.out.println("index:" + index);
                //System.out.println("master map keys: " + Arrays.toString(masterMap.keySet().toArray()));
                // Find all the variables that are usable on the specific panel (masterMap.get(index))
                for (Entry<String, Rectangle> varsToGen : masterMap.get(index).entrySet()) {

                    boolean contains = true;

                    // selectVar = [Cond Outlet Pressure , %rackname]                    
                    for (String part : selectedVar) {
                        if (!contains) {
                            break;
                        }
                        if (!part.contains("%")) {
                            // Find the linking variable eg.
                            // Cond Outlet Pressure `%rackname` . contains(Cond Outlet Pressure)
                            if (!varsToGen.getKey().contains(part)) {
                                //if (!varsToGen.getKey().startsWith(part)) {
                                contains = false;
                            }
                        }

                    }
                    if (importedIOVariables != null && contains) {

                        String masterMapKey = varsToGen.getKey();

                        // Since we use the total variables twice, ive added a identifier using the 
                        // ampersand characters. The ampersand characters are removed right before trying
                        // to find the same key in the exported excel file. This will allow the variables
                        // to be listed and have unique widgets and locations while still using the same variables
                        boolean ignore = false;
                        while (masterMapKey.contains("&")) {
                            ignore = true;
                            int beginIndex = masterMapKey.indexOf("&");
                            int endIndex = masterMapKey.indexOf("&", beginIndex + 1) + 1;
                            String replace = " " + masterMapKey.substring(beginIndex, endIndex);
                            masterMapKey = masterMapKey.replace(replace, "");
                        }

                        //System.out.println("key: " + varsToGen.getKey());
                        if (importedIOVariables.containsKey(masterMapKey)) {

                            int[] io_id;
                            if (!ignore && (masterMapKey.startsWith("Performance Cost Sum Predicted")
                                    || masterMapKey.startsWith("Performance kW Sum Predicted")
                                    || masterMapKey.startsWith("Performance Total Cost Sum")
                                    || masterMapKey.startsWith("Performance Total kW Sum"))
                                    && entry.getValue().getWidgetCodeName().startsWith("SmartPredvsAct")) {

                                //System.out.println("Multi widget key: " + masterMapKey);
                                //System.out.println("Multi widget key replaced: " + masterMapKey.replace("Predicted", "Actual"));
                                io_id = new int[]{importedIOVariables.get(masterMapKey),
                                    importedIOVariables.get(masterMapKey.replace("Predicted", "Actual"))};
                            } else {
                                io_id = new int[]{importedIOVariables.get(masterMapKey)};
                            }

                            //System.out.println("Linked " + orgKey + " to: " + varsToGen.getKey() + " with IO_ID: " + io_id);
                            exportStringMap.get(panelName).add(createWidget(entry.getValue(), varsToGen.getValue(), io_id));

                        } else {
                            System.out.println("Linked " + orgKey + " to: " + masterMapKey + ". IO_ID not found");
                            // No io for this
                            exportStringMap.get(panelName).add(createWidget(entry.getValue(), varsToGen.getValue(), new int[]{0}));
                        }

                    } /*else if (contains) {

                     System.out.println("Linked " + orgKey + " to: " + varsToGen.getKey() + ". IO_ID not loaded");
                     } else {
                     System.err.println("Ignored " + orgKey + " to: " + varsToGen.getKey());
                     }*/

                }
            } while (rackEntry || loadEntry); // Generate all the widget links for each rack

        }

        return exportStringMap;

    }

    public boolean writeOutExports(Map<String, List<String>> exports) {

        _FileChooser_IoFile.setDialogTitle("Save Text Exports");
        _FileChooser_IoFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        _FileChooser_IoFile.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser_IoFile.setApproveButtonText("Save Here");

        int returnVal = _FileChooser_IoFile.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser_IoFile.getSelectedFile();
            //System.out.println("File: " + file.getAbsolutePath());
            String filepath = file.getAbsolutePath();

            for (Entry<String, List<String>> entry : exports.entrySet()) {

                // Use this to keep a maximum of 15 elements per text file to reduce the import lag
                List<String> importList = new ArrayList<>();
                // The string that will contain up to 15 json entries
                String jsonEntry = "[";
                int sizeCounter = 0;

                // For every json code generated for the entry (Main, Racks, Loads, etc)
                // we add the json code to a array list to contain up to 15 json codes
                // Importing more than 15 at a time can severely lag the system.
                for (String listString : entry.getValue()) {
                    if (sizeCounter >= 90000) {
                        sizeCounter = 0;
                        jsonEntry = jsonEntry.substring(0, jsonEntry.length() - 1) + "]";
                        importList.add(jsonEntry);
                        jsonEntry = "[";
                    }
                    jsonEntry += listString + ",";
                    sizeCounter += listString.length() + 2;
                }
                jsonEntry = jsonEntry.substring(0, jsonEntry.length() - 1) + "]";
                importList.add(jsonEntry);

                sizeCounter = 0;
                for (String imports : importList) {
                    String fp = filepath + "\\WidgetExports-" + entry.getKey() + "-" + sizeCounter + ".txt";
                    //System.out.println("Writing " + fp);

                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(fp), "utf-8"))) {
                        writer.write(imports);
                        sizeCounter++;
                        //System.out.println("Writing " + fp + " Completed");
                    } catch (Exception e) {
                        System.out.println("Failed writing to: " + fp);
                    }
                }
            }
        } else {
            System.out.println("Saving widgets exports cancelled.");
        }

        return true;
    }

    public String createWidget(WidgetLink wl, Rectangle rect, int[] io_id) {

        if (widgetList.containsKey(wl.getWidgetCodeName())) {
            WidgetCode wc = widgetList.get(wl.getWidgetCodeName());
            Point per = wl.getPositionPercentage();

            int xPos = rect.x + (int) (per.getX() * rect.getWidth() / 100.0);
            int yPos = rect.y + (int) (per.getY() * rect.getHeight() / 100.0);

            String code = wc.getFullWidgetText();

            code = code.replace("`%IO_ID%`", String.valueOf(io_id[0]))
                    .replace("`%XPOS%`", String.valueOf(xPos))
                    .replace("`%YPOS%`", String.valueOf(yPos));

            for (int i = 1; i < io_id.length; i++) {
                code = code.replace("`%IO_ID" + String.valueOf(i + 1) + "%`", String.valueOf(io_id[i]));
            }

            for (Map.Entry<String, String> entry : wl.getVariables().entrySet()) {

                code = code.replace(entry.getKey(), entry.getValue());
            }

            return code;
        } else {
            System.out.println("Could not find a link for the widget named: " + wl.getWidgetCodeName());
            return "";
        }
    }

    public void loadMasterMapList() {

        String index = _ComboBox_DisplayPanel.getSelectedItem().toString();

        if (masterMap.get(index) != null) {

            String[] selectedVar = null;
            if (!_List_WidgetVars.isSelectionEmpty()) {
                selectedVar = _List_WidgetVars.getSelectedValue().toString().split("`");
            }

            listModelMasterMap.removeAllElements();
            for (Map.Entry<String, Rectangle> entry : masterMap.get(index).entrySet()) {
                if (selectedVar != null) {
                    boolean contains = true;
                    for (String part : selectedVar) {
                        if (!contains) {
                            break;
                        }
                        if (!part.contains("%")) {
                            //System.out.println("Does " + entry.getKey() + " contain " + part);
                            if (!entry.getKey().contains(part)) {
                                contains = false;
                            }
                        }

                    }
                    if (contains) {
                        Rectangle r = entry.getValue();
                        String pos = "[" + r.x + "," + r.y + "] W: " + r.width + " H: " + r.height;
                        listModelMasterMap.addElement(entry.getKey() + ": " + pos);
                    }
                } else {
                    Rectangle r = entry.getValue();
                    String pos = "[" + r.x + "," + r.y + "] W: " + r.width + " H: " + r.height;
                    listModelMasterMap.addElement(entry.getKey() + ": " + pos);
                }

            }
        }

    }

    public void loadWidgetVarsComboBox() {

        String[] tabs = groupNamesWidget.keySet().toArray(new String[groupNamesWidget.keySet().size()]);
        _ComboBox_Subgroup.setModel(new javax.swing.DefaultComboBoxModel(tabs));
        loadWidgetVars();

    }

    public void loadWidgetVars() {

        listModelWidgetsVars.removeAllElements();
        for (String item : groupNamesWidget.get(_ComboBox_Subgroup.getSelectedItem().toString())) {
            listModelWidgetsVars.addElement(item);
        }

    }

    public void updateStore() {
        mf.updateWidgetSettings(ws);
    }

    public void getWidgetFiles(String dirName, ArrayList<String> filePaths) throws FileNotFoundException {

        File directory = new File(dirName);

        if (!directory.exists()) {
            throw new FileNotFoundException("File Not Found: " + dirName);
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                filePaths.add(file.getName() + "," + file.getAbsolutePath());
            } else if (file.isDirectory()) {
                getWidgetFiles(file.getAbsolutePath(), filePaths);
            }
        }

    }

    public void readWidgetVars() {

        groupNamesWidget = new HashMap<>();
        String path = "/Creator/textFiles/Default Widget Names.txt";

        InputStream loc = this.getClass().getResourceAsStream(path);
        Scanner scan = new Scanner(loc);

        String line, groupName = "";
        while (scan.hasNextLine()) {
            line = scan.nextLine();

            if (line == null) { // make sure it doesnt break

            } else if (line.startsWith("`")) {
                groupName = line.substring(1);
                if (!groupNamesWidget.containsKey(groupName)) {
                    groupNamesWidget.put(groupName, new ArrayList<>());
                }
                //System.out.println("Grouping name: " + groupName);
            } else {
                groupNamesWidget.get(groupName).add(line);
            }
        }

        scan.close();

        loadWidgetVarsComboBox();
    }

    /**
     * Reads a file and returns a list of strings which contain all the variable
     * names
     *
     * @param result the combination of files + file paths
     */
    public void readCodeFile(String result) {

        //System.out.println("Reading " + result);
        String[] ss = result.split(",");
        ss[0] = removeExtension(ss[0]);
        // s[0] -> File name
        // s[1] -> File pathWidgetCode        

        String filename = ss[1];
        String entireWidget = "";
        String line;

        ArrayList<String> vars = new ArrayList<>();
        int begin, end;
        try {
            try (Scanner scan = new Scanner(new FileReader(filename))) {
                while (scan.hasNextLine()) {
                    line = scan.nextLine();
                    entireWidget += line;

                    while (line.contains("`%")) {

                        begin = line.indexOf("`%");
                        end = line.indexOf("%`") + 2;
                        String var = line.substring(begin, end);
                        if (!var.contains("`%IO_ID") && !var.equals("`%XPOS%`") && !var.equals("`%YPOS%`")) {
                            vars.add(var);
                        }
                        line = line.substring(end);
                    }

                }
            }
        } catch (FileNotFoundException e) {

            System.out.println("File not found error " + e.getMessage());
            System.out.println("Error trying to read " + filename);
        }

        WidgetCode widget = new WidgetCode(ss[0], vars, entireWidget);
        widgetList.put(ss[0], widget);

    }

    /**
     * Reads a a jar file and returns a list of strings which contain all the
     * variable names
     *
     * @param result the combination of files + file paths
     */
    public void readJarFile(String result) {

        String name = result.substring(result.lastIndexOf("/") + 1);
        String entireWidget = "";
        String line;

        ArrayList<String> vars = new ArrayList<>();
        int begin, end;

        InputStream loc = this.getClass().getResourceAsStream(result);
        try (Scanner scan = new Scanner(loc, "UTF-8")) {
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                entireWidget += line;

                while (line.contains("`%")) {

                    begin = line.indexOf("`%");
                    end = line.indexOf("%`") + 2;
                    String var = line.substring(begin, end);
                    if (!var.contains("`%IO_ID") && !var.equals("`%XPOS%`") && !var.equals("`%YPOS%`")) {
                        vars.add(var);
                    }
                    //System.out.println("Variable added: " + line.substring(begin, end));
                    line = line.substring(end);
                }

            }
        }

        WidgetCode widget = new WidgetCode(name, vars, entireWidget);
        widgetList.put(name, widget);

    }

    /**
     * Reads a file and returns a list of strings which contain all the variable
     * names
     *
     * @param filename
     */
    public void readXFile(String filename) {

        try {

            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;

            int idCol = -1, idName = -1;
            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for (int i = 0; i < 1; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if (tmp > cols) {
                        cols = tmp;
                    }
                }

                if (!sheet.getRow(i).getCell(0).toString().equals("io_id")) {
                    for (int c = 1; c < cols; c++) {
                        if (sheet.getRow(i).getCell(c).equals("io_id")) {
                            idCol = c;
                            break;
                        }
                    }
                } else {
                    idCol = 0;
                }
                if (!sheet.getRow(i).getCell(1).toString().equals("io_name")) {
                    for (int c = 1; c < cols; c++) {
                        if (sheet.getRow(i).getCell(c).equals("io_name")) {
                            idName = c;
                            break;
                        }
                    }
                } else {
                    idName = 1;
                }

                if (!sheet.getRow(i).getCell(2).toString().equals("io_station_id")) {
                    for (int c = 0; c < cols; c++) {
                        if (sheet.getRow(i).getCell(c).equals("io_station_id")) {
                            stationID = (int) sheet.getRow(1).getCell(c).getNumericCellValue();
                            break;
                        }
                    }
                } else {
                    stationID = (int) sheet.getRow(1).getCell(2).getNumericCellValue();
                }

            }

            if (idName == -1 || idCol == -1) {
                System.out.println("Could not locate io_name or io_id in excel header");
                return;
            }
            if (stationID == -1) {
                System.out.println("Couldnt locate station id");
                return;
            }

            importedIOVariables = new TreeMap<>();
            int io_id;
            String io_name;

            for (int r = 1; r < rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {

                    cell = row.getCell(idCol);
                    if (cell != null) {
                        io_id = (int) cell.getNumericCellValue();

                        cell = row.getCell(idName);
                        if (cell != null) {
                            io_name = cell.toString().replace("\"", "");
                            // Read both name and id
                            importedIOVariables.put(io_name, io_id);
                        }
                    }

                }
            }

            fs.close();

            mf.loadImportedIos(importedIOVariables, 1, stationID);

        } catch (Exception e) {
            System.out.println("Error reading excel file " + e.getMessage());
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_AddAll;
    private javax.swing.JButton _Button_AddCurrent;
    private javax.swing.JButton _Button_ClearAll;
    private javax.swing.JButton _Button_ClearCurrent;
    private javax.swing.JButton _Button_ClearLinks;
    private javax.swing.JButton _Button_ClearSelection;
    private javax.swing.JButton _Button_CreateImports;
    private javax.swing.JButton _Button_GenerateLinks;
    private javax.swing.JButton _Button_GenerateWidgetLink;
    private javax.swing.JButton _Button_LoadDefaults;
    private javax.swing.JButton _Button_LoadDefaults1;
    private javax.swing.JButton _Button_LoadXls;
    private javax.swing.JButton _Button_Save;
    private javax.swing.JButton _Button_widgetPositions;
    private javax.swing.JComboBox _ComboBox_DisplayPanel;
    private javax.swing.JComboBox _ComboBox_Panels;
    private javax.swing.JComboBox _ComboBox_Subgroup;
    private javax.swing.JFormattedTextField _FTF_PanelID;
    private javax.swing.JFormattedTextField _FTF_WigetParam_xPos;
    private javax.swing.JFormattedTextField _FTF_WigetParam_xPosPer;
    private javax.swing.JFormattedTextField _FTF_WigetParam_yPos;
    private javax.swing.JFormattedTextField _FTF_WigetParam_yPosPer;
    private javax.swing.JFormattedTextField _FTF_XPOS;
    private javax.swing.JFormattedTextField _FTF_YPOS;
    private javax.swing.JFileChooser _FileChooser_IoFile;
    private javax.swing.JTree _JTree_WidgetLinks;
    private javax.swing.JLabel _Label_Loaded;
    private javax.swing.JLabel _Label_VarNames;
    private javax.swing.JLabel _Label_VarsOnPanel;
    private javax.swing.JLabel _Label_Widget;
    private javax.swing.JLabel _Label_WidgetParams;
    private javax.swing.JLabel _Label_WigetParam_xPos;
    private javax.swing.JLabel _Label_WigetParam_xPos1;
    private javax.swing.JLabel _Label_WigetParam_yPos;
    private javax.swing.JLabel _Label_WigetParam_yPos1;
    private javax.swing.JList _List_MasterMapVariables;
    private javax.swing.JList _List_WidgetCodeList;
    private javax.swing.JList _List_WidgetVars;
    private javax.swing.JPanel _Panel_LinkPanel;
    private javax.swing.JPanel _Panel_WidgetParams;
    private javax.swing.JPanel _Panel_WidgetSettings;
    private javax.swing.JScrollPane _ScrollPane_Log;
    private javax.swing.JScrollPane _ScrollPane_MasterMap;
    private javax.swing.JScrollPane _ScrollPane_Tree;
    private javax.swing.JScrollPane _ScrollPane_VariableNames;
    private javax.swing.JScrollPane _ScrollPane_WidgetNames;
    private javax.swing.JScrollPane _ScrollPane_WidgetSettings;
    private javax.swing.JTextField _TF_PanelName;
    private javax.swing.JTextArea _TextArea_Log;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
