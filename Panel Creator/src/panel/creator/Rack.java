/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.util.ArrayList;

/**
 * Rack class holds information about the rack
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

    public Rack() {
    }

    public Rack(String name) {
        this.name = name;
        this.numSuctionGroups = 1;
        this.numCondenserFans = 1;
        this.suctionGroup = new ArrayList<>();
        this.suctionGroup.add(new SuctionGroup("Suction Group 1"));

    }

    @Override
    public String toString() {
        return "Rack{" + "name=" + name + ", numSuctionGroups=" + numSuctionGroups
                + ", numCondenserFans=" + numCondenserFans + 
                ", maxSuctionGroups=" + maxSuctionGroups + 
                "\nsuctionGroup=" + suctionGroup + '}';
    }

    //===================================================
    //                  getters and setters
    //===================================================
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumSuctionGroups() {
        return numSuctionGroups;
    }

    public void setNumSuctionGroups(int numSuctionGroups) {
        this.numSuctionGroups = numSuctionGroups;
        if (numSuctionGroups > this.maxSuctionGroups) {
            this.maxSuctionGroups = numSuctionGroups;
            this.addSuctionGroup(numSuctionGroups);
        }
    }

    public int getNumCondenserFans() {
        return numCondenserFans;
    }

    public void setNumCondenserFans(int numCondenserFans) {
        this.numCondenserFans = numCondenserFans;
    }

    public ArrayList<SuctionGroup> getSuctionGroup() {
        return suctionGroup;
    }

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

    public void setSuctionGroup(ArrayList<SuctionGroup> suctionGroup) {
        this.suctionGroup = suctionGroup;
    }

    public void setSuctionGroupIndex(SuctionGroup suctionGroup, int index) {
        if (suctionGroup != null) {
            this.suctionGroup.set(index, suctionGroup);
        } else {
            System.out.println("Suction group was null, trying to be added to index " + index);
        }
    }

    public int getMaxSuctionGroups() {
        return maxSuctionGroups;
    }

    public void setMaxSuctionGroups(int maxSuctionGroups) {
        this.maxSuctionGroups = maxSuctionGroups;
    }

    public String getSuctionGroupNameIndex(int index) {
        String name;
        if (this.suctionGroup.get(index) != null) {
            name = this.suctionGroup.get(index).getName();
        } else {
            name = "Name not found";
        }
        return name;
    }

    public String[] getSuctionGroupNames() {
        String[] names = new String[this.numSuctionGroups];

        for (int i = 0; i < this.numSuctionGroups; i++) {
            names[i] = this.suctionGroup.get(i).getName();
        }

        return names;
    }

    //===================================================
    //                  functions
    //===================================================
    public void addSuctionGroup(int num) {
        int size = this.suctionGroup.size();
        int toAdd = num - size;

        for (int i = 0; i < toAdd; i++) {
            //System.out.println("Adding Suction Group " + (size + i+1));
            this.suctionGroup.add(new SuctionGroup("Suction Group " + (size + i + 1)));
        }
    }

}
