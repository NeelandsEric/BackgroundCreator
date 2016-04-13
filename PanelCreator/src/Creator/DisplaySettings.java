package Creator;

import java.awt.Color;
import java.awt.Font;
import java.util.Objects;
import javax.swing.border.Border;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author EricGummerson
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class DisplaySettings implements java.io.Serializable {

    private static final long serialVersionUID = 422L;

    @XmlTransient
    public Font font;
    @XmlTransient
    
    public Border border;
    public int displayWidth;
    public int displayHeight;

    public int borderSize;
    public int fontTypeSel, fontSizeSel, borderTypeSel, borderSizeSel;
    public boolean bold, italic;
    public String colorStr;

    public DisplaySettings() {

        this.displayWidth = 1200;
        this.displayHeight = 900;
        this.colorStr = String.valueOf(Color.white.getRGB());
        this.bold = this.italic = false;
        this.font = new Font("Arial", Font.PLAIN, 14);
        this.fontTypeSel = 11;
        this.fontSizeSel = 6;
        this.borderTypeSel = 0;
        this.borderSizeSel = 1;
        this.borderSize = 1;
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
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.font);
        hash = 79 * hash + Objects.hashCode(this.border);
        hash = 79 * hash + this.displayWidth;
        hash = 79 * hash + this.displayHeight;
        hash = 79 * hash + this.borderSize;
        hash = 79 * hash + this.fontTypeSel;
        hash = 79 * hash + this.fontSizeSel;
        hash = 79 * hash + this.borderTypeSel;
        hash = 79 * hash + this.borderSizeSel;
        hash = 79 * hash + (this.bold ? 1 : 0);
        hash = 79 * hash + (this.italic ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.colorStr);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DisplaySettings other = (DisplaySettings) obj;
        if (!Objects.equals(this.font, other.font)) {
            return false;
        }
        if (!Objects.equals(this.border, other.border)) {
            return false;
        }
        if (this.displayWidth != other.displayWidth) {
            return false;
        }
        if (this.displayHeight != other.displayHeight) {
            return false;
        }
        if (this.borderSize != other.borderSize) {
            return false;
        }
        if (this.fontTypeSel != other.fontTypeSel) {
            return false;
        }
        if (this.fontSizeSel != other.fontSizeSel) {
            return false;
        }
        if (this.borderTypeSel != other.borderTypeSel) {
            return false;
        }
        if (this.borderSizeSel != other.borderSizeSel) {
            return false;
        }
        if (this.bold != other.bold) {
            return false;
        }
        if (this.italic != other.italic) {
            return false;
        }
        if (!Objects.equals(this.colorStr, other.colorStr)) {
            return false;
        }
        return true;
    }
    
    
    

    @Override
    public String toString() {
        return "DisplaySettings{" + "displayWidth=" + displayWidth
                + ", displayHeight=" + displayHeight + "\nfont=" + font
                + "\nborder=" + border + ", borderSize=" + borderSize
                + "\nfontTypeSel=" + fontTypeSel + ", fontSizeSel=" + fontSizeSel
                + "\nborderTypeSel=" + borderTypeSel + ", borderSizeSel="
                + borderSizeSel + ", bold=" + bold + ", italic=" + italic
                + "\ncolorStr=" + colorStr + '}';
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
