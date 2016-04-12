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
public class WidgetPanelLinks {

    class LinkInfo {

        protected int panelID;
        protected String panelName;

        protected LinkInfo() {
        }

        protected LinkInfo(int panelID, String panelName) {
            this.panelID = panelID;
            this.panelName = panelName;
        }

        protected int getPanelID() {
            return panelID;
        }

        protected void setPanelID(int panelID) {
            this.panelID = panelID;
        }

        protected String getPanelName() {
            return panelName;
        }

        protected void setPanelName(String panelName) {
            this.panelName = panelName;
        }

    }

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

    public void addLink(String panel, int panelID, String panelName) {

        links.put(panel, new LinkInfo(panelID, panelName));

        System.out.println("Added: [" + panel + "] {" + panelID + ", " + panelName);

    }

    public Map<String, LinkInfo> getLinks() {
        return links;
    }

    public void setLinks(Map<String, LinkInfo> links) {
        this.links = links;
    }
    
    
    

}
