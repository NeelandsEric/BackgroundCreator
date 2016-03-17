package Creator;

import java.util.ArrayList;
import java.util.Collections;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Control settings contains all information regarding the store layout. Name,
 * number of racks, rack settings
 *
 * @author EricGummerson
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ControlSettings implements java.io.Serializable {

    private static final long serialVersionUID = 2234L;
    public String storeName;
    public String imgStr;
    public int numRacks;
    public ArrayList<Rack> racks;

    /**
     * Default Constructor
     */
    public ControlSettings() {
        this.storeName = "Store Name";
        racks = new ArrayList<>();
        racks.add(new Rack("Rack A"));
        numRacks = 1;
        imgStr = "";
    }

    /**
     * Default constructor but takes one parameter, Store Name
     *
     * @param storeName String
     */
    public ControlSettings(String storeName) {
        this.storeName = storeName;
        racks = new ArrayList<>();
        racks.add(new Rack("Rack A"));
        numRacks = 1;
        imgStr = "";
    }

    /**
     * Takes all parameters needed to initalize the control settings class
     *
     *
     * @param storeName String Name of the store
     * @param imgStr String Path of the image used
     * @param numRacks int Number of active racks (different that the size of
     * racks
     * @param racks List<Racks> List of racks
     */
    public ControlSettings(String storeName, String imgStr, int numRacks, ArrayList<Rack> racks) {
        this.storeName = storeName;
        this.imgStr = imgStr;
        this.numRacks = numRacks;
        this.racks = racks;
    }

    //-------------------------------------------------------------
    //--------------- Get/Set for class variables -----------------
    //-------------------------------------------------------------
    /**
     * Get store name
     *
     * @return String name of store
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Set store name
     *
     * @param storeName String name of store
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * Get image string
     *
     * @return String file path of the image being used
     */
    public String getImgStr() {
        return imgStr;
    }

    /**
     * Set image string
     *
     * @param imgStr String file path of image
     */
    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    /**
     * Get number of active racks
     *
     * @return int Number of active racks
     */
    public int getNumRacks() {
        return numRacks;
    }

    /**
     * Set number of active racks
     *
     * @param numRacks int Number of active racks
     */
    public void setNumRacks(int numRacks) {
        this.numRacks = numRacks;
    }

    /**
     * Get list of racks
     *
     * @return ArrayList<Rack>
     */
    public ArrayList<Rack> getRacks() {
        return racks;
    }

    /**
     * Add a rack to the list at an index
     *
     * @param index int Rack index in the arraylist
     * @param r Rack to be added
     */
    public void setRackIndex(int index, Rack r) {
        racks.set(index, r);
    }

    /**
     * Get the rack at index
     *
     * @param index int Rack index in the arraylist
     * @return Rack at index
     */
    public Rack getRackIndex(int index) {
        return racks.get(index);
    }

    /**
     * Set the rack list
     *
     * @param racks ArrayList<Rack>
     */
    public void setRacks(ArrayList<Rack> racks) {
        this.racks = racks;
    }

    /**
     * Add a rack to the list
     *
     * @param r Rack to be added
     */
    public void addRack(Rack r) {
        this.racks.add(r);
    }

    /**
     * Get rack name at index
     *
     * @param index int Rack index in the arraylist
     * @return String name of rack
     */
    public String getRackName(int index) {
        return this.racks.get(index).getName();
    }

    /**
     * Get number of compressors on rack at index
     *
     * @param index int Rack index in the arraylist
     * @return int number of compressors on rack at the index
     */
    public int getRackNumComp(int index) {
        return this.racks.get(index).getNumCompressors();
    }

    /**
     * Get number of condenser fans on rack at index
     *
     * @param index int Rack index in the arraylist
     * @return int number of condenser fans
     */
    public int getRackNumFan(int index) {
        return this.racks.get(index).getNumCondenserFans();
    }

    /**
     * Get name of suction group in a rack
     *
     * @param rackIndex int Rack index in the arraylist
     * @param sgIndex int SuctionGroup index in the arraylist
     * @return
     */
    public String getSgNameIndex(int rackIndex, int sgIndex) {
        return this.racks.get(rackIndex).getSuctionGroupNameIndex(sgIndex);
    }

    /**
     * Get compressor name
     *
     * @param rackIndex int Rack index in the arraylist
     * @param sgIndex int SuctionGroup index in the arraylist
     * @param compIndex int Compressor index in the arraylist
     * @return
     */
    public String getCompNameIndex(int rackIndex, int sgIndex, int compIndex) {
        return this.racks.get(rackIndex).getSuctionGroupIndex(sgIndex).getCompressorNameIndex(compIndex);
    }

    /**
     *
     * @param rackIndex int Rack index in the arraylist
     * @param sgIndex int SuctionGroup index in the arraylist
     * @param sysIndex int System index in the arraylist
     * @return
     */
    public String getSysNameIndex(int rackIndex, int sgIndex, int sysIndex) {
        return this.racks.get(rackIndex).getSuctionGroupIndex(sgIndex).getSystemNameIndex(sysIndex);
    }

    /**
     * Get number of suction groups in a rack
     *
     * @param rackIndex int Rack index in the arraylist
     * @return int number of suction groups
     */
    public int getRackNumSG(int rackIndex) {
        return this.racks.get(rackIndex).getNumSuctionGroups();
    }

    /**
     * Get total number of systems over all racks
     *
     * @return int number of systems
     */
    public int getTotalSystems() {
        int total = 0;
        for (int i = 0; i < numRacks; i++) {
            total += this.getRackNumComp(i);
        }
        return total;
    }

    /**
     * Gets the total number of suction groups over all racks
     *
     * @return int number of suction groups
     */
    public int getTotalSG() {
        int total = 0;
        for (int i = 0; i < numRacks; i++) {
            total += this.getRackNumSG(i);
        }
        return total;
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

    /**
     * Gets the strings needed for the modbus settings This will return every
     * compressor + 1 condenser & rack variable for each rack
     *
     * @return ArrayList<String> list of strings to be used as a model list for
     * the modbus
     */
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

        Collections.sort(list, (String s1, String s2) -> s1.compareToIgnoreCase(s2));

        return list;
    }

}
