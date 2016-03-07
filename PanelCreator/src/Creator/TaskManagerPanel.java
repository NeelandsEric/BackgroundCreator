/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
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

    /**
     * Creates new form TaskManagerPanel
     *
     * @param mf
     */
    public TaskManagerPanel(MainFrame mf) {
        this.mf = mf;
        this.stationId = -1;
        initComponents();
        loadDefaultTasks();

    }

    public void setImportedIoVariables(Map<String, Integer> newIo) {
        if (importedIOVariables != null && !importedIOVariables.isEmpty()) {
            importedIOVariables.clear();
        }
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

            for (int i = 0; i < rows; i++) {

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
                                    rowData[j] = "no data @ [ " + i + "][" + j + "]";
                                    break;
                                case 4: // boolean
                                    rowData[j] = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case 5: // error
                                    rowData[j] = String.valueOf(cell.getErrorCellString());
                                    break;
                                default:
                                    rowData[j] = "default @ [ " + i + "][" + j + "]";
                                    break;

                            }

                        } else {
                            rowData[j] = "nullValue @ [ " + i + "][" + j + "]";
                        }
                    }
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
        jLabel1 = new javax.swing.JLabel();
        _Panel_Imports = new javax.swing.JPanel();
        _Button_LoadXls = new javax.swing.JButton();
        _Label_Loaded = new javax.swing.JLabel();
        _Button_CreateImports = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        _FileChooser_IoFile.setApproveButtonText("Open");
        _FileChooser_IoFile.setApproveButtonToolTipText("Open a xls file");
        _FileChooser_IoFile.setCurrentDirectory(new java.io.File("C:\\Users\\EricGummerson\\Documents\\Background Creator Files"));
        _FileChooser_IoFile.setDialogTitle("Open a XLS File");
        _FileChooser_IoFile.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XLS files", "xls"));

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
        _Button_CreateImports.setText("Create Imports");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_ImportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_ImportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(_Button_CreateImports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Button_LoadXls, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Label_Loaded, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
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
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jButton1.setText("Test DB insert");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(351, 351, 351)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Panel_Imports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Panel_Imports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(210, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _Button_LoadXlsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_LoadXlsActionPerformed
        // TODO add your handling code here:
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
            mf.loadImportedIos(importedIOVariables);
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event__Button_LoadXlsActionPerformed

    private void _Button_CreateImportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_CreateImportsActionPerformed

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

                name = inputs = outputs = "";
                
                if (taskEntry[6].equals("1")) {
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
                    }

                    // Number of values for each output key
                    for (int i = 0; i < outputKeys.length; i++) {
                        if (mappings.containsKey(outputKeys[i])) {
                            numValsPerOutKeys[i] = mappings.get(outputKeys[i]).size();
                            if (numValsPerOutKeys[i] > maxOut) {
                                maxOut = numValsPerOutKeys[i];
                            }
                        }
                    }

                    // Since we can add all the racks inputs to the script, we just
                    // loop through each of the vals in the inputs
                    for (int numRuns = 0; numRuns < maxIn; numRuns++) {
                        for (int i = 0; i < inputKeys.length; i++) {
                            
                            if(numValsPerInKeys[i] >= numRuns){                           
                                inputs += mappings.get(inputKeys[i]).get(numRuns); 
                                inputs += ",";
                            }
                        }
                    }
                    
                    // Since we can add all the racks inputs to the script, we just
                    // loop through each of the vals in the inputs
                    for (int numRuns = 0; numRuns < maxOut; numRuns++) {
                        for (int i = 0; i < outputKeys.length; i++) {
                            
                            if(numValsPerOutKeys[i] >= numRuns){                           
                                outputs += mappings.get(outputKeys[i]).get(numRuns); 
                                outputs += ",";
                            }
                        }
                    }

                    name = taskEntry[1];
                    
                    // taskEntry []
                    // [0] description of task | [1] task_manager_name | [2] task_manager_task_id
                    // [3] task_manager_inputs | [4] task_manager_outputs | [5] task_manager_crontab_line
                    // [6]  task_manager_pass_inputs_as_io_id | [7] EachRack

                    // Query template
                    // (task_id, inputs, outputs, crontab_line, station_id, name, pass_inputs)
                    rowImports.add(String.format(queryTemplate,
                            taskEntry[2], inputs, outputs,
                            taskEntry[5], stationId, name, taskEntry[6]));
                }
                break;
            }
            System.out.println("Row import: " + rowImports.get(0));
        }// No data, do nothing
        else {
            System.out.println(importedIOVariables.isEmpty() + " | " + importedTasks.isEmpty());
        }

    }//GEN-LAST:event__Button_CreateImportsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        /*
         db = new DBConn();
         //String query = "insert into eepr (eepr_io_id, eepr_station_id) "
         //        + "values (18945, (select distinct io_station_id from io where io_id =18945));";
         //String query = "delete from eepr where eepr_io_id = 18945";
        
         db.executeQuery(query);
         db.closeConn();*/

        createQueries();
    }//GEN-LAST:event_jButton1ActionPerformed

    public void closeConn() {
        if (db != null) {
            db.closeConn();
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

            System.out.println("Station ID: " + stationId);

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
        } catch (Exception e) {
            System.out.println("Error reading excel file " + e.getMessage());
        }
    }

    public void createQueries() {

        // This function will return a Map containing all the formatted strings
        // for each base string
        // Amp Avg `%rackname` -> Amp Avg Rack A, Amp Avg Rack B, etc.
        Map<String, List> mappings = mf.getMapFullStrings();

        for (Map.Entry<String, List> entry: mappings.entrySet()) {
            
            for (Object val : entry.getValue().toArray()) {
                System.out.println(entry.getKey() + " -> " + (String) val);
            }
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_CreateImports;
    private javax.swing.JButton _Button_LoadXls;
    private javax.swing.JFileChooser _FileChooser_IoFile;
    private javax.swing.JLabel _Label_Loaded;
    private javax.swing.JPanel _Panel_Imports;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
