/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EricGummerson
 */
public class ControlPanel_VFD extends javax.swing.JFrame {

    public ControlsPanel cp;
    public SuctionGroup sgr;
    public ArrayList<Integer> vfdIndexs;
    public MyTableModel dm;

    /**
     * Creates new form ControlPanel_VFDs
     */
    public ControlPanel_VFD(ControlsPanel cp, SuctionGroup sgr) {
        this.sgr = sgr;
        this.cp = cp;
        vfdIndexs = sgr.getCompVFD();
        initComponents();
        buildConfig();
        
        _Table_VFDConfig.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                updateVFD();
            }
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

        jPanel1 = new javax.swing.JPanel();
        _Button_Return = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        _Table_VFDConfig = new javax.swing.JTable();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        _Button_Return.setText("Save and Close");
        _Button_Return.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_ReturnActionPerformed(evt);
            }
        });

        _Table_VFDConfig.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(_Table_VFDConfig);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(_Button_Return, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_Button_Return, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _Button_ReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_ReturnActionPerformed
        // TODO add your handling code here:
        updateVFD();        
        this.cp.returnVFDConfig(vfdIndexs);
    }//GEN-LAST:event__Button_ReturnActionPerformed

    private void buildConfig() {
        //_Panel_Compressors

        Object[] columnNames = {"Comp #", "Comp Name", "VFD Active"};
        Object[][] data = new Object[sgr.getNumCompressors()][3];
        for (int nc = 0; nc < sgr.getNumCompressors(); nc++) {
            data[nc][0] = (nc + 1);
            data[nc][1] = sgr.getCompressorNameIndex(nc);
            data[nc][2] = new Boolean(vfdIndexs.contains(nc));
        }

        dm = new MyTableModel(data, columnNames);
        _Table_VFDConfig.setModel(dm);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int x = 0; x < 2; x++){
            _Table_VFDConfig.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }

    }
    
    
    private void updateVFD(){
        
        vfdIndexs.clear();
        for(int i = 0; i < dm.getRowCount(); i++){
            if(((boolean) dm.getValueAt(i, 2))){
                vfdIndexs.add(i);
            }
        }
        
    }
    
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_Return;
    private javax.swing.JTable _Table_VFDConfig;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public class MyTableModel extends DefaultTableModel {

        public MyTableModel(Object [][] data, Object [] columnNames){
            super(data, columnNames);
        }
        
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class clazz = String.class;
            switch (columnIndex) {
                case 0:
                    clazz = Integer.class;
                    break;
                case 2:
                    clazz = Boolean.class;
                    break;
            }
            return clazz;
        }

    }

}