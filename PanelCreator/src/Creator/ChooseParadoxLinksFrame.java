/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 *
 * @author EricGummerson
 */
public class ChooseParadoxLinksFrame extends javax.swing.JFrame {

    private TaskManagerPanel parentPanel;           // Return a list to this panel
    private Map<String, List> formattedIoNames;     // Cond `%rackname` links to a list -> Cond Rack A, Cond Rack B, etc
    private ParadoxKeyMap paradoxKeyMap;            // Paradox key map  key: 'Rack A/Cnd/COP2COT' value=2942
    private Map<String, String> paradoxLinkMap;  // Custom links, linking an IO string "Cond `%rackname` Outlet Temp" to the paradox name "~RackA\Cnd\OAT Measure"
    private Map<String, String> customMappings;

    /**
     * Creates new form ChooseParadoxLinksFrame
     */
    public ChooseParadoxLinksFrame(Map<String, List> formattedIoNames, 
            Map<String, String> knownParadoxLinks, ParadoxKeyMap paradoxKeyMap,
            Map<String, String> customMappings, TaskManagerPanel parentPanel) {

        initComponents();

        this.formattedIoNames = formattedIoNames;
        this.paradoxLinkMap = knownParadoxLinks;
        this.paradoxKeyMap = paradoxKeyMap;
        this.parentPanel = parentPanel;        
        if(customMappings == null){
            this.customMappings = new TreeMap<>();
        }else {
            this.customMappings = customMappings;
        }


        initalLoad();
        updateCurrentLinks();

        addChangeListener(editTextField, e -> editingField());
        addChangeListener(searchFieldParadox, e -> search());
        addChangeListener(searchFieldIO, e -> searchIO());

    }

    private void initalLoad() {

        // Load the first list
        DefaultListModel dm = new DefaultListModel();
        for (String item : paradoxLinkMap.keySet()) {
            dm.addElement(item);
        }
        ioNameList.setModel(dm);       
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        knownIONames = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ioNameList = new javax.swing.JList();
        knownIONames1 = new javax.swing.JLabel();
        editParadoxLink = new javax.swing.JButton();
        editTextField = new javax.swing.JTextField();
        knownIONames2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        possibleNamesList = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        currentLinksList = new javax.swing.JList();
        knownIONames3 = new javax.swing.JLabel();
        knownIONames4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        possibleParadoxList = new javax.swing.JList();
        addLink = new javax.swing.JButton();
        deleteParadoxLink = new javax.swing.JButton();
        saveNClose = new javax.swing.JButton();
        saveParadoxLink = new javax.swing.JButton();
        searchFieldParadox = new javax.swing.JTextField();
        knownIONames5 = new javax.swing.JLabel();
        knownIONames6 = new javax.swing.JLabel();
        searchFieldIO = new javax.swing.JTextField();
        deleteParadoxLink1 = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Paradox Linker");
        setResizable(false);

        knownIONames.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        knownIONames.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        knownIONames.setText("Known IO Names");

        ioNameList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ioNameList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "None" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        ioNameList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ioNameList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ioNameListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ioNameList);

        knownIONames1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        knownIONames1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        knownIONames1.setText("Paradox Link");

        editParadoxLink.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        editParadoxLink.setText("Edit");
        editParadoxLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editParadoxLinkActionPerformed(evt);
            }
        });

        editTextField.setEditable(false);
        editTextField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        knownIONames2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        knownIONames2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        knownIONames2.setText("IO Keys for ");

        possibleNamesList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        possibleNamesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "None" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        possibleNamesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(possibleNamesList);

        currentLinksList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        currentLinksList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "None" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        currentLinksList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        currentLinksList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                currentLinksListValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(currentLinksList);

        knownIONames3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        knownIONames3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        knownIONames3.setText("Current Links");

        knownIONames4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        knownIONames4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        knownIONames4.setText("Paradox Keys Found");

        possibleParadoxList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        possibleParadoxList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "None" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        possibleParadoxList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(possibleParadoxList);

        addLink.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        addLink.setText("Add Link");
        addLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLinkActionPerformed(evt);
            }
        });

        deleteParadoxLink.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        deleteParadoxLink.setText("Delete Link");
        deleteParadoxLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteParadoxLinkActionPerformed(evt);
            }
        });

        saveNClose.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        saveNClose.setText("Save & Close");
        saveNClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveNCloseActionPerformed(evt);
            }
        });

        saveParadoxLink.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        saveParadoxLink.setText("Save");
        saveParadoxLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveParadoxLinkActionPerformed(evt);
            }
        });

        searchFieldParadox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        knownIONames5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        knownIONames5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        knownIONames5.setText("Search For paradox Keys");

        knownIONames6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        knownIONames6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        knownIONames6.setText("Search For IO Names");

        searchFieldIO.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        deleteParadoxLink1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        deleteParadoxLink1.setText("Delete All");
        deleteParadoxLink1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteParadoxLink1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(knownIONames, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3)
                    .addComponent(knownIONames2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(knownIONames6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchFieldIO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(knownIONames1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editTextField)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(editParadoxLink, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveParadoxLink, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                    .addComponent(searchFieldParadox)
                    .addComponent(knownIONames5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(knownIONames4, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addLink, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(deleteParadoxLink, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteParadoxLink1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(saveNClose, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4)
                    .addComponent(knownIONames3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(knownIONames3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteParadoxLink, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveNClose, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteParadoxLink1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(knownIONames, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(knownIONames1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(editTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(editParadoxLink, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(saveParadoxLink, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(knownIONames5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchFieldParadox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(knownIONames6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchFieldIO, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(knownIONames2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addLink, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(knownIONames4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                            .addComponent(jScrollPane5))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ioNameListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ioNameListValueChanged
        // TODO add your handling code here:
        if (!evt.getValueIsAdjusting()) {

            if (!ioNameList.isSelectionEmpty()) {
                // Load Paradox Link
                loadParadoxLink();

                // Load Io Names Following
                //loadIONames();
                searchFieldIO.setText(ioNameList.getSelectedValue().toString());
            } else {
                editTextField.setText("");
                editTextField.setEditable(false);
                saveParadoxLink.setEnabled(false);
                editParadoxLink.setEnabled(false);
            }

        }
    }//GEN-LAST:event_ioNameListValueChanged

    private void currentLinksListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_currentLinksListValueChanged
        // TODO add your handling code here:
        if (!evt.getValueIsAdjusting()) {
            if (currentLinksList.isSelectionEmpty()) {
                deleteParadoxLink.setEnabled(false);
            } else {
                deleteParadoxLink.setEnabled(true);
            }
        }
    }//GEN-LAST:event_currentLinksListValueChanged

    private void saveNCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveNCloseActionPerformed
        // TODO add your handling code here:
        Map<String,Integer> retMappings = new TreeMap<>();
        
        for(Entry<String, String> entry: customMappings.entrySet()){
            retMappings.put(entry.getKey(), paradoxKeyMap.get(entry.getValue()));
        }
        
        parentPanel.returnParadoxLinks(customMappings, retMappings);
    }//GEN-LAST:event_saveNCloseActionPerformed

    private void editParadoxLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editParadoxLinkActionPerformed
        // TODO add your handling code here:
        editTextField.setEditable(true);
    }//GEN-LAST:event_editParadoxLinkActionPerformed

    private void saveParadoxLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveParadoxLinkActionPerformed
        // TODO add your handling code here:
        if (!ioNameList.isSelectionEmpty()) {
            if (paradoxLinkMap.containsKey((String) ioNameList.getSelectedValue())) {
                paradoxLinkMap.replace((String) ioNameList.getSelectedValue(), editTextField.getText());
            } else {
                System.out.println("Couldnt find the key: " + (String) ioNameList.getSelectedValue());
            }
        } else {
            System.out.println("Cant save when no element selected");
        }
    }//GEN-LAST:event_saveParadoxLinkActionPerformed

    private void addLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLinkActionPerformed
        // TODO add your handling code here:

        // Both lists have an element selected
        if (!possibleParadoxList.isSelectionEmpty() && !possibleNamesList.isSelectionEmpty()) {

            String ioName = (String) possibleNamesList.getSelectedValue();
            String paradoxName = (String) possibleParadoxList.getSelectedValue();
            paradoxName = paradoxName.substring(0, paradoxName.indexOf("||"));           

            if (customMappings.containsKey(ioName)) {

                String val = customMappings.get(ioName);
                // If so prompt to see if they want to re-add the data
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(this,
                        "IO " + ioName + " is already mapped to " + val + ". Would you like to replace it?", "Replace Mapping", dialogButton);

                if (dialogResult != 0) {
                    return;
                }

            } else if (customMappings.containsValue(paradoxName)) {

                String key = "";
                for (Entry<String, String> entry : customMappings.entrySet()) {
                    if (entry.getValue().equals(paradoxName)) {
                        key = entry.getKey();
                    }
                }

                // If so prompt to see if they want to re-add the data
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(this,
                        "IO " + ioName + " is already mapped to " + key + ". Would you like to replace it?", "Replace Mapping", dialogButton);

                if (dialogResult != 0) {
                    return;
                }
            }

            customMappings.put(ioName, paradoxName);
            updateCurrentLinks();
            
            // Shift the selected index down for each list
            int interval = (possibleParadoxList.getSelectedIndex() + 1) >= possibleParadoxList.getModel().getSize() ? 0: possibleParadoxList.getSelectedIndex() + 1;            
            possibleParadoxList.setSelectedIndex(interval);
            possibleParadoxList.ensureIndexIsVisible(interval);
             
            interval = (possibleNamesList.getSelectedIndex() + 1) >= possibleNamesList.getModel().getSize() ? 0: possibleNamesList.getSelectedIndex() + 1;    
            possibleNamesList.setSelectedIndex(interval);
            possibleNamesList.ensureIndexIsVisible(interval);
            
            
            
        }
        
        

    }//GEN-LAST:event_addLinkActionPerformed

    private void deleteParadoxLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteParadoxLinkActionPerformed
        
        if(!currentLinksList.isSelectionEmpty()){
            String key = currentLinksList.getSelectedValue().toString().split(" --> ")[0];
            System.out.println("Removed key: " + key + "\tValue: " + customMappings.remove(key));            
            updateCurrentLinks();
        }
    }//GEN-LAST:event_deleteParadoxLinkActionPerformed

    private void deleteParadoxLink1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteParadoxLink1ActionPerformed
        customMappings.clear();        
        updateCurrentLinks();
    }//GEN-LAST:event_deleteParadoxLink1ActionPerformed

    private void editingField() {

        if (!editTextField.getText().equals("")) {
            // Check similar paradox fields
            search(editTextField.getText());

        }

    }
    
    private void searchIO() {
        
        String searchItem = searchFieldIO.getText();
        if (!searchItem.equals("")) {
            // Check similar paradox fields
            DefaultListModel dm = new DefaultListModel();
            for(Entry<String, List> entry: formattedIoNames.entrySet()){
                if(entry.getKey().contains(searchItem)){
                    for(Object val: entry.getValue()){
                        dm.addElement(val.toString());
                    }
                }
            }
            
            ArrayList<String> searchRetItems = paradoxKeyMap.search(searchItem);

            possibleNamesList.setModel(dm);

        }
    }

    private void search() {
        
        String searchItem = searchFieldParadox.getText();
        if (!searchItem.equals("")) {
            // Check similar paradox fields
            ArrayList<String> searchRetItems = paradoxKeyMap.search(searchItem);

            DefaultListModel dm = new DefaultListModel();
            for (String item : searchRetItems) {
                dm.addElement(item);
            }

            possibleParadoxList.setModel(dm);

        }
    }

    private void search(String searchItem) {

        
        if (!searchItem.equals("")) {
            // Check similar paradox fields
            ArrayList<String> searchRetItems = paradoxKeyMap.search(searchItem);

            DefaultListModel dm = new DefaultListModel();
            for (String item : searchRetItems) {
                dm.addElement(item);
            }

            possibleParadoxList.setModel(dm);

        }
    }

    private void loadParadoxLink() {

        if (!ioNameList.isSelectionEmpty()) {
            String io = ioNameList.getSelectedValue().toString();

            if (paradoxLinkMap.containsKey(io)) {

                editTextField.setText(paradoxLinkMap.get(io));
                editTextField.setEditable(false);
                editParadoxLink.setEnabled(true);
                saveParadoxLink.setEnabled(true);

            } else {
                System.out.println("No links found for " + io);
            }

        }

    }
    

    private void updateCurrentLinks() {
        DefaultListModel dm = new DefaultListModel();
        for (Entry<String, String> entry : customMappings.entrySet()) {
            dm.addElement(entry.getKey() + " --> " + entry.getValue());
        }
        currentLinksList.setModel(dm);
    }

    /**
     * Installs a listener to receive notification when the text of any
     * {@code JTextComponent} is changed. Internally, it installs a
     * {@link DocumentListener} on the text component's {@link Document}, and a
     * {@link PropertyChangeListener} on the text component to detect if the
     * {@code Document} itself is replaced.
     *
     * @param text any text component, such as a {@link JTextField} or
     * {@link JTextArea}
     * @param changeListener a listener to receieve {@link ChangeEvent}s when
     * the text is changed; the source object for the events will be the text
     * component
     * @throws NullPointerException if either parameter is null
     */
    public static void addChangeListener(JTextComponent text, ChangeListener changeListener) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(changeListener);
        DocumentListener dl = new DocumentListener() {
            private int lastChange = 0, lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange) {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }
        };
        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document d1 = (Document) e.getOldValue();
            Document d2 = (Document) e.getNewValue();
            if (d1 != null) {
                d1.removeDocumentListener(dl);
            }
            if (d2 != null) {
                d2.addDocumentListener(dl);
            }
            dl.changedUpdate(null);
        });
        Document d = text.getDocument();
        if (d != null) {
            d.addDocumentListener(dl);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addLink;
    private javax.swing.JList currentLinksList;
    private javax.swing.JButton deleteParadoxLink;
    private javax.swing.JButton deleteParadoxLink1;
    private javax.swing.JButton editParadoxLink;
    private javax.swing.JTextField editTextField;
    private javax.swing.JList ioNameList;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel knownIONames;
    private javax.swing.JLabel knownIONames1;
    private javax.swing.JLabel knownIONames2;
    private javax.swing.JLabel knownIONames3;
    private javax.swing.JLabel knownIONames4;
    private javax.swing.JLabel knownIONames5;
    private javax.swing.JLabel knownIONames6;
    private javax.swing.JList possibleNamesList;
    private javax.swing.JList possibleParadoxList;
    private javax.swing.JButton saveNClose;
    private javax.swing.JButton saveParadoxLink;
    private javax.swing.JTextField searchFieldIO;
    private javax.swing.JTextField searchFieldParadox;
    // End of variables declaration//GEN-END:variables
}
