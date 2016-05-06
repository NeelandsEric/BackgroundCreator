/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author EricGummerson
 */
public class TaskManagerPanel extends javax.swing.JPanel {

    public MainFrame mf;
    private int stationId;
    private Map<String, Integer> importedIOVariables;       // io_name,io_id
    private List<String[]> importedTasks;
    private DBConn db;
    private ControlSettings cs;
    private WidgetPanelLinks wpl;

    private static String widgetCodeName = "Panel1 Link";

    /**
     * Creates new form TaskManagerPanel
     *
     * @param mf main frame
     * @param cs control settings
     * @param wpl widget panel links
     */
    public TaskManagerPanel(MainFrame mf, ControlSettings cs, WidgetPanelLinks wpl) {
        this.mf = mf;
        this.stationId = -1;
        this.cs = cs;
        if (wpl != null) {
            this.wpl = wpl;
        } else {
            this.wpl = new WidgetPanelLinks();
        }
        initComponents();
        loadDefaultTasks();
        loadComboBoxPanels();

    }

    public MainFrame getMf() {
        return mf;
    }

    public void setMf(MainFrame mf) {
        this.mf = mf;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public WidgetPanelLinks getWpl() {
        return wpl;
    }

    public void setWpl(WidgetPanelLinks wpl) {
        this.wpl = wpl;
    }

    public void setImportedIoVariables(Map<String, Integer> newIo, int stationId) {
        if (importedIOVariables != null && !importedIOVariables.isEmpty()) {
            importedIOVariables.clear();
        }
        this.stationId = stationId;
        importedIOVariables = newIo;
        _Button_CreateImports.setEnabled(true);
        _Label_Loaded.setText("Loaded File!");
    }

    public Map<String, Integer> getImportedIoVariables() {
        return importedIOVariables;
    }

    private void loadDefaultTasks() {

        String path = "/Creator/textFiles/tasks.xlsx";
        InputStream loc = this.getClass().getResourceAsStream(path);
        importedTasks = new ArrayList<>();
        try {

            XSSFWorkbook wb = new XSSFWorkbook(loc);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;
            String[] rowData;
            int rows, cols; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rows; i++) {

                row = sheet.getRow(i);
                if (row != null) {
                    cols = row.getPhysicalNumberOfCells();
                    rowData = new String[cols];

                    for (int j = 0; j < cols; j++) {

                        cell = row.getCell(j);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case 1: // string
                                    rowData[j] = cell.getStringCellValue();
                                    break;
                                case 2: // int
                                    rowData[j] = String.valueOf(cell.getNumericCellValue());
                                    break;
                                case 3: // blank
                                    System.out.println("Blank data @ [" + i + "][" + j + "]");
                                    rowData[j] = "no data @ [" + i + "][" + j + "]";
                                    break;
                                case 4: // boolean
                                    rowData[j] = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case 5: // error
                                    rowData[j] = String.valueOf(cell.getErrorCellString());
                                    break;
                                default:
                                    System.out.println("default @ [" + i + "][" + j + "]");
                                    rowData[j] = "default @ [" + i + "][" + j + "]";
                                    break;

                            }

                        } else {
                            System.out.println("null @ [" + i + "][" + j + "]");
                            rowData[j] = "nullValue @ [" + i + "][" + j + "]";
                        }
                    }
                    rowData[5] = "'" + rowData[5] + "'";
                    importedTasks.add(rowData);

                }

            }

            wb.close();

        } catch (Exception e) {
            System.out.println("Error reading excel file " + e.getMessage());
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

        _FileChooser_IoFile = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        _Panel_Imports = new javax.swing.JPanel();
        _Button_LoadXls = new javax.swing.JButton();
        _Label_Loaded = new javax.swing.JLabel();
        _Button_CreateImports = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        _TextArea_Status = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
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

        _FileChooser_IoFile.setApproveButtonText("Open");
        _FileChooser_IoFile.setApproveButtonToolTipText("Open a xls file");
        _FileChooser_IoFile.setCurrentDirectory(new java.io.File("C:\\Users\\EricGummerson\\Documents\\Background Creator Files"));
        _FileChooser_IoFile.setDialogTitle("Open a XLS File");
        _FileChooser_IoFile.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XLS files", "xls"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setMinimumSize(new java.awt.Dimension(969, 544));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Task Manager Imports");

        _Button_LoadXls.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        _Button_LoadXls.setText("LOAD EXPORT FILE");
        _Button_LoadXls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_LoadXlsActionPerformed(evt);
            }
        });

        _Label_Loaded.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Label_Loaded.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Loaded.setText("XLS File Not loaded, load file to continue");

        _Button_CreateImports.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_CreateImports.setText("Import Tasks");
        _Button_CreateImports.setEnabled(false);
        _Button_CreateImports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_CreateImportsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout _Panel_ImportsLayout = new javax.swing.GroupLayout(_Panel_Imports);
        _Panel_Imports.setLayout(_Panel_ImportsLayout);
        _Panel_ImportsLayout.setHorizontalGroup(
            _Panel_ImportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_ImportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_ImportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Button_CreateImports, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Button_LoadXls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Label_Loaded, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addContainerGap())
        );
        _Panel_ImportsLayout.setVerticalGroup(
            _Panel_ImportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_ImportsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_Button_LoadXls, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_Label_Loaded, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_Button_CreateImports, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        _TextArea_Status.setEditable(false);
        _TextArea_Status.setColumns(20);
        _TextArea_Status.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _TextArea_Status.setLineWrap(true);
        _TextArea_Status.setRows(5);
        jScrollPane1.setViewportView(_TextArea_Status);

        _Button_GenerateLinks.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_GenerateLinks.setText("Generate HTML Links");
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(_Button_GenerateLinks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_ComboBox_Panels, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(_FTF_XPOS)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(_FTF_YPOS)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(_FTF_PanelID, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                    .addComponent(_TF_PanelName)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(_Button_Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(3, 3, 3)))
                        .addGap(0, 7, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(_ComboBox_Panels, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_FTF_XPOS, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_FTF_YPOS, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(_FTF_PanelID))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(_TF_PanelName))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_Button_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addComponent(_Button_GenerateLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(295, 295, 295)
                        .addComponent(_Panel_Imports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(351, 351, 351)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_Panel_Imports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89))))
        );
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

    private void _Button_CreateImportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_CreateImportsActionPerformed

        if (checkTaskExist()) {
            _TextArea_Status.append("\nStatus: Tasks already exist for Station ID " + stationId);
            return;
        }

        if (stationId == -1) {
            stationId = mf.stationId;
            System.out.println("New ID: " + stationId);
        }

        _TextArea_Status.append("\nStatus: Tasks being created and imported to Station ID: " + stationId);

        if (!importedIOVariables.isEmpty() && !importedTasks.isEmpty()) {
            // Imported io variables and imported tasks are good
            List<String> rowImports = new ArrayList<>();

            // This function will return a Map containing all the formatted strings
            // for each base string
            // Amp Avg `%rackname` -> Amp Avg Rack A, Amp Avg Rack B, etc.
            Map<String, List> mappings = mf.getMapFullStrings();

            // (task_manager_task_id, task_manager_inputs, task_manager_outputs, task_manager_crontab_line,
            //  task_manager_station_id, task_manager_name, task_manager_pass_inputs_as_io_id)
            String queryTemplate = "(%s, %s, %s, %s, %s, %s, %s)";
            String name, inputs, outputs;

            String[] inputKeys, outputKeys;
            int[] numValsPerInKeys, numValsPerOutKeys;
            int maxIn = 0, maxOut = 0;
            // Loop through each task
            for (String[] taskEntry : importedTasks) {

                inputs = outputs = "'";

                switch (taskEntry[7]) {
                    case "1":
                        // Script uses all the inputs, assign the keys

                        inputKeys = taskEntry[3].split(",");
                        outputKeys = taskEntry[4].split(",");
                        numValsPerInKeys = new int[inputKeys.length];
                        numValsPerOutKeys = new int[outputKeys.length];
                        // Number of values for each input key
                        for (int i = 0; i < inputKeys.length; i++) {
                            if (mappings.containsKey(inputKeys[i])) {
                                numValsPerInKeys[i] = mappings.get(inputKeys[i]).size();
                                if (numValsPerInKeys[i] > maxIn) {
                                    maxIn = numValsPerInKeys[i];
                                }
                            }
                        }   // Number of values for each output key
                        for (int i = 0; i < outputKeys.length; i++) {
                            if (mappings.containsKey(outputKeys[i])) {
                                numValsPerOutKeys[i] = mappings.get(outputKeys[i]).size();
                                if (numValsPerOutKeys[i] > maxOut) {
                                    maxOut = numValsPerOutKeys[i];
                                }
                            }
                        }   // Since we can add all the racks inputs to the script, we just
                        // loop through each of the vals in the inputs
                        for (int numRuns = 0; numRuns < maxIn; numRuns++) {
                            for (int i = 0; i < inputKeys.length; i++) {
                                if (numValsPerInKeys[i] > numRuns) {
                                    inputs += mappings.get(inputKeys[i]).get(numRuns);
                                    inputs += ",";
                                }
                            }
                        }   // Since we can add all the racks inputs to the script, we just
                        // loop through each of the vals in the inputs
                        for (int numRuns = 0; numRuns < maxOut; numRuns++) {
                            for (int i = 0; i < outputKeys.length; i++) {
                                if (numValsPerOutKeys[i] > numRuns) {
                                    outputs += mappings.get(outputKeys[i]).get(numRuns);
                                    outputs += ",";
                                }
                            }
                        }
                        inputs = inputs.substring(0, inputs.length() - 1) + "'";
                        outputs = outputs.substring(0, outputs.length() - 1) + "'";
                        name = "'" + taskEntry[1] + "'";
                        // taskEntry []
                        // [0] description of task | [1] task_manager_name | [2] task_manager_task_id
                        // [3] task_manager_inputs | [4] task_manager_outputs | [5] task_manager_crontab_line
                        // [6]  task_manager_pass_inputs_as_io_id | [7] EachRack
                        // Query template
                        // (task_id, inputs, outputs, crontab_line, station_id, name, pass_inputs)
                        inputs = findIDs(inputs);
                        outputs = findIDs(outputs);
                        rowImports.add(String.format(queryTemplate,
                                taskEntry[2], inputs, outputs,
                                taskEntry[5], stationId, name, taskEntry[6]));

                        //System.out.println("Row import #1: " + rowImports.get(rowImports.size() - 1) + "\n");
                        maxIn = maxOut = 0;
                        break;
                    case "0":
                        // Doesnt use all the racks, run for each rack
                        inputKeys = taskEntry[3].split(",");
                        outputKeys = taskEntry[4].split(",");
                        numValsPerInKeys = new int[inputKeys.length];
                        numValsPerOutKeys = new int[outputKeys.length];
                        // Number of values for each input key
                        for (int i = 0; i < inputKeys.length; i++) {
                            if (mappings.containsKey(inputKeys[i])) {
                                numValsPerInKeys[i] = mappings.get(inputKeys[i]).size();
                                if (numValsPerInKeys[i] > maxIn) {
                                    maxIn = numValsPerInKeys[i];
                                }
                            }
                        }   // Number of values for each output key
                        for (int i = 0; i < outputKeys.length; i++) {
                            if (mappings.containsKey(outputKeys[i])) {
                                numValsPerOutKeys[i] = mappings.get(outputKeys[i]).size();
                                if (numValsPerOutKeys[i] > maxOut) {
                                    maxOut = numValsPerOutKeys[i];
                                }
                            }
                        }   // Since we cant add all the racks inputs to the script, we just
                        // loop through and add to the tasks for each run

                        // Find number of racks
                        int numRacks = mf.store.cs.getNumRacks();

                        for (int numRuns = 0; numRuns < numRacks; numRuns++) {

                            for (int i = 0; i < inputKeys.length; i++) {
                                if (numValsPerInKeys[i] > numRuns) {
                                    inputs += mappings.get(inputKeys[i]).get(numRuns);
                                    inputs += ",";
                                } else if (i > 0 && numValsPerInKeys[i] == 1) {
                                    inputs += mappings.get(inputKeys[i]).get(0);
                                    inputs += ",";
                                }
                            }

                            for (int i = 0; i < outputKeys.length; i++) {
                                if (numValsPerOutKeys[i] > numRuns) {
                                    outputs += mappings.get(outputKeys[i]).get(numRuns);
                                    outputs += ",";
                                }
                            }

                            inputs = inputs.substring(0, inputs.length() - 1) + "'";
                            outputs = outputs.substring(0, outputs.length() - 1) + "'";

                            name = "'" + taskEntry[1].replace("`%rackname`", mf.store.cs.getRackName(numRuns)) + "'";

                            // taskEntry []
                            // [0] description of task | [1] task_manager_name | [2] task_manager_task_id
                            // [3] task_manager_inputs | [4] task_manager_outputs | [5] task_manager_crontab_line
                            // [6]  task_manager_pass_inputs_as_io_id | [7] EachRack
                            // Query template
                            // (task_id, inputs, outputs, crontab_line, station_id, name, pass_inputs)
                            inputs = findIDs(inputs);
                            outputs = findIDs(outputs);

                            rowImports.add(String.format(queryTemplate,
                                    taskEntry[2], inputs, outputs,
                                    taskEntry[5], stationId, name, taskEntry[6]));
                            inputs = outputs = "'";
                            //System.out.println("Row import #0: " + rowImports.get(rowImports.size() - 1) + "\n");
                        }
                        maxIn = maxOut = 0;
                        break;
                    case "2":
                        // This is used for tasks that involve multiple items from each rack
                        // and the script is used one per rack
                        // Ex - CompStatus - Comp Amps/Run -> Comp Status Rack _
                        // This can only be used per rack as there is an X many compressors

                        int outputsUsed = 0;
                        // Doesnt use all the racks, run for each rack
                        inputKeys = taskEntry[3].split(",");
                        outputKeys = taskEntry[4].split(",");
                        numValsPerInKeys = new int[inputKeys.length];
                        numValsPerOutKeys = new int[outputKeys.length];
                        // Number of values for each input key
                        for (int i = 0; i < inputKeys.length; i++) {
                            if (mappings.containsKey(inputKeys[i])) {
                                numValsPerInKeys[i] = mappings.get(inputKeys[i]).size();
                                if (numValsPerInKeys[i] > maxIn) {
                                    maxIn = numValsPerInKeys[i];
                                }
                            }
                        }   // Number of values for each output key
                        for (int i = 0; i < outputKeys.length; i++) {
                            if (mappings.containsKey(outputKeys[i])) {
                                numValsPerOutKeys[i] = mappings.get(outputKeys[i]).size();
                                if (numValsPerOutKeys[i] > maxOut) {
                                    maxOut = numValsPerOutKeys[i];
                                }
                            }
                        }   //System.out.println("maxin: " + maxIn + " | in key len: " + inputKeys.length);

                        //System.out.println("npr: " + numPerRack);
                        // Since we cant add all the racks inputs to the script, we just
                        // loop through and add to the tasks for each run
                        // Since there are an unknown amount of items per rack, we check the strings to see if it contains
                        // the current rack name
                        int rackNameIndex = 0;
                        String rackNameCheck = mf.store.cs.getRackName(rackNameIndex);

                        for (int numRuns = 0; numRuns < maxIn; numRuns++) {

                            // Check to make sure that the current inputs have the same rack name
                            // If it changes, we add the current setup, and start for the next rack
                            boolean checkRackName = false;
                            for (String inputKey : inputKeys) {
                                if (mappings.get(inputKey).size() > numRuns) {
                                    if (((String) mappings.get(inputKey).get(numRuns)).contains(rackNameCheck)) {
                                        checkRackName = true;
                                        break;
                                    }
                                }
                            }

                            if (!checkRackName) {
                                // New rack

                                inputs = inputs.substring(0, inputs.length() - 1) + "'";
                                outputs = outputs.substring(0, outputs.length() - 1) + "'";

                                inputs = findIDs(inputs);
                                outputs = findIDs(outputs);

                                name = "'" + taskEntry[1].replace("`%rackname`", rackNameCheck) + "'";

                                rackNameIndex++;
                                rackNameCheck = mf.store.getCs().getRackName(rackNameIndex);

                                rowImports.add(String.format(queryTemplate,
                                        taskEntry[2], inputs, outputs,
                                        taskEntry[5], stationId, name, taskEntry[6]));

                                //System.out.println("Row import #2: " + rowImports.get(rowImports.size() - 1));
                                inputs = outputs = "'";

                            }

                            for (int i = 0; i < inputKeys.length; i++) {
                                if (numValsPerInKeys[i] > numRuns) {
                                    inputs += mappings.get(inputKeys[i]).get(numRuns);
                                    inputs += ",";
                                }
                            }

                            // (inputs) - (outputs)
                            // One group of inputs relates to an output
                            // Compressor status (Amps,SLA,Temp) - (Status)
                            if (maxIn == maxOut) {

                                for (int i = 0; i < outputKeys.length; i++) {

                                    if (numValsPerOutKeys[i] > numRuns) {
                                        outputs += mappings.get(outputKeys[i]).get(numRuns);
                                        outputs += ",";
                                    }

                                }
                            } else {
                                // Used for rack comp/cond faults
                                // All rack inputs links to 1 output
                                // Number of outputs per task -> outputsMax / numRacks

                                int numOutPer = maxOut / cs.getNumRacks();
                                for (int i = 0; i < outputKeys.length; i++) {
                                    if (numOutPer == 1) {
                                        // Only 1 output
                                        if (outputs.equals("'")) {
                                            outputs += mappings.get(outputKeys[i]).get(outputsUsed);
                                            outputs += ",";
                                            outputsUsed++;
                                        }
                                    } else {
                                        if (outputs.split(",").length < numOutPer) {
                                            outputs += mappings.get(outputKeys[i]).get(outputsUsed);
                                            outputs += ",";
                                            outputsUsed++;
                                        }
                                    }

                                }
                            }

                            // taskEntry []
                            // [0] description of task | [1] task_manager_name | [2] task_manager_task_id
                            // [3] task_manager_inputs | [4] task_manager_outputs | [5] task_manager_crontab_line
                            // [6]  task_manager_pass_inputs_as_io_id | [7] EachRack
                            // Query template
                            // (task_id, inputs, outputs, crontab_line, station_id, name, pass_inputs)
                        }

                        // All inputs are done, add the last task
                        inputs = inputs.substring(0, inputs.length() - 1) + "'";
                        outputs = outputs.substring(0, outputs.length() - 1) + "'";

                        inputs = findIDs(inputs);
                        outputs = findIDs(outputs);

                        name = "'" + taskEntry[1].replace("`%rackname`", rackNameCheck) + "'";

                        rowImports.add(String.format(queryTemplate,
                                taskEntry[2], inputs, outputs,
                                taskEntry[5], stationId, name, taskEntry[6]));

                        //System.out.println("Row import #2: " + rowImports.get(rowImports.size() - 1));
                        //System.out.println("Row import: " + rowImports.get(rowImports.size()-1));
                        maxIn = maxOut = 0;
                        break;
                }
                //System.out.println("Row import: " + rowImports.get(rowImports.size() - 1));
            }

            String retString = insertTasks(rowImports);
            if (retString.startsWith("Fail")) {
                _TextArea_Status.append("\nStatus: " + retString);
            } else {
                _TextArea_Status.append("\nStatus: Successfully imported tasks for Station ID: " + stationId);
            }
        }// No data, do nothing
        else {
            System.out.println(importedIOVariables.isEmpty() + " | " + importedTasks.isEmpty());
        }


    }//GEN-LAST:event__Button_CreateImportsActionPerformed

    public void setCs(ControlSettings cs) {
        if (this.cs != cs) {
            this.cs = cs;
        }

        loadComboBoxPanels();
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

    private void _ComboBox_PanelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_PanelsActionPerformed

        if (_ComboBox_Panels.getSelectedItem() != null) {
            String pn = _ComboBox_Panels.getSelectedItem().toString();
            LinkInfo li = wpl.getLinkInfo(pn);
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

    public void buttonClick(Point p) {
        _FTF_XPOS.setText(String.valueOf(p.x));
        _FTF_YPOS.setText(String.valueOf(p.y));
    }
    private void _Button_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_SaveActionPerformed
        // TODO add your handling code here:
        if (!_FTF_PanelID.getText().equals("") && !_TF_PanelName.getText().equals("")) {
            wpl.addLink(_ComboBox_Panels.getSelectedItem().toString(),
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

        mf.store.ws.setWpl(wpl);


    }//GEN-LAST:event__Button_SaveActionPerformed

    private void _Button_GenerateLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_GenerateLinksActionPerformed
        // TODO add your handling code here:

        if (wpl.links.isEmpty()) {
            return;
        }

        String outputCode = "[";

        WidgetCode wc = mf.wgPanel.getWidgetCode(widgetCodeName);
        for (Map.Entry<String, LinkInfo> entry : wpl.getLinks().entrySet()) {

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

            outputCode += newCode + ",";

        }

        outputCode = outputCode.substring(0, outputCode.length() - 1) + "]";

        System.out.println(outputCode);

    }//GEN-LAST:event__Button_GenerateLinksActionPerformed

    private boolean checkTaskExist() {

        String query = "select task_manager_station_id from task_manager where task_manager_station_id = " + stationId + ";";

        db = new DBConn();
        boolean status = db.checkForData(query);
        db.closeConn();

        return status;
    }

    private String insertTasks(List<String> taskList) {

        String query = "INSERT INTO task_manager "
                + "(task_manager_task_id, task_manager_inputs, task_manager_outputs, task_manager_crontab_line,"
                + "task_manager_station_id, task_manager_name, task_manager_pass_inputs_as_io_id) values ";

        for (String value : taskList) { 
            if(!value.split(", ")[1].equals("''") && !value.split(", ")[1].equals("','")){            
                query += value + ",";  
            }else {
                System.out.println("Didnt ADD: " + value);
            }
        }

        query = query.substring(0, query.length() - 1) + ";";

        System.out.println(query);

        db = new DBConn();

        String returnString = db.executeQuery(query);
        db.closeConn();

        return returnString;

    }

    public void closeConn() {
        if (db != null) {
            db.closeConn();
        }
    }
    
    
    public String findIDs(String list) {

        String[] elements = list.replace("'", "").split(",");

        String returnString = "'";
        for (String s : elements) {
            returnString += findIDForString(s) + ",";
        }
        
        returnString = returnString.replace(",,", ",");

        return returnString.substring(0, returnString.length() - 1) + "'";
        
    }

    public String findIDForString(String ioName) {

        if (importedIOVariables.containsKey(ioName)) {
            return String.valueOf(importedIOVariables.get(ioName));
        } else {
            System.out.println("No variables found for " + ioName);
            return "";
        }
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
                    for (int c = 0; c < cols; c++) {
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
                            stationId = (int) sheet.getRow(1).getCell(c).getNumericCellValue();
                            break;
                        }
                    }
                } else {
                    stationId = (int) sheet.getRow(1).getCell(2).getNumericCellValue();
                }

            }

            if (idName == -1 || idCol == -1) {
                System.out.println("Could not locate io_name or io_id in excel header");
                return;
            }
            if (stationId == -1) {
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
            mf.loadImportedIos(importedIOVariables, 2, stationId);
        } catch (Exception e) {
            System.out.println("Error reading excel file " + e.getMessage());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_CreateImports;
    private javax.swing.JButton _Button_GenerateLinks;
    private javax.swing.JButton _Button_LoadXls;
    private javax.swing.JButton _Button_Save;
    private javax.swing.JComboBox _ComboBox_Panels;
    private javax.swing.JFormattedTextField _FTF_PanelID;
    private javax.swing.JFormattedTextField _FTF_XPOS;
    private javax.swing.JFormattedTextField _FTF_YPOS;
    private javax.swing.JFileChooser _FileChooser_IoFile;
    private javax.swing.JLabel _Label_Loaded;
    private javax.swing.JPanel _Panel_Imports;
    private javax.swing.JTextField _TF_PanelName;
    private javax.swing.JTextArea _TextArea_Status;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
