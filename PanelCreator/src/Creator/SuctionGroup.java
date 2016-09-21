package Creator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
/**
 * Suction group contains suction group info
 *
 * @author EricGummerson
 */


@XmlAccessorType(XmlAccessType.FIELD)
public class SuctionGroup implements java.io.Serializable {

    //private static final long serialVersionUID = 122L;
    public String name;
    public int numCompressors;
    public int numSystems;
    public ArrayList<Integer> compVFD_Index;
    public boolean compVFDActive;
    public ArrayList<String> systemNames;
    public ArrayList<String> compressorNames;
    public TreeMap<String, Circuit> subSystems;

    
    
    public SuctionGroup(){
        this.name = "";
        this.numCompressors = 1;
        this.numSystems = 1;
        this.compVFDActive = false;
        this.compVFD_Index = new ArrayList<>();
        this.compVFD_Index.add(-1);
        this.systemNames = new ArrayList<>();
        this.systemNames.add("System 1");
        this.subSystems = new TreeMap<>();
        this.subSystems.put("System 1", new Circuit("System 1", 0));
        this.compressorNames = new ArrayList<>();
        this.compressorNames.add("Comp 1");
    }
    /**
     * SuctionGroup constructor needs a name
     *
     * @param name String
     */
    public SuctionGroup(String name) {
        this.name = name;
        this.numCompressors = 1;
        this.numSystems = 1;
        this.compVFDActive = false;
        this.compVFD_Index = new ArrayList<>();
        this.compVFD_Index.add(-1);
        this.systemNames = new ArrayList<>();
        this.systemNames.add("System 1");
        this.subSystems = new TreeMap<>();
        this.subSystems.put("System 1", new Circuit("System 1", 0));
        this.compressorNames = new ArrayList<>();
        this.compressorNames.add("Comp 1");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + this.numCompressors;
        hash = 17 * hash + this.numSystems;
        hash = 17 * hash + Objects.hashCode(this.systemNames);
        hash = 17 * hash + Objects.hashCode(this.compressorNames);
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
        final SuctionGroup other = (SuctionGroup) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.numCompressors != other.numCompressors) {
            return false;
        }
        if (this.numSystems != other.numSystems) {
            return false;
        }
        if (!Objects.equals(this.systemNames, other.systemNames)) {
            return false;
        }
        if (!Objects.equals(this.compressorNames, other.compressorNames)) {
            return false;
        }
        return true;
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

    public TreeMap<String, Circuit> getSubSystems() {
        return subSystems;
    }

    public void setSubSystems(TreeMap<String, Circuit> subSystems) {
        this.subSystems = subSystems;
    }
    
    public ArrayList<String> getAllSystemNames(String systemName){
                
        if(this.subSystems.containsKey(systemName)){
            ArrayList<String> list = this.subSystems.get(systemName).getSubSystemNames();
            list.add(0, systemName);   
            return list;
        }else {
            return null;
        }                
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
        
        for(String s: this.systemNames){
            if(!this.subSystems.containsKey(s)){
                this.subSystems.put(s, new Circuit(s));
            }
        }
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
                this.subSystems.put("System " + (size + i + 1), new Circuit("System " + (size + i + 1), 0));
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
        String oldName = this.systemNames.get(index);
        Circuit oldSubNames = null;
        if(this.systemNames.contains(oldName)){
            oldSubNames = this.subSystems.remove(oldName);  
        }
        if(oldSubNames == null){
            oldSubNames = new Circuit(name);
        }
        
        this.systemNames.set(index, name);
        
        oldSubNames.updateNames(name);
        
        this.subSystems.put(name, oldSubNames);
    }
    
    public void replaceSubSystemName(String systemName, String subSystemName, int index){
        if(this.subSystems.containsKey(systemName)){
            this.subSystems.get(systemName).replaceSubSystemName(subSystemName, index);
        }else {
            System.out.println(systemName + " not found in sub system map");
        }
        
    }

    
    public void updateSubSystems(){
        
        for(String s: this.systemNames){
            if(!this.subSystems.containsKey(s)){
                this.subSystems.put(s, new Circuit(s));
            }
        }
        
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
    
    public void setNumSubSystems(String systemName, int numSubSystems){
        
        if(this.subSystems.containsKey(systemName)){
            this.subSystems.get(systemName).setNumSubSystems(numSubSystems);
        }else {
            System.out.println("no subsystem " + systemName);
            this.subSystems.put(systemName, new Circuit(systemName, numSubSystems));
        }
        
    }
    
    public int getNumSubSystems(String systemName){
        if(this.subSystems.containsKey(systemName)){
            return this.subSystems.get(systemName).getNumSubSystems();
        }else {
            return 0;
        }
    }

    
    public String getSubSystemNameIndex(String systemName, int index){
        if(this.subSystems.containsKey(systemName)){
            return this.subSystems.get(systemName).getSubSystemName(index);
        }else {
            return "No system found " + systemName;
        }
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

    
    public ArrayList<Integer> getCompVFD() {
        return compVFD_Index;
    }
    
    public int getCompVFDIndex(int index){
        return compVFD_Index.get(index);
    }

    public void setCompVFD(ArrayList<Integer> compVFD) {
        this.compVFD_Index = compVFD;
    }
    
    public void setCompVFDIndex(int compVFD, int index) {
        this.compVFD_Index.set(index, compVFD);
    }
    
    public void addCompVFD(int compVFD){
        this.compVFD_Index.add(compVFD);
    }
    
    public boolean isCompVFDActive() {
        return compVFDActive;
    }

    public void setCompVFDActive(boolean compVFDActive) {
        this.compVFDActive = compVFDActive;
    }
    
    public boolean checkCompressorIsVFD(int index){
        return this.compVFD_Index.contains(index);
    }
}
