/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author EricGummerson
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class GlycolSettings {
    
    
    public List<String> glycolSystemNames;
    public int numGlycolSystems;
    public TreeMap<String, Circuit> glycolSubSystems;
    // Other glycol settings to be implemented

    public GlycolSettings() {
        this.numGlycolSystems = 1;
        this.glycolSystemNames = new ArrayList<>();
        this.glycolSystemNames.add("G01");
        this.glycolSubSystems = new TreeMap<>();
        this.glycolSubSystems.put("G01", new Circuit("G01", 1));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.glycolSystemNames);
        hash = 23 * hash + Objects.hashCode(this.glycolSubSystems);
        hash = 23 * hash + this.numGlycolSystems;
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
        final GlycolSettings other = (GlycolSettings) obj;
        if (!Objects.equals(this.glycolSystemNames, other.glycolSystemNames)) {
            return false;
        }
        if (!Objects.equals(this.glycolSubSystems, other.glycolSubSystems)) {
            return false;
        }
        if (this.numGlycolSystems != other.numGlycolSystems) {
            return false;
        }
        return true;
    }

    
    
    public TreeMap<String, Circuit> getSubSystems() {
        return glycolSubSystems;
    }

    public void setSubSystems(TreeMap<String, Circuit> subSystems) {
        this.glycolSubSystems = subSystems;
    }
    
    public ArrayList<String> getAllSystemNames(String systemName){
                
        if(this.glycolSubSystems.containsKey(systemName)){
            ArrayList<String> list = this.glycolSubSystems.get(systemName).getSubSystemNames();
            list.add(0, systemName);   
            return list;
        }else {
            return null;
        }                
    }
    
    public List<String> getGlycolSystemNames() {
        return glycolSystemNames;
    }
    
    public String getGlycolSystemNameIndex(int index){
        return glycolSystemNames.get(index);
        
    }

    public void setGlycolSystemNames(List<String> glycolSystems) {
        this.glycolSystemNames = glycolSystems;
    }    
    

    public int getNumGlycolSystems() {
        return numGlycolSystems;
    }

    public void setNumGlycolSystems(int numGlycolSystems) {
        this.numGlycolSystems = numGlycolSystems;
    }
    
    /**
     * add system names
     *
     * @param num int
     */
    public void addGlycolSystemNames(int num) {
        int size = this.glycolSystemNames.size();
        int toAdd = num - size;

        if (toAdd > 0) {
            for (int i = 0; i < toAdd; i++) {
                //System.out.println("Adding System " + (size + i+1));
                String s = "";
                if((size + i + 1) < 10){
                    s = "0" + String.valueOf(size + i + 1);
                }else {
                    s = String.valueOf(size + i + 1);
                }
                this.glycolSystemNames.add("G" + s);
                this.numGlycolSystems++;
            }
        } else {
            this.numGlycolSystems = num;
        }

    }

    /**
     * replace system name
     *
     * @param name string
     * @param index int
     */
    public void replaceGlycolSystemName(String name, int index) {
        this.glycolSystemNames.set(index, name);
    }
    
}
