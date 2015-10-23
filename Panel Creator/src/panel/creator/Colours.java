/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.awt.Color;

/**
 * Colours is a quick enumerated class to define custom colours used on the 
 * backgrounds
 * @author EricGummerson
 */
public enum Colours {

    BlueDark(0, 69, 134),
    BlueLight(0, 102, 255),
    BlueLightest(51, 153, 255),
    
    GreenDark(0,153,51),
    GreenLight(0,204,51),
    GreenLightest(1,255,102),
    
    OrangeDark(204,109,0),
    OrangeLight(255,140,8),
    OrangeLightest(255,169,47),
    
    GreyDark(46,46,46),
    GreyLight(110,110,110),
    GreyLightest(180,180,180),
    
    
    Gray(159,162,167),
    White(255,255,255),
    
    
    ;

    private Colours(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    private final int red, green, blue;
    
    public Color getCol(){
        return new Color(this.red, this.green, this.blue);
    }

    public String getRGB() {
        return red + "," + green + "," + blue;
    }

}
