package panel.creator;

import java.util.ArrayList;


/**
 * Store will contain all information for the store
 * @author EricGummerson
 */
public class Store implements java.io.Serializable{
    
    private static final long serialVersionUID = 222L;
    public String storeName;
    public String imgStr;
    public int numRacks;
    public ArrayList<Rack> racks;
    
    public Store() {
        this.storeName = "";
        racks = new ArrayList<>();
        racks.add(new Rack("Rack 1"));
        numRacks = 1;
        imgStr = "";
        storeName = "";
    }

    public Store(String storeName) {
        this.storeName = storeName;
        racks = new ArrayList<>();
        racks.add(new Rack("Rack 1"));
        numRacks = 1;
        imgStr = "";
        storeName = "";
    }

    
    
    public Store(String storeName, String imgStr, int numRacks, ArrayList<Rack> racks) {
        this.storeName = storeName;
        this.imgStr = imgStr;
        this.numRacks = numRacks;
        this.racks = racks;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public int getNumRacks() {
        return numRacks;
    }

    public void setNumRacks(int numRacks) {
        this.numRacks = numRacks;
    }

    public ArrayList<Rack> getRacks() {
        return racks;
    }
    
    public void setRackIndex(int index, Rack r){
        racks.set(index, r);
    }
    
    public Rack getRackIndex(int index){
        return racks.get(index);
    }

    public void setRacks(ArrayList<Rack> racks) {
        this.racks = racks;
    }
    
    public void addRack(Rack r){
        this.racks.add(r);
    }
    
    public String getRackName(int index){
        return this.racks.get(index).getName();
    }
    
    public int getRackNumComp(int index){
        return this.racks.get(index).getNumCompressors();
    }
    
    public int getRackNumFan(int index){
        return this.racks.get(index).getNumCondenserFans();
    }
    
    /**
     * gets the rack names from the array list
     *
     * @return string array of the rack names
     */
    public String[] getRackNames() {
        String[] names = new String[numRacks];
        for (int i = 0; i < numRacks; i++) {
            names[i] = racks.get(i).getName();
        }
        return names;
    }
    
    
}
