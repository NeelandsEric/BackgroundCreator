/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

    public String widgetCodeName;
    //public WidgetCode widgetCode;
    @XmlJavaTypeAdapter(MyPointAdapter.class)
    public Point positionPercentage;
    public String panelType;
    public String subGroup;
    public Map<String, String> variables;

    public WidgetLink() {
        //this.widgetCode = null;
        this.positionPercentage = null;
        this.panelType = "BLANK";
        this.subGroup = "BLANK";
        this.variables = new HashMap<>();
    }
                    // WidgetCode widgetCode
    public WidgetLink(String widgetCodeName, Point positionPercentage, String panelType, String subGroup, Map<String, String> vars) {
        //this.widgetCode = widgetCode;
        this.widgetCodeName = widgetCodeName;
        this.positionPercentage = positionPercentage;
        this.panelType = panelType;
        this.subGroup = subGroup;
        this.variables = vars;
    }

    /*
    public WidgetCode getWidgetCode() {
        return widgetCode;
    }

    public void setWidgetCode(WidgetCode widgetCode) {
        this.widgetCode = widgetCode;
    }*/

    public String getWidgetCodeName() {
        return widgetCodeName;
    }

    public void setWidgetCodeName(String widgetCodeName) {
        this.widgetCodeName = widgetCodeName;
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

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }
    
    
    
    public String getValue(String key) {
        if (variables.containsKey(key)) {
            return variables.get(key);
        } else {
            return null;
        }
    }

    public void setVariable(String key, String value) {
        Object replaced = variables.put(key, value);
        if (replaced != null) {
            System.out.println(key + " replaced " + replaced + " with " + value);
        }/*else {
            System.out.println("Added " + key + ": " + value);
        }*/
    }
    
    

    @Override
    public String toString() {
        String re = "\nWidget Name = " + widgetCodeName + "\n";
        
        re += variables.size() + " Mappings\n";
        for(Entry<String, String> entry: variables.entrySet()){
            re += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        
        re += "Position Percentage = [" + positionPercentage.getX()
                + "," + positionPercentage.getY() + "]\nPanel Name = "
                + panelType + "\nSubgroup = " + subGroup + "\n";
        
        
        
        return re;                

    }

}
