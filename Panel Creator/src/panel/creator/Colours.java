/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.awt.Color;

/**
 *
 * @author EricGummerson
 */
public enum Colours {

    DarkerBlue(0, 69, 134),
    LighterBlue(0, 102, 255),
    LightestBlue(51, 153, 255),
    DarkerGreen(0,153,51),
    LighterGreen(0,204,51),
    LightestGreen(1,255,102),
    Gray(159,162,167),
    White(255,255,255);

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
