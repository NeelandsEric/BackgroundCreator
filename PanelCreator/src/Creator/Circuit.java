/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 *
 * @author EricGummerson
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Circuit {
    
    public String systemName;
    public int numSubSystems;
    public ArrayList<String> subSystemNames;

    public Circuit() {
        this.systemName = "";
        this.numSubSystems = 0;
        this.subSystemNames = new ArrayList<>();
    }    
    

    public Circuit(String systemName, int numSubSystems) {
        this.systemName = systemName;
        this.numSubSystems = numSubSystems;
        this.subSystemNames = new ArrayList<>();
    }    
    
    

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public int getNumSubSystems() {
        return numSubSystems;
    }

    public void setNumSubSystems(int numSubSystems) {
        this.numSubSystems = numSubSystems;
    }

    public ArrayList<String> getSubSystemNames() {
        return subSystemNames;
    }

    public void setSubSystemNames(ArrayList<String> subSystemNames) {
        this.subSystemNames = subSystemNames;
    }
    
    public void addSubSystemName(String name){
        this.subSystemNames.add(name);
    }

    
    public boolean hasSystem(String name){
        return this.subSystemNames.contains(name);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.systemName);
        hash = 97 * hash + this.numSubSystems;
        hash = 97 * hash + Objects.hashCode(this.subSystemNames);
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
        final Circuit other = (Circuit) obj;
        if (!Objects.equals(this.systemName, other.systemName)) {
            return false;
        }
        if (this.numSubSystems != other.numSubSystems) {
            return false;
        }
        if (!Objects.equals(this.subSystemNames, other.subSystemNames)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
