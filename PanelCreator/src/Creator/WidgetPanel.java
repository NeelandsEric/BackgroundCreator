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
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author EricGummerson
 */
public class WidgetPanel extends javax.swing.JPanel {

    private MainFrame mf;                                   // main frame    
    private ControlSettings cs;                             // control settings
    private boolean[] mouseActive;                          // which screen can we click on    
    private WidgetSettings ws;                              // Widget settings
    private DefaultListModel listModelWidgetsVars;          // List model for the default widgets vars used on displays
    private DefaultListModel listModelCodeWidgets;          // List model for the code widgets
    private DefaultListModel listModelMasterMap;            // List model for the master map variables
    private Map<String, WidgetCode> widgetList;             // WidgetName,WidgetCode Variable
    private Map<String, List<String>> groupNamesWidget;     // GroupName,List of vars for the group (Store [Temp, Humidity, ..])
    private Map<String, Integer> importedIOVariables;       // io_name,io_id
    private Map<String, Map<String, Rectangle>> masterMap;  // list of variables relating to each view's variables and locations on the panel
    private Map<String, JTextField> widgetCodeSettings;      // custom values for widget code settings

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
        this.mouseActive = new boolean[cs.getNumRacks() + 3];
        this.widgetList = new TreeMap<>();
        this.ws = ws;
        this.listModelWidgetsVars = new DefaultListModel();
        this.listModelCodeWidgets = new DefaultListModel();
        this.listModelMasterMap = new DefaultListModel();
        initComponents();
        this._Button_EnableClicks.setEnabled(false);
    }

    public void loadControlSettings(ControlSettings cs, WidgetSettings ws) {
        this.cs = cs;
        this.ws = ws;
        this.mouseActive = new boolean[cs.getNumRacks() + 3];
        this.widgetList = new TreeMap<>();
        this._Button_EnableClicks.setEnabled(false);
        _Button_CreateImports.setEnabled(true);
        _Label_Loaded.setText("CSV File Not loaded, load file to continue");

        // Load defaults if no links
        if (ws.getWidgetLinks().isEmpty()) {
            DefaultWidgets dw = mf.loadDefaultWidgets();
            if (dw != null) {
                ws.setWidgetLinks(dw.getWidgetLinks());
                widgetList = dw.getWidgetCodeMappings();
                updateWidgetCode();
            } else {
                loadWidgetCode();
            }
        } else {
            loadWidgetCode();
        }
    }

    public ControlSettings getCs() {
        return cs;
    }

    public void setCs(ControlSettings cs) {
        this.cs = cs;
        updateDisplay();
    }

    public WidgetSettings getWs() {
        return ws;
    }

    public void setWs(WidgetSettings ws) {
        this.ws = ws;
        updateDisplay();
    }

    public void updateDisplay() {

        String[] tabs = new String[cs.getNumRacks() + 3];
        tabs[0] = "Main";
        for (int i = 0; i < cs.getNumRacks(); i++) {
            tabs[i + 1] = "R: " + cs.getRackName(i);
        }
        tabs[tabs.length - 2] = "Loads";
        tabs[tabs.length - 1] = "Financial";

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

    public void printWidgetVariables() {
        ArrayList<String> ss = new ArrayList<>();
        for (WidgetCode s : widgetList.values()) {
            for (String vars : s.getVariables().keySet()) {
                if (!ss.contains(vars)) {
                    ss.add(vars);
                }
            }
        }

        System.out.println(ss);
    }

    public boolean isMouseActive(int index) {
        return mouseActive[index];
    }

    public void setMouseActive(boolean mouseActive, int index) {
        this.mouseActive[index] = mouseActive;
    }

    public void returnClick(Point p) {
        //System.out.println("WidgetCode click at " + p);
        clicked(p);

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
                    _FTF_WigetParam_xPosPer.setText(String.valueOf(per.x));
                    _FTF_WigetParam_yPosPer.setText(String.valueOf(per.y));
                } else {
                    _FTF_WigetParam_xPosPer.setText(String.valueOf(-2));
                    _FTF_WigetParam_yPosPer.setText(String.valueOf(-2));
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
        return new DefaultWidgets(ws.getWidgetLinks(), widgetList);
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
        _ScrollPane_WidgetNames = new javax.swing.JScrollPane();
        _List_WidgetCodeList = new javax.swing.JList();
        _ComboBox_DisplayPanel = new javax.swing.JComboBox();
        _ScrollPane_ExportPane = new javax.swing.JScrollPane();
        _TextArea_WidgetExport = new javax.swing.JTextArea();
        _Button_Copy = new javax.swing.JButton();
        _ScrollPane_VariableNames = new javax.swing.JScrollPane();
        _List_WidgetVars = new javax.swing.JList();
        _Label_VarNames = new javax.swing.JLabel();
        _Label_Widget = new javax.swing.JLabel();
        _Button_LoadXls = new javax.swing.JButton();
        _Label_Loaded = new javax.swing.JLabel();
        _Panel_WidgetParams = new javax.swing.JPanel();
        _Label_WidgetParams = new javax.swing.JLabel();
        _Label_WigetParam_yPos = new javax.swing.JLabel();
        _Button_EnableClicks = new javax.swing.JButton();
        _FTF_WigetParam_xPos = new javax.swing.JFormattedTextField();
        _FTF_WigetParam_yPos = new javax.swing.JFormattedTextField();
        _Label_WigetParam_xPos = new javax.swing.JLabel();
        _Label_ClickStatus = new javax.swing.JLabel();
        _FTF_WigetParam_xPosPer = new javax.swing.JFormattedTextField();
        _Label_WigetParam_xPos1 = new javax.swing.JLabel();
        _Label_WigetParam_yPos1 = new javax.swing.JLabel();
        _FTF_WigetParam_yPosPer = new javax.swing.JFormattedTextField();
        _Button_GenerateWidgetLink = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        _TextArea_Log = new javax.swing.JTextArea();
        _ScrollPane_WidgetSettings = new javax.swing.JScrollPane();
        _Panel_WidgetSettings = new javax.swing.JPanel();
        _Button_LoadDefaults = new javax.swing.JButton();
        _Button_LoadDefaults1 = new javax.swing.JButton();
        _Button_widgetPositions = new javax.swing.JButton();
        _ComboBox_Subgroup = new javax.swing.JComboBox();
        _ScrollPane_MasterMap = new javax.swing.JScrollPane();
        _List_MasterMapVariables = new javax.swing.JList();
        _Label_Widget1 = new javax.swing.JLabel();
        _Button_ClearSelection = new javax.swing.JButton();
        _Button_CreateImports = new javax.swing.JButton();
        _Button_ClearLinks = new javax.swing.JButton();
        _Button_print = new javax.swing.JButton();

        _FileChooser_IoFile.setApproveButtonText("Open");
        _FileChooser_IoFile.setApproveButtonToolTipText("Open a xls file");
        _FileChooser_IoFile.setCurrentDirectory(new java.io.File("C:\\Users\\EricGummerson\\Documents\\Background Creator Files"));
        _FileChooser_IoFile.setDialogTitle("Open a XLS File");
        _FileChooser_IoFile.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XLS files", "xls"));

        setMinimumSize(new java.awt.Dimension(969, 544));
        setPreferredSize(new java.awt.Dimension(1031, 620));

        _List_WidgetCodeList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _List_WidgetCodeList.setModel(listModelCodeWidgets);
        _List_WidgetCodeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        _List_WidgetCodeList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                _List_WidgetCodeListValueChanged(evt);
            }
        });
        _ScrollPane_WidgetNames.setViewportView(_List_WidgetCodeList);

        _ComboBox_DisplayPanel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_DisplayPanel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Main" }));
        _ComboBox_DisplayPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_DisplayPanelActionPerformed(evt);
            }
        });

        _TextArea_WidgetExport.setColumns(20);
        _TextArea_WidgetExport.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _TextArea_WidgetExport.setLineWrap(true);
        _TextArea_WidgetExport.setRows(5);
        _TextArea_WidgetExport.setWrapStyleWord(true);
        _TextArea_WidgetExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _TextArea_WidgetExportMouseClicked(evt);
            }
        });
        _ScrollPane_ExportPane.setViewportView(_TextArea_WidgetExport);

        _Button_Copy.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_Copy.setText("Copy");
        _Button_Copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_CopyActionPerformed(evt);
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

        _Label_Widget.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_Widget.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Widget.setText("Widget Code");

        _Button_LoadXls.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        _Button_LoadXls.setText("LOAD EXPORT FILE");
        _Button_LoadXls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_LoadXlsActionPerformed(evt);
            }
        });

        _Label_Loaded.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        _Label_Loaded.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Loaded.setText("XLS File Not loaded, load file to continue");

        _Label_WidgetParams.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WidgetParams.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WidgetParams.setText("Widget Parameters");

        _Label_WigetParam_yPos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WigetParam_yPos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WigetParam_yPos.setText("Positon Y");

        _Button_EnableClicks.setText("Turn On/Off");
        _Button_EnableClicks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_EnableClicksActionPerformed(evt);
            }
        });

        _FTF_WigetParam_xPos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FTF_WigetParam_xPos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_WigetParam_xPos.setText("10");

        _FTF_WigetParam_yPos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FTF_WigetParam_yPos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_WigetParam_yPos.setText("10");

        _Label_WigetParam_xPos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WigetParam_xPos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WigetParam_xPos.setText("Positon X");

        _Label_ClickStatus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_ClickStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_ClickStatus.setText("Click Off");

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
        jScrollPane1.setViewportView(_TextArea_Log);

        _ScrollPane_WidgetSettings.setPreferredSize(new java.awt.Dimension(232, 265));

        javax.swing.GroupLayout _Panel_WidgetSettingsLayout = new javax.swing.GroupLayout(_Panel_WidgetSettings);
        _Panel_WidgetSettings.setLayout(_Panel_WidgetSettingsLayout);
        _Panel_WidgetSettingsLayout.setHorizontalGroup(
            _Panel_WidgetSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );
        _Panel_WidgetSettingsLayout.setVerticalGroup(
            _Panel_WidgetSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );

        _ScrollPane_WidgetSettings.setViewportView(_Panel_WidgetSettings);

        _Button_LoadDefaults.setFont(new java.awt.Font("Arial", 0, 8)); // NOI18N
        _Button_LoadDefaults.setText("Load Defaults");
        _Button_LoadDefaults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_LoadDefaultsActionPerformed(evt);
            }
        });

        _Button_LoadDefaults1.setFont(new java.awt.Font("Arial", 0, 8)); // NOI18N
        _Button_LoadDefaults1.setText("Save Defaults");
        _Button_LoadDefaults1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_LoadDefaults1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout _Panel_WidgetParamsLayout = new javax.swing.GroupLayout(_Panel_WidgetParams);
        _Panel_WidgetParams.setLayout(_Panel_WidgetParamsLayout);
        _Panel_WidgetParamsLayout.setHorizontalGroup(
            _Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                        .addComponent(_Label_WidgetParams, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(_ScrollPane_WidgetSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_WidgetParamsLayout.createSequentialGroup()
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_Label_ClickStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(_Label_WigetParam_yPos, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(_Label_WigetParam_xPos, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_FTF_WigetParam_xPos)
                            .addComponent(_Button_EnableClicks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_FTF_WigetParam_yPos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                            .addComponent(_Button_GenerateWidgetLink, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(_Button_LoadDefaults, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                .addComponent(_Button_LoadDefaults1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_WidgetParamsLayout.createSequentialGroup()
                                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(_Label_WigetParam_xPos1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(_Label_WigetParam_yPos1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(_FTF_WigetParam_xPosPer)
                                    .addComponent(_FTF_WigetParam_yPosPer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        _Panel_WidgetParamsLayout.setVerticalGroup(
            _Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_ClickStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Button_EnableClicks))
                        .addGap(4, 4, 4)
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_WigetParam_xPos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FTF_WigetParam_xPos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                                .addComponent(_Button_LoadDefaults, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_Button_LoadDefaults1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(_Button_GenerateWidgetLink, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                        .addComponent(_Label_WidgetParams, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_ScrollPane_WidgetSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        _Panel_WidgetParamsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_Button_LoadDefaults, _Button_LoadDefaults1});

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

        _Label_Widget1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_Widget1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Widget1.setText("Variables on Panel");

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
        _Button_ClearLinks.setText("Clear All Links");
        _Button_ClearLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_ClearLinksActionPerformed(evt);
            }
        });

        _Button_print.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_print.setText("Print");
        _Button_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(_ScrollPane_VariableNames, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_VarNames, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(_Button_ClearSelection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(_Label_Widget1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(_Button_widgetPositions, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(_ComboBox_DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(_ScrollPane_MasterMap)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_Label_Widget, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_ScrollPane_WidgetNames, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_ComboBox_Subgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Panel_WidgetParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_Button_LoadXls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_Label_Loaded, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_ScrollPane_ExportPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(_Button_CreateImports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_Button_Copy, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Button_ClearLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_Button_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_Button_ClearSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(_Label_VarNames, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(_Button_widgetPositions, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(_Label_Widget1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_ScrollPane_MasterMap)
                            .addComponent(_ScrollPane_VariableNames, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_Widget)
                            .addComponent(_ComboBox_DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_ComboBox_Subgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_Panel_WidgetParams, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_ScrollPane_WidgetNames, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_Button_LoadXls, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(_Label_Loaded, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_ScrollPane_ExportPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_Button_CreateImports, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(_Button_Copy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_Button_ClearLinks, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(_Button_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)))
                .addGap(30, 30, 30))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _Button_CopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_CopyActionPerformed
        // TODO add your handling code here:
        Highlighter h = _TextArea_WidgetExport.getHighlighter();
        h.removeAllHighlights();
        int sel = _TextArea_WidgetExport.getText().length();
        if (sel > 0) {
            try {
                h.addHighlight(0, sel, DefaultHighlighter.DefaultPainter);
            } catch (BadLocationException ex) {
                System.out.println("Bad selection");
            }
        }
        StringSelection stringSelection = new StringSelection(_TextArea_WidgetExport.getText());
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }//GEN-LAST:event__Button_CopyActionPerformed

    private void _TextArea_WidgetExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event__TextArea_WidgetExportMouseClicked

        Highlighter h = _TextArea_WidgetExport.getHighlighter();
        h.removeAllHighlights();
    }//GEN-LAST:event__TextArea_WidgetExportMouseClicked

    private void _Button_EnableClicksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_EnableClicksActionPerformed

        int index = _ComboBox_DisplayPanel.getSelectedIndex();
        mouseActive[index] = !mouseActive[index];
        if (mouseActive[index]) {
            _Label_ClickStatus.setText("Click On");
        } else {
            _Label_ClickStatus.setText("Click Off");
        }
        mf.canClick(_ComboBox_DisplayPanel.getSelectedIndex(), mouseActive[index]);
    }//GEN-LAST:event__Button_EnableClicksActionPerformed

    private void _Button_LoadXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_LoadXlsActionPerformed

        _FileChooser_IoFile.setDialogTitle("Load XLS File");
        _FileChooser_IoFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser_IoFile.setDialogType(JFileChooser.OPEN_DIALOG);
        _FileChooser_IoFile.setApproveButtonText("Save Here");

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
        // TODO add your handling code here:
        int index = _ComboBox_DisplayPanel.getSelectedIndex();
        mf.displayFrame.setTab(index);

        if (mouseActive[index]) {
            _Label_ClickStatus.setText("Click On");
        } else {
            _Label_ClickStatus.setText("Click Off");
        }

        if (masterMap != null) {
            loadMasterMapList();
        }
    }//GEN-LAST:event__ComboBox_DisplayPanelActionPerformed

    private void _Button_widgetPositionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_widgetPositionsActionPerformed
        // TODO add your handling code here:
        _Button_EnableClicks.setEnabled(true);
        masterMap = mf.displayFrame.getWidgetPositions();

        loadMasterMapList();
    }//GEN-LAST:event__Button_widgetPositionsActionPerformed

    private void _ComboBox_SubgroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_SubgroupActionPerformed
        // TODO add your handling code here:

        loadWidgetVars();

    }//GEN-LAST:event__ComboBox_SubgroupActionPerformed

    private void _List_WidgetVarsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event__List_WidgetVarsValueChanged
        // TODO add your handling code here:

        if (!evt.getValueIsAdjusting()) {
            if (masterMap != null) {
                loadMasterMapList();
            }
        }
    }//GEN-LAST:event__List_WidgetVarsValueChanged

    private void _Button_ClearSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_ClearSelectionActionPerformed
        // TODO add your handling code here:
        _List_WidgetVars.clearSelection();
        if (masterMap != null && masterMap.size() > 0) {
            loadMasterMapList();
        }
    }//GEN-LAST:event__Button_ClearSelectionActionPerformed

    private void _Button_GenerateWidgetLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_GenerateWidgetLinkActionPerformed
        // TODO add your handling code here:
        // Make sure we have a selection for the widget type, the widget variable type
        if (!_List_WidgetVars.isSelectionEmpty() && !_List_WidgetCodeList.isSelectionEmpty()) {
            String panelName = _ComboBox_DisplayPanel.getSelectedItem().toString();
            String panelType;

            if (panelName.startsWith("R:")) {
                panelType = "Rack";
            } else {

                panelType = panelName;
            }

            String subGroup = _ComboBox_Subgroup.getSelectedItem().toString();
            String varType = _List_WidgetVars.getSelectedValue().toString();
            String key = panelType + "-" + varType;

            String widgetCodeStr = _List_WidgetCodeList.getSelectedValue().toString();
            WidgetCode wc = widgetList.get(widgetCodeStr + ".txt");
            //System.out.println(widgetCodeStr + " gives -> " + wc);
            Point per = new Point(Integer.parseInt(_FTF_WigetParam_xPosPer.getText()), Integer.parseInt(_FTF_WigetParam_yPosPer.getText()));

            WidgetLink wl = new WidgetLink(wc, per, panelType, subGroup);

            if (ws.add(key, wl) != null) {
                _TextArea_Log.append("The key {" + key + "} already exists. It has now been overwritten\n");
            } else {
                _TextArea_Log.append("Added {" + key + "} with WidgetVar {" + widgetCodeStr + "}\n");
            }

        } else {
            _TextArea_Log.append("Selection is required for Widget Variable and Widget Code.\n");

        }
    }//GEN-LAST:event__Button_GenerateWidgetLinkActionPerformed

    private void _Button_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_printActionPerformed
        // TODO add your handling code here:

        _TextArea_WidgetExport.setText("");
        //System.out.println(ws.numberLinks());
        for (Entry<String, WidgetLink> entry : ws.getWidgetLinkEntrySet()) {
            //System.out.println("Key: " + entry.getKey() + " : " + entry.getValue());
            _TextArea_WidgetExport.append("Key: " + entry.getKey() + " : " + entry.getValue() + "\n\n");
        }

    }//GEN-LAST:event__Button_printActionPerformed

    private void _Button_ClearLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_ClearLinksActionPerformed
        // TODO add your handling code here:

        //System.out.println("Size Before: " + mf.store.ws.widgetLinks.size());
        ws.clear();
        _TextArea_WidgetExport.setText("");
        //System.out.println("Size After: " + mf.store.ws.widgetLinks.size());

    }//GEN-LAST:event__Button_ClearLinksActionPerformed

    private void _List_WidgetCodeListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event__List_WidgetCodeListValueChanged
        // TODO add your handling code here:

        // _ScrollPane_WidgetSettings
        if (!evt.getValueIsAdjusting()) {

            // Load the variables of the widget
            String widgetCodeStr = _List_WidgetCodeList.getSelectedValue().toString();
            WidgetCode wc = widgetList.get(widgetCodeStr + ".txt");
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

            for (Entry<String, String> entry : wc.getVariables().entrySet()) {
                JLabel label = new JLabel(entry.getKey());
                label.setFont(new Font("Arial", Font.BOLD, 13));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                JTextField textfield = new JTextField(entry.getValue());
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

    private void _Button_CreateImportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_CreateImportsActionPerformed
        // TODO add your handling code here:
        _TextArea_WidgetExport.setText("");

        // Generate the code (Save to Files later)
        Map<String, List<String>> exportStringMap = new HashMap<>();
        for (Entry<String, WidgetLink> entry : ws.getWidgetLinkEntrySet()) {

            String panelType = entry.getValue().getPanelType();
            String panelName = panelType;
            boolean rackEntry = panelType.equals("Rack");

            List<String> usedRacks = new ArrayList<>();

            do {

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
                    if(!rackEntry){
                        break;
                    }
                    System.out.println("Rack: " + panelName + " -- " + rackEntry);

                }

                if (!exportStringMap.containsKey(panelName)) {
                    exportStringMap.put(panelName, new ArrayList<>());
                } else {
                    System.out.println("Export already contained " + panelName);
                }
                // For each entry, we want to generate the code that applies to all variables
                //System.out.println(entry);
                String orgKey = entry.getKey().substring(entry.getKey().indexOf("-") + 1);
                String[] selectedVar = orgKey.split("`");
                //System.out.println("SV: " + Arrays.toString(selectedVar));

                String index = entry.getValue().getPanelType();

                if (rackEntry) {
                    index = "R: " + panelName;
                }
                System.out.println("index:" + index);
                System.out.println("master map keys: " + Arrays.toString(masterMap.keySet().toArray()));
                for (Entry<String, Rectangle> varsToGen : masterMap.get(index).entrySet()) {

                    boolean contains = true;
                    for (String part : selectedVar) {
                        if (!contains) {
                            break;
                        }
                        if (!part.contains("%")) {
                            //System.out.println("Does " + entry.getKey() + " contain " + part);
                            if (!varsToGen.getKey().contains(part)) {
                                contains = false;
                            }
                        }

                    }
                    if (importedIOVariables != null && contains) {

                        //System.out.println("key: " + varsToGen.getKey());
                        if (importedIOVariables.containsKey(varsToGen.getKey())) {

                            int io_id = importedIOVariables.get(varsToGen.getKey());
                            //System.out.println("Linked " + orgKey + " to: " + varsToGen.getKey() + " with IO_ID: " + io_id);

                            exportStringMap.get(panelName).add(createWidget(entry.getValue().getWidgetCode(), varsToGen.getValue(), entry.getValue().getPositionPercentage(), io_id));

                        } else {
                            //System.out.println("Linked " + orgKey + " to: " + varsToGen.getKey() + ". IO_ID not found");
                        }

                    } else if (contains) {

                        //System.out.println("Linked " + orgKey + " to: " + varsToGen.getKey() + ". IO_ID not loaded");
                    } else {
                        //System.err.println("Ignored " + orgKey + " to: " + varsToGen.getKey());
                    }

                }
            } while (rackEntry);

        }

        writeOutExports(exportStringMap);

        /*
         Highlighter h = _TextArea_WidgetExport.getHighlighter();
         h.removeAllHighlights();
         int sel = _TextArea_WidgetExport.getText().length();
         if (sel > 0) {
         try {
         h.addHighlight(0, sel, DefaultHighlighter.DefaultPainter);
         } catch (BadLocationException ex) {
         System.out.println("Bad selection");
         }
         }
         StringSelection stringSelection = new StringSelection(_TextArea_WidgetExport.getText());
         Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
         clpbrd.setContents(stringSelection, null);
         */

    }//GEN-LAST:event__Button_CreateImportsActionPerformed

    private void _Button_LoadDefaults1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_LoadDefaults1ActionPerformed
        // TODO add your handling code here:
        mf.saveDefaultWidgets();
    }//GEN-LAST:event__Button_LoadDefaults1ActionPerformed

    private void _Button_LoadDefaultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_LoadDefaultsActionPerformed
        // TODO add your handling code here:
        DefaultWidgets dw = mf.loadDefaultWidgets();
        if (dw != null) {
            ws.setWidgetLinks(dw.getWidgetLinks());
            widgetList = dw.getWidgetCodeMappings();
        }

    }//GEN-LAST:event__Button_LoadDefaultsActionPerformed

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
                String adder = "[";
                for (String listString : entry.getValue()) {
                    adder += listString + ",";
                }
                adder = adder.substring(0, adder.length() - 1) + "]";

                String fp = filepath + "\\WidgetExports-" + entry.getKey() + ".txt";
                System.out.println("Writing " + fp);

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fp), "utf-8"))) {
                    writer.write(adder);
                } catch (Exception e) {
                }

                System.out.println("Writing " + fp + " Completed");

            }

        } else {
            System.out.println("File access cancelled by user.");
        }

        return true;
    }

    public String createWidget(WidgetCode wc, Rectangle rect, Point per, int io_id) {

        int xPos = rect.x + (int) (per.getX() * rect.getWidth() / 100.0);
        int yPos = rect.y + (int) (per.getY() * rect.getHeight() / 100.0);

        String code = wc.getFullWidgetText();

        code = code.replace("`%IO_ID%`", String.valueOf(io_id))
                .replace("`%XPOS%`", String.valueOf(xPos))
                .replace("`%YPOS%`", String.valueOf(yPos));

        return code;
    }

    public void loadMasterMapList() {

        String index = _ComboBox_DisplayPanel.getSelectedItem().toString();

        if (masterMap.get(index) != null) {

            String[] selectedVar = null;
            if (!_List_WidgetVars.isSelectionEmpty()) {
                selectedVar = _List_WidgetVars.getSelectedValue().toString().split("`");

                /*if (selectedVar.contains("`%")) {
                 selectedVar = selectedVar.substring(0, selectedVar.indexOf("`%"));
                 }*/
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
        String path = "/Creator/textFiles/Default Widgets.txt";

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

        if (scan != null) {
            scan.close();
        }

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
                        if (!var.equals("`%IO_ID%`") && !var.equals("`%XPOS%`") && !var.equals("`%YPOS%`")) {
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
                    if (!var.equals("`%IO_ID%`") && !var.equals("`%XPOS%`") && !var.equals("`%YPOS%`")) {
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
            ArrayList<String> vars = new ArrayList<>();
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
            }

            if (idName == -1 || idCol == -1) {
                System.out.println("Could not locate io_name or io_id in excel header");
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

        } catch (Exception e) {
            System.out.println("Error reading excel file " + e.getMessage());
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_ClearLinks;
    private javax.swing.JButton _Button_ClearSelection;
    private javax.swing.JButton _Button_Copy;
    private javax.swing.JButton _Button_CreateImports;
    private javax.swing.JButton _Button_EnableClicks;
    private javax.swing.JButton _Button_GenerateWidgetLink;
    private javax.swing.JButton _Button_LoadDefaults;
    private javax.swing.JButton _Button_LoadDefaults1;
    private javax.swing.JButton _Button_LoadXls;
    private javax.swing.JButton _Button_print;
    private javax.swing.JButton _Button_widgetPositions;
    private javax.swing.JComboBox _ComboBox_DisplayPanel;
    private javax.swing.JComboBox _ComboBox_Subgroup;
    private javax.swing.JFormattedTextField _FTF_WigetParam_xPos;
    private javax.swing.JFormattedTextField _FTF_WigetParam_xPosPer;
    private javax.swing.JFormattedTextField _FTF_WigetParam_yPos;
    private javax.swing.JFormattedTextField _FTF_WigetParam_yPosPer;
    private javax.swing.JFileChooser _FileChooser_IoFile;
    private javax.swing.JLabel _Label_ClickStatus;
    private javax.swing.JLabel _Label_Loaded;
    private javax.swing.JLabel _Label_VarNames;
    private javax.swing.JLabel _Label_Widget;
    private javax.swing.JLabel _Label_Widget1;
    private javax.swing.JLabel _Label_WidgetParams;
    private javax.swing.JLabel _Label_WigetParam_xPos;
    private javax.swing.JLabel _Label_WigetParam_xPos1;
    private javax.swing.JLabel _Label_WigetParam_yPos;
    private javax.swing.JLabel _Label_WigetParam_yPos1;
    private javax.swing.JList _List_MasterMapVariables;
    private javax.swing.JList _List_WidgetCodeList;
    private javax.swing.JList _List_WidgetVars;
    private javax.swing.JPanel _Panel_WidgetParams;
    private javax.swing.JPanel _Panel_WidgetSettings;
    private javax.swing.JScrollPane _ScrollPane_ExportPane;
    private javax.swing.JScrollPane _ScrollPane_MasterMap;
    private javax.swing.JScrollPane _ScrollPane_VariableNames;
    private javax.swing.JScrollPane _ScrollPane_WidgetNames;
    private javax.swing.JScrollPane _ScrollPane_WidgetSettings;
    private javax.swing.JTextArea _TextArea_Log;
    private javax.swing.JTextArea _TextArea_WidgetExport;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
