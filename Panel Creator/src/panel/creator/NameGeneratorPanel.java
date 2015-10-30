/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author EricGummerson
 */
public class NameGeneratorPanel extends javax.swing.JPanel {

    public MainFrame mf;
    private Scanner scan;
    private int comboBoxIndex;
    private ArrayList<String> storeStr;
    private ArrayList<String> rackStr;
    private ArrayList<String> condStr;
    private ArrayList<String> sgStr;
    private ArrayList<String> compStr;
    private ArrayList<String> sysStr;
    private ArrayList<String> extraStr;

    /**
     * Creates new form NameGeneratorPanel
     *
     * @param mf
     * @param store
     */
    public NameGeneratorPanel(MainFrame mf) {
        this.mf = mf;
        comboBoxIndex = 0;
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        _Panel_Rack = new javax.swing.JPanel();
        _Label_GroupName = new javax.swing.JLabel();
        _Button_Check = new javax.swing.JToggleButton();
        _Label_CheckStatus = new javax.swing.JLabel();
        _ScrollPane_CommandsRack = new javax.swing.JScrollPane();
        _TextArea_VarNames = new javax.swing.JTextArea();
        _ScrollPane_InfoRacks = new javax.swing.JScrollPane();
        _TextArea_Info = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        _ComboBox_Groups = new javax.swing.JComboBox();
        _Button_Save = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        jTextField1.setText("jTextField1");

        setMinimumSize(new java.awt.Dimension(972, 555));

        _Label_GroupName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Label_GroupName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_GroupName.setText("Group Name");

        _Button_Check.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_Check.setText("Check Formatting");
        _Button_Check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_CheckActionPerformed(evt);
            }
        });

        _Label_CheckStatus.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Label_CheckStatus.setText("Status: ");

        _TextArea_VarNames.setColumns(20);
        _TextArea_VarNames.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _TextArea_VarNames.setLineWrap(true);
        _TextArea_VarNames.setRows(5);
        _TextArea_VarNames.setWrapStyleWord(true);
        _ScrollPane_CommandsRack.setViewportView(_TextArea_VarNames);

        _TextArea_Info.setEditable(false);
        _TextArea_Info.setColumns(20);
        _TextArea_Info.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _TextArea_Info.setLineWrap(true);
        _TextArea_Info.setRows(5);
        _TextArea_Info.setWrapStyleWord(true);
        _ScrollPane_InfoRacks.setViewportView(_TextArea_Info);

        javax.swing.GroupLayout _Panel_RackLayout = new javax.swing.GroupLayout(_Panel_Rack);
        _Panel_Rack.setLayout(_Panel_RackLayout);
        _Panel_RackLayout.setHorizontalGroup(
            _Panel_RackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_RackLayout.createSequentialGroup()
                .addGroup(_Panel_RackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(_Panel_RackLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(_ScrollPane_InfoRacks, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, _Panel_RackLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(_Label_GroupName, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(_Panel_RackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_Label_CheckStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_Button_Check, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(_ScrollPane_CommandsRack, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addContainerGap())
        );
        _Panel_RackLayout.setVerticalGroup(
            _Panel_RackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_RackLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(_Panel_RackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_ScrollPane_CommandsRack)
                    .addGroup(_Panel_RackLayout.createSequentialGroup()
                        .addGroup(_Panel_RackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_GroupName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Button_Check, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_Label_CheckStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(_ScrollPane_InfoRacks, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("IO Variable Name Generator");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Grouping");

        _ComboBox_Groups.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _ComboBox_Groups.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Store", "Rack", "Condenser", "Suction Group", "Compressor", "System", "Other" }));
        _ComboBox_Groups.setToolTipText("Groups");
        _ComboBox_Groups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_GroupsActionPerformed(evt);
            }
        });

        _Button_Save.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _Button_Save.setText("Save All Variables");
        _Button_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_SaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(304, 304, 304)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(_Panel_Rack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_ComboBox_Groups, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(_Button_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(_Button_Save, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(_ComboBox_Groups, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(_Panel_Rack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _ComboBox_GroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_GroupsActionPerformed
        // TODO add your handling code here:
        // Parse the text field, save it
        parseVarNames();
        update();
        comboBoxIndex = _ComboBox_Groups.getSelectedIndex();
    }//GEN-LAST:event__ComboBox_GroupsActionPerformed

    private void _Button_CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_CheckActionPerformed
        // TODO add your handling code here:
        System.out.println("check formats");

    }//GEN-LAST:event__Button_CheckActionPerformed

    private void _Button_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_SaveActionPerformed
        // TODO add your handling code here:
        // Parse the fields
        parseVarNames();
        mf.updateVarNames(storeStr, rackStr, condStr, sgStr, compStr, sysStr, extraStr);
        
    }//GEN-LAST:event__Button_SaveActionPerformed

    public void loadStore(Store store){
        storeStr = store.getStoreStr();
        rackStr = store.getRackStr();
        condStr = store.getCondStr();
        sgStr = store.getSgStr();
        compStr = store.getCompStr();
        sysStr = store.getSysStr();
        extraStr = store.getExtraStr();
        update();
    }
    
    public void loadGroups() {

        storeStr = new ArrayList<>();
        rackStr = new ArrayList<>();
        condStr = new ArrayList<>();
        sgStr = new ArrayList<>();
        compStr = new ArrayList<>();
        sysStr = new ArrayList<>();
        extraStr = new ArrayList<>();

        String filename = "Default IO Variables.txt";
        try {
            scan = new Scanner(new FileReader(filename));

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
                        case "store":
                            //System.out.println("Added to Store: " + line);
                            storeStr.add(line);
                            break;
                        case "rack":
                            //System.out.println("Added to Rack: " + line);
                            rackStr.add(line);
                            break;
                        case "condenser":
                            //System.out.println("Added to Condenser: " + line);
                            condStr.add(line);
                            break;
                        case "suction group":
                            //System.out.println("Added to SuctionGroup: " + line);
                            sgStr.add(line);
                            break;                        
                        case "compressor":
                            //System.out.println("Added to Compressor: " + line);
                            compStr.add(line);
                            break;
                        case "system":
                            //System.out.println("Added to System: " + line);
                            sysStr.add(line);
                            break;
                        default:
                            //System.out.println("Unknown groupname, added to extra" + line);
                            extraStr.add(line);
                            break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + filename);
        } finally {
            // update the fields
            update();
            if (scan != null) {
                scan.close();
            }
            
            mf.updateVarNames(storeStr, rackStr, condStr, sgStr, compStr, sysStr, extraStr);
        }

    }

    /**
     * parse var names in the text field
     */
    public void parseVarNames() {
        String tfString = _TextArea_VarNames.getText();
        String line = "";
    
        scan = new Scanner(tfString);
        switch (comboBoxIndex) {
            case 0:
                storeStr.clear();
                break;
            case 1:
                rackStr.clear();
                break;
            case 2:
                condStr.clear();
                break;
            case 3:
                sgStr.clear();
                break;
            case 4:
                compStr.clear();                
                break;
            case 5:
                sysStr.clear();
                break;
            default:
                extraStr.clear();
                break;
        }

        while (scan.hasNextLine()) {
            line = scan.nextLine();
            line = formatVarString(line);
            switch (comboBoxIndex) {
                case 0:
                    //System.out.println("Added to Store: " + line);
                    storeStr.add(line);
                    break;
                case 1:
                    //System.out.println("Added to Rack: " + line);
                    rackStr.add(line);
                    break;
                case 2:
                    //System.out.println("Added to Condenser: " + line);
                    condStr.add(line);
                    break;
                case 3:
                    //System.out.println("Added to SuctionGroup: " + line);
                    sgStr.add(line);
                    break;
                case 4:
                    //System.out.println("Added to Compressor: " + line);
                    compStr.add(line);                    
                    break;
                case 5:
                    //System.out.println("Added to System: " + line);
                    sysStr.add(line);
                    break;
                default:
                    //System.out.println("Unknown groupname, added to extra" + line);
                    extraStr.add(line);
                    break;
            }

        }

    }

    /**
     * Updates the text fields
     */
    public void update() {
        String info = "";
        String vars = "";
        switch (_ComboBox_Groups.getSelectedIndex()) {
            case 0: // store
                info = getVarInfoStore();
                for (String s : storeStr) {
                    vars += s;
                    vars += "\n";
                }
                break;
            case 1:
                info = getVarInfoRack();
                for (String s : rackStr) {
                    vars += s;
                    vars += "\n";
                }
                break;
            case 2:
                info = getVarInfoCond();
                for (String s : condStr) {
                    vars += s;
                    vars += "\n";
                }
                break;
            case 3:
                info = getVarInfoSG();
                for (String s : sgStr) {
                    vars += s;
                    vars += "\n";
                }
                break;
            case 4:
                info = getVarInfoComp();
                for (String s : compStr) {
                    vars += s;
                    vars += "\n";
                }
                break;
            case 5:
                info = getVarInfoSys();
                for (String s : sysStr) {
                    vars += s;
                    vars += "\n";
                }
                break;
            case 6:
                info = getVarInfoExtra();
                for (String s : extraStr) {
                    vars += s;
                    vars += "\n";
                }
                break;
        }

        _Label_GroupName.setText(_ComboBox_Groups.getSelectedItem().toString());
        _TextArea_Info.setText(info);
        _TextArea_VarNames.setText(vars);
    }

    /**
     * formats a string input based on specific formating `name` indicates a
     * grouping `
     *
     * @param input
     * @return
     */
    public String formatVarString(String input) {
        String output;

        if (input.isEmpty()) {
            //System.out.println("Empty line");
            output = null;

        } else if (input.startsWith("\t")) {
            //System.out.println("Tab line");
            if (input.length() > 2) {
                input = input.substring(1);
                output = formatVarString(input);
            } else {
                output = null;
            }

        } else {
            output = input;
        }

        return output;

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

    
    public String getVarInfoStore(){
        String info = "";
        
        info += "Add store variables to your string by wrapping the variable name"
                + "in the grave character '`'. Ex.\nOutside Air Temp `%storename`"
                + "\n\nStore variables include:\n\n"
                + "%storename";        
        
        return info;       
        
    }
    
    
    public String getVarInfoRack(){
        String info = "";
        
        info += "Add rack variables to your string by wrapping the variable name"
                + "in the grave character '`'. Ex.\nRack Status `%rackname`"
                + "\n\nRack variables include:\n\n"
                + "%storename\n%rackname\n";        
        
        return info;       
        
    }
    
    
    
    public String getVarInfoCond(){
        String info = "";
        
        info += "Add condenser variables to your string by wrapping the variable name"
                + "in the grave character '`'. Ex.\nCondenser Fan `%rackname` `%fannum`"
                + "\n\nCondenser variables include:\n\n"
                + "%storename\n%rackname\n%fannum\n";        
        
        return info;      
        
    }
    
    public String getVarInfoSG(){
        String info = "";
        
        info += "Add suction group variables to your string by wrapping the variable name"
                + "in the grave character '`'. Ex.\nLiquid Pressure `%rackname` `%sgname`"
                + "\n\nCondenser variables include:\n\n"
                + "%storename\n%rackname\n%sgname\n";        
        
        return info;      
        
    }
    
    public String getVarInfoSys(){
        String info = "";
        
        info += "Add system variables to your string by wrapping the variable name"
                + "in the grave character '`'. Ex.\nSystem Status `%rackname` `%sysname`"
                + "\n\nSystem variables include:\n\n"
                + "%storename\n%rackname\n%sgname\n%sysname\n";        
        
        return info;      
        
    }
    
     public String getVarInfoComp(){
        String info = "";
        
        info += "Add compressor variables to your string by wrapping the variable name"
                + "in the grave character '`'. Ex.\nComp Status `%rackname` `%compname`"
                + "\n\nCompressor variables include:\n\n"
                + "%storename\n%rackname\n%sgname\n%compname\n";        
        
        return info;      
        
    }
     
     
     public String getVarInfoExtra(){
        String info = "";
        
        info += "Add variables to your string by wrapping the variable name"
                + "in the grave character '`'.\nAll variables in other groups can"
                + " be used in any group";
        
        return info;      
        
    }
    
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton _Button_Check;
    private javax.swing.JButton _Button_Save;
    private javax.swing.JComboBox _ComboBox_Groups;
    private javax.swing.JLabel _Label_CheckStatus;
    private javax.swing.JLabel _Label_GroupName;
    private javax.swing.JPanel _Panel_Rack;
    private javax.swing.JScrollPane _ScrollPane_CommandsRack;
    private javax.swing.JScrollPane _ScrollPane_InfoRacks;
    private javax.swing.JTextArea _TextArea_Info;
    private javax.swing.JTextArea _TextArea_VarNames;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
