/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EricGummerson
 */

@XmlRootElement(name = "GenericVariableLinks")
@XmlAccessorType(XmlAccessType.FIELD)
public class GenericVariableLinks {
    
    private Map<String, String> gvLinks;

    public GenericVariableLinks() {
    }

    public GenericVariableLinks(Map<String, String> gvLinks) {
        this.gvLinks = gvLinks;
    }

    public Map<String, String> getGvLinks() {
        return gvLinks;
    }

    public void setGvLinks(Map<String, String> gvLinks) {
        this.gvLinks = gvLinks;
    }

    public int size() {
        return gvLinks.size();
    }

    public boolean isEmpty() {
        return gvLinks.isEmpty();
    }

    public boolean containsKey(Object key) {
        return gvLinks.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return gvLinks.containsValue(value);
    }

    public String get(Object key) {
        return gvLinks.get(key);
    }

    public String put(String key, String value) {
        return gvLinks.put(key, value);
    }

    public String remove(Object key) {
        return gvLinks.remove(key);
    }

    public void clear() {
        gvLinks.clear();
    }

    public Set<String> keySet() {
        return gvLinks.keySet();
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return gvLinks.entrySet();
    }

    public boolean equals(Object o) {
        return gvLinks.equals(o);
    }

    public String replace(String key, String value) {
        return gvLinks.replace(key, value);
    }
    
    
    
    
}
