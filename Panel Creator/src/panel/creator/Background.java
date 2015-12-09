/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.border.Border;

/**
 * Interface for backgrounds
 * @author EricGummerson
 */
public interface Background {

    /**
     * Updates the form with the right information
     *
     * @param racks rack list
     * @param numRacks number of racks to read from the list
     * @param font global font
     * @param border global border
     * @param img global img string for the logo
     * @param storeName global string for the store name
     */
    public void updateRacks(ArrayList<Rack> racks, int numRacks, Font font, Border border, String img, String storeName);

    /**
     * updates the storename
     *
     * @param storeName string of the store name
     */
    public void updateStoreName(String storeName);

    /**
     * updates the image url for the logo
     *
     * @param img string file path of the logo
     */
    public void updateImageURL(String img);

    /**
     * updates the font selected from the settings panel
     *
     * @param font Font
     */
    public void updateFont(Font font);

    /**
     * Updates the border
     *
     * @param border Border
     */
    public void updateBorder(Border border);

    /**
     * Updates the font and bother
     *
     * @param font Font
     * @param border Border
     */
    public void updateFontBorder(Font font, Border border);

    public boolean canClick();

    public void setCanClick(boolean canClick);

    
    public void updateView();
    
    public void setLabels(Container p1, Color c);
    
}
