package panel.creator;

import java.util.ArrayList;

/**
 * Rack class holds information about the rack update
 *
 * @author EricGummerson
 */
public class Rack implements java.io.Serializable {

    private static final long serialVersionUID = 022L;
    public String name;
    public int numSuctionGroups;
    public int numCondenserFans;
    public int maxSuctionGroups;
    public ArrayList<SuctionGroup> suctionGroup;

    /**
     * Empty contructor
     */
    public Rack() {
    }

    /**
     * Rack constructor only reqires a name
     *
     * @param name String name
     */
    public Rack(String name) {
        this.name = name;
        this.numSuctionGroups = 1;
        this.numCondenserFans = 1;
        this.suctionGroup = new ArrayList<>();
        this.suctionGroup.add(new SuctionGroup("Suction Group 1"));

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
                + ", maxSuctionGroups=" + maxSuctionGroups
                + "\nsuctionGroup=" + suctionGroup + '}';
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
        this.numSuctionGroups = numSuctionGroups;
        if (numSuctionGroups > this.maxSuctionGroups) {
            this.maxSuctionGroups = numSuctionGroups;
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
     * gets the max number of suction groups
     *
     * @return int
     */
    public int getMaxSuctionGroups() {
        return maxSuctionGroups;
    }

    /**
     * sets the max number of suction groups
     *
     * @param maxSuctionGroups int
     */
    public void setMaxSuctionGroups(int maxSuctionGroups) {
        this.maxSuctionGroups = maxSuctionGroups;
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
            this.suctionGroup.add(new SuctionGroup("Suction Group " + (size + i + 1)));
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

}
