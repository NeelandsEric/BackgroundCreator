/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
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
    private List<String[]> importedAlerts;
    private DBConn db;
    private ControlSettings cs;
    private Map<String, Integer> users;
    private Map<String, Integer> userGroups;
    private DefaultListModel listUsers;
    private DefaultListModel listUserGroups;

    /**
     * Creates new form TaskManagerPanel
     *
     * @param mf main frame
     * @param cs control settings
     * @param wpl widget panel links
     */
    public TaskManagerPanel(MainFrame mf, ControlSettings cs) {
        this.mf = mf;
        this.stationId = -1;
        this.cs = cs;
        this.db = null;
        
        initComponents();
        loadDefaultTasks();
        loadDefaultAlerts();
        
        listUsers = new DefaultListModel();
        listUserGroups = new DefaultListModel();
        loadData();
        
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

    

    public void setImportedIoVariables(Map<String, Integer> newIo, int stationId) {
        if (importedIOVariables != null && !importedIOVariables.isEmpty()) {
            importedIOVariables.clear();
        }
        this.stationId = stationId;
        importedIOVariables = newIo;
        _Button_CreateImports.setEnabled(true);
        _Button_InsertCustom.setEnabled(true);
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

    private void loadDefaultAlerts() {

        String path = "/Creator/textFiles/alerts.xlsx";
        InputStream loc = this.getClass().getResourceAsStream(path);
        importedAlerts = new ArrayList<>();
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
                                    //System.out.println("default @ [" + i + "][" + j + "] = " + String.valueOf(cell.getRawValue()));
                                    rowData[j] = String.valueOf(cell.getRawValue());
                                    break;
                            }

                        } else {
                            System.out.println("null @ [" + i + "][" + j + "]");
                            rowData[j] = "nullValue @ [" + i + "][" + j + "]";
                        }
                    }
                    importedAlerts.add(rowData);

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
        _Panel_Imports = new javax.swing.JPanel();
        _Button_LoadXls = new javax.swing.JButton();
        _Label_Loaded = new javax.swing.JLabel();
        _Button_CreateImports = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        _TextArea_Status = new javax.swing.JTextArea();
        _Panel_AlertInserts = new javax.swing.JPanel();
        _Label_Users = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        _List_Users = new javax.swing.JList();
        _Label_UserGroups = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        _List_UserGroups = new javax.swing.JList();
        _Button_AddUserToGroups = new javax.swing.JButton();
        _Label_Username = new javax.swing.JLabel();
        _TF_Username = new javax.swing.JTextField();
        _Label_Users3 = new javax.swing.JLabel();
        _TF_UserGroup = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        _CB_NavOption = new javax.swing.JComboBox();
        _Label_Nav = new javax.swing.JLabel();
        _Label_UserType = new javax.swing.JLabel();
        _CB_UserType = new javax.swing.JComboBox();
        _Buton_CreateUser = new javax.swing.JButton();
        _Label_Password = new javax.swing.JLabel();
        _TF_Password = new javax.swing.JTextField();
        _Label_UGHomePanel = new javax.swing.JLabel();
        _TF_UGHomePanel = new javax.swing.JTextField();
        _Button_AddTemplates = new javax.swing.JButton();
        _Button_InsertCustom = new javax.swing.JButton();

        _FileChooser_IoFile.setApproveButtonText("Open");
        _FileChooser_IoFile.setApproveButtonToolTipText("Open a xls file");
        _FileChooser_IoFile.setCurrentDirectory(new java.io.File("C:\\Users\\EricGummerson\\Documents\\Background Creator Files"));
        _FileChooser_IoFile.setDialogTitle("Open a XLS File");
        _FileChooser_IoFile.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XLS files", "xls"));

        setMinimumSize(new java.awt.Dimension(1031, 680));

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

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Task Manager Imports");

        javax.swing.GroupLayout _Panel_ImportsLayout = new javax.swing.GroupLayout(_Panel_Imports);
        _Panel_Imports.setLayout(_Panel_ImportsLayout);
        _Panel_ImportsLayout.setHorizontalGroup(
            _Panel_ImportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_ImportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_ImportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Button_CreateImports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Button_LoadXls, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Label_Loaded, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addContainerGap())
        );
        _Panel_ImportsLayout.setVerticalGroup(
            _Panel_ImportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_ImportsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_Button_LoadXls)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_Label_Loaded, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_Button_CreateImports, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        _TextArea_Status.setEditable(false);
        _TextArea_Status.setColumns(20);
        _TextArea_Status.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _TextArea_Status.setLineWrap(true);
        _TextArea_Status.setRows(5);
        jScrollPane1.setViewportView(_TextArea_Status);

        _Label_Users.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        _Label_Users.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Users.setText("Users");

        _List_Users.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _List_Users.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "User 1" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        _List_Users.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                _List_UsersValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(_List_Users);

        _Label_UserGroups.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        _Label_UserGroups.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_UserGroups.setText("User Groups");

        _List_UserGroups.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _List_UserGroups.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "User Group 1" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        _List_UserGroups.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                _List_UserGroupsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(_List_UserGroups);

        _Button_AddUserToGroups.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_AddUserToGroups.setText("Add User(s) to Group(s)");
        _Button_AddUserToGroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_AddUserToGroupsActionPerformed(evt);
            }
        });

        _Label_Username.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        _Label_Username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Username.setText("Username");

        _TF_Username.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _TF_Username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _TF_Username.setText("Username");
        _TF_Username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                _TF_UsernameKeyPressed(evt);
            }
        });

        _Label_Users3.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        _Label_Users3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Users3.setText("User Group");

        _TF_UserGroup.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _TF_UserGroup.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _TF_UserGroup.setText("User Group");
        _TF_UserGroup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                _TF_UserGroupKeyPressed(evt);
            }
        });

        jLabel8.setText("If no user group is selected, a user group is created");

        _CB_NavOption.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Full Menu", "Mini-Menu + Reports", "Mini-Menu" }));

        _Label_Nav.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_Nav.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Nav.setText("Navigation Menu");

        _Label_UserType.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_UserType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_UserType.setText("User Type");

        _CB_UserType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Admin", "Monitor", "Monitor & Command" }));

        _Buton_CreateUser.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Buton_CreateUser.setText("Create User");
        _Buton_CreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Buton_CreateUserActionPerformed(evt);
            }
        });

        _Label_Password.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        _Label_Password.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Password.setText("Password");

        _TF_Password.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _TF_Password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _TF_Password.setText("pass");

        _Label_UGHomePanel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_UGHomePanel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_UGHomePanel.setText("User Group Home Panel");

        _TF_UGHomePanel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _TF_UGHomePanel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _TF_UGHomePanel.setText("0");

        _Button_AddTemplates.setText("Add Templates");
        _Button_AddTemplates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_AddTemplatesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout _Panel_AlertInsertsLayout = new javax.swing.GroupLayout(_Panel_AlertInserts);
        _Panel_AlertInserts.setLayout(_Panel_AlertInsertsLayout);
        _Panel_AlertInsertsLayout.setHorizontalGroup(
            _Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_Label_Users, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_Label_UserGroups, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(_CB_UserType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Label_Username, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_TF_Username, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Label_Users3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_TF_UserGroup, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_CB_NavOption, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Label_Nav, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Label_UserType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(_Label_UGHomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(_TF_UGHomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                    .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(_Label_Password, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                        .addComponent(_TF_Password))
                    .addComponent(_Button_AddTemplates, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(_Button_AddUserToGroups, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(_Buton_CreateUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        _Panel_AlertInsertsLayout.setVerticalGroup(
            _Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                        .addComponent(_Label_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_TF_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                        .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_UserGroups, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Label_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                                .addComponent(_TF_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                                        .addComponent(_Label_Users3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(_TF_UserGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                                        .addComponent(_Label_UGHomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(_TF_UGHomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(_Panel_AlertInsertsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(_Label_Nav, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(_CB_NavOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(_Label_UserType, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(_CB_UserType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                                        .addComponent(_Buton_CreateUser)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(_Button_AddUserToGroups)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(_Button_AddTemplates, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 139, Short.MAX_VALUE))))
                    .addGroup(_Panel_AlertInsertsLayout.createSequentialGroup()
                        .addComponent(_Label_Users, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );

        _Button_InsertCustom.setText("Insert to Custom Table");
        _Button_InsertCustom.setEnabled(false);
        _Button_InsertCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_InsertCustomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Panel_AlertInserts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_Panel_Imports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                        .addGap(45, 45, 45)
                        .addComponent(_Button_InsertCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Panel_Imports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(_Button_InsertCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addComponent(_Panel_AlertInserts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            _Button_InsertCustom.setEnabled(true);
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
    }   

    

    private void _List_UsersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event__List_UsersValueChanged
        

        // _ScrollPane_WidgetSettings
        if (!evt.getValueIsAdjusting()) {
            if (_List_Users.getSelectedIndices().length > 1) {
                // More than 1 selection
                _TF_Username.setText("");
            } else {
                // One selection

                _TF_Username.setText((String) _List_Users.getSelectedValue());

            }

        }
    }//GEN-LAST:event__List_UsersValueChanged

    private void _List_UserGroupsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event__List_UserGroupsValueChanged
        
        if (!evt.getValueIsAdjusting()) {
            if (!_List_UserGroups.isSelectionEmpty()) {

                // One selection
                _TF_UserGroup.setText((String) _List_UserGroups.getSelectedValue());

            } else {
                // More than 1 selection
                _TF_UserGroup.setText("");
            }

        }
    }//GEN-LAST:event__List_UserGroupsValueChanged

    private void _Buton_CreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Buton_CreateUserActionPerformed
        

        if (_List_Users.isSelectionEmpty()) {

            db = newDBConn();

            String userName = _TF_Username.getText();
            String userID = null;

            String groupName = _TF_UserGroup.getText();
            String userGroupID = null;

            // Check if user exists
            if (users.containsKey(userName)) {
                userID = String.valueOf(users.get(userName));
            } else {

                String[] params = new String[6];
                params[0] = "'" + _TF_Username.getText() + "'";     // Username
                params[1] = "'" + _TF_Password.getText() + "'";     // Password
                params[2] = "'" + "-" + "'";     // Email
                params[3] = String.valueOf(_CB_UserType.getSelectedIndex()); // User type
                params[4] = "'" + _TF_Username.getText() + "'";     // Full Name
                params[5] = String.valueOf(_CB_NavOption.getSelectedIndex());     // Show menu
                String valuesTemplate = "(%s, %s, %s, %s, %s, %s)";
                String realVals = String.format(valuesTemplate, (Object[]) params);
                String query = "insert into interface_users (interface_user_login, interface_user_password, "
                        + "interface_user_email, interface_user_type, interface_user_full_name, "
                        + "interface_user_showmenu) values " + realVals + " returning interface_user_id;";

                System.out.println(query);

                userID = db.executeReturnQuery(query);

            }

            // Check if the user group exists
            if (userGroups.containsKey(groupName)) {
                userGroupID = String.valueOf(userGroups.get(groupName));
            } else {
                // Add the user group to the Database
                String userGroupPanelID = _TF_UGHomePanel.getText();
                String queryUG = "insert into user_groups (user_group_name, user_group_home_panel_id) values "
                        + "('" + groupName + "', " + userGroupPanelID + ") returning user_group_id;";

                userGroupID = db.executeReturnQuery(queryUG);

            }

            String vals = "(" + userID + ", " + userGroupID + ")";
            String queryGroup = "insert into user_group_members (user_group_member_interface_user_id, "
                    + "user_group_member_user_group_id) values " + vals + ";";
            System.out.println(queryGroup);

            db.executeQuery(queryGroup);

            db.closeConn();

            loadData();
        }

    }//GEN-LAST:event__Buton_CreateUserActionPerformed

    private void _TF_UsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event__TF_UsernameKeyPressed
        
        _List_Users.clearSelection();
    }//GEN-LAST:event__TF_UsernameKeyPressed

    private void _TF_UserGroupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event__TF_UserGroupKeyPressed
        
        _List_UserGroups.clearSelection();
    }//GEN-LAST:event__TF_UserGroupKeyPressed

    private void _Button_AddUserToGroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_AddUserToGroupsActionPerformed
        
        // For each user selected, add them to the specific group

        db = newDBConn();

        String groupName = _TF_UserGroup.getText();
        String userGroupID = null;

        // Check if the user group exists
        if (userGroups.containsKey(groupName)) {
            userGroupID = String.valueOf(userGroups.get(groupName));
        } else {
            // Add the user group to the Database
            String userGroupPanelID = _TF_UGHomePanel.getText();
            String queryUG = "insert into user_groups (user_group_name, user_group_home_panel_id) values "
                    + "('" + groupName + "', " + userGroupPanelID + ") returning user_group_id;";

            userGroupID = db.executeReturnQuery(queryUG);

        }

        for (Object item : _List_Users.getSelectedValuesList()) {

            String userID = String.valueOf(users.get(item.toString()));
            String query = "insert into user_group_members (user_group_member_interface_user_id, "
                    + "user_group_member_user_group_id) values (" + userID + ", " + userGroupID + ");";

            db.executeQuery(query);
        }

        db.closeConn();

    }//GEN-LAST:event__Button_AddUserToGroupsActionPerformed

    private void _Button_AddTemplatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_AddTemplatesActionPerformed

        _TextArea_Status.append("\nStatus: Alerts being created and imported.");

        if (!importedIOVariables.isEmpty() && !importedAlerts.isEmpty()) {
            // Imported io variables and imported tasks are good
            List<String> rowImports = new ArrayList<>();

            // This function will return a Map containing all the formatted strings
            // for each base string
            // Amp Avg `%rackname` -> Amp Avg Rack A, Amp Avg Rack B, etc.
            Map<String, List> mappings = mf.getMapFullStrings();

            // (task_manager_task_id, task_manager_inputs, task_manager_outputs, task_manager_crontab_line,
            //  task_manager_station_id, task_manager_name, task_manager_pass_inputs_as_io_id)
            String queryTemplate = "(%s, %s, %s)";

            List<String> userGroupsToAdd = new ArrayList<>();

            for (Object ugName : _List_UserGroups.getSelectedValuesList()) {
                if (userGroups.containsKey((String) ugName)) {
                    userGroupsToAdd.add(String.valueOf(userGroups.get((String) ugName)));
                }
            }

            String alertTemplateID;

            // Loop through each task
            for (String[] taskEntry : importedAlerts) {

                List<String> iosToAdd = new ArrayList<>();

                alertTemplateID = taskEntry[1];

                // taskEntry[2] = Comp Amps `%rackname` `%sgname` `%compname`
                if (mappings.containsKey(taskEntry[2])) {

                    for (Object io : mappings.get(taskEntry[2])) {

                        // io = Comp Amps Rack A SGr1(-18) Comp 1
                        // io = Comp Amps Rack A SGr1(-18) Comp 2
                        // io = Comp Amps Rack A SGr1(-18) Comp 3
                        iosToAdd.add((String) io);
                    }
                }

                // For each user group add each io                
                for (String ug : userGroupsToAdd) {
                    // Make sure to convert the iosToAdd from the string version to the ID its linked to
                    for (String io : iosToAdd) {
                        String ioVal = findIDForString(io); // Comp Amps SG1 -> 18232
                        rowImports.add(String.format(queryTemplate, ioVal, alertTemplateID, ug));
                    }
                }

            }

            // Insert all the alerts
            String retString = insertAlerts(rowImports);

            if (retString.startsWith("Fail")) {
                _TextArea_Status.append("\nStatus: " + retString);
            } else {
                _TextArea_Status.append("\nStatus: Successfully imported alerts.");
            }

        }// No data, do nothing
        else {
            System.out.println(importedIOVariables.isEmpty() + " | " + importedTasks.isEmpty());
        }


    }//GEN-LAST:event__Button_AddTemplatesActionPerformed

    private void _Button_InsertCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_InsertCustomActionPerformed
        
        
        String setpoints;
        String setpoint;
        
    }//GEN-LAST:event__Button_InsertCustomActionPerformed

    private boolean checkTaskExist() {

        String query = "select task_manager_station_id from task_manager where task_manager_station_id = " + stationId + ";";

        db = newDBConn();
        boolean status = db.checkForData(query);
        db.closeConn();

        return status;
    }

    private String insertTasks(List<String> taskList) {

        String query = "INSERT INTO task_manager "
                + "(task_manager_task_id, task_manager_inputs, task_manager_outputs, task_manager_crontab_line,"
                + "task_manager_station_id, task_manager_name, task_manager_pass_inputs_as_io_id) values ";

        for (String value : taskList) {
            if (!value.split(", ")[1].equals("''") && !value.split(", ")[1].equals("','")) {
                query += value + ",";
            } else {
                System.out.println("Didnt ADD: " + value);
            }
        }

        query = query.substring(0, query.length() - 1) + ";";

        System.out.println(query);

        db = newDBConn();

        String returnString = db.executeQuery(query);
        db.closeConn();

        return returnString;

    }

    private String insertAlerts(List<String> taskList) {

        String query = "insert into alert_template_allocations "
                + "(alert_template_allocation_io_id,"
                + "alert_template_allocation_alert_template_id,"
                + "alert_template_allocation_user_group_id) "
                + "values ";

        for (String value : taskList) {
            if (!value.split(", ")[1].equals("''") && !value.split(", ")[1].equals("','")) {
                query += value + ",";
            } else {
                System.out.println("Didnt ADD: " + value);
            }
        }

        query = query.substring(0, query.length() - 1) + ";";

        //System.out.println(query);
        db = newDBConn();
        String returnString = db.executeQuery(query);
        db.closeConn();
        return returnString;

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

    private void loadData() {

        db = newDBConn();

        loadUserData();
        loadUserGroupData();

        db.closeConn();
    }

    private void loadUserData() {

        String query = "select interface_user_id,interface_user_login from interface_users order by 2 asc;";
        users = db.getUserData(query);
        updateUserData();
    }

    private void loadUserGroupData() {

        String query = "select user_group_id, user_group_name from user_groups order by 2 asc;";
        userGroups = db.getUserGroupData(query);
        updateUserGroupData();
    }

    public void updateUserData() {

        if (users != null) {
            listUsers.removeAllElements();

            for (String s : users.keySet()) {
                listUsers.addElement(s);
            }

            _List_Users.setModel(listUsers);
        }

    }

    public void updateUserGroupData() {

        if (userGroups != null) {
            listUserGroups.removeAllElements();

            for (String s : userGroups.keySet()) {
                listUserGroups.addElement(s);
            }

            _List_UserGroups.setModel(listUserGroups);
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
    private javax.swing.JButton _Buton_CreateUser;
    private javax.swing.JButton _Button_AddTemplates;
    private javax.swing.JButton _Button_AddUserToGroups;
    private javax.swing.JButton _Button_CreateImports;
    private javax.swing.JButton _Button_InsertCustom;
    private javax.swing.JButton _Button_LoadXls;
    private javax.swing.JComboBox _CB_NavOption;
    private javax.swing.JComboBox _CB_UserType;
    private javax.swing.JFileChooser _FileChooser_IoFile;
    private javax.swing.JLabel _Label_Loaded;
    private javax.swing.JLabel _Label_Nav;
    private javax.swing.JLabel _Label_Password;
    private javax.swing.JLabel _Label_UGHomePanel;
    private javax.swing.JLabel _Label_UserGroups;
    private javax.swing.JLabel _Label_UserType;
    private javax.swing.JLabel _Label_Username;
    private javax.swing.JLabel _Label_Users;
    private javax.swing.JLabel _Label_Users3;
    private javax.swing.JList _List_UserGroups;
    private javax.swing.JList _List_Users;
    private javax.swing.JPanel _Panel_AlertInserts;
    private javax.swing.JPanel _Panel_Imports;
    private javax.swing.JTextField _TF_Password;
    private javax.swing.JTextField _TF_UGHomePanel;
    private javax.swing.JTextField _TF_UserGroup;
    private javax.swing.JTextField _TF_Username;
    private javax.swing.JTextArea _TextArea_Status;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
