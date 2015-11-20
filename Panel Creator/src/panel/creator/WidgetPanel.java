/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author EricGummerson
 */
public class WidgetPanel extends javax.swing.JPanel {

    private ControlSettings cs;
    private boolean mouseActive;
    private MainFrame mf;
    private Map<String, Widget> widgetList;

    /**
     * Creates new form WidgetPanel
     *
     * @param cs
     */
    public WidgetPanel(MainFrame mf, ControlSettings cs) {
        this.mf = mf;
        this.cs = cs;
        this.mouseActive = false;
        this.widgetList = new HashMap<>();
        initComponents();
    }

    public void loadControlSettings(ControlSettings cs) {
        this.cs = cs;
        this.mouseActive = false;
        this.widgetList = new HashMap<>();
    }

    public ControlSettings getCs() {
        return cs;
    }

    public void setCs(ControlSettings cs) {
        this.cs = cs;
        updateDisplay();
    }
    
    public void updateDisplay(){
        
        String [] tabs = new String[cs.getNumRacks() + 2];
        tabs[0] = "Main";
        for(int i = 0; i < cs.getNumRacks(); i++){
            tabs[i+1] = cs.getRackName(i);
        }
        tabs[tabs.length-1] = "Loads";
        
        _ComboBox_DisplayPanel.setModel(new javax.swing.DefaultComboBoxModel(tabs));
        
        
    }

    public boolean isMouseActive() {
        return mouseActive;
    }

    public void setMouseActive(boolean mouseActive) {
        this.mouseActive = mouseActive;
    }
    
    public void returnClick(Point p){
        System.out.println("Widget click at " + p);
        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        _List_WidgetList = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        _ComboBox_DisplayPanel = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        _TextArea_WidgetExport = new javax.swing.JTextArea();
        _Button_Copy = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        _Button_EnableClicks = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setMinimumSize(new java.awt.Dimension(969, 544));

        _List_WidgetList.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _List_WidgetList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Widget 1" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        _List_WidgetList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(_List_WidgetList);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Widget Creator");

        _ComboBox_DisplayPanel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        _ComboBox_DisplayPanel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Main" }));

        _TextArea_WidgetExport.setColumns(20);
        _TextArea_WidgetExport.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        _TextArea_WidgetExport.setLineWrap(true);
        _TextArea_WidgetExport.setRows(5);
        _TextArea_WidgetExport.setWrapStyleWord(true);
        _TextArea_WidgetExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                _TextArea_WidgetExportMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(_TextArea_WidgetExport);

        _Button_Copy.setText("Copy");
        _Button_Copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_CopyActionPerformed(evt);
            }
        });

        jButton1.setText("TTTESTT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        _Button_EnableClicks.setText("Clicks");
        _Button_EnableClicks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_EnableClicksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(_Button_EnableClicks, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(146, 146, 146)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(_Button_Copy, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(_ComboBox_DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jButton1)))
                        .addGap(49, 49, 49)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Button_Copy, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Button_EnableClicks, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(54, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_ComboBox_DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(135, 135, 135))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _Button_CopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_CopyActionPerformed
        // TODO add your handling code here:
        Highlighter h = _TextArea_WidgetExport.getHighlighter();
        h.removeAllHighlights();
        int sel = _TextArea_WidgetExport.getText().length();
        if (sel > 0) {
            try {
                h.addHighlight(0, sel, DefaultHighlighter.DefaultPainter);
            } catch (BadLocationException ex) {
                System.out.println("Bad selection");
            }
        }
        StringSelection stringSelection = new StringSelection(_TextArea_WidgetExport.getText());
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }//GEN-LAST:event__Button_CopyActionPerformed

    private void _TextArea_WidgetExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event__TextArea_WidgetExportMouseClicked

        Highlighter h = _TextArea_WidgetExport.getHighlighter();
        h.removeAllHighlights();
    }//GEN-LAST:event__TextArea_WidgetExportMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Widget widget;
        ArrayList<String> results = new ArrayList<>();
        String dir = new File("").getAbsolutePath() + "\\widgets";
        getWidgetFiles(dir, results);

        for (String s : results) {
            String[] ss = s.split(",");
            //System.out.println("Variables for " + ss[0]);
            widget = new Widget(ss[0], readFile(ss[1]));
            widgetList.put(ss[0], widget);
        }
                
    }//GEN-LAST:event_jButton1ActionPerformed

    private void _Button_EnableClicksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_EnableClicksActionPerformed
        
        mouseActive = !mouseActive;
        mf.canClick(_ComboBox_DisplayPanel.getSelectedIndex(), mouseActive);
    }//GEN-LAST:event__Button_EnableClicksActionPerformed

    public void getWidgetFiles(String dirName, ArrayList<String> filePaths) {

        File directory = new File(dirName);

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                filePaths.add(file.getName() + "," + file.getAbsolutePath());                
            } else if (file.isDirectory()) {
                getWidgetFiles(file.getAbsolutePath(), filePaths);
            }
        }

    }

    /**
     * Reads a file and returns a list of strings which contain all the variable
     * names
     *
     * @param filename
     * @return List of variable names as an ArrayList of Strings
     */
    public ArrayList<String> readFile(String filename) {

        String line;
        ArrayList<String> vars = new ArrayList<>();
        int begin, end;
        try {
            Scanner scan = new Scanner(new FileReader(filename));

            while (scan.hasNextLine()) {
                line = scan.nextLine();
                while (line.contains("`%")) {

                    begin = line.indexOf("`%");
                    end = line.indexOf("%`") + 2;
                    vars.add(line.substring(begin, end));
                    //System.out.println("Variable added: " + line.substring(begin, end));
                    line = line.substring(end);

                }

            }

        } catch (Exception e) {

            System.out.println("Error trying to read " + filename);
        }

        System.out.println("Variables found: " + vars);

        return vars;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_Copy;
    private javax.swing.JButton _Button_EnableClicks;
    private javax.swing.JComboBox _ComboBox_DisplayPanel;
    private javax.swing.JList _List_WidgetList;
    private javax.swing.JTextArea _TextArea_WidgetExport;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
