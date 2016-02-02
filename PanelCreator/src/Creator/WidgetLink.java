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
    public String variableName; 
    public String panelName;

    public WidgetLink() {
        this.widgetCode = null;
        this.positionPercentage = null;
        this.variableName = "BLANK";
        this.panelName = "BLANK";
    }

    public WidgetLink(WidgetCode widgetCode, Point positionPercentage, String variableName, String panelName) {
        this.widgetCode = widgetCode;
        this.positionPercentage = positionPercentage;
        this.variableName = variableName;
        this.panelName = panelName;
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

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    
    
    
    
    
    
    
    
    
    @Override
    public String toString() {
        return "WidgetLink{" + "widgetCode=" + widgetCode + ", positionPercentage=" + positionPercentage + ", variableName=" + variableName + ", panelName=" + panelName + '}';
    }
    
    
    

}
