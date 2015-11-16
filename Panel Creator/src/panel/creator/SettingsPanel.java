package panel.creator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Settings frame controls the font, font size, font color, border style, border
 * color, as well as display sizes
 *
 * @author EricGummerson
 */
public class SettingsPanel extends javax.swing.JPanel implements ChangeListener {

    private DisplaySettings ds;
    private final MainFrame mf;
    /*
     public int displayWidth;
     public int displayHeight;
     public Font font;
     public Border border;
     public int borderSize;
     public Color color;
     */
    public String[] fonts;
    public String[] fontSizes;
    public GraphicsEnvironment ge;
    public boolean loading;

    /**
     * Creates new form SettingsFrame
     *
     * @param mf
     * @param ds
     */
    public SettingsPanel(MainFrame mf, DisplaySettings ds) {

        loading = true;
        initComponents();
        this.mf = mf;
        this.ds = ds;

        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fonts = ge.getAvailableFontFamilyNames();
        _ComboBox_Fonts.setModel(new javax.swing.DefaultComboBoxModel(fonts));

        fontSizes = new String[23];
        for (int i = 8; i < 31; i++) {
            fontSizes[i - 8] = Integer.toString(i);
        }
        _ComboBox_FontSize.setModel(new javax.swing.DefaultComboBoxModel(fontSizes));
        _ComboBox_Fonts.setSelectedIndex(11);
        _ComboBox_FontSize.setSelectedIndex(6);        

        _ColorChooser_Color.getSelectionModel().addChangeListener(this);
        ds.setColor(Color.WHITE);
        ds.setBorderTypeSel(0);
        ds.setBorderSizeSel(1);
        ds.setBorderSize(1);
        _ComboBox_Borders.setSelectedIndex(0);
        _ComboBox_BorderSize.setSelectedIndex(1);
        ds.setBorder(BorderFactory.createLineBorder(ds.getColor()));
        _Panel_BorderPanel.setBorder(ds.getBorder());
        
        loading = false;

    }

    /**
     * sets the dimensions of the frame
     *
     * @param d dimension
     */
    public void setDim(Dimension d) {
        int displayWidth = (int) d.getWidth();
        _FormattedTextField_Width.setText(String.valueOf(displayWidth));
        int displayHeight = (int) d.getHeight();
        _FormattedTextField_Height.setText(String.valueOf(displayHeight));

        ds.setDisplayWidth(displayWidth);
        ds.setDisplayHeight(displayHeight);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _Label_DisplayWidth = new javax.swing.JLabel();
        _Label_DisplayHeight = new javax.swing.JLabel();
        _Button_DefaultSettings = new javax.swing.JButton();
        _Label_DisplaySettings = new javax.swing.JLabel();
        _Label_FontType = new javax.swing.JLabel();
        _ComboBox_Fonts = new javax.swing.JComboBox();
        _Label_FontSize = new javax.swing.JLabel();
        _ComboBox_FontSize = new javax.swing.JComboBox();
        _CheckBox_Bold = new javax.swing.JCheckBox();
        _CheckBox_Italic = new javax.swing.JCheckBox();
        _FormattedTextField_Width = new javax.swing.JFormattedTextField();
        _FormattedTextField_Height = new javax.swing.JFormattedTextField();
        _Label_Font = new javax.swing.JLabel();
        _Panel_BorderPanel = new javax.swing.JPanel();
        _ComboBox_Borders = new javax.swing.JComboBox();
        _Label_BorderType = new javax.swing.JLabel();
        _ColorChooser_Color = new javax.swing.JColorChooser();
        _ComboBox_BorderSize = new javax.swing.JComboBox();
        _Label_BorderSize = new javax.swing.JLabel();

        _Label_DisplayWidth.setText("Display Width");

        _Label_DisplayHeight.setText("Display Height");

        _Button_DefaultSettings.setText("Default Settings");
        _Button_DefaultSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _Button_DefaultSettingsActionPerformed(evt);
            }
        });

        _Label_DisplaySettings.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        _Label_DisplaySettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_DisplaySettings.setText("Display Frame Settings");

        _Label_FontType.setText("Font Type");

        _ComboBox_Fonts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tahoma" }));
        _ComboBox_Fonts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_FontsActionPerformed(evt);
            }
        });

        _Label_FontSize.setText("Font Size");

        _ComboBox_FontSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "8", "10", "12", "14" }));
        _ComboBox_FontSize.setSelectedIndex(2);
        _ComboBox_FontSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_FontSizeActionPerformed(evt);
            }
        });

        _CheckBox_Bold.setText("Bold");
        _CheckBox_Bold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _CheckBox_BoldActionPerformed(evt);
            }
        });

        _CheckBox_Italic.setText("Italic");
        _CheckBox_Italic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _CheckBox_ItalicActionPerformed(evt);
            }
        });

        _FormattedTextField_Width.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FormattedTextField_Width.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FormattedTextField_Width.setText("1200");
        _FormattedTextField_Width.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _FormattedTextField_WidthFocusLost(evt);
            }
        });

        _FormattedTextField_Height.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        _FormattedTextField_Height.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        _FormattedTextField_Height.setText("800");
        _FormattedTextField_Height.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                _FormattedTextField_HeightFocusLost(evt);
            }
        });

        _Label_Font.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        _Label_Font.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        _Label_Font.setText("Sample Text");

        _ComboBox_Borders.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Line Border", "Raised Etched Border", "Lowered Etched Border", "Raised Bevel Border", "Lowered Bevel Border", "No Border" }));
        _ComboBox_Borders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_BordersActionPerformed(evt);
            }
        });

        _Label_BorderType.setText("Border Type");

        _ComboBox_BorderSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        _ComboBox_BorderSize.setToolTipText("");
        _ComboBox_BorderSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _ComboBox_BorderSizeActionPerformed(evt);
            }
        });

        _Label_BorderSize.setText("Border Size");

        javax.swing.GroupLayout _Panel_BorderPanelLayout = new javax.swing.GroupLayout(_Panel_BorderPanel);
        _Panel_BorderPanel.setLayout(_Panel_BorderPanelLayout);
        _Panel_BorderPanelLayout.setHorizontalGroup(
            _Panel_BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_BorderPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(_Panel_BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_Panel_BorderPanelLayout.createSequentialGroup()
                        .addComponent(_Label_BorderType, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(_ComboBox_Borders, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(_Label_BorderSize, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(_ComboBox_BorderSize, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(_ColorChooser_Color, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        _Panel_BorderPanelLayout.setVerticalGroup(
            _Panel_BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(_Panel_BorderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(_Panel_BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(_Panel_BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(_Label_BorderSize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(_ComboBox_BorderSize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(_Panel_BorderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(_ComboBox_Borders, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(_Label_BorderType, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(_ColorChooser_Color, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(_Panel_BorderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_DisplayWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_FormattedTextField_Width, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(_Label_FontSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(_ComboBox_FontSize, 0, 165, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(_CheckBox_Italic, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(_Label_FontType, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(_ComboBox_Fonts, 0, 165, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(_CheckBox_Bold, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(_Label_DisplayHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(_FormattedTextField_Height, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addComponent(_Label_DisplaySettings, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(_Label_Font, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_Button_DefaultSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 263, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {_ComboBox_FontSize, _ComboBox_Fonts});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {_Label_FontSize, _Label_FontType});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {_CheckBox_Bold, _CheckBox_Italic});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(_Label_DisplaySettings, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_DisplayWidth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_FormattedTextField_Width)
                            .addComponent(_Label_FontType, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_ComboBox_Fonts, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_CheckBox_Bold))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_DisplayHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_FormattedTextField_Height)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(_Label_FontSize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_ComboBox_FontSize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(_CheckBox_Italic)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(_Button_DefaultSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_Label_Font, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_Panel_BorderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_FormattedTextField_Height, _FormattedTextField_Width, _Label_DisplayHeight, _Label_DisplayWidth});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {_CheckBox_Bold, _CheckBox_Italic});

    }// </editor-fold>//GEN-END:initComponents

    private void _CheckBox_ItalicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__CheckBox_ItalicActionPerformed
        // TODO add your handling code here:
        updateFontLabel();
    }//GEN-LAST:event__CheckBox_ItalicActionPerformed

    private void _ComboBox_FontsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_FontsActionPerformed
        // TODO add your handling code here:
        updateFontLabel();
    }//GEN-LAST:event__ComboBox_FontsActionPerformed

    private void _ComboBox_FontSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_FontSizeActionPerformed
        // TODO add your handling code here:
        updateFontLabel();
    }//GEN-LAST:event__ComboBox_FontSizeActionPerformed

    private void _CheckBox_BoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__CheckBox_BoldActionPerformed
        // TODO add your handling code here:
        updateFontLabel();
    }//GEN-LAST:event__CheckBox_BoldActionPerformed

    private void _FormattedTextField_WidthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__FormattedTextField_WidthFocusLost
        // TODO add your handling code here:
        if ("".equals(_FormattedTextField_Width.getText())) {
            _FormattedTextField_Width.setText(String.valueOf(ds.getDisplayWidth()));
        } else {
            int val = Integer.parseInt(_FormattedTextField_Width.getText());
            val = Math.abs(val);
            int displayWidth = val;
            ds.setDisplayWidth(displayWidth);
            String sVal = String.valueOf(val);
            _FormattedTextField_Width.setText(sVal);
        }
        mf.updateDisplaySize(ds);
    }//GEN-LAST:event__FormattedTextField_WidthFocusLost

    private void _FormattedTextField_HeightFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event__FormattedTextField_HeightFocusLost
        // TODO add your handling code here:
        if ("".equals(_FormattedTextField_Height.getText())) {
            _FormattedTextField_Height.setText(String.valueOf(ds.getDisplayHeight()));
        } else {
            int val = Integer.parseInt(_FormattedTextField_Height.getText());
            val = Math.abs(val);
            int displayHeight = val;
            ds.setDisplayHeight(displayHeight);
            String sVal = String.valueOf(val);
            _FormattedTextField_Height.setText(sVal);
        }
        mf.updateDisplaySize(ds);
    }//GEN-LAST:event__FormattedTextField_HeightFocusLost

    private void _Button_DefaultSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__Button_DefaultSettingsActionPerformed
        defaultSettings();
    }//GEN-LAST:event__Button_DefaultSettingsActionPerformed

    public void updateLoad() {
        // Update the border

        int index = ds.getBorderTypeSel();

        Color color = ds.getColor();
        Border border;
        switch (index) {

            case 0:
                //Line Border
                border = BorderFactory.createLineBorder(color, ds.getBorderSize());
                break;
            case 1:
                //Raised Etched Border
                border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, color, Color.GRAY);
                break;
            case 2:
                //Lowered Etched Border
                border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, color, Color.GRAY);
                break;
            case 3:
                //Raised Bevel Border
                border = BorderFactory.createRaisedBevelBorder();
                break;
            case 4:
                //Lowered Bevel Border
                border = BorderFactory.createLoweredBevelBorder();
                break;
            case 5:
                //No Border
                border = BorderFactory.createEmptyBorder();
                break;
            default:
                border = BorderFactory.createEmptyBorder();
                break;
        }

        ds.setBorder(border);
        _Panel_BorderPanel.setBorder(border);

        String name = (String) _ComboBox_Fonts.getSelectedItem();
        int size = Integer.parseInt((String) _ComboBox_FontSize.getSelectedItem());
        int style;

        if (_CheckBox_Bold.isSelected() && _CheckBox_Italic.isSelected()) {
            style = Font.BOLD | Font.ITALIC;
        } else if (_CheckBox_Bold.isSelected()) {
            style = Font.BOLD;
        } else if (_CheckBox_Italic.isSelected()) {
            style = Font.ITALIC;
        } else {
            style = Font.PLAIN;
        }

        Font f = new Font(name, style, size);
        ds.setFont(f);        
        _Label_Font.setFont(f);   
        mf.updateSettings(ds);

    }

    private void _ComboBox_BordersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_BordersActionPerformed
        // TODO add your handling code here:
        // Update the border
        if (!loading) {
            int index = _ComboBox_Borders.getSelectedIndex();
            int bIndex = index;
            Color color = _ColorChooser_Color.getColor();
            Border border;
            switch (index) {

                case 0:
                    //Line Border
                    border = BorderFactory.createLineBorder(color, ds.getBorderSize());
                    break;
                case 1:
                    //Raised Etched Border
                    border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, color, Color.GRAY);
                    break;
                case 2:
                    //Lowered Etched Border
                    border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, color, Color.GRAY);
                    break;
                case 3:
                    //Raised Bevel Border
                    border = BorderFactory.createRaisedBevelBorder();
                    break;
                case 4:
                    //Lowered Bevel Border
                    border = BorderFactory.createLoweredBevelBorder();
                    break;
                case 5:
                    //No Border
                    border = BorderFactory.createEmptyBorder();
                    break;
                default:
                    border = BorderFactory.createEmptyBorder();
                    break;
            }

            ds.setColor(color);
            ds.setBorder(border);
            ds.setBorderTypeSel(bIndex);
            ds.setBorderSizeSel(_ComboBox_BorderSize.getSelectedIndex());
            _Panel_BorderPanel.setBorder(border);
            mf.updateSettings(ds);
        }

    }//GEN-LAST:event__ComboBox_BordersActionPerformed

    private void _ComboBox_BorderSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__ComboBox_BorderSizeActionPerformed
        // TODO add your handling code here:
        if (!loading) {
            int borderSize = Integer.parseInt((String) _ComboBox_BorderSize.getSelectedItem());
            int index = _ComboBox_Borders.getSelectedIndex();
            int bIndex = index;
            Border border;
            Color color = _ColorChooser_Color.getColor();
            switch (index) {

                case 0:
                    //Line Border
                    border = BorderFactory.createLineBorder(color, borderSize);
                    break;
                case 1:
                    //Raised Etched Border
                    border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, color, Color.GRAY);
                    break;
                case 2:
                    //Lowered Etched Border
                    border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, color, Color.GRAY);
                    break;
                case 3:
                    //Raised Bevel Border
                    border = BorderFactory.createRaisedBevelBorder();
                    break;
                case 4:
                    //Lowered Bevel Border
                    border = BorderFactory.createLoweredBevelBorder();
                    break;
                case 5:
                    //No Border
                    border = BorderFactory.createEmptyBorder();
                    break;
                default:
                    border = BorderFactory.createEmptyBorder();
                    break;
            }
            ds.setColor(color);
            ds.setBorder(border);
            ds.setBorderSize(borderSize);
            ds.setBorderTypeSel(bIndex);
            ds.setBorderSizeSel(_ComboBox_BorderSize.getSelectedIndex());
            _Panel_BorderPanel.setBorder(border);
            mf.updateSettings(ds);
        }
    }//GEN-LAST:event__ComboBox_BorderSizeActionPerformed

    private void updateFontLabel() {
        if (!loading) {
            String name = (String) _ComboBox_Fonts.getSelectedItem();
            int size = Integer.parseInt((String) _ComboBox_FontSize.getSelectedItem());
            int style;

            if (_CheckBox_Bold.isSelected() && _CheckBox_Italic.isSelected()) {
                style = Font.BOLD | Font.ITALIC;
            } else if (_CheckBox_Bold.isSelected()) {
                style = Font.BOLD;
            } else if (_CheckBox_Italic.isSelected()) {
                style = Font.ITALIC;
            } else {
                style = Font.PLAIN;
            }

            Font f = new Font(name, style, size);
            ds.setFont(f);
            ds.setBold(_CheckBox_Bold.isSelected());
            ds.setItalic(_CheckBox_Italic.isSelected());
            ds.setFontTypeSel(_ComboBox_Fonts.getSelectedIndex());
            ds.setFontSizeSel(_ComboBox_FontSize.getSelectedIndex());
            _Label_Font.setFont(f);

            mf.updateSettings(ds);
        }
    }

    public void defaultSettings() {
        loading = true;
        ds = new DisplaySettings();

    
        _FormattedTextField_Width.setText(String.valueOf(ds.getDisplayWidth()));
        _FormattedTextField_Height.setText(String.valueOf(ds.getDisplayHeight()));
        _ColorChooser_Color.setColor(ds.getColor());
        _ComboBox_Fonts.setSelectedIndex(ds.getFontTypeSel());
        _ComboBox_FontSize.setSelectedIndex(ds.getFontSizeSel());
        _ComboBox_Borders.setSelectedIndex(ds.getBorderTypeSel());
        _ComboBox_BorderSize.setSelectedIndex(ds.getBorderSizeSel());
        _CheckBox_Bold.setSelected(ds.isBold());
        _CheckBox_Italic.setSelected(ds.isItalic());
           
        this.updateLoad();
        loading = false;

    }

    public void loadSettings(DisplaySettings dds) {
        loading = true;
        this.ds = dds;

        _ColorChooser_Color.setColor(ds.getColor());
        _FormattedTextField_Width.setText(String.valueOf(ds.getDisplayWidth()));
        _FormattedTextField_Height.setText(String.valueOf(ds.getDisplayHeight()));
        _ComboBox_Fonts.setSelectedIndex(ds.getFontTypeSel());
        _ComboBox_FontSize.setSelectedIndex(ds.getFontSizeSel());
        _ComboBox_Borders.setSelectedIndex(ds.getBorderTypeSel());
        _ComboBox_BorderSize.setSelectedIndex(ds.getBorderSizeSel());
        _CheckBox_Bold.setSelected(ds.isBold());
        _CheckBox_Italic.setSelected(ds.isItalic());
        
        this.updateLoad();
        loading = false;

    }

    public DisplaySettings getDS() {
        return ds;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _Button_DefaultSettings;
    private javax.swing.JCheckBox _CheckBox_Bold;
    private javax.swing.JCheckBox _CheckBox_Italic;
    private javax.swing.JColorChooser _ColorChooser_Color;
    private javax.swing.JComboBox _ComboBox_BorderSize;
    private javax.swing.JComboBox _ComboBox_Borders;
    private javax.swing.JComboBox _ComboBox_FontSize;
    private javax.swing.JComboBox _ComboBox_Fonts;
    private javax.swing.JFormattedTextField _FormattedTextField_Height;
    private javax.swing.JFormattedTextField _FormattedTextField_Width;
    private javax.swing.JLabel _Label_BorderSize;
    private javax.swing.JLabel _Label_BorderType;
    private javax.swing.JLabel _Label_DisplayHeight;
    private javax.swing.JLabel _Label_DisplaySettings;
    private javax.swing.JLabel _Label_DisplayWidth;
    private javax.swing.JLabel _Label_Font;
    private javax.swing.JLabel _Label_FontSize;
    private javax.swing.JLabel _Label_FontType;
    private javax.swing.JPanel _Panel_BorderPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void stateChanged(ChangeEvent e) {

        if (!loading) {
            Color color = _ColorChooser_Color.getColor();
            Border border;
            int index = _ComboBox_Borders.getSelectedIndex();
            int bIndex = index;
            if (index < 3) {
                switch (index) {
                    case 0:
                        //Line Border
                        border = BorderFactory.createLineBorder(color, ds.getBorderSize());
                        break;
                    case 1:
                        //Raised Etched Border
                        border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, color, Color.GRAY);
                        break;
                    case 2:
                        //Lowered Etched Border
                        border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, color, Color.GRAY);
                        break;
                    default:
                        border = BorderFactory.createEmptyBorder();
                        break;
                }

                ds.setColor(color);
                ds.setBorder(border);

                ds.setBorderTypeSel(bIndex);
                ds.setBorderSizeSel(_ComboBox_BorderSize.getSelectedIndex());
                _Panel_BorderPanel.setBorder(border);
                mf.updateSettings(ds);
            }
        }
    }
}
