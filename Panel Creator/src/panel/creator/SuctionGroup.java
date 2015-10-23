package panel.creator;

import java.util.ArrayList;

/**
 * Suction group contains suction group info
 *
 * @author EricGummerson
 */
public class SuctionGroup implements java.io.Serializable {

    private static final long serialVersionUID = 122L;
    public String name;
    public int numCompressors;
    public int numSystems;
    public ArrayList<String> systemNames;
    public ArrayList<String> compressorNames;

    /**
     * SuctionGroup constructor needs a name
     *
     * @param name String
     */
    public SuctionGroup(String name) {
        this.name = name;
        this.numCompressors = 1;
        this.numSystems = 1;
        this.systemNames = new ArrayList<>();
        this.systemNames.add("System 1");
        this.compressorNames = new ArrayList<>();
        this.compressorNames.add("Comp 1");
    }

    /**
     * converts the object to a string
     *
     * @return
     */
    @Override
    public String toString() {
        return "SuctionGroup{" + "name=" + name + ", numCompressors=" + numCompressors
                + "\nnumSystems=" + numSystems + "\nsystemNames="
                + systemNames + "\ncompressorNames=" + compressorNames + '}';
    }

    //===================================================
    //                  Getters and Setters
    //===================================================
    /**
     * get the name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * set the name
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get number compressors
     *
     * @return int
     */
    public int getNumCompressors() {
        return numCompressors;
    }

    /**
     * set the number of compressors
     *
     * @param numCompressors int
     */
    public void setNumCompressors(int numCompressors) {
        this.numCompressors = numCompressors;
    }

    /**
     * get number of systems
     *
     * @return int
     */
    public int getNumSystems() {
        return numSystems;
    }

    /**
     * set number of systems
     *
     * @param numSystems int
     */
    public void setNumSystems(int numSystems) {
        this.numSystems = numSystems;
    }

    /**
     * get system names
     *
     * @return ArrayList of strings
     */
    public ArrayList<String> getSystemNames() {
        return systemNames;
    }

    /**
     * get system name index
     *
     * @param index int
     * @return String system name at index
     */
    public String getSystemNameIndex(int index) {
        if (systemNames.size() > index) {
            return systemNames.get(index);
        } else {
            //System.out.println("System index " + index + " does not exist");
            return "System " + (index + 1);
        }
    }

    /**
     * set system names
     *
     * @param systemNames arraylist of strings
     */
    public void setSystemNames(ArrayList<String> systemNames) {
        this.systemNames = systemNames;
    }

    /**
     * add system names
     *
     * @param num int
     */
    public void addSystemNames(int num) {
        int size = this.systemNames.size();
        int toAdd = num - size;

        if (toAdd > 0) {
            for (int i = 0; i < toAdd; i++) {
                //System.out.println("Adding System " + (size + i+1));
                this.systemNames.add("System " + (size + i + 1));
                this.numSystems++;
            }
        } else {
            this.numSystems = num;
        }

    }

    /**
     * replace system name
     *
     * @param name string
     * @param index int
     */
    public void replaceSystemName(String name, int index) {
        this.systemNames.set(index, name);
    }

    /**
     * get compressor names
     *
     * @return arraylist of strings
     */
    public ArrayList<String> getCompressorNames() {
        return compressorNames;
    }

    /**
     * get compressor name at index
     *
     * @param index int
     * @return string of compressor name at index
     */
    public String getCompressorNameIndex(int index) {
        if (compressorNames.size() > index) {
            return compressorNames.get(index);
        } else {
            //System.out.println("System index " + index + " does not exist");
            return "Comp " + (index + 1);
        }
    }

    /**
     * set the compressor names
     *
     * @param compressorNames arraylist of strings
     */
    public void setCompressorNames(ArrayList<String> compressorNames) {
        this.compressorNames = compressorNames;
    }

    /**
     * add compressor names
     *
     * @param num int
     */
    public void addCompressorNames(int num) {
        int size = this.compressorNames.size();
        int toAdd = num - size;
        if (toAdd > 0) {
            for (int i = 0; i < toAdd; i++) {
                //System.out.println("Adding System " + (size + i+1));
                this.compressorNames.add("Comp " + (size + i + 1));
                this.numCompressors++;
            }
        } else {
            this.numCompressors = num;
        }
    }

    /**
     * replace compressor name at index
     *
     * @param name string
     * @param index int
     */
    public void replaceCompressorName(String name, int index) {
        this.compressorNames.set(index, name);
    }

}
