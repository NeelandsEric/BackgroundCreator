/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;

/**
 *
 * @author EricGummerson
 */
public class MeterPanel extends javax.swing.JPanel implements java.io.Serializable {

    private static final long serialVersionUID = 672L;
    public ModbusPanel mp;
    public String name;
    public int slaveNumber;
    public int type;
    public ComboBoxModel[] cm;
    public boolean[] itemSelected;
    public String[] selectedItem;
    public boolean loading;

    /**
     * Creates new form MeterPanel
     *
     * @param mp
     * @param slaveNumber
     * @param type
     */
    public MeterPanel(ModbusPanel mp, int slaveNumber, int type) {
        initComponents();
        loading = true;

        this.mp = mp;
        this.slaveNumber = slaveNumber;
        this.name = "Slave " + (slaveNumber + 1);
        _Label_Slave1.setText(this.name);
        this.type = type;
        // 3 phase
        if (type != 1) {

            _ComboBox_Reg2.setEnabled(false);
            _ComboBox_Reg3.setEnabled(false);
        }
        cm = new ComboBoxModel[3];
        itemSelected = new boolean[]{false, false, false};
        selectedItem = new String[]{"No Selection", "No Selection", "No Selection"};
        loading = false;
    }

    public void updateModelList(DefaultListModel df) {

        loading = true;
        if (itemSelected[0]) {
            DefaultListModel df1 = new DefaultListModel();
            for (Object e : df.toArray()) {
                df1.addElement(e);
            }

            //System.out.println("[0] " + df1.get(1) + " replaced with " + selectedItem[0]);
            df1.set(1, selectedItem[0]);
            cm[0] = new ListAdapterComboBoxModel(df1);
            _ComboBox_Reg1.setModel(cm[0]);
            _ComboBox_Reg1.setSelectedIndex(1); // no selection option              
        } else {

            //System.out.println("[0] " + df.get(0) + "\t[1] " + df.get(1) + "\tSI [0]: " + selectedItem[0]);
            cm[0] = new ListAdapterComboBoxModel(df);
            _ComboBox_Reg1.setModel(cm[0]);
            _ComboBox_Reg1.setSelectedIndex(1); // no selection option
        }

        if (type == 1) {

            if (itemSelected[1]) {
                DefaultListModel df1 = new DefaultListModel();
                for (Object e : df.toArray()) {
                    df1.addElement(e);
                }

                //System.out.println("[1] " + df1.get(1) + " replaced with " + selectedItem[1]);
                df1.set(1, selectedItem[1]);
                cm[1] = new ListAdapterComboBoxModel(df1);
                _ComboBox_Reg2.setModel(cm[1]);
                _ComboBox_Reg2.setSelectedIndex(1); // no selection option            
            } else {

                //System.out.println("[0] " + df.get(0) + "\t[1] " + df.get(1) + "\tSI [1]: " + selectedItem[1]);
                cm[1] = new ListAdapterComboBoxModel(df);
                _ComboBox_Reg2.setModel(cm[1]);
                _ComboBox_Reg2.setSelectedIndex(1); // no selection option
            }

            if (itemSelected[2]) {
                DefaultListModel df1 = new DefaultListModel();
                for (Object e : df.toArray()) {
                    df1.addElement(e);
                }

                //System.out.println("[2] " + df1.get(1) + " replaced with " + selectedItem[2]);
                df1.set(1, selectedItem[2]);
                cm[2] = new ListAdapterComboBoxModel(df1);
                _ComboBox_Reg3.setModel(cm[2]);
                _ComboBox_Reg3.setSelectedIndex(1); // no selection option            
            } else {

                //System.out.println("[0] " + df.get(0) + "\t[1] " + df.get(1) + "\tSI [2]: " + selectedItem[2]);
                cm[2] = new ListAdapterComboBoxModel(df);
                _ComboBox_Reg3.setModel(cm[2]);
                _ComboBox_Reg3.setSelectedIndex(1); // no selection option
            }
        } else {

            DefaultListModel df1 = new DefaultListModel();
            df1.addElement("None");
            cm[1] = new ListAdapterComboBoxModel(df1);
            _ComboBox_Reg2.setModel(cm[1]);
            _ComboBox_Reg2.setSelectedIndex(0); // no selection option

            cm[2] = new ListAdapterComboBoxModel(df1);
            _ComboBox_Reg3.setModel(cm[2]);
            _ComboBox_Reg3.setSelectedIndex(0); // no selection option

        }

        loading = false;
    }

    public String getMeterName() {
        return name;
    }

    public void setMeterName(String name) {
        this.name = name;
        this._Label_Slave1.setText(name);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSlaveNumber() {
        return slaveNumber;
    }

    public void setSlaveNumber(int slaveNumber) {
        this.slaveNumber = slaveNumber;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        _Panel_Slave1 = new javax.swing.JPanel();
        _Label_Slave1 = new javax.swing.JLabel();
        _ComboBox_Slave1 = new javax.swing.JComboBox();
        _ComboBox_Reg1 = new javax.swing.JComboBox();
        _ComboBox_Reg2 = new javax.swing.JComboBox();
        _ComboBox_Reg3 = new javax.swing.JComboBox();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        _Panel_Slave1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        _Panel_Slave1.setPreferredSize(new java.awt.Dimension(318, 120));

        _Label_Slave1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        _Label_Slave1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Slave1.setText("Slave 1");

        _ComboBox_Slave1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _ComboBox_Slave1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3 Phase", "1 Phase" }));
        _ComboBox_Slave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_Slave1ActionPerformed(evt);
            }
        });

        _ComboBox_Reg1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        _ComboBox_Reg1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Empty" }));
        _ComboBox_Reg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_Reg1ActionPerformed(evt);
            }
        });

        _ComboBox_Reg2.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        _ComboBox_Reg2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Empty" }));
        _ComboBox_Reg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_Reg2ActionPerformed(evt);
            }
        });

        _ComboBox_Reg3.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        _ComboBox_Reg3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Empty" }));
        _ComboBox_Reg3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_Reg3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout _Panel_Slave1Layout = new javax.swing.GroupLayout(_Panel_Slave1);
        _Panel_Slave1.setLayout(_Panel_Slave1Layout);
        _Panel_Slave1Layout.setHorizontalGroup(
            _Panel_Slave1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_Slave1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_Slave1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Label_Slave1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(_ComboBox_Slave1, 0, 97, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(_Panel_Slave1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_Panel_Slave1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(_ComboBox_Reg2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(_ComboBox_Reg1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(_ComboBox_Reg3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        _Panel_Slave1Layout.setVerticalGroup(
            _Panel_Slave1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_Slave1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(_Label_Slave1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_ComboBox_Slave1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(_Panel_Slave1Layout.createSequentialGroup()
                .addComponent(_ComboBox_Reg1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_ComboBox_Reg2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_ComboBox_Reg3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        _Panel_Slave1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_ComboBox_Reg1, _ComboBox_Reg2, _ComboBox_Reg3});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_Panel_Slave1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(_Panel_Slave1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _ComboBox_Slave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_Slave1ActionPerformed
        // TODO add your handling code here:

        if (_ComboBox_Slave1.getSelectedIndex() == 0) {
            _ComboBox_Reg2.setEnabled(true);
            _ComboBox_Reg3.setEnabled(true);
            this.type = 1;
        } else {
            _ComboBox_Reg2.setEnabled(false);
            _ComboBox_Reg3.setEnabled(false);
            this.type = 2;
        }
    }//GEN-LAST:event__ComboBox_Slave1ActionPerformed

    private void _ComboBox_Reg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_Reg1ActionPerformed

        if (!loading) {
            String n = (String) _ComboBox_Reg1.getSelectedItem();
            if (n.equals("Remove Item")) {
                n = selectedItem[0];
                selectedItem[0] = "No Selection";
                itemSelected[0] = false;
            } else {
                selectedItem[0] = n;
                itemSelected[0] = true;
            }
            boolean b = _ComboBox_Reg1.getSelectedIndex() > 1;
            System.out.println("[0] New item selected -> " + n);

            mp.itemUsed(n, b, slaveNumber, 0);
            //String n 
            //mp.itemUsed(name, loading);
        }

    }//GEN-LAST:event__ComboBox_Reg1ActionPerformed

    private void _ComboBox_Reg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_Reg2ActionPerformed
        if (!loading) {
            String n = (String) _ComboBox_Reg2.getSelectedItem();
            if (n.equals("Remove Item")) {
                n = selectedItem[1];
                selectedItem[1] = "No Selection";
                itemSelected[1] = false;
            } else {
                selectedItem[1] = n;
                itemSelected[1] = true;
            }
            boolean b = _ComboBox_Reg2.getSelectedIndex() > 1;
            System.out.println("[1] New item selected -> " + n);

            mp.itemUsed(n, b, slaveNumber, 1);
            //String n 
            //mp.itemUsed(name, loading);
        }
    }//GEN-LAST:event__ComboBox_Reg2ActionPerformed

    private void _ComboBox_Reg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_Reg3ActionPerformed
        if (!loading) {
            String n = (String) _ComboBox_Reg3.getSelectedItem();
            if (n.equals("Remove Item")) {
                n = selectedItem[2];
                selectedItem[2] = "No Selection";
                itemSelected[2] = false;
            } else {
                selectedItem[2] = n;
                itemSelected[2] = true;
            }
            boolean b = _ComboBox_Reg3.getSelectedIndex() > 1;
            System.out.println("[2] New item selected -> " + n);

            mp.itemUsed(n, b, slaveNumber, 2);
            //String n 
            //mp.itemUsed(name, loading);
        }

    }//GEN-LAST:event__ComboBox_Reg3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox _ComboBox_Reg1;
    private javax.swing.JComboBox _ComboBox_Reg2;
    private javax.swing.JComboBox _ComboBox_Reg3;
    private javax.swing.JComboBox _ComboBox_Slave1;
    private javax.swing.JLabel _Label_Slave1;
    private javax.swing.JPanel _Panel_Slave1;
    private javax.swing.JComboBox jComboBox1;
    // End of variables declaration//GEN-END:variables
}
