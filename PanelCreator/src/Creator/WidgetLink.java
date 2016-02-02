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
    public String panelName;
    public String subGroup;

    public WidgetLink() {
        this.widgetCode = null;
        this.positionPercentage = null;
        this.panelName = "BLANK";
        this.subGroup = "BLANK";
    }

    public WidgetLink(WidgetCode widgetCode, Point positionPercentage, String panelName, String subGroup) {
        this.widgetCode = widgetCode;
        this.positionPercentage = positionPercentage;
         this.panelName = panelName;
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

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    
    
    
    @Override
    public String toString() {
        return "WidgetLink{\nwidgetCode = " + widgetCode
                + "\npositionPercentage = [" + positionPercentage.getX()
                + "," + positionPercentage.getY() + "]\npanelName = "
                + panelName + "\nsubGroup = " + subGroup + "\n}";
    
    }
    
    
    

}
