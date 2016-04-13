package Creator;

import java.util.ArrayList;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 * Rack class holds information about the rack update
 *
 * @author EricGummerson
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Rack implements java.io.Serializable {

    private static final long serialVersionUID = 022L;
    public String name;
    public int numSuctionGroups;
    public int numCondenserFans;    
    public int compVFD;
    public boolean condVFD;
    public boolean condSplit;
    public ArrayList<SuctionGroup> suctionGroup;

    /**
     * Empty contructor
     */
    public Rack() {
        this.name = "Rack _";
        this.compVFD = -1;
        this.condSplit = false;
        this.condVFD = false;
        this.numSuctionGroups = 1;
        this.numCondenserFans = 1;
        this.suctionGroup = new ArrayList<>();
        this.suctionGroup.add(new SuctionGroup("SGr1()"));
    }

    /**
     * Rack constructor only reqires a name
     *
     * @param name String name
     */
    public Rack(String name) {
        this.name = name;
        this.compVFD = -1;
        this.condSplit = false;
        this.condVFD = false;
        this.numSuctionGroups = 1;
        this.numCondenserFans = 1;
        this.suctionGroup = new ArrayList<>();
        this.suctionGroup.add(new SuctionGroup("SGr1()"));

    }

    /**
     * to string method
     *
     * @return a string of object information
     */
    @Override
    public String toString() {
        return "Rack{" + "name=" + name + ", numSuctionGroups=" + numSuctionGroups
                + ", numCondenserFans=" + numCondenserFans                
                + "\nsuctionGroup=" + suctionGroup + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + this.numSuctionGroups;
        hash = 11 * hash + this.numCondenserFans;
        hash = 11 * hash + this.compVFD;
        hash = 11 * hash + (this.condVFD ? 1 : 0);
        hash = 11 * hash + (this.condSplit ? 1 : 0);
        hash = 11 * hash + Objects.hashCode(this.suctionGroup);
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
        final Rack other = (Rack) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.numSuctionGroups != other.numSuctionGroups) {
            return false;
        }
        if (this.numCondenserFans != other.numCondenserFans) {
            return false;
        }
        if (this.compVFD != other.compVFD) {
            return false;
        }
        if (this.condVFD != other.condVFD) {
            return false;
        }
        if (this.condSplit != other.condSplit) {
            return false;
        }
        if (!Objects.equals(this.suctionGroup, other.suctionGroup)) {
            return false;
        }
        return true;
    }
    
    
    

    //===================================================
    //                  getters and setters
    //===================================================
    /**
     * get name
     *
     * @return string name of rack
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name
     *
     * @param name string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get number of suction groups
     *
     * @return int
     */
    public int getNumSuctionGroups() {
        return numSuctionGroups;
    }

    /**
     * set number of suction groups
     *
     * @param numSuctionGroups int
     */
    public void setNumSuctionGroups(int numSuctionGroups) {        
        if (numSuctionGroups > this.numSuctionGroups) {
            this.numSuctionGroups = numSuctionGroups;            
            this.addSuctionGroup(numSuctionGroups);
        }
    }

    /**
     * get number of condenser fans
     *
     * @return int
     */
    public int getNumCondenserFans() {
        return numCondenserFans;
    }

    /**
     * set number of condenser fans
     *
     * @param numCondenserFans int
     */
    public void setNumCondenserFans(int numCondenserFans) {
        this.numCondenserFans = numCondenserFans;
    }

    /**
     * get suction group as an arraylist
     *
     * @return ArrayList of suctiongroups
     */
    public ArrayList<SuctionGroup> getSuctionGroup() {
        return suctionGroup;
    }

    /**
     * get suction group index
     *
     * @param index int is the index to find a suctiongroup from an arraylist
     * @return SuctionGroup at the index
     */
    public SuctionGroup getSuctionGroupIndex(int index) {
        try {
            if (suctionGroup.get(index) != null) {
                return suctionGroup.get(index);
            } else {
                System.out.println("Suction group at " + index + " does not exist.");
                return null;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds for suction group on rack " + this.getName());
            return null;
        }
    }

    /**
     * set suction group arraylist
     *
     * @param suctionGroup ArrayList of suctiongroup
     */
    public void setSuctionGroup(ArrayList<SuctionGroup> suctionGroup) {
        this.suctionGroup = suctionGroup;
    }

    /**
     * set suction group at index
     *
     * @param suctionGroup suctiongroup
     * @param index int
     */
    public void setSuctionGroupIndex(SuctionGroup suctionGroup, int index) {
        if (suctionGroup != null) {
            this.suctionGroup.set(index, suctionGroup);
        } else {
            System.out.println("Suction group was null, trying to be added to index " + index);
        }
    }

    /**
     * gets the suctiongroup name at index
     *
     * @param index int
     * @return String suctiongroup name at index
     */
    public String getSuctionGroupNameIndex(int index) {
        String name;
        if (this.suctionGroup.get(index) != null) {
            name = this.suctionGroup.get(index).getName();
        } else {
            name = "Name not found";
        }
        return name;
    }

    /**
     * gets the list of suctiongroup names
     *
     * @return string array of the suction group names
     */
    public String[] getSuctionGroupNames() {
        String[] names = new String[this.numSuctionGroups];

        for (int i = 0; i < this.numSuctionGroups; i++) {
            names[i] = this.suctionGroup.get(i).getName();
        }

        return names;
    }

    /**
     * add a suctiongroup to the list
     *
     * @param num
     */
    public void addSuctionGroup(int num) {
        int size = this.suctionGroup.size();
        int toAdd = num - size;

        for (int i = 0; i < toAdd; i++) {
            //System.out.println("Adding Suction Group " + (size + i+1));
            this.suctionGroup.add(new SuctionGroup("SGr" + (size + i + 1)+"()"));
        }
    }

    /**
     * get the number of compressors
     *
     * @return int
     */
    public int getNumCompressors() {
        int numComp = 0;
        for (int i = 0; i < this.numSuctionGroups; i++) {
            numComp += this.getSuctionGroupIndex(i).getNumCompressors();
        }
        return numComp;
    }

    /**
     * get number of systems
     *
     * @return int
     */
    public int getNumSystems() {
        int numSys = 0;
        for (int i = 0; i < this.numSuctionGroups; i++) {
            numSys += this.getSuctionGroupIndex(i).getNumSystems();
        }
        return numSys;
    }

    public int getCompVFD() {
        return compVFD;
    }

    public void setCompVFD(int compVFD) {
        this.compVFD = compVFD;
    }

    public boolean isCondVFD() {
        return condVFD;
    }

    public void setCondVFD(boolean condVFD) {
        this.condVFD = condVFD;
    }

    public boolean isCondSplit() {
        return condSplit;
    }

    public void setCondSplit(boolean condSplit) {
        this.condSplit = condSplit;
    }

    
    
    
}
