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
        this.glycolSubSystems.put("G01", new Circuit("G01", 0));
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
    
    public void updateSubSystems(){
        for(String s: this.glycolSystemNames){
            if(!this.glycolSubSystems.containsKey(s)){
                this.glycolSubSystems.put(s, new Circuit(s));
            }
        }
    }

    
    public int getNumSubSystems(String systemName){
        if(this.glycolSubSystems.containsKey(systemName)){
            return this.glycolSubSystems.get(systemName).getNumSubSystems();
        }else {
            return 0;
        }
    }
    
    public String getSubSystemNameIndex(String systemName, int index){
        if(this.glycolSubSystems.containsKey(systemName)){
            return this.glycolSubSystems.get(systemName).getSubSystemName(index);
        }else {
            return "No GSystem found " + systemName;
        }
    }
    
    public void setNumSubSystems(String glycolSystemName, int numGlycolSubSystems){
        if(this.glycolSubSystems.containsKey(glycolSystemName)){
           this.glycolSubSystems.get(glycolSystemName).setNumSubSystems(numGlycolSubSystems); 
        }else {
            System.out.println("No glycol system: " + glycolSystemName);
            this.glycolSubSystems.put(glycolSystemName, new Circuit(glycolSystemName, numGlycolSubSystems));
        }
        
    }
    
    /**
     * replace system name
     *
     * @param name string
     * @param index int
     */
    public void replaceGlycolSystemName(String name, int index) {
        
        String oldName = this.glycolSystemNames.get(index);
        Circuit oldSubNames = null;
        if(this.glycolSystemNames.contains(oldName)){
            oldSubNames = this.glycolSubSystems.remove(oldName); 
        }
        if(oldSubNames == null){
            oldSubNames = new Circuit(name);
        }
        
        this.glycolSystemNames.set(index, name);       
        
        oldSubNames.updateNames(name);
        
        this.glycolSubSystems.put(name, oldSubNames);
    }
    
    public void replaceGlycolSubSystemName(String systemName, String subSystemName, int index){
        if(this.glycolSubSystems.containsKey(systemName)){
            this.glycolSubSystems.get(systemName).replaceSubSystemName(subSystemName, index);
        }else {
            System.out.println(systemName + " not found in sub system map");
        }
        
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
}
