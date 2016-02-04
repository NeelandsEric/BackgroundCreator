/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.awt.Point;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author EricGummerson
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WidgetLink implements java.io.Serializable {

    public static final long serialVersionUID = 49121L;

    public WidgetCode widgetCode;
    @XmlJavaTypeAdapter(MyPointAdapter.class)
    public Point positionPercentage;
    public String panelType;
    public String subGroup;

    public WidgetLink() {
        this.widgetCode = null;
        this.positionPercentage = null;
        this.panelType = "BLANK";
        this.subGroup = "BLANK";
    }

    public WidgetLink(WidgetCode widgetCode, Point positionPercentage, String panelType, String subGroup) {
        this.widgetCode = widgetCode;
        this.positionPercentage = positionPercentage;
        this.panelType = panelType;
        this.subGroup = subGroup;
    }

    public WidgetCode getWidgetCode() {
        return widgetCode;
    }

    public void setWidgetCode(WidgetCode widgetCode) {
        this.widgetCode = widgetCode;
    }

    public Point getPositionPercentage() {
        return positionPercentage;
    }

    public void setPositionPercentage(Point positionPercentage) {
        this.positionPercentage = positionPercentage;
    }

   
    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public String getPanelType() {
        return panelType;
    }

    public void setPanelType(String panelType) {
        this.panelType = panelType;
    }
    
    

    @Override
    public String toString() {
        return "WidgetLink{\nwidgetCode = " + widgetCode
                + "\npositionPercentage = [" + positionPercentage.getX()
                + "," + positionPercentage.getY() + "]\npanelType = "
                + panelType + "\nsubGroup = " + subGroup + "\n}";

    }

}
