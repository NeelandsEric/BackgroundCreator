package panel.creator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Store will contain all information for the store
 *
 * @author EricGummerson
 */
public class Store implements java.io.Serializable {

    private static final long serialVersionUID = 222L;
    public String storeName;
    public String imgStr;
    public int numRacks;
    public ArrayList<Rack> racks;
    
    ArrayList<String> storeStr;
    ArrayList<String> rackStr;
    ArrayList<String> condStr;
    ArrayList<String> sgStr;
    ArrayList<String> compStr;
    ArrayList<String> sysStr;
    ArrayList<String> extraStr;

    public Store() {
        this.storeName = "";
        racks = new ArrayList<>();
        racks.add(new Rack("Rack 1"));
        numRacks = 1;
        imgStr = "";
        storeName = "";
        
        storeStr = new ArrayList<>();
        rackStr = new ArrayList<>();
        condStr = new ArrayList<>();
        sgStr = new ArrayList<>();
        compStr = new ArrayList<>();
        sysStr = new ArrayList<>();
        extraStr = new ArrayList<>();
    }

    public Store(String storeName) {
        this.storeName = storeName;
        racks = new ArrayList<>();
        racks.add(new Rack("Rack 1"));
        numRacks = 1;
        imgStr = "";
        storeName = "";
        
        storeStr = new ArrayList<>();
        rackStr = new ArrayList<>();
        condStr = new ArrayList<>();
        sgStr = new ArrayList<>();
        compStr = new ArrayList<>();
        sysStr = new ArrayList<>();
        extraStr = new ArrayList<>();
    }

    public Store(String storeName, String imgStr, int numRacks, ArrayList<Rack> racks) {
        this.storeName = storeName;
        this.imgStr = imgStr;
        this.numRacks = numRacks;
        this.racks = racks;
        
        storeStr = new ArrayList<>();
        rackStr = new ArrayList<>();
        condStr = new ArrayList<>();
        sgStr = new ArrayList<>();
        compStr = new ArrayList<>();
        sysStr = new ArrayList<>();
        extraStr = new ArrayList<>();
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

    public ArrayList<String> getStoreStr() {
        return storeStr;
    }

    public void setStoreStr(ArrayList<String> storeStr) {
        this.storeStr = storeStr;
    }

    public ArrayList<String> getRackStr() {
        return rackStr;
    }

    public void setRackStr(ArrayList<String> rackStr) {
        this.rackStr = rackStr;
    }

    public ArrayList<String> getCondStr() {
        return condStr;
    }

    public void setCondStr(ArrayList<String> condStr) {
        this.condStr = condStr;
    }

    public ArrayList<String> getSgStr() {
        return sgStr;
    }

    public void setSgStr(ArrayList<String> sgStr) {
        this.sgStr = sgStr;
    }

    public ArrayList<String> getCompStr() {
        return compStr;
    }

    public void setCompStr(ArrayList<String> compStr) {
        this.compStr = compStr;
    }

    public ArrayList<String> getSysStr() {
        return sysStr;
    }

    public void setSysStr(ArrayList<String> sysStr) {
        this.sysStr = sysStr;
    }

    public ArrayList<String> getExtraStr() {
        return extraStr;
    }

    public void setExtraStr(ArrayList<String> extraStr) {
        this.extraStr = extraStr;
    }

    
    public String getSgNameIndex(int rackIndex, int sgIndex){
        return this.racks.get(rackIndex).getSuctionGroupNameIndex(sgIndex);
    }
    
    public String getCompNameIndex(int rackIndex, int sgIndex, int compIndex){
        return this.racks.get(rackIndex).getSuctionGroupIndex(sgIndex).getCompressorNameIndex(compIndex);
    }
    
     public String getSysNameIndex(int rackIndex, int sgIndex, int sysIndex){
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

    public void readCSV(String filepath) {

        try {
            CSVReader reader = new CSVReader(new FileReader(filepath));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void writeCSV(String filepath) {

        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filepath), ',');
            // feed in your array (or convert your data to an array)           
            
            writer.writeAll(formatStrings());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    public List<String []> formatStrings(){
        
        List<String []> vars = new ArrayList<String[]>() {};
        int numfans, numsg, numcomp, numsys;
        String [] newString;
        String rName, sgName, compName, sysName;
        
        for(int i = 0; i < numRacks; i++){
            // do all racks
            rName = racks.get(i).getName();
            
            for(String s: rackStr){
                newString = new String[1];                
                s = s.replaceAll("`%rackname`", rName);
                newString[0] = s;
                System.out.println(newString[0]);
                vars.add(newString);
            }
            
            
            
            // CONDENSERS
            numfans = this.racks.get(i).getNumCondenserFans();
            for(int nf = 0; nf < numfans; nf++){
                
                // do all condenser                
                
            }
            
            // SUCTION GROUPS
            
            numsg = this.racks.get(i).getNumSuctionGroups();
            for(int sg = 0; sg < numsg; sg++){
                
                // do all suction groups
                
                
                // COMPRESSORS
                numcomp = this.racks.get(i).getSuctionGroupIndex(i).getNumCompressors();                
                for(int nc = 0; nc < numcomp; nc++){
                    
                    // do all compressors
                }
                
                // SYSTEMS
                numsys = this.racks.get(i).getSuctionGroupIndex(i).getNumSystems();                
                for(int ns = 0; ns < numsys; ns++){
                    
                    // do all systems
                    
                }
                
            }
        }
        
        return vars;
        
    }
    
}
