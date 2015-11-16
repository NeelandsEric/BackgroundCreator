package panel.creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;

/**
 *
 * @author EricGummerson
 */
public class ModbusSettings implements java.io.Serializable {

    private static final long serialVersionUID = 532L;
    public ArrayList<String> modelList;
    public ArrayList<String> removedItems;
    public DefaultListModel cm;
    public int numPowerScouts;
    public int numSingleLoads;
    public int[][] powerScoutsPanelType;
    public int[] singlePanelType;
    public Map<String, Sensor> items;

    public ModbusSettings() {
        powerScoutsPanelType = new int[10][8];
        for (int i = 0; i < powerScoutsPanelType.length; i++) {
            Arrays.fill(powerScoutsPanelType[i], 1);
        }
        items = new HashMap<>();
        removedItems = new ArrayList<>();        

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

    public void updateKey(String key, Boolean used, int meter, int slave, int register) {

        if (!items.isEmpty()) {
            if (items.containsKey(key)) {
                Sensor s = items.get(key);

                //System.out.println("Updated " + key + " from "
                //+ s.isUsed() + " to " + used);
                s.updateKey(key, meter, slave, register, used);
                items.replace(key, s);

            } else {
                System.out.println("Key: " + key + " not found!");
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
    
    
    

    public ArrayList<String> getList() {
        return modelList;
    }

    public void setList(ArrayList<String> list) {
        this.modelList = list;
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

}
