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

        System.out.println("ps: " + mb.getNumPowerScouts());
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
        _ComboBox_Meters.setModel(new javax.swing.DefaultComboBoxModel(names));

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
        _ComboBox_SingleMeters.setModel(new javax.swing.DefaultComboBoxModel(names));

        this.updatePowerIP();
        this.updateSingleIP();

        this.updatePanels();
        this.loadModels();
        this.loadSettings();

    }

    public void updatePowerIP() {
        String ip = mb.getPowerScoutIPIndex(_ComboBox_Meters.getSelectedIndex());
        _FTF_PowerScoutIP.setText(ip);
        String s = _ComboBox_Meters.getSelectedItem() + " IP - " + ip;
        _Label_PowerScout.setText(s);
    }

    public void updateSingleIP() {
        String ip = mb.getSingleLoadIPIndex(_ComboBox_SingleMeters.getSelectedIndex());
        _FTF_SingleLoadIP.setText(ip);
        String s = _ComboBox_SingleMeters.getSelectedItem() + " IP - " + ip;
        _Label_Single.setText(s);

    }

    public void updatePanels() {

        // Display the right panels
        int powerIndex = _ComboBox_Meters.getSelectedIndex();
        int singleIndex = _ComboBox_SingleMeters.getSelectedIndex();

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

        for (int i = 0; i <  15; i++) {
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
                if(sensor.isPowerScout()){
                    powerScoutPanels[meterIndex].get(slaveIndex).loadSensor(registerIndex, key);
                }else {
                    singleLoadPanels.get(meterIndex).loadSensor(registerIndex, key);
                }

            }
        }

    }

    public void itemUsed(String key, boolean used, int slave, int register, boolean powerScout) {
        //System.out.println("Key: " + key);

        System.out.println("Item used: " + key + ", slave: " + slave + 
                        ", register: " + register + ", powerScout: " + powerScout);
        if (!key.equals("No Selection")) {
            if(powerScout){
                mb.updateKey(key, used, _ComboBox_Meters.getSelectedIndex(), slave, register, powerScout);
            }else {
                mb.updateKey(key, used, _ComboBox_SingleMeters.getSelectedIndex(), slave, register, powerScout);
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

        int table = _ComboBox_Meters.getSelectedIndex();
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
        _ComboBox_Meters = new javax.swing.JComboBox();
        _Panel_SingleLoads = new javax.swing.JPanel();
        _Label_PowerScout = new javax.swing.JLabel();
        _Label_NumPowerScouts = new javax.swing.JLabel();
        _FTF_NumPowerScouts = new javax.swing.JFormattedTextField();
        _Label_Title = new javax.swing.JLabel();
        _Label_SingleIP = new javax.swing.JLabel();
        _ComboBox_SingleMeters = new javax.swing.JComboBox();
        _FTF_NumSingleLoads = new javax.swing.JFormattedTextField();
        _Label_Single = new javax.swing.JLabel();
        _Label_NumSingle = new javax.swing.JLabel();
        _Label_PowerScoutIP = new javax.swing.JLabel();
        _FTF_PowerScoutIP = new javax.swing.JFormattedTextField(new IPAddressFormatter());
        _FTF_SingleLoadIP = new javax.swing.JFormattedTextField(new IPAddressFormatter());

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

        _ComboBox_Meters.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_Meters.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Meter 1" }));
        _ComboBox_Meters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_MetersActionPerformed(evt);
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
            .addGap(0, 89, Short.MAX_VALUE)
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

        _ComboBox_SingleMeters.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_SingleMeters.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Meter 2" }));
        _ComboBox_SingleMeters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_SingleMetersActionPerformed(evt);
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

        _FTF_PowerScoutIP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_PowerScoutIP.setText("192.168.0.20");
        _FTF_PowerScoutIP.setToolTipText("IP Address");
        _FTF_PowerScoutIP.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                _FTF_PowerScoutIPPropertyChange(evt);
            }
        });

        _FTF_SingleLoadIP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FTF_SingleLoadIP.setText("192.168.0.30");
        _FTF_SingleLoadIP.setToolTipText("IP Address");
        _FTF_SingleLoadIP.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                _FTF_SingleLoadIPPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Panel_PowerScout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(_ComboBox_Meters, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(_Label_PowerScout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(_Label_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(_FTF_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(_Label_PowerScoutIP, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(_FTF_PowerScoutIP, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Panel_SingleLoads, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(_Label_Single, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_ComboBox_SingleMeters, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_NumSingle, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(_FTF_NumSingleLoads, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_SingleIP, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_FTF_SingleLoadIP, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_Label_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_FTF_NumPowerScouts, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_Label_PowerScoutIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_FTF_PowerScoutIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_ComboBox_Meters, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(_Label_PowerScout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_NumSingle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FTF_NumSingleLoads, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(_ComboBox_SingleMeters, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_SingleIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FTF_SingleLoadIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addComponent(_Label_Single, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(_Panel_SingleLoads, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(_Panel_PowerScout, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _FTF_NumPowerScoutsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event__FTF_NumPowerScoutsPropertyChange
        // TODO add your handling code here:

        if (Integer.parseInt(_FTF_NumPowerScouts.getText()) > 10) {
            _FTF_NumPowerScouts.setText("10");
            mb.setNumPowerScouts(10);
        } else if (Integer.parseInt(_FTF_NumPowerScouts.getText()) < 1) {
            _FTF_NumPowerScouts.setText("1");
            mb.setNumPowerScouts(1);
        } else {
            mb.setNumPowerScouts(Math.abs(Integer.parseInt(_FTF_NumPowerScouts.getText())));
        }
        String[] names = new String[mb.getNumPowerScouts()];
        for (int i = 0; i < names.length; i++) {
            names[i] = "Meter " + (i + 1);
        }
        _ComboBox_Meters.setModel(new javax.swing.DefaultComboBoxModel(names));

        names = new String[mb.getNumSingleLoads()];
        for (int i = 0; i < mb.getNumSingleLoads(); i++) {
            names[i] = "Meter " + (mb.getNumPowerScouts() + (i + 1));
        }
        _ComboBox_SingleMeters.setModel(new javax.swing.DefaultComboBoxModel(names));

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
        _ComboBox_SingleMeters.setModel(new javax.swing.DefaultComboBoxModel(names));

    }//GEN-LAST:event__FTF_NumSingleLoadsPropertyChange

    private void _ComboBox_MetersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_MetersActionPerformed
        updatePowerIP();
        updatePanels();
        loadModels();
    }//GEN-LAST:event__ComboBox_MetersActionPerformed

    private void _ComboBox_SingleMetersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_SingleMetersActionPerformed
        updateSingleIP();
        updatePanels();
        loadModels();
    }//GEN-LAST:event__ComboBox_SingleMetersActionPerformed

    private void _FTF_PowerScoutIPPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event__FTF_PowerScoutIPPropertyChange
        // TODO add your handling code here:
        int powerIndex = _ComboBox_Meters.getSelectedIndex();
        mb.setPowerScoutIP(_FTF_PowerScoutIP.getText(), powerIndex);
        updatePowerIP();
    }//GEN-LAST:event__FTF_PowerScoutIPPropertyChange

    private void _FTF_SingleLoadIPPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event__FTF_SingleLoadIPPropertyChange
        // TODO add your handling code here:
        int singleIndex = _ComboBox_SingleMeters.getSelectedIndex();
        mb.setSingleLoadIP(_FTF_SingleLoadIP.getText(), singleIndex);
        updateSingleIP();
    }//GEN-LAST:event__FTF_SingleLoadIPPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox _ComboBox_Meters;
    private javax.swing.JComboBox _ComboBox_SingleMeters;
    private javax.swing.JFormattedTextField _FTF_NumPowerScouts;
    private javax.swing.JFormattedTextField _FTF_NumSingleLoads;
    private javax.swing.JFormattedTextField _FTF_PowerScoutIP;
    private javax.swing.JFormattedTextField _FTF_SingleLoadIP;
    private javax.swing.JLabel _Label_NumPowerScouts;
    private javax.swing.JLabel _Label_NumSingle;
    private javax.swing.JLabel _Label_PowerScout;
    private javax.swing.JLabel _Label_PowerScoutIP;
    private javax.swing.JLabel _Label_Single;
    private javax.swing.JLabel _Label_SingleIP;
    private javax.swing.JLabel _Label_Title;
    private javax.swing.JPanel _Panel_PowerScout;
    private javax.swing.JPanel _Panel_SingleLoads;
    // End of variables declaration//GEN-END:variables
}
