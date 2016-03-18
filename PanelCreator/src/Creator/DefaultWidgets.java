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
 * DefaultWidgets is a class to load and save default Widget variables to a
 * users home directory. This class contains two mappings widgetLinks is a map
 * between a key and the widget links widgetCodeMappings is a map between a key
 * and widget code
 *
 * @author EricGummerson
 */
@XmlRootElement(name = "DefaultWidgets")
@XmlAccessorType(XmlAccessType.FIELD)
public class DefaultWidgets implements java.io.Serializable {

    public static final long serialVersionUID = 43271L;
    public Map<String, WidgetLink> widgetLinks;

    /**
     * Default Constructor
     */
    public DefaultWidgets() {
    }

    /**
     * Constructor taking both maps
     *
     * @param widgetLinks Map<String, WidgetLink>
     */
    public DefaultWidgets(Map<String, WidgetLink> widgetLinks) {
        this.widgetLinks = widgetLinks;

    }

    /**
     * Get widget links
     *
     * @return Map<String, WidgetLink>
     */
    public Map<String, WidgetLink> getWidgetLinks() {
        return widgetLinks;
    }

    /**
     * Set widget Links
     *
     * @param widgetLinks Map<String, WidgetLink>
     */
    public void setWidgetLinks(Map<String, WidgetLink> widgetLinks) {
        this.widgetLinks = widgetLinks;
    }

}
