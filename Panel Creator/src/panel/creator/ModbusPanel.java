package panel.creator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

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

        mb.setNumPowerScouts(1);
        powerScoutPanels = (ArrayList<MeterPanel>[]) new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            powerScoutPanels[i] = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                powerScoutPanels[i].add(new MeterPanel(this, j, 1, true));
            }
        }
        mb.setNumSingleLoads(1);
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
                System.out.println("Sensor -> " + sensor);
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
                mb.updateKey(key, used, _ComboBox_SingleMeter.getSelectedIndex(), slave, register, powerScout);
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

        _Panel_PowerScout = new javax.swing.JPanel();
        _ComboBox_PowerMeter = new javax.swing.JComboBox();
        _Panel_SingleLoads = new javax.swing.JPanel();
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

        _ComboBox_PowerMeter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_PowerMeter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Meter 1" }));
        _ComboBox_PowerMeter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_PowerMeterActionPerformed(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_Panel_SingleLoads, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(_Label_Single, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_ComboBox_SingleMeter, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_NumSingle, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(_FTF_NumSingleLoads, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 28, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_SingleIP, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_TF_SingleLoadIP)))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_Button_ClearMeterSingle, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                        .addComponent(_Panel_SingleLoads, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FTF_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Label_PowerScoutIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_TF_PowerScoutIP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_ComboBox_PowerMeter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Label_PowerScout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_Button_ClearMeterPower, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(_Panel_PowerScout, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _FTF_NumPowerScoutsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event__FTF_NumPowerScoutsPropertyChange
        // TODO add your handling code here:

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

        names = new String[mb.getNumSingleLoads()];
        for (int i = 0; i < mb.getNumSingleLoads(); i++) {
            names[i] = "Meter " + (mb.getNumPowerScouts() + (i + 1));
        }
        _ComboBox_SingleMeter.setModel(new javax.swing.DefaultComboBoxModel(names));

    }//GEN-LAST:event__FTF_NumPowerScoutsPropertyChange

    private void _FTF_NumSingleLoadsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event__FTF_NumSingleLoadsPropertyChange
        // TODO add your handling code here:

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
        // TODO add your handling code here:
        String ip = _TF_PowerScoutIP.getText();
        int powerIndex = _ComboBox_PowerMeter.getSelectedIndex();
        if (ipValidator.validate(ip)) {
            System.out.println("Validated " + ip);
            mb.setPowerScoutIP(ip, powerIndex);
            updatePowerIP(ip);
        } else {
            System.out.println("Couldnt validate " + ip);
            updatePowerIP();
        }
        /*
         int singleIndex = _ComboBox_SingleMeters.getSelectedIndex();
         mb.setSingleLoadIP(_FTF_SingleLoadIP.getText(), singleIndex);
         updateSingleIP();*/
    }//GEN-LAST:event__TF_PowerScoutIPActionPerformed

    private void _TF_PowerScoutIPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__TF_PowerScoutIPFocusLost
        // TODO add your handling code here:
        // TODO add your handling code here:
        String ip = _TF_PowerScoutIP.getText();
        int powerIndex = _ComboBox_PowerMeter.getSelectedIndex();
        if (ipValidator.validate(ip)) {
            System.out.println("Validated " + ip);
            mb.setPowerScoutIP(ip, powerIndex);
            updatePowerIP(ip);
        } else {
            System.out.println("Couldnt validate " + ip);
            updatePowerIP();
        }

    }//GEN-LAST:event__TF_PowerScoutIPFocusLost

    private void _TF_SingleLoadIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__TF_SingleLoadIPActionPerformed
        String ip = _TF_SingleLoadIP.getText();
        int powerIndex = _ComboBox_SingleMeter.getSelectedIndex();
        if (ipValidator.validate(ip)) {
            //System.out.println("Validated " + ip);
            mb.setSingleLoadIP(ip, powerIndex);
            updateSingleIP(ip);
        } else {
            //System.out.println("Couldnt validate " + ip);
            updateSingleIP();
        }
    }//GEN-LAST:event__TF_SingleLoadIPActionPerformed

    private void _TF_SingleLoadIPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__TF_SingleLoadIPFocusLost
        String ip = _TF_SingleLoadIP.getText();
        int powerIndex = _ComboBox_SingleMeter.getSelectedIndex();
        if (ipValidator.validate(ip)) {
            //System.out.println("Validated " + ip);
            mb.setSingleLoadIP(ip, powerIndex);
            updateSingleIP(ip);
        } else {
            //System.out.println("Couldnt validate " + ip);
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

        loadModels();
        mf.updateModbusSettings(mb);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_ClearMeterPower;
    private javax.swing.JButton _Button_ClearMeterSingle;
    private javax.swing.JComboBox _ComboBox_PowerMeter;
    private javax.swing.JComboBox _ComboBox_SingleMeter;
    private javax.swing.JFormattedTextField _FTF_NumPowerScouts;
    private javax.swing.JFormattedTextField _FTF_NumSingleLoads;
    private javax.swing.JLabel _Label_NumPowerScouts;
    private javax.swing.JLabel _Label_NumSingle;
    private javax.swing.JLabel _Label_PowerScout;
    private javax.swing.JLabel _Label_PowerScoutIP;
    private javax.swing.JLabel _Label_Single;
    private javax.swing.JLabel _Label_SingleIP;
    private javax.swing.JLabel _Label_Title;
    private javax.swing.JPanel _Panel_PowerScout;
    private javax.swing.JPanel _Panel_SingleLoads;
    private javax.swing.JTextField _TF_PowerScoutIP;
    private javax.swing.JTextField _TF_SingleLoadIP;
    // End of variables declaration//GEN-END:variables
}
