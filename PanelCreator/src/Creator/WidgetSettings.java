/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
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

    public WidgetSettings() {
        this.widgetLinks = new TreeMap<>();
        //this.percentage = new Point(-99,-99);
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
     
     
     public void clear(){
         this.widgetLinks.clear();
     }

   

    @Override
    public String toString() {
        return "WidgetLink{\nwidgetType= " + widgetLinks + "\n}";
    }
    
    
    
    

}
