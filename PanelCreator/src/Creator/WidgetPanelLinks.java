/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author EricGummerson
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WidgetPanelLinks implements java.io.Serializable {

    public static final long serialVersionUID = 412351L;
    public Map<String, LinkInfo> links;

    /**
     *
     */
    public WidgetPanelLinks() {
        links = new HashMap<>();
    }

    public WidgetPanelLinks(Map<String, LinkInfo> links) {
        this.links = links;
    }

    public void addLink(String panel, int panelID, String panelName, int xPos, int yPos) {

        links.put(panel, new LinkInfo(panelID, panelName, xPos, yPos));

        //System.out.println("Added: [" + panel + "] {" + panelID + ", " + panelName
        //                    + ", (" + xPos + ", " + yPos + ")}");

    }    
    

    public Map<String, LinkInfo> getLinks() {
        return links;
    }

    public void setLinks(Map<String, LinkInfo> links) {
        this.links = links;
    }

    public LinkInfo getLinkInfo(String key) {
        return this.links.get(key);
    }
    
    public boolean hasLink(String key){
        return this.links.containsKey(key);
    }
    
    public void clearLinks(){
        links.clear();
    }
}
