package panel.creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author EricGummerson
 */
public class ControlSettings implements java.io.Serializable {

    private static final long serialVersionUID = 2234L;
    public String storeName;
    public String imgStr;
    public int numRacks;
    public ArrayList<Rack> racks;

    public ControlSettings() {
        this.storeName = "New Store";
        racks = new ArrayList<>();
        racks.add(new Rack("Rack 1"));
        numRacks = 1;
        imgStr = "";
        storeName = "";

    }

    public ControlSettings(String storeName) {
        this.storeName = storeName;
        racks = new ArrayList<>();
        racks.add(new Rack("Rack 1"));
        numRacks = 1;
        imgStr = "";
        storeName = "";

    }

    public ControlSettings(String storeName, String imgStr, int numRacks, ArrayList<Rack> racks) {
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

    public void setRackIndex(int index, Rack r) {
        racks.set(index, r);
    }

    public Rack getRackIndex(int index) {
        return racks.get(index);
    }

    public void setRacks(ArrayList<Rack> racks) {
        this.racks = racks;
    }

    public void addRack(Rack r) {
        this.racks.add(r);
    }

    public String getRackName(int index) {
        return this.racks.get(index).getName();
    }

    public int getRackNumComp(int index) {
        return this.racks.get(index).getNumCompressors();
    }

    public int getRackNumFan(int index) {
        return this.racks.get(index).getNumCondenserFans();
    }

    public String getSgNameIndex(int rackIndex, int sgIndex) {
        return this.racks.get(rackIndex).getSuctionGroupNameIndex(sgIndex);
    }

    public String getCompNameIndex(int rackIndex, int sgIndex, int compIndex) {
        return this.racks.get(rackIndex).getSuctionGroupIndex(sgIndex).getCompressorNameIndex(compIndex);
    }

    public String getSysNameIndex(int rackIndex, int sgIndex, int sysIndex) {
        return this.racks.get(rackIndex).getSuctionGroupIndex(sgIndex).getSystemNameIndex(sysIndex);
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

    public ArrayList<String> getModbusOptions() {
        ArrayList<String> list = new ArrayList<>();
        Rack r;
        SuctionGroup sg;
        for (int i = 0; i < getNumRacks(); i++) {
            r = getRacks().get(i);
            list.add(r.getName());
            list.add(r.getName() + " Condenser");
            for (int j = 0; j < r.getNumSuctionGroups(); j++) {
                sg = r.getSuctionGroupIndex(j);
                for (int k = 0; k < sg.getNumCompressors(); k++) {
                    String name = r.getName() + " " + sg.getName() + " " + sg.getCompressorNameIndex(k);
                    list.add(name);
                }
            }
        }
        
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        
        return list;
    }

}
