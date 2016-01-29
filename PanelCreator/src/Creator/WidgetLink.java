/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.awt.Point;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author EricGummerson
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WidgetLink implements java.io.Serializable {

    public static final long serialVersionUID = 49321L;
    public WidgetCode widgetType;
    public Point percentage;

    public WidgetLink() {
        this.widgetType = new WidgetCode();
        this.percentage = new Point(-99,-99);
    }
    
    

    public WidgetLink(WidgetCode wt) {
        this.widgetType = wt;
        this.percentage = null;

    }

    public WidgetLink(WidgetCode wt, Point per) {
        this.widgetType = wt;
        this.percentage = per;

    }

    @Override
    public String toString() {
        return "WidgetLink{" + "widgetType=" + widgetType + ", percentage=" + percentage + '}';
    }
    
    

    public WidgetCode getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(WidgetCode widgetType) {
        this.widgetType = widgetType;
    }

    public Point getPercentage() {
        return percentage;
    }

    public void setPercentage(Point percentage) {
        this.percentage = percentage;
    }
    
    

}
