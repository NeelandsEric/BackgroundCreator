/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author EricGummerson
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WidgetSettings implements java.io.Serializable {

    public static final long serialVersionUID = 49321L;    
    public Map<String, WidgetLink> widgetLinks;
    public WidgetPanelLinks wpl;
 
    public WidgetSettings() {
        this.widgetLinks = new TreeMap<>();
        this.wpl = new WidgetPanelLinks();
        //this.percentage = new Point(-99,-99);
    }

    public WidgetSettings(Map<String, WidgetLink> widgetLinks, WidgetPanelLinks wpl) {
        this.widgetLinks = widgetLinks;
        this.wpl = wpl;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.widgetLinks);
        hash = 53 * hash + Objects.hashCode(this.wpl);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WidgetSettings other = (WidgetSettings) obj;
        if (!Objects.equals(this.widgetLinks, other.widgetLinks)) {
            return false;
        }
        if (!Objects.equals(this.wpl, other.wpl)) {
            return false;
        }
        return true;
    }
    

    

    public WidgetPanelLinks getWpl() {
        return wpl;
    }

    public void setWpl(WidgetPanelLinks wpl) {
        this.wpl = wpl;
    }   
    
    
    
    public WidgetLink add(String key, WidgetLink wl){
        return widgetLinks.put(key, wl);
    }
    
    
    public int numberLinks(){
        return widgetLinks.size();
    }

    public Map<String, WidgetLink> getWidgetLinks() {
        return widgetLinks;
    }

    public void setWidgetLinks(Map<String, WidgetLink> widgetLinks) {
        this.widgetLinks = widgetLinks;
    }

    public Set<String> getWidgetLinkKeys(){
       return this.widgetLinks.keySet();
    }
    
     public Collection<WidgetLink> getWidgetLinkValues(){
       return this.widgetLinks.values();
    }
     
     public Set<Entry<String, WidgetLink>> getWidgetLinkEntrySet(){
         return this.widgetLinks.entrySet();
     }

    public boolean containsKey(String key) {
        return widgetLinks.containsKey(key);
    }

    public boolean containsValue(WidgetLink value) {
        return widgetLinks.containsValue(value);
    }

    public WidgetLink get(String key) {
        return widgetLinks.get(key);
    }
     
     
     
     
     public void clear(){
         this.widgetLinks.clear();
     }

   

    @Override
    public String toString() {
        return "WidgetLink{\nwidgetType= " + widgetLinks + "\n}";
    }
    
    
    
    

}
