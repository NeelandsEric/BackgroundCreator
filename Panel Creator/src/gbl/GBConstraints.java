package gbl;

import java.awt.*;

/**
 * Convenience class to simplify the creation of a GridBagConstraints object.
 */
public class GBConstraints extends GridBagConstraints
{
  public GBConstraints(int gridX, int gridY)
  {
    super();

    this.gridx = gridX;
    this.gridy = gridY;

    this.gridwidth = 1;
    this.gridheight = 1;
    this.weightx = 0;
    this.weighty = 0;
    this.anchor = NORTHWEST;              // old default was CENTER
    this.fill = NONE;
    this.insets = new Insets(1,2,1,2);    // old default was (0,0,0,0)
    this.ipadx = 1;                       // old default was 0
    this.ipady = 1;                       // old default was 0
  }

  public GBConstraints anchor(Anchor anchor)
  {
    this.anchor = anchor.getConstraint();
    return this;
  }

  public GBConstraints fill(Fill fill)
  {
    this.fill = fill.getConstraint();

    /*
     * As a convenience, set the weights appropriately since these values are
     * almost always used in tandem with the given Fill. The caller can always
     * call the weight(...) method later if these defaults aren't desired. 
     */
    switch (fill)
    {
      case HORIZONTAL:
        this.weightx = 1;
        this.weighty = 0;
        break;
      case VERTICAL:
        this.weightx = 0;
        this.weighty = 1;
        break;
      case BOTH:
        this.weightx = 1;
        this.weighty = 1;
        break;
      default:
        this.weightx = 0;
        this.weighty = 0;
        break;
    }

    return this;
  }

  public GBConstraints insets(int length)
  {
    return insets(length, length, length, length);
  }

  public GBConstraints insets(int top, int left, int bottom, int right)
  {
    this.insets = new Insets(top, left, bottom, right);
    return this;
  }

  public GBConstraints ipad(int ipadX, int ipadY)
  {
    this.ipadx = ipadX;
    this.ipady = ipadY;
    return this;
  }

  public GBConstraints span(int gridWidth, int gridHeight)
  {
    this.gridwidth = gridWidth;
    this.gridheight = gridHeight;
    return this;
  }

  public GBConstraints spanX(int gridWidth)
  {
    this.gridwidth = gridWidth;
    return this;
  }

  public GBConstraints spanY(int gridHeight)
  {
    this.gridheight = gridHeight;
    return this;
  }

  public GBConstraints weight(double weightX, double weightY)
  {
    this.weightx = weightX;
    this.weighty = weightY;
    return this;
  }

  public GBConstraints weightX(double weightX)
  {
    this.weightx = weightX;
    return this;
  }

  public GBConstraints weightY(double weightY)
  {
    this.weighty = weightY;
    return this;
  }
}