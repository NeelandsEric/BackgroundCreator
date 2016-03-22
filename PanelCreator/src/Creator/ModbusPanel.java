package Creator;

import static Creator.MainFrame.isStringNumeric;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author EricGummerson
 */
public class ModbusPanel extends javax.swing.JPanel {

    public MainFrame mf;
    public ModbusSettings mb;
    public ArrayList<MeterPanel>[] powerScoutPanels;
    public ArrayList<MeterPanel> singleLoadPanels;
    private IPAddressValidator ipValidator;
    private ArrayList<String> rackStr;
    private ArrayList<String> compStr;
    private Map<String, Integer> importedIOVariables;       // io_name,io_id
    private int stationID;
    /**
     * Creates new form ModbusPanel
     *
     * @param mf
     * @param mb
     */
    public ModbusPanel(MainFrame mf, ModbusSettings mb) {
        initComponents();
        this.mf = mf;
        this.mb = mb;
        this.stationID = -1;
        this.readModbusFile();
        ipValidator = new IPAddressValidator();
        mb.updateModbusSettings(mf.getStore().getCs());
    }

    public ModbusSettings getMb() {
        return mb;
    }

    public void setMb(ModbusSettings mb) {
        this.mb = mb;
    }

    public void initalizeMeters() {

        if (mb.getNumPowerScouts() == 0) {
            mb.setNumPowerScouts(1);
        }
        powerScoutPanels = (ArrayList<MeterPanel>[]) new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            powerScoutPanels[i] = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                powerScoutPanels[i].add(new MeterPanel(this, j, 1, true));
            }
        }
        if (mb.getNumSingleLoads() == 0) {
            mb.setNumSingleLoads(1);
        }

        singleLoadPanels = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            singleLoadPanels.add(new MeterPanel(this, 0, 1, false));
        }
        this.updatePanels();
        this.loadModels();
    }

    public void loadStore(ModbusSettings mb) {

        this.mb = mb;
        int panelType;

        _FTF_NumPowerScouts.setText(String.valueOf(mb.getNumPowerScouts()));
        powerScoutPanels = (ArrayList<MeterPanel>[]) new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            powerScoutPanels[i] = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                panelType = mb.getPanelType(i, j);
                powerScoutPanels[i].add(new MeterPanel(this, j, panelType, true));
            }
        }

        String[] names = new String[mb.getNumPowerScouts()];
        for (int i = 0; i < names.length; i++) {
            names[i] = "Meter " + (i + 1);
        }
        _ComboBox_PowerMeter.setModel(new javax.swing.DefaultComboBoxModel(names));

        _FTF_NumSingleLoads.setValue(mb.getNumSingleLoads());
        singleLoadPanels = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            panelType = mb.getPanelType(i);
            singleLoadPanels.add(new MeterPanel(this, i, panelType, false));
        }

        names = new String[mb.getNumSingleLoads()];
        for (int i = 0; i < mb.getNumSingleLoads(); i++) {
            names[i] = "Meter " + (mb.getNumPowerScouts() + (i + 1));
        }
        _ComboBox_SingleMeter.setModel(new javax.swing.DefaultComboBoxModel(names));

        this.updatePowerIP();
        this.updateSingleIP();

        this.updatePanels();
        this.loadModels();
        this.loadSettings();

    }

    private void readModbusFile() {

        String path = "/Creator/textFiles/Default Modbus Variables.txt";
        InputStream loc = this.getClass().getResourceAsStream(path);
        try (Scanner scan = new Scanner(loc)) {
            rackStr = new ArrayList<>();
            compStr = new ArrayList<>();
            String line, groupName = "";
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                line = formatString(line);

                if (line == null) { // make sure it doesnt break

                } else if (line.startsWith("`gn")) {
                    groupName = line.substring(3).toLowerCase();
                    //System.out.println("Grouping name: " + groupName);
                } else {
                    switch (groupName) {
                        case "":
                            //System.out.println("No groupname line = " + line);
                            break;
                        case "rack":
                            //System.out.println("Added to Rack: " + line);
                            rackStr.add(line);
                            break;
                        case "compressor":
                            //System.out.println("Added to Compressor: " + line);
                            compStr.add(line);
                            break;
                    }
                }
            }
        }

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

    /**
     * formats a string input based on specific formating `name` indicates a
     * grouping `
     *
     * @param input
     * @return
     */
    public String formatString(String input) {
        String output;
        if (input.startsWith("`")) {
            // Make sure the follow character is not a % indicating a variable name
            if (input.charAt(1) != '%') {
                output = "`gn" + input.substring(1, input.length() - 1);

            } else {
                output = input;
            }
        } else if (input.isEmpty()) {
            //System.out.println("Empty line");
            output = null;

        } else if (input.startsWith("\t")) {
            //System.out.println("Tab line");
            if (input.length() > 2) {
                input = input.substring(1);
                output = formatString(input);
            } else {
                output = null;
            }

        } else {
            output = input;
        }

        return output;

    }

    public void updatePowerIP() {
        String ip = mb.getPowerScoutIPIndex(_ComboBox_PowerMeter.getSelectedIndex());
        _TF_PowerScoutIP.setText(ip);
        String s = _ComboBox_PowerMeter.getSelectedItem() + " IP - " + ip;
        _Label_PowerScout.setText(s);
    }

    public void updatePowerIP(String ip) {

        _TF_PowerScoutIP.setText(ip);
        String s = _ComboBox_PowerMeter.getSelectedItem() + " IP - " + ip;
        _Label_PowerScout.setText(s);
    }

    public void updateSingleIP() {
        String ip = mb.getSingleLoadIPIndex(_ComboBox_SingleMeter.getSelectedIndex());
        _TF_SingleLoadIP.setText(ip);
        String s = _ComboBox_SingleMeter.getSelectedItem() + " IP - " + ip;
        _Label_Single.setText(s);

    }

    public void updateSingleIP(String ip) {

        _TF_SingleLoadIP.setText(ip);
        String s = _ComboBox_SingleMeter.getSelectedItem() + " IP - " + ip;
        _Label_Single.setText(s);

    }

    public void updatePanels() {

        // Display the right panels
        int powerIndex = _ComboBox_PowerMeter.getSelectedIndex();
        int singleIndex = _ComboBox_SingleMeter.getSelectedIndex();

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        // Constraints               
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1; // No space between bottom and below row?        
        c.gridx = c.gridy = 0;

        _Panel_PowerScout.removeAll();
        _Panel_PowerScout.setLayout(gbl);
        for (int i = 0; i < 8; i++) {
            _Panel_PowerScout.add(powerScoutPanels[powerIndex].get(i), c);
            if (i == 3) {
                c.gridy = 0;
                c.gridx += 1;
            } else {
                c.gridy += 1;
            }
        }

        _Panel_SingleLoads.removeAll();
        _Panel_SingleLoads.setLayout(gbl);
        _Panel_SingleLoads.add(singleLoadPanels.get(singleIndex), c);

        _Panel_PowerScout.revalidate();
        _Panel_SingleLoads.revalidate();
        _Panel_PowerScout.repaint();
        _Panel_SingleLoads.repaint();
    }

    public void loadModels() {

        mb.updateModel();
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 8; i++) {
                powerScoutPanels[j].get(i).updateModelList(mb.getCm(), mb.getRemovedItems());
            }
        }

        for (int i = 0; i < 15; i++) {
            singleLoadPanels.get(i).updateModelList(mb.getCm(), mb.getRemovedItems());

        }

    }

    public void loadSettings() {
        int meterIndex, slaveIndex, registerIndex;
        String key;
        for (Sensor sensor : mb.getItems().values()) {

            if (sensor.isUsed()) {
                meterIndex = sensor.getMeter();
                slaveIndex = sensor.getSlave();
                registerIndex = sensor.getRegister();
                key = sensor.getKey();
                //System.out.println("Sensor -> " + sensor);
                if (sensor.isPowerScout()) {
                    powerScoutPanels[meterIndex].get(slaveIndex).loadSensor(registerIndex, key);
                } else {
                    singleLoadPanels.get(meterIndex).loadSensor(registerIndex, key);
                }

            }
        }

    }

    public void itemUsed(String key, boolean used, int slave, int register, boolean powerScout) {
        //System.out.println("Key: " + key);

        //System.out.println("Item used: " + key + ", slave: " + slave
        //        + ", register: " + register + ", powerScout: " + powerScout);
        if (!key.equals("No Selection")) {
            if (powerScout) {
                mb.updateKey(key, used, _ComboBox_PowerMeter.getSelectedIndex(), slave, register, powerScout);
            } else {
                mb.updateKey(key, used, (mb.getNumPowerScouts() + _ComboBox_SingleMeter.getSelectedIndex()), slave, register, powerScout);
            }
        } else if (key.equals("Remove Item")) {
            mb.removeKey(key);
        } else {
            //System.out.println("item used empty else, key: " + key);
        }

        loadModels();
        mf.updateModbusSettings(mb);

    }

    public void changeTableType(int slave, int type, String a, String b) {

        int table = _ComboBox_PowerMeter.getSelectedIndex();
        mb.updateTableType(table, slave, type);
        if (!a.equals("No Selection")) {
            mb.removeKey(a);
        }
        if (!b.equals("No Selection")) {
            mb.removeKey(b);
        }
        updatePanels();
        loadModels();

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
        _Panel_PowerScout = new javax.swing.JPanel();
        _Panel_SingleLoads = new javax.swing.JPanel();
        _ComboBox_PowerMeter = new javax.swing.JComboBox();
        _Label_PowerScout = new javax.swing.JLabel();
        _Label_NumPowerScouts = new javax.swing.JLabel();
        _FTF_NumPowerScouts = new javax.swing.JFormattedTextField();
        _Label_Title = new javax.swing.JLabel();
        _Label_SingleIP = new javax.swing.JLabel();
        _ComboBox_SingleMeter = new javax.swing.JComboBox();
        _FTF_NumSingleLoads = new javax.swing.JFormattedTextField();
        _Label_Single = new javax.swing.JLabel();
        _Label_NumSingle = new javax.swing.JLabel();
        _Label_PowerScoutIP = new javax.swing.JLabel();
        _Button_ClearMeterPower = new javax.swing.JButton();
        _TF_PowerScoutIP = new javax.swing.JTextField();
        _TF_SingleLoadIP = new javax.swing.JTextField();
        _Button_ClearMeterSingle = new javax.swing.JButton();
        _Panel_ImportXLS = new javax.swing.JPanel();
        _Button_LoadXls = new javax.swing.JButton();
        _Label_Loaded = new javax.swing.JLabel();
        _Button_CreateImports = new javax.swing.JButton();

        _FileChooser_IoFile.setApproveButtonText("Open");
        _FileChooser_IoFile.setApproveButtonToolTipText("Open a xls file");
        _FileChooser_IoFile.setCurrentDirectory(new java.io.File("C:\\Users\\EricGummerson\\Documents\\Background Creator Files"));
        _FileChooser_IoFile.setDialogTitle("Open a XLS File");
        _FileChooser_IoFile.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XLS files", "xls"));

        _Panel_PowerScout.setPreferredSize(new java.awt.Dimension(636, 480));

        javax.swing.GroupLayout _Panel_PowerScoutLayout = new javax.swing.GroupLayout(_Panel_PowerScout);
        _Panel_PowerScout.setLayout(_Panel_PowerScoutLayout);
        _Panel_PowerScoutLayout.setHorizontalGroup(
            _Panel_PowerScoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );
        _Panel_PowerScoutLayout.setVerticalGroup(
            _Panel_PowerScoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );

        _Panel_SingleLoads.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        _Panel_SingleLoads.setPreferredSize(new java.awt.Dimension(318, 480));

        javax.swing.GroupLayout _Panel_SingleLoadsLayout = new javax.swing.GroupLayout(_Panel_SingleLoads);
        _Panel_SingleLoads.setLayout(_Panel_SingleLoadsLayout);
        _Panel_SingleLoadsLayout.setHorizontalGroup(
            _Panel_SingleLoadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        _Panel_SingleLoadsLayout.setVerticalGroup(
            _Panel_SingleLoadsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 95, Short.MAX_VALUE)
        );

        _ComboBox_PowerMeter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_PowerMeter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Meter 1" }));
        _ComboBox_PowerMeter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_PowerMeterActionPerformed(evt);
            }
        });

        _Label_PowerScout.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_PowerScout.setText("Meter #1 IP - ");

        _Label_NumPowerScouts.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_NumPowerScouts.setText("Number of Powerscout 24's");

        _FTF_NumPowerScouts.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        _FTF_NumPowerScouts.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_NumPowerScouts.setText("1");
        _FTF_NumPowerScouts.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                _FTF_NumPowerScoutsPropertyChange(evt);
            }
        });

        _Label_Title.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        _Label_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Title.setText("Modbus Generator");

        _Label_SingleIP.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_SingleIP.setText("IP Address");

        _ComboBox_SingleMeter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_SingleMeter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Meter 2" }));
        _ComboBox_SingleMeter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_SingleMeterActionPerformed(evt);
            }
        });

        _FTF_NumSingleLoads.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        _FTF_NumSingleLoads.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_NumSingleLoads.setText("1");
        _FTF_NumSingleLoads.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                _FTF_NumSingleLoadsPropertyChange(evt);
            }
        });

        _Label_Single.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_Single.setText("Meter #2 IP - ");

        _Label_NumSingle.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_NumSingle.setText("Number of Single Load Meters");

        _Label_PowerScoutIP.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _Label_PowerScoutIP.setText("IP Address");

        _Button_ClearMeterPower.setText("Clear Meter");
        _Button_ClearMeterPower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_ClearMeterPowerActionPerformed(evt);
            }
        });

        _TF_PowerScoutIP.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _TF_PowerScoutIP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _TF_PowerScoutIP.setText("192.168.0.30");
        _TF_PowerScoutIP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _TF_PowerScoutIPFocusLost(evt);
            }
        });
        _TF_PowerScoutIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _TF_PowerScoutIPActionPerformed(evt);
            }
        });

        _TF_SingleLoadIP.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _TF_SingleLoadIP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _TF_SingleLoadIP.setText("192.168.0.30");
        _TF_SingleLoadIP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _TF_SingleLoadIPFocusLost(evt);
            }
        });
        _TF_SingleLoadIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _TF_SingleLoadIPActionPerformed(evt);
            }
        });

        _Button_ClearMeterSingle.setText("Clear Meter");
        _Button_ClearMeterSingle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_ClearMeterSingleActionPerformed(evt);
            }
        });

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

        _Button_CreateImports.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_CreateImports.setText("Create Imports");
        _Button_CreateImports.setEnabled(false);
        _Button_CreateImports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_CreateImportsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout _Panel_ImportXLSLayout = new javax.swing.GroupLayout(_Panel_ImportXLS);
        _Panel_ImportXLS.setLayout(_Panel_ImportXLSLayout);
        _Panel_ImportXLSLayout.setHorizontalGroup(
            _Panel_ImportXLSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_ImportXLSLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(_Panel_ImportXLSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(_Button_CreateImports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_Button_LoadXls, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                    .addComponent(_Label_Loaded, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46))
        );
        _Panel_ImportXLSLayout.setVerticalGroup(
            _Panel_ImportXLSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_ImportXLSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(_Button_LoadXls, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_Label_Loaded, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_Button_CreateImports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(_FTF_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(_ComboBox_PowerMeter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(_Label_PowerScoutIP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_TF_PowerScoutIP))
                            .addComponent(_Label_PowerScout, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(_Panel_PowerScout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_Button_ClearMeterPower, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_Panel_SingleLoads, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(_ComboBox_SingleMeter, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_SingleIP, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_TF_SingleLoadIP))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_Single, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(92, 92, 92))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(_Label_NumSingle, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(_FTF_NumSingleLoads, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(_Button_ClearMeterSingle, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_Panel_ImportXLS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(417, 417, 417)
                .addComponent(_Label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(_Label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_NumSingle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FTF_NumSingleLoads, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(_ComboBox_SingleMeter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_Label_SingleIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_TF_SingleLoadIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(_Label_Single, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_Button_ClearMeterSingle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(_Panel_SingleLoads, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(_Panel_ImportXLS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_TF_PowerScoutIP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(_Label_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(_FTF_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(_Label_PowerScoutIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_ComboBox_PowerMeter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Label_PowerScout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_Button_ClearMeterPower, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(_Panel_PowerScout, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _FTF_NumPowerScoutsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event__FTF_NumPowerScoutsPropertyChange

        int endIndex = mb.getNumPowerScouts();

        if (Integer.parseInt(_FTF_NumPowerScouts.getText()) > 10) {
            _FTF_NumPowerScouts.setText("10");
            mb.setNumPowerScouts(10);
        } else if (Integer.parseInt(_FTF_NumPowerScouts.getText()) < 1) {
            _FTF_NumPowerScouts.setText("1");
            mb.setNumPowerScouts(1);
        } else {
            mb.setNumPowerScouts(Math.abs(Integer.parseInt(_FTF_NumPowerScouts.getText())));
        }

        int startIndex = mb.getNumPowerScouts();

        for (int i = startIndex; i < endIndex; i++) {
            removePowerMeter(i);
        }

        String[] names = new String[mb.getNumPowerScouts()];
        for (int i = 0; i < names.length; i++) {
            names[i] = "Meter " + (i + 1);
        }

        _ComboBox_PowerMeter.setModel(new javax.swing.DefaultComboBoxModel(names));
        updatePowerIP();

        names = new String[mb.getNumSingleLoads()];
        for (int i = 0; i < mb.getNumSingleLoads(); i++) {
            names[i] = "Meter " + (mb.getNumPowerScouts() + (i + 1));
        }
        _ComboBox_SingleMeter.setModel(new javax.swing.DefaultComboBoxModel(names));
        updateSingleIP();

    }//GEN-LAST:event__FTF_NumPowerScoutsPropertyChange

    private void _FTF_NumSingleLoadsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event__FTF_NumSingleLoadsPropertyChange

        if (Integer.parseInt(_FTF_NumSingleLoads.getText()) > 15) {
            _FTF_NumSingleLoads.setText("15");
            mb.setNumSingleLoads(15);
        } else if (Integer.parseInt(_FTF_NumSingleLoads.getText()) < 1) {
            _FTF_NumSingleLoads.setText("1");
            mb.setNumSingleLoads(1);
        } else {
            mb.setNumSingleLoads(Math.abs(Integer.parseInt(_FTF_NumSingleLoads.getText())));
        }

        String[] names = new String[mb.getNumSingleLoads()];
        for (int i = 0; i < mb.getNumSingleLoads(); i++) {
            names[i] = "Meter " + (mb.getNumPowerScouts() + (i + 1));
        }
        _ComboBox_SingleMeter.setModel(new javax.swing.DefaultComboBoxModel(names));
        updateSingleIP();

    }//GEN-LAST:event__FTF_NumSingleLoadsPropertyChange

    private void _ComboBox_PowerMeterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_PowerMeterActionPerformed
        updatePowerIP();
        updatePanels();
        loadModels();
    }//GEN-LAST:event__ComboBox_PowerMeterActionPerformed

    private void _ComboBox_SingleMeterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_SingleMeterActionPerformed
        updateSingleIP();
        updatePanels();
        loadModels();
    }//GEN-LAST:event__ComboBox_SingleMeterActionPerformed

    private void _TF_PowerScoutIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__TF_PowerScoutIPActionPerformed

        String ip = _TF_PowerScoutIP.getText();
        int powerIndex = _ComboBox_PowerMeter.getSelectedIndex();
        if (ipValidator.validate(ip)) {
            mb.setPowerScoutIP(ip, powerIndex);
            updatePowerIP(ip);
        } else {
            updatePowerIP();
        }
        /*
         int singleIndex = _ComboBox_SingleMeters.getSelectedIndex();
         mb.setSingleLoadIP(_FTF_SingleLoadIP.getText(), singleIndex);
         updateSingleIP();*/
    }//GEN-LAST:event__TF_PowerScoutIPActionPerformed

    private void _TF_PowerScoutIPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__TF_PowerScoutIPFocusLost

        String ip = _TF_PowerScoutIP.getText();
        int powerIndex = _ComboBox_PowerMeter.getSelectedIndex();
        if (ipValidator.validate(ip)) {
            mb.setPowerScoutIP(ip, powerIndex);
            updatePowerIP(ip);
        } else {
            updatePowerIP();
        }

    }//GEN-LAST:event__TF_PowerScoutIPFocusLost

    private void _TF_SingleLoadIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__TF_SingleLoadIPActionPerformed
        String ip = _TF_SingleLoadIP.getText();
        int powerIndex = _ComboBox_SingleMeter.getSelectedIndex();
        if (ipValidator.validate(ip)) {
            mb.setSingleLoadIP(ip, powerIndex);
            updateSingleIP(ip);
        } else {
            updateSingleIP();
        }
    }//GEN-LAST:event__TF_SingleLoadIPActionPerformed

    private void _TF_SingleLoadIPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__TF_SingleLoadIPFocusLost
        String ip = _TF_SingleLoadIP.getText();
        int powerIndex = _ComboBox_SingleMeter.getSelectedIndex();
        if (ipValidator.validate(ip)) {
            mb.setSingleLoadIP(ip, powerIndex);
            updateSingleIP(ip);
        } else {

            updateSingleIP();
        }
    }//GEN-LAST:event__TF_SingleLoadIPFocusLost

    private void _Button_ClearMeterPowerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_ClearMeterPowerActionPerformed
        int meter = _ComboBox_PowerMeter.getSelectedIndex();
        removePowerMeter(meter);
    }//GEN-LAST:event__Button_ClearMeterPowerActionPerformed

    private void _Button_ClearMeterSingleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_ClearMeterSingleActionPerformed
        int meter = _ComboBox_SingleMeter.getSelectedIndex();
        removeSingleMeter(meter);
    }//GEN-LAST:event__Button_ClearMeterSingleActionPerformed

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

        _FileChooser_IoFile.setDialogTitle("Save Modbus XLS File");
        _FileChooser_IoFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        _FileChooser_IoFile.setDialogType(JFileChooser.SAVE_DIALOG);
        _FileChooser_IoFile.setApproveButtonText("Save Here");

        int returnVal = _FileChooser_IoFile.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = _FileChooser_IoFile.getSelectedFile();
            //System.out.println("File: " + file.getAbsolutePath());
            String filePath = file.getAbsolutePath();
            filePath = removeExtension(filePath);

            // -------------------- Daemons ---------------------------
            // Make the inital workbook for the daemon import
            Workbook wbd = new XSSFWorkbook();

            int rowNum = 0;
            Sheet sheet = wbd.createSheet("Modbus Daemons");

            String[] headers = {"daemon_name", "daemon_ip_address", "daemon_period",
                "daemon_active", "daemon_device_communication_status",
                "daemon_communication_status_data_tag_id",
                "daemon_port", "daemon_idletime", "daemon_station_id"
            };

            // Add the header row
            Row row = sheet.createRow(rowNum);
            // Create a cell and put a value in it.
            for (int j = 0; j < headers.length; j++) {
                Cell cell = row.createCell(j);

                // If the string is a number, write it as a number
                if (isStringNumeric(headers[j])) {
                    cell.setCellValue(Double.parseDouble(headers[j].replace("\"", "")));
                } else {
                    cell.setCellValue(headers[j]);
                }
            }

            rowNum++;

            List<String> ips = mb.activeMeters(mf.store.getStoreName());

            for (int i = 0; i < ips.size(); i++) {

                // Create a row and put some cells in it. Rows are 0 based.
                row = sheet.createRow(rowNum);
                // Create a cell and put a value in it.
                String[] vals = new String[headers.length];
                vals[0] = ips.get(i).split(",")[0];
                vals[1] = ips.get(i).split(",")[1];
                vals[2] = "15000";
                vals[3] = "1";
                vals[4] = "1";
                vals[5] = "2812";
                vals[6] = "502";
                vals[7] = "300";
                vals[8] = String.valueOf(mf.getStationId());

                for (int j = 0; j < headers.length; j++) {
                    Cell cell = row.createCell(j);

                    // If the string is a number, write it as a number
                    if (isStringNumeric(vals[j])) {
                        cell.setCellValue(Double.parseDouble(vals[j].replace("\"", "")));
                    } else {
                        cell.setCellValue(vals[j]);
                    }

                }

                rowNum++;

            }
            try (FileOutputStream fileOutd = new FileOutputStream(filePath + "-Daemons.xlsx")) {
                wbd.write(fileOutd);
                fileOutd.close();
                wbd.close();
            } catch (NumberFormatException | IOException e) {

                System.out.println("Fail writing modbus daemon settings to file: " + filePath);
            }

            // ------------------- Mappings ---------------------------
            Map<String, List<String[]>> meterMappings = writeOutModbusSettings(mf.store.getCs());

            rowNum = 0;
            for (Entry<String, List<String[]>> entry : meterMappings.entrySet()) {

                String newFP = filePath + "-" + entry.getKey() + "-Modbus.xlsx";

                List<String[]> list = entry.getValue();

                if (list.size() > 1) {

                    try (Workbook wb = new XSSFWorkbook()) {
                        List<String[]> newList = formatList(list);
                        rowNum = 0;
                        sheet = wb.createSheet("Modbus Settings");
                        for (String[] r : newList) {
                            // Create a row and put some cells in it. Rows are 0 based.
                            row = sheet.createRow(rowNum);
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
                        if (rowNum > 1) {
                            try (FileOutputStream fileOut = new FileOutputStream(newFP)) {
                                wb.write(fileOut);
                                fileOut.close();
                            } catch (Exception e) {

                            }
                        }
                    } catch (NumberFormatException | IOException e) {

                        System.out.println("Fail writing modbus settings for " + entry.getKey()
                                + " to file: " + filePath);
                    }
                }
            }

            // ------------ End of For loop on Mappings ----------------------
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event__Button_CreateImportsActionPerformed

    private List<String[]> formatList(List<String[]> list) {

        // Check positions for slave then register
        // slave     [2]
        // register  [4]
        Comparator<String[]> comp = (String[] a, String[] b) -> {

            if (a[0].equals("cycle_name")) {
                return -1;
            } else if (b[0].equals("cycle_name")) {
                return 1;
            }

            if (a[2].equals(b[2])) {
                // Slaves are the same now compare registers
                if (Integer.parseInt(a[4]) > Integer.parseInt(b[4])) {
                    return 1;
                } else {
                    return -1;
                }
            } else if (Integer.parseInt(a[2]) > Integer.parseInt(b[2])) {
                return 1;
            } else {
                return -1;
            }
        };

        Collections.sort(list, comp);

        // Now that the list is sorted we can organize the specific slaves with
        // concurrent registers to be combined
        List<String[]> newList = new ArrayList<>();
        String currReg = null;
        String[] curr = null;
        int cycleCounter = 1;
        for (String[] next : list) {

            if (next[0].equals("cycle_name") || next[5].equals("L[HINT16]")) {
                newList.add(next);
                if (curr != null) {
                    //curr[0] = "Cycle " + cycleCounter;
                    cycleCounter++;
                    newList.add(curr);
                }

                curr = null;

            } else {
                // Not headers
                if (curr == null) {
                    curr = next;
                    currReg = curr[4];
                } else {
                    // We have an existing current item, so check it
                    if (curr[2].equals(next[2])) {
                        // Slaves are the same now compare registers
                        if ((Integer.parseInt(next[4]) - Integer.parseInt(currReg)) == 1) {
                            // New register 1 more than previous one
                            curr[1] += "," + next[1];
                            currReg = next[4];

                        } else {
                            // Add current
                            //curr[0] = "Cycle " + cycleCounter;
                            cycleCounter++;
                            newList.add(curr);

                            curr = next;
                            currReg = next[4];
                        }

                    } else {
                        // New slave, add current and reset it
                        //curr[0] = "Cycle " + cycleCounter;
                        cycleCounter++;
                        newList.add(curr);

                        curr = next;
                        currReg = next[4];
                    }

                }
            }
        }
        return newList;
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

            wb.close();
            fs.close();

            mf.loadImportedIos(importedIOVariables, 3, stationID);

        } catch (Exception e) {
            System.out.println("Error reading excel file " + e.getMessage());
        }

    }

    public void removePowerMeter(int meter) {
        String[] removedItems;
        for (int i = 0; i < 8; i++) {
            removedItems = powerScoutPanels[meter].get(i).clearRegisters();
            for (String s : removedItems) {
                if (!s.equals("No Selection") && !s.equals("Remove Item")) {
                    mb.removeKey(s);
                }
            }
        }
        mb.clearKeys(meter);
        loadModels();
        mf.updateModbusSettings(mb);
    }

    public void removeSingleMeter(int meter) {
        String[] removedItems;

        removedItems = singleLoadPanels.get(meter).clearRegisters();
        for (String s : removedItems) {
            if (!s.equals("No Selection") && !s.equals("Remove Item")) {
                mb.removeKey(s);
            }
        }
        mb.clearSingleKeys(meter);
        loadModels();
        mf.updateModbusSettings(mb);
    }

    public Map<String, List<String[]>> writeOutModbusSettings(ControlSettings cs) {

        Map<String, List<String[]>> meterMapping = new HashMap<>();

        String[] headers = new String[]{"cycle_name", "cycle_inputs", "cycle_slave", "cycle_function",
            "cycle_address", "cycle_data_type", "cycle_response_wait_time", "cycle_type"
        };

        for (int i = 0; i < mb.getNumPowerScouts(); i++) {
            meterMapping.put("PowerScout " + i, new ArrayList<>());
            meterMapping.get("PowerScout " + i).add(headers);
        }
        for (int i = 0; i < mb.getNumSingleLoads(); i++) {
            meterMapping.put("SingleScout " + meterMapping.size(), new ArrayList<>());
            meterMapping.get("SingleScout " + (meterMapping.size() - 1)).add(headers);
        }

        String[] newString;

        int numsg, numcomp;

        Rack r;
        Sensor sensor;
        String rName, sgName;
        SuctionGroup sucG;
        String compName;
        String key, meterName;

        for (int i = 0; i < cs.getNumRacks(); i++) {
            // do all racks
            r = cs.getRacks().get(i);
            rName = r.getName();
            sgName = r.getSuctionGroupNameIndex(0);

            sucG = r.getSuctionGroupIndex(0);
            compName = sucG.getCompressorNameIndex(0);

            // RACKS
            // do all condenser     
            for (String s : rackStr) {
                newString = s.split(",");
                newString[0] = newString[0]
                        .replace("`%rackname`", rName)
                        .replace("`%sgname`", sgName)
                        .replace("`%compname`", compName);

                key = rName;
                if (newString[0].contains("Cond")) {
                    // Cond string
                    key += " " + "Condenser";
                }
                //System.out.println("Key: " + key + "|| revised string: " + newString[0]);

                sensor = mb.getSensorForKey(key);
                if (sensor != null && sensor.getSlave() != -1) {
                    newString[1] = newString[1].replace("`%io_id`", getIOForString(newString[0]));
                    newString[2] = newString[2].replace("`%slave_address`", String.valueOf(sensor.getSlave()));
                    if (sensor.isPowerScout()) {
                        meterName = "PowerScout " + sensor.getMeter();
                    } else {
                        meterName = "SingleScout " + sensor.getMeter();
                    }
                    meterMapping.get(meterName).add(newString);
                } else {
                    System.out.println("Did not add " + newString[0] + " because no sensor was linked");
                }

            }

            // SUCTION GROUPS
            numsg = r.getNumSuctionGroups();
            for (int sg = 0; sg < numsg; sg++) {
                sucG = r.getSuctionGroupIndex(sg);
                sgName = sucG.getName();

                // do all suction groups
                // COMPRESSORS
                numcomp = sucG.getNumCompressors();
                for (int nc = 0; nc < numcomp; nc++) {
                    compName = sucG.getCompressorNameIndex(nc);
                    // do all compressors
                    for (String s : compStr) {
                        newString = s.split(",");
                        newString[0] = newString[0]
                                .replace("`%rackname`", rName)
                                .replace("`%sgname`", sgName)
                                .replace("`%compname`", compName);

                        //System.out.println("COMP - New string: " + Arrays.toString(newString) + "\tFrom old string: " + s);
                        //System.out.println(": " + Arrays.toString(newString) + "\tFrom old string: " + s);
                        key = rName + " " + sgName + " " + compName;

                        sensor = mb.getSensorForKey(key);
                        if (sensor != null && sensor.getSlave() != -1) {
                            newString[1] = newString[1].replace("`%io_id`", getIOForString(newString[0]));
                            newString[2] = newString[2].replace("`%slave_address`", String.valueOf(sensor.getSlave()));
                            newString[4] = findRegisterValue(newString[4], sensor.getRegister());
                            if (sensor.isPowerScout()) {
                                meterName = "PowerScout " + sensor.getMeter();
                            } else {
                                meterName = "SingleScout " + sensor.getMeter();
                            }
                            meterMapping.get(meterName).add(newString);
                        } else {
                            System.out.println("Did not add " + newString[0] + " because no sensor was linked");
                        }

                    }
                }

            }
        }

        // Now that we have the list, we have to replace the 
        return meterMapping;

    }

    private String getIOForString(String variableName) {

        if (importedIOVariables.isEmpty()) {
            return "`%io_id`";
        } else {
            return String.valueOf(importedIOVariables.get(variableName));
        }
    }

    private String findRegisterValue(String regString, int register) {

        String[] items = regString.split(" ");

        //System.out.println("Reg: " + Arrays.toString(items));
        int newVal;
        if (items.length > 3) {
            newVal = Integer.parseInt(items[0]) + (register * Integer.parseInt(items[4]));
        } else {
            newVal = Integer.parseInt(items[0]) + register;
        }

        return String.valueOf(newVal);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_ClearMeterPower;
    private javax.swing.JButton _Button_ClearMeterSingle;
    private javax.swing.JButton _Button_CreateImports;
    private javax.swing.JButton _Button_LoadXls;
    private javax.swing.JComboBox _ComboBox_PowerMeter;
    private javax.swing.JComboBox _ComboBox_SingleMeter;
    private javax.swing.JFormattedTextField _FTF_NumPowerScouts;
    private javax.swing.JFormattedTextField _FTF_NumSingleLoads;
    private javax.swing.JFileChooser _FileChooser_IoFile;
    private javax.swing.JLabel _Label_Loaded;
    private javax.swing.JLabel _Label_NumPowerScouts;
    private javax.swing.JLabel _Label_NumSingle;
    private javax.swing.JLabel _Label_PowerScout;
    private javax.swing.JLabel _Label_PowerScoutIP;
    private javax.swing.JLabel _Label_Single;
    private javax.swing.JLabel _Label_SingleIP;
    private javax.swing.JLabel _Label_Title;
    private javax.swing.JPanel _Panel_ImportXLS;
    private javax.swing.JPanel _Panel_PowerScout;
    private javax.swing.JPanel _Panel_SingleLoads;
    private javax.swing.JTextField _TF_PowerScoutIP;
    private javax.swing.JTextField _TF_SingleLoadIP;
    // End of variables declaration//GEN-END:variables
}
