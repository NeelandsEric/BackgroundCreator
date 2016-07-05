package Creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import javax.swing.DefaultListModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 *
 * @author EricGummerson
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ModbusSettings implements java.io.Serializable {

    private static final long serialVersionUID = 532L;
    public ArrayList<String> modelList;
    public ArrayList<String> removedItems;
    public DefaultListModel cm;
    public int numPowerScouts;
    public String [] powerScoutIPs;
    public String [] singleLoadIPs;
    public int numSingleLoads;
    public int[][] powerScoutsPanelType;
    public int[] singlePanelType;
    public Map<String, Sensor> items;

    public ModbusSettings() {
        powerScoutsPanelType = new int[10][8];
        for (int i = 0; i < powerScoutsPanelType.length; i++) {
            Arrays.fill(powerScoutsPanelType[i], 1);
        }
        singlePanelType = new int [15];
        Arrays.fill(singlePanelType, 1);
        
        items = new HashMap<>();
        removedItems = new ArrayList<>();   
        powerScoutIPs = new String[10];
        singleLoadIPs = new String[15];
        
        Arrays.fill(powerScoutIPs, "192.168.0.00");
        Arrays.fill(singleLoadIPs, "192.168.0.00");

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.modelList);
        hash = 89 * hash + this.numPowerScouts;
        hash = 89 * hash + Arrays.deepHashCode(this.powerScoutIPs);
        hash = 89 * hash + Arrays.deepHashCode(this.singleLoadIPs);
        hash = 89 * hash + this.numSingleLoads;
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
        final ModbusSettings other = (ModbusSettings) obj;
        if (!Objects.equals(this.modelList, other.modelList)) {
            return false;
        }
        if (this.numPowerScouts != other.numPowerScouts) {
            return false;
        }
        if (!Arrays.deepEquals(this.powerScoutIPs, other.powerScoutIPs)) {
            return false;
        }
        if (!Arrays.deepEquals(this.singleLoadIPs, other.singleLoadIPs)) {
            return false;
        }
        if (this.numSingleLoads != other.numSingleLoads) {
            return false;
        }
        return true;
    }
    
    

    public void updateModbusSettings(ControlSettings cs) {

        // Find all the possible strings from the store
        modelList = cs.getModbusOptions();

        removedItems = new ArrayList<>();
        // Go through the old hashmap and remove any items not in the new list
        
        for (String key : items.keySet()) {
            if(!modelList.contains(key)){
                //System.out.println("New list doesnt contain " + key);
                removedItems.add(key);
            }
        }
        
        for(String s: removedItems){
            items.remove(s);
        }
              
        // With the current list we now update the Map
        // To default, all items will be false
        
        
        if (!modelList.isEmpty()) {
            for (String st : modelList) {
                if (!items.containsKey(st)) {                    
                    items.put(st, new Sensor());
                }
            }
        }
    }
    
    

    public void updateKey(String key, boolean used, int meter, int slave, int register, boolean powerScout) {

        if (!items.isEmpty()) {
            if (items.containsKey(key)) {
                Sensor s = items.get(key);

                //System.out.println("Updated " + key + " from "
                //+ s.isUsed() + " to " + used);
                s.updateKey(key, meter, slave, register, used, powerScout);
                items.replace(key, s);

            } else {
                System.out.println("Key: " + key + " not found!");
            }
        }

    }
    
    /**
     * Remove all keys that are part of that meter
     * for the power scouts
     * @param meter 
     */
    public void clearKeys(int meter){
                
        for(Entry<String, Sensor> entry: items.entrySet()){
            Sensor s = entry.getValue();
            if(s.isPowerScout() && s.getMeter() == meter){               
                s.removeKey();
                items.replace(entry.getKey(), s);                
            }
            
        }
    }
    
    
    /**
     * remove all keys that are part of that meter
     * for the single loads
     * @param meter 
     */
    public void clearSingleKeys(int meter){
                
        // Meter is actually powerScouts + meter
        if(meter <= getNumPowerScouts()){
            meter += getNumPowerScouts();
        }
        for(Entry<String, Sensor> entry: items.entrySet()){
            Sensor s = entry.getValue();
            if(!s.isPowerScout() && s.getMeter() == meter){               
                s.removeKey();
                items.replace(entry.getKey(), s);                
            }
            
        }
    }

    public void removeKey(String key) {

        if (!items.isEmpty()) {
            if (items.containsKey(key)) {
                Sensor s = items.get(key);

                //System.out.println("Removed " + key + " from "
                //+ s.isUsed() + " to false");
                s.removeKey();
                items.replace(key, s);

            } else {
                System.out.println("Key: " + key + " not found!");
            }
        }
    }

    public void updateTableType(int meter, int slave, int type) {
        powerScoutsPanelType[meter][slave] = type;
    }

    public boolean checkKey(String key) {
        return items.get(key).isUsed();
    }
    
    public Sensor getSensorForKey(String key){
        return items.get(key);
    }

    public int numUnusedKeys() {
        int n = 0;

        if (!items.isEmpty()) {
            for (Sensor s : items.values()) {
                if (!s.isUsed()) {
                    n++;
                }
            }
        }

        return n;

    }

    public void updateModel() {

        cm = new DefaultListModel();
        cm.addElement("Remove Item");
        cm.addElement("No Selection");
        ArrayList<String> l = new ArrayList<>();
        if (!items.isEmpty()) {
            for (Map.Entry<String, Sensor> entry : items.entrySet()) {
                if (!entry.getValue().isUsed()) {
                    l.add(entry.getKey());
                    //cm.addElement(entry.getKey());
                }
            }
        }
        Collections.sort(l);
        for (String s : l) {
            cm.addElement(s);
        }

    }

    public ArrayList<String> getRemovedItems() {
        return removedItems;
    }

    public void setRemovedItems(ArrayList<String> removedItems) {
        this.removedItems = removedItems;
    }

    public ArrayList<String> getModelList() {
        return modelList;
    }

    public void setModelList(ArrayList<String> modelList) {
        this.modelList = modelList;
    }

    public String[] getPowerScoutIPs() {
        return powerScoutIPs;
    }
    
    public String getPowerScoutIPIndex(int index){
        return powerScoutIPs[index];
    }

    public void setPowerScoutIPs(String[] powerScoutIPs) {
        this.powerScoutIPs = powerScoutIPs;
    }
    
    public void setPowerScoutIP(String ip, int index){
        this.powerScoutIPs[index] = ip;
    }

    public String[] getSingleLoadIPs() {
        return singleLoadIPs;
    }
    
    public String getSingleLoadIPIndex(int index){
        return singleLoadIPs[index];
    }

    public void setSingleLoadIPs(String[] singleLoadIPs) {
        this.singleLoadIPs = singleLoadIPs;
    }
    
     public void setSingleLoadIP(String ip, int index){
        this.singleLoadIPs[index] = ip;
    }

    public int[][] getPowerScoutsPanelType() {
        return powerScoutsPanelType;
    }

    public void setPowerScoutsPanelType(int[][] powerScoutsPanelType) {
        this.powerScoutsPanelType = powerScoutsPanelType;
    }

    public int[] getSinglePanelType() {
        return singlePanelType;
    }

    public void setSinglePanelType(int[] singlePanelType) {
        this.singlePanelType = singlePanelType;
    }
    
    
    

    public ArrayList<String> getList() {
        return modelList;
    }

    public void setList(ArrayList<String> list) {
        this.modelList = list;
    }

    public int getPanelType(int i) {
        return singlePanelType[i];
    }

    public void setPanelType(int[] panelType) {
        this.singlePanelType = panelType;
    }
    
    
    public int[][] getPanelType() {
        return powerScoutsPanelType;
    }

    public int getPanelType(int i, int j) {
        return powerScoutsPanelType[i][j];
    }

    public void setPanelType(int[][] panelType) {
        this.powerScoutsPanelType = panelType;
    }

    public Map<String, Sensor> getItems() {
        return items;
    }

    public void setItems(Map<String, Sensor> items) {
        this.items = items;
    }

    public DefaultListModel getCm() {
        return cm;
    }

    public void setCm(DefaultListModel cm) {
        this.cm = cm;
    }

    public int getNumPowerScouts() {
        
        return numPowerScouts;
    }

    public void setNumPowerScouts(int numPowerScouts) {
        this.numPowerScouts = numPowerScouts;
    }

    public int getNumSingleLoads() {
        return numSingleLoads;
    }

    public void setNumSingleLoads(int numSingleLoads) {
        this.numSingleLoads = numSingleLoads;
    }
    
    public List<String> activeMeters(String sn){
        
        Map<String, String> list = new HashMap<>();
        
        for(Sensor s: items.values()){
            if(s.getMeter() > -1 && !list.containsKey(String.valueOf(s.getMeter()))){
                String name = sn + " ";
                name += "- Meter ";
                name += String.valueOf(s.getMeter() + 1);
                name += "(" + (s.isPowerScout() ? "PS) " : "SS) ");
                name += "," + (s.isPowerScout() ? getPowerScoutIPIndex(s.getMeter()): getSingleLoadIPIndex(s.getMeter() - numPowerScouts));
                list.put(String.valueOf(s.getMeter()), name);
            }
        }
        
        // Returns a list of the strings
        return list.values() instanceof List ? (List) list.values(): new ArrayList(list.values());
    }
    
            
    

}
