/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Creator;

import java.util.ArrayList;
import java.util.List;
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
    // Other glycol settings to be implemented

    public GlycolSettings() {
        this.numGlycolSystems = 1;
        this.glycolSystemNames = new ArrayList<>();
        this.glycolSystemNames.add("G1");
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
                this.glycolSystemNames.add("G" + (size + i + 1));
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