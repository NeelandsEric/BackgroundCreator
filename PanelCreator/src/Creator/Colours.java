package Creator;

import java.awt.Color;

/**
 * Colours is a quick enumerated class to define custom colours used on the
 * backgrounds
 *
 * @author EricGummerson
 */
public enum Colours {

    BlueDark(0, 69, 134),
    BlueLight(0, 102, 255),
    BlueLightest(51, 153, 255),
    GreenDark(0, 153, 51),
    GreenLight(0, 204, 51),
    GreenLightest(1, 255, 102),
    OrangeDark(204, 109, 0),
    OrangeLight(255, 140, 8),
    OrangeLightest(255, 169, 47),
    GreyDark(46, 46, 46),
    GreyLight(110, 110, 110),
    GreyLightest(180, 180, 180),
    BrownDark(80, 57, 1),
    BrownLight(166, 117, 2),
    BrownLightest(200, 153, 45),
    Gray(159, 162, 167),
    White(255, 255, 255),;

    private final int red, green, blue;

    /**
     * assigns the colours to their respective values
     *
     * @param red int red value
     * @param green int green value
     * @param blue int blue value
     */
    private Colours(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * gets the colour of the enumerated value
     *
     * @return Color
     */
    public Color getCol() {
        return new Color(this.red, this.green, this.blue);
    }

    /**
     * the string of values for the color
     *
     * @return string
     */
    public String getRGB() {
        return red + "," + green + "," + blue;
    }

}
