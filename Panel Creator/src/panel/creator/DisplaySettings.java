package panel.creator;

import java.awt.Color;
import java.awt.Font;
import javax.swing.border.Border;

/**
 * 
 * @author EricGummerson
 */
public class DisplaySettings implements java.io.Serializable {

    
    private static final long serialVersionUID = 422L;
    public int displayWidth;
    public int displayHeight;
    public Font font;
    public Border border;
    public int borderSize;
    public int fontTypeSel, fontSizeSel, borderTypeSel, borderSizeSel;
    public boolean bold, italic;
    public String colorStr;

    public DisplaySettings() {
        this.displayWidth = 1200;
        this.displayHeight = 900;
        this.colorStr = String.valueOf(Color.white);
        this.bold = this.italic = false;        
        
    }

    public DisplaySettings(int displayWidth, int displayHeight, Font font,
            Border border, int borderSize, int fontTypeSel, int fontSizeSel,
            int borderTypeSel, int borderSizeSel, boolean bold, boolean italic, Color color) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.font = font;
        this.border = border;
        this.borderSize = borderSize;
        this.fontTypeSel = fontTypeSel;
        this.fontSizeSel = fontSizeSel;
        this.borderTypeSel = borderTypeSel;
        this.borderSizeSel = borderSizeSel;
        this.bold = bold;
        this.italic = italic;        
        this.colorStr = String.valueOf(color.getRGB());
    }

    @Override
    public String toString() {
        return "DisplaySettings{" + "displayWidth=" + displayWidth + 
                ", displayHeight=" + displayHeight + "\nfont=" + font + 
                "\nborder=" + border + ", borderSize=" + borderSize + 
                "\nfontTypeSel=" + fontTypeSel + ", fontSizeSel=" + fontSizeSel 
                + "\nborderTypeSel=" + borderTypeSel + ", borderSizeSel=" + 
                borderSizeSel + ", bold=" + bold + ", italic=" + italic + 
                "\ncolorStr=" + colorStr + '}';
    }
    
    

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    public int getFontTypeSel() {
        return fontTypeSel;
    }

    public void setFontTypeSel(int fontTypeSel) {
        this.fontTypeSel = fontTypeSel;
    }

    public int getFontSizeSel() {
        return fontSizeSel;
    }

    public void setFontSizeSel(int fontSizeSel) {
        this.fontSizeSel = fontSizeSel;
    }

    public int getBorderTypeSel() {
        return borderTypeSel;
    }

    public void setBorderTypeSel(int borderTypeSel) {
        this.borderTypeSel = borderTypeSel;
    }

    public int getBorderSizeSel() {
        return borderSizeSel;
    }

    public void setBorderSizeSel(int borderSizeSel) {
        this.borderSizeSel = borderSizeSel;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public Color getColor() {        
        return new Color(Integer.parseInt(colorStr));        
    }

    public void setColor(Color color) {        
        this.colorStr = String.valueOf(color.getRGB());
    }

}
