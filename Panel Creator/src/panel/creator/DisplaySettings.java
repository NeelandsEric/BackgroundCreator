/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.awt.Color;
import java.awt.Font;
import javax.swing.border.Border;

/**
 *
 * @author EricGummerson
 */
public class DisplaySettings implements java.io.Serializable{
    
    public int displayWidth;
    public int displayHeight;
    public Font font;   
    public Border border;
    public int borderSize;
    public int fontTypeSel, fontSizeSel, borderTypeSel, borderSizeSel;
    public Color color;

    public DisplaySettings() {
        this.displayWidth = 1200;
        this.displayHeight = 900;
        
    }
    
    
    public DisplaySettings(int displayWidth, int displayHeight, Font font, 
            Border border, int borderSize, int fontTypeSel, int fontSizeSel,
            int borderTypeSel, int borderSizeSel, Color color) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.font = font;
        this.border = border;
        this.borderSize = borderSize;
        this.fontTypeSel = fontTypeSel;
        this.fontSizeSel = fontSizeSel;
        this.borderTypeSel = borderTypeSel;
        this.borderSizeSel = borderSizeSel;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    
    
    
    
    
}
