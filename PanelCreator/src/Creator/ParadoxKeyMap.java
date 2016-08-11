/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EricGummerson
 */

@XmlRootElement(name = "ParadoxKeyMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParadoxKeyMap {
    
    
    private Map<String, Integer> keyMap;    

    public ParadoxKeyMap() {
        keyMap = new TreeMap<>();
    }

    public Map<String, Integer> getKeyMap() {
        return keyMap;
    }

    public void setKeyMap(Map<String, Integer> keyMap) {
        this.keyMap = keyMap;
    }

    public int size() {
        return keyMap.size();
    }

    public boolean isEmpty() {
        return keyMap.isEmpty();
    }

    public boolean containsKey(String key) {
        return keyMap.containsKey(key);
    }

    public boolean containsValue(int value) {
        return keyMap.containsValue(value);
    }

    public Integer get(String key) {
        return keyMap.get(key);
    }

    public Integer put(String key, Integer value) {
        return keyMap.put(key, value);
    }

    public void clear() {
        keyMap.clear();
    }

    public Collection<Integer> values() {
        return keyMap.values();
    }

    public Set<Map.Entry<String, Integer>> entrySet() {
        return keyMap.entrySet();
    }

    public boolean remove(String key, int value) {
        return keyMap.remove(key, value);
    }

    public Integer replace(String key, Integer value) {
        return keyMap.replace(key, value);
    }
    
    
    
    public ArrayList<String> search(String key){
        
        ArrayList<String> retItems = new ArrayList<>();
        
        for(String searchable: keyMap.keySet()){
            if(searchable.contains(key)){
                retItems.add(searchable + "|| " + String.valueOf(keyMap.get(searchable)));
            }
        }
        
        return retItems;
        
    }

    @Override
    public String toString() {
        String s = "ParadoxKeyMap{";
        
        for(Entry entry: keyMap.entrySet()){
            s += "\nEntry";
            s += "\n\tName: " + entry.getKey();
            s += "\n\tMkey: " + String.valueOf(entry.getValue());
        }
        s += "\n}";
        return s;
    }
    
    
    
    
    
    
    
    
}
