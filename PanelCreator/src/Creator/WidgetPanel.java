/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
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
    private boolean ioFileLoaded;                           // Check to see if the excel file is loaded
    private DefaultListModel listModelWidgetsVars;          // List model for the default widgets vars used on displays
    private DefaultListModel listModelCodeWidgets;          // List model for the code widgets
    private DefaultListModel listModelMasterMap;            // List model for the master map variables
    private Map<String, WidgetCode> widgetList;             // WidgetName,WidgetCode Variable
    private Map<String, List<String>> groupNamesWidget;     // GroupName,List of vars for the group (Store [Temp, Humidity, ..])
    private Map<String, Integer> importedIOVariables;       // io_name,io_id
    private Map<String, Map<String, Rectangle>> masterMap;         // list of variables relating to each view's variables and locations on the panel

    /**
     * Creates new form WidgetPanel
     *
     * @param mf
     * @param cs
     */
    public WidgetPanel(MainFrame mf, ControlSettings cs) {
        this.mf = mf;
        this.cs = cs;
        this.mouseActive = new boolean[cs.getNumRacks() + 3];
        this.ioFileLoaded = false;
        this.widgetList = new TreeMap<>();
        listModelWidgetsVars = new DefaultListModel();
        listModelCodeWidgets = new DefaultListModel();
        listModelMasterMap = new DefaultListModel();
        initComponents();
        _Button_EnableClicks.setEnabled(false);
    }

    public void loadControlSettings(ControlSettings cs) {
        this.cs = cs;
        this.mouseActive = new boolean[cs.getNumRacks() + 3];
        this.widgetList = new TreeMap<>();
        this.ioFileLoaded = false;
        _Button_EnableClicks.setEnabled(false);
    }

    public ControlSettings getCs() {
        return cs;
    }

    public void setCs(ControlSettings cs) {
        this.cs = cs;
        updateDisplay();
    }

    public void updateDisplay() {

        String[] tabs = new String[cs.getNumRacks() + 3];
        tabs[0] = "Main";
        for (int i = 0; i < cs.getNumRacks(); i++) {
            tabs[i + 1] = cs.getRackName(i);
        }
        tabs[tabs.length - 2] = "Loads";
        tabs[tabs.length - 1] = "Financial";

        _ComboBox_DisplayPanel.setModel(new javax.swing.DefaultComboBoxModel(tabs));

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
                            System.out.println("Added name: " + entryName);
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
            for (String vars : s.variables.keySet()) {
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

    }

    public void updateWidgetCode() {

        listModelCodeWidgets.removeAllElements();

        widgetList.values().stream().forEach((widget) -> {

            listModelCodeWidgets.addElement(widget.getWidgetName());
        });

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
        _Button_LoadCSV = new javax.swing.JButton();
        _Label_Loaded = new javax.swing.JLabel();
        _Panel_WidgetParams = new javax.swing.JPanel();
        _Label_WigetParam_stationID = new javax.swing.JLabel();
        _Label_WidgetParams = new javax.swing.JLabel();
        _Label_WigetParam_yPos = new javax.swing.JLabel();
        _FTF_WigetParam_stationID = new javax.swing.JFormattedTextField();
        _Button_EnableClicks = new javax.swing.JButton();
        _FTF_WigetParam_xPos = new javax.swing.JFormattedTextField();
        _FTF_WigetParam_yPos = new javax.swing.JFormattedTextField();
        _Label_WigetParam_xPos = new javax.swing.JLabel();
        _Label_ClickStatus = new javax.swing.JLabel();
        _Button_widgetPositions = new javax.swing.JButton();
        _ComboBox_Subgroup = new javax.swing.JComboBox();
        _ScrollPane_MasterMap = new javax.swing.JScrollPane();
        _List_MasterMapVariables = new javax.swing.JList();
        _Label_Widget1 = new javax.swing.JLabel();

        _FileChooser_IoFile.setApproveButtonText("Open");
        _FileChooser_IoFile.setApproveButtonToolTipText("Open a xls file");
        _FileChooser_IoFile.setCurrentDirectory(null);
        _FileChooser_IoFile.setDialogTitle("Open a XLS File");
        _FileChooser_IoFile.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XLS files", "xls"));

        setMinimumSize(new java.awt.Dimension(969, 544));
        setPreferredSize(new java.awt.Dimension(1031, 620));

        _List_WidgetCodeList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _List_WidgetCodeList.setModel(listModelCodeWidgets);
        _List_WidgetCodeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
        _ScrollPane_VariableNames.setViewportView(_List_WidgetVars);

        _Label_VarNames.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_VarNames.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_VarNames.setText("Subgroup of IO Variables");

        _Label_Widget.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_Widget.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Widget.setText("Widget List");

        _Button_LoadCSV.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        _Button_LoadCSV.setText("LOAD EXPORT FILE");
        _Button_LoadCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_LoadCSVActionPerformed(evt);
            }
        });

        _Label_Loaded.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        _Label_Loaded.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Loaded.setText("CSV File Not loaded, load file to continue");

        _Label_WigetParam_stationID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WigetParam_stationID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WigetParam_stationID.setText("Station ID");

        _Label_WidgetParams.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WidgetParams.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WidgetParams.setText("Widget Parameters");

        _Label_WigetParam_yPos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_WigetParam_yPos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_WigetParam_yPos.setText("Positon Y");

        _FTF_WigetParam_stationID.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        _FTF_WigetParam_stationID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_WigetParam_stationID.setText("10");

        _Button_EnableClicks.setText("Click");
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

        javax.swing.GroupLayout _Panel_WidgetParamsLayout = new javax.swing.GroupLayout(_Panel_WidgetParams);
        _Panel_WidgetParams.setLayout(_Panel_WidgetParamsLayout);
        _Panel_WidgetParamsLayout.setHorizontalGroup(
            _Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Label_WidgetParams, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                        .addComponent(_Label_WigetParam_stationID, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_FTF_WigetParam_stationID, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_Label_ClickStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(_Label_WigetParam_yPos, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(_Label_WigetParam_xPos, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_FTF_WigetParam_xPos)
                    .addComponent(_Button_EnableClicks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_FTF_WigetParam_yPos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(_Label_WigetParam_yPos, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(_Panel_WidgetParamsLayout.createSequentialGroup()
                        .addComponent(_Label_WidgetParams, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(_Panel_WidgetParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_WigetParam_stationID, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FTF_WigetParam_stationID, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(82, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Panel_WidgetParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(_ScrollPane_VariableNames, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(_Label_VarNames, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(_ComboBox_Subgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(_Label_Widget, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                .addComponent(_ScrollPane_WidgetNames, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_Label_Widget1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(_ComboBox_DisplayPanel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(_Button_widgetPositions, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(_ScrollPane_MasterMap, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(163, 163, 163)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_ScrollPane_ExportPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_Button_Copy, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_Button_LoadCSV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_Label_Loaded, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_ComboBox_Subgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Label_VarNames, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_ComboBox_DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Button_widgetPositions, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_ScrollPane_MasterMap)
                            .addComponent(_ScrollPane_VariableNames, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_Widget, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Label_Widget1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(_ScrollPane_WidgetNames, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(_Panel_WidgetParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_Button_LoadCSV, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(_Label_Loaded, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(_ScrollPane_ExportPane, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_Button_Copy, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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

    private void _Button_LoadCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_LoadCSVActionPerformed
        int returnVal = _FileChooser_IoFile.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser_IoFile.getSelectedFile();

            // What to do with the file, e.g. display it in a TextArea
            //System.out.println("File: " + file.getAbsolutePath());
            String filePath = file.getAbsolutePath();
            readXFile(filePath);

            if (!importedIOVariables.isEmpty()) {
                //updateIoVariables();
            }
            _Button_EnableClicks.setEnabled(true);
        } else {
            System.out.println("File access cancelled by user.");

        }
    }//GEN-LAST:event__Button_LoadCSVActionPerformed

    private void _ComboBox_DisplayPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_DisplayPanelActionPerformed
        // TODO add your handling code here:
        int index = _ComboBox_DisplayPanel.getSelectedIndex();
        mf.displayFrame.setTab(index);

        if (mouseActive[index]) {
            _Label_ClickStatus.setText("Click On");
        } else {
            _Label_ClickStatus.setText("Click Off");
        }

        loadMasterMapList();
    }//GEN-LAST:event__ComboBox_DisplayPanelActionPerformed

    private void _Button_widgetPositionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_widgetPositionsActionPerformed
        // TODO add your handling code here:
        _Button_EnableClicks.setEnabled(true);
        masterMap = mf.displayFrame.getWidgetPositions();

        /*
         for (Map.Entry<String, Map<String, Rectangle>> mm : masterMap.entrySet()) {
         for (Map.Entry<String, Rectangle> entry: mm.getValue().entrySet()) {
         String key = entry.getKey();
         Rectangle rect = entry.getValue();
                
         System.out.println(mm.getKey() + ":" + key + " = " + rect);
         }
         }*/
        loadMasterMapList();
    }//GEN-LAST:event__Button_widgetPositionsActionPerformed

    private void _ComboBox_SubgroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_SubgroupActionPerformed
        // TODO add your handling code here:

        loadWidgetVars();

    }//GEN-LAST:event__ComboBox_SubgroupActionPerformed

    public void loadMasterMapList() {

        String index = _ComboBox_DisplayPanel.getSelectedItem().toString();

        if (masterMap.get(index) != null) {

            listModelMasterMap.removeAllElements();
            for (Map.Entry<String, Rectangle> entry : masterMap.get(index).entrySet()) {
                Rectangle r = entry.getValue();

                String pos = "X: " + r.x + " Y: " + r.y + " Width: " + r.width + " Height: " + r.height;
                listModelMasterMap.addElement(entry.getKey() + ": " + pos);
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
                        vars.add(line.substring(begin, end));
                        //System.out.println("Variable added: " + line.substring(begin, end));
                        line = line.substring(end);
                    }

                }
            }
        } catch (FileNotFoundException e) {

            System.out.println("File not found error " + e.getMessage());
            System.out.println("Error trying to read " + filename);
        }

        WidgetCode widget = new WidgetCode(ss[0], vars, entireWidget, filename);
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
        try (Scanner scan = new Scanner(loc)) {
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                entireWidget += line;

                while (line.contains("`%")) {

                    begin = line.indexOf("`%");
                    end = line.indexOf("%`") + 2;
                    vars.add(line.substring(begin, end));
                    //System.out.println("Variable added: " + line.substring(begin, end));
                    line = line.substring(end);
                }

            }
        }

        WidgetCode widget = new WidgetCode(name, vars, entireWidget, result);
        widgetList.put(name, widget);

    }

    /**
     * Reads a file and returns a list of strings which contain all the variable
     * names
     *
     * @param filename
     * @return List of variable names as an ArrayList of Strings
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

        /*
         try {
         try (Scanner scan = new Scanner(new FileReader(filename))) {
         while (scan.hasNextLine()) {
         vars.add(scan.nextLine());

         }

         ioFileLoaded = true;
         _Label_Loaded.setText("File loaded");
         _Button_EnableClicks.setEnabled(true);
         }

         } catch (FileNotFoundException e) {

         System.out.println("File not found error " + e.getMessage());
         System.out.println("Error trying to read " + filename);
         }

         //System.out.println("Variables found: " + vars);
         return vars;
         */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_Copy;
    private javax.swing.JButton _Button_EnableClicks;
    private javax.swing.JButton _Button_LoadCSV;
    private javax.swing.JButton _Button_widgetPositions;
    private javax.swing.JComboBox _ComboBox_DisplayPanel;
    private javax.swing.JComboBox _ComboBox_Subgroup;
    private javax.swing.JFormattedTextField _FTF_WigetParam_stationID;
    private javax.swing.JFormattedTextField _FTF_WigetParam_xPos;
    private javax.swing.JFormattedTextField _FTF_WigetParam_yPos;
    private javax.swing.JFileChooser _FileChooser_IoFile;
    private javax.swing.JLabel _Label_ClickStatus;
    private javax.swing.JLabel _Label_Loaded;
    private javax.swing.JLabel _Label_VarNames;
    private javax.swing.JLabel _Label_Widget;
    private javax.swing.JLabel _Label_Widget1;
    private javax.swing.JLabel _Label_WidgetParams;
    private javax.swing.JLabel _Label_WigetParam_stationID;
    private javax.swing.JLabel _Label_WigetParam_xPos;
    private javax.swing.JLabel _Label_WigetParam_yPos;
    private javax.swing.JList _List_MasterMapVariables;
    private javax.swing.JList _List_WidgetCodeList;
    private javax.swing.JList _List_WidgetVars;
    private javax.swing.JPanel _Panel_WidgetParams;
    private javax.swing.JScrollPane _ScrollPane_ExportPane;
    private javax.swing.JScrollPane _ScrollPane_MasterMap;
    private javax.swing.JScrollPane _ScrollPane_VariableNames;
    private javax.swing.JScrollPane _ScrollPane_WidgetNames;
    private javax.swing.JTextArea _TextArea_WidgetExport;
    // End of variables declaration//GEN-END:variables
}
