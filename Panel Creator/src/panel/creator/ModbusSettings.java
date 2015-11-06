package panel.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.DefaultListModel;

/**
 *
 * @author EricGummerson
 */
public class ModbusSettings implements java.io.Serializable {

    private static final long serialVersionUID = 532L;
    public ArrayList<String> list;
    public DefaultListModel cm;
    public int numPowerScouts;
    public int numSingleLoads;
    public Map<String, Boolean> items;
    public String [][][] itemPosition;

    public ModbusSettings() {
        itemPosition = new String[10][8][3];
        for(int i = 0; i < itemPosition.length; i++){
            for(int j = 0; j < itemPosition[i].length; j++){
                for(int k = 0; k < itemPosition[i][j].length; k++){
                    itemPosition[i][j][k] = "";
                    //System.out.printf("[%d][%d][%d] -> %s\n", i, j, k, itemPosition[i][j][k]);
                }
            }
            //itemPosition[i] = new String[8][3];
        }
    }

    public void updateModbusSettings(Store s) {

        // Find all the possible strings from the store
        list = s.getModbusOptions();

        // With the current list we now update the Map
        // To default, all items will be false
        items = new HashMap<>();

        if (!list.isEmpty()) {
            for (String st : list) {
                items.put(st, false);
            }
        }

    }

    public void updateKey(String key, Boolean used, int meter, int slave, int register) {

        if (!items.isEmpty()) {
            System.out.println("Updated " + key + " from "
                    + String.valueOf(items.get(key))
                    + " to " + String.valueOf(used));

            items.replace(key, used);
            itemPosition[meter][slave][register] = key;
            
        }
    }
    
    public void removeKey(String key, Boolean used, int meter, int slave, int register) {

        if (!items.isEmpty()) {
            System.out.println("Removed " + key + " from "
                    + String.valueOf(items.get(key))
                    + " to " + String.valueOf(used));

            items.replace(key, used);
            itemPosition[meter][slave][register] = key;
            
        }
    }

    public boolean checkKey(String key) {        
        return (boolean) items.get(key);
    }

    public int numUnusedKeys() {
        int n = 0;
        
        if(!items.isEmpty()){
            for(Boolean b: items.values()){
                if(!b){
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
        if(!items.isEmpty()){
            for(Map.Entry<String, Boolean> entry: items.entrySet()){
                if(!entry.getValue()){
                    cm.addElement(entry.getKey());
                }
            }
        }

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
