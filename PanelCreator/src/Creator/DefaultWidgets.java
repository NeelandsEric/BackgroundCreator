/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EricGummerson
 */
@XmlRootElement(name = "DefaultWidgets")
@XmlAccessorType(XmlAccessType.FIELD)
public class DefaultWidgets implements java.io.Serializable{    
    

    public static final long serialVersionUID = 43271L; 
    public Map<String, WidgetLink> widgetLinks;
    public Map<String, WidgetCode> widgetCodeMappings;

    public DefaultWidgets() {
    }

    public DefaultWidgets(Map<String, WidgetLink> widgetLinks, Map<String, WidgetCode> widgetCodeMappings) {
        this.widgetLinks = widgetLinks;
        this.widgetCodeMappings = widgetCodeMappings;
    }

    public Map<String, WidgetLink> getWidgetLinks() {
        return widgetLinks;
    }

    public void setWidgetLinks(Map<String, WidgetLink> widgetLinks) {
        this.widgetLinks = widgetLinks;
    }

    public Map<String, WidgetCode> getWidgetCodeMappings() {
        return widgetCodeMappings;
    }

    public void setWidgetCodeMappings(Map<String, WidgetCode> widgetCodeMappings) {
        this.widgetCodeMappings = widgetCodeMappings;
    }
    
    
    
}
