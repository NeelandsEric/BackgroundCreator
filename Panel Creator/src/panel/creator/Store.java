package panel.creator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.awt.Font;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.Border;

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
    private ArrayList<String> storeStr;
    private ArrayList<String> rackStr;
    private ArrayList<String> condStr;
    private ArrayList<String> sgStr;
    private ArrayList<String> compStr;
    private ArrayList<String> sysStr;
    private ArrayList<String> extraStr;
    public DisplaySettings ds;

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

    public DisplaySettings getDs() {
        return ds;
    }

    public void setDs(DisplaySettings ds) {
        this.ds = ds;
    }

    public Font getCustomFont() {
        return ds.getFont();
    }

    public Border getCustomBorder() {
        return ds.getBorder();
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

    /**
     * reads a csv file
     * @param filepath filepath to the file
     */
    public void readCSV(String filepath) {

        try {
            CSVReader reader = new CSVReader(new FileReader(filepath));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
            }
        } catch (Exception ex) {
            System.out.println("Read csv error with " + filepath + " : " + ex.getMessage());
        }

    }

    
    public void writeCSV(String filepath) {

        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filepath), ',', CSVWriter.NO_QUOTE_CHARACTER);
            // feed in your array (or convert your data to an array)          

            writer.writeAll(formatStrings());
            writer.close();
        } catch (IOException ex) {
            System.out.println("Write csv error with " + filepath + " : " + ex.getMessage());
        }

    }

    public void writeNames(String filepath) {

        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filepath), ',', CSVWriter.NO_QUOTE_CHARACTER);
            // feed in your array (or convert your data to an array)          

            writer.writeAll(nameStrings());
            writer.close();
        } catch (IOException ex) {
            System.out.println("Write csv error with " + filepath + " : " + ex.getMessage());
        }

    }

    public List<String[]> formatStrings() {

        long start = System.currentTimeMillis(), end;
        List<String[]> vars = new ArrayList<String[]>() {
        };

        // Add header
        String[] headers = new String[]{"io_name", "io_type", "io_unit_of_measure",
            "io_constant", "io_offset", "io_float_digits",
            "io_alert", "io_alert_range_low", "io_alert_range_high",
            "rest modbus"
        };
        vars.add(headers);

        int numfans, numsg, numcomp, numsys;
        String[] newString;
        Rack r;
        String rName, sgName, fannum = "1";
        SuctionGroup sucG;
        String compName, sysName;

        for (int i = 0; i < numRacks; i++) {
            // do all racks
            r = racks.get(i);
            rName = r.getName();
            sgName = r.getSuctionGroupNameIndex(0);
            fannum = "1";
            sucG = r.getSuctionGroupIndex(0);
            compName = sucG.getCompressorNameIndex(0);
            sysName = sucG.getSystemNameIndex(0);

            // RACKS
            // do all condenser     
            for (String s : rackStr) {
                newString = s.split(",");
                newString[0] = newString[0]
                        .replace("`%rackname`", rName)
                        .replace("`%fannum`", fannum)
                        .replace("`%sgname`", sgName)
                        .replace("`%compname`", compName)
                        .replace("`%sysname`", sysName);

                //System.out.println("RACK - New string: " + newString[0] + "\tFrom old string: " + s);
                vars.add(newString);
            }

            // CONDENSERS
            numfans = r.getNumCondenserFans();
            for (int nf = 0; nf < numfans; nf++) {

                fannum = ('0' + String.valueOf((nf + 1)));
                if (nf < 99) {
                    fannum = fannum.substring(fannum.length() - 2);
                } else {
                    fannum = fannum.substring(fannum.length() - 3);
                }

                // do all condenser     
                for (String s : condStr) {
                    newString = s.split(",");
                    newString[0] = newString[0]
                            .replace("`%rackname`", rName)
                            .replace("`%fannum`", fannum)
                            .replace("`%sgname`", sgName)
                            .replace("`%compname`", compName)
                            .replace("`%sysname`", sysName);

                    //System.out.println("COND - New string: " + newString[0] + "\tFrom old string: " + s);
                    vars.add(newString);
                }
            }

            // SUCTION GROUPS
            numsg = r.getNumSuctionGroups();
            for (int sg = 0; sg < numsg; sg++) {
                sucG = r.getSuctionGroupIndex(sg);
                sgName = sucG.getName();
                for (String s : sgStr) {
                    newString = s.split(",");
                    newString[0] = newString[0]
                            .replace("`%rackname`", rName)
                            .replace("`%fannum`", fannum)
                            .replace("`%sgname`", sgName)
                            .replace("`%compname`", compName)
                            .replace("`%sysname`", sysName);

                    //System.out.println("SG - New string: " + newString[0] + "\tFrom old string: " + s);
                    vars.add(newString);
                }

                // do all suction groups
                // COMPRESSORS
                numcomp = sucG.getNumCompressors();
                for (int nc = 0; nc < numcomp; nc++) {
                    compName = sucG.getCompressorNameIndex(nc);
                    // do all compressors
                    for (String s : compStr) {
                        newString = s.split(",");
                        newString[0] = newString[0]
                                .replace("`%rackname`", rName)
                                .replace("`%fannum`", fannum)
                                .replace("`%sgname`", sgName)
                                .replace("`%compname`", compName)
                                .replace("`%sysname`", sysName);

                        //System.out.println("COMP - New string: " + newString[0] + "\tFrom old string: " + s);
                        vars.add(newString);
                    }
                }

                // SYSTEMS
                numsys = sucG.getNumSystems();
                for (int ns = 0; ns < numsys; ns++) {
                    sysName = sucG.getSystemNameIndex(ns);
                    // do all systems
                    for (String s : sysStr) {
                        newString = s.split(",");
                        newString[0] = newString[0]
                                .replace("`%rackname`", rName)
                                .replace("`%fannum`", fannum)
                                .replace("`%sgname`", sgName)
                                .replace("`%compname`", compName)
                                .replace("`%sysname`", sysName);

                        //System.out.println("SYS - New string: " + newString[0] + "\tFrom old string: " + s);
                        vars.add(newString);
                    }

                }

            }
        }

        for (String s : storeStr) {
            newString = s.split(",");

            //System.out.println("STORE - New string: " + newString[0] + "\tFrom old string: " + s);
            vars.add(newString);
        }

        for (String s : extraStr) {
            newString = s.split(",");

            //System.out.println("EXTRA - New string: " + newString[0] + "\tFrom old string: " + s);
            vars.add(newString);
        }

        end = System.currentTimeMillis();

        System.out.println("Format strings took " + ((end - start)) + " ms");
        return vars;

    }

    public void writeText(String filepath) {

        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filepath), ',', CSVWriter.NO_QUOTE_CHARACTER);
            // feed in your array (or convert your data to an array)          

            writer.writeAll(unformattedStrings());
            writer.close();
        } catch (IOException ex) {
            System.out.println("Write text error with " + filepath + " : " + ex.getMessage());
        }

    }

    public List<String[]> unformattedStrings() {

        List<String[]> vars = new ArrayList<String[]>() {
        };

        // Add header
        String[] headers = new String[]{"io_name", "io_type", "io_unit_of_measure",
            "io_constant", "io_offset", "io_float_digits",
            "io_alert", "io_alert_range_low", "io_alert_range_high",
            "rest modbus"
        };
        vars.add(headers);
        // Store
        vars.add(new String[]{"`Store`"});
        for (String s : storeStr) {
            vars.add(s.split(","));
        }

        // Racks
        vars.add(new String[]{"`Rack`"});
        for (String s : rackStr) {
            vars.add(s.split(","));
        }

        // Cond
        vars.add(new String[]{"`Condenser`"});
        for (String s : condStr) {
            vars.add(s.split(","));
        }

        // Suction Group
        vars.add(new String[]{"`Suction Group`"});
        for (String s : sgStr) {
            vars.add(s.split(","));
        }

        // System
        vars.add(new String[]{"`System`"});
        for (String s : sysStr) {
            vars.add(s.split(","));
        }

        // Compressor
        vars.add(new String[]{"`Compressor`"});
        for (String s : compStr) {
            vars.add(s.split(","));
        }

        // Extra
        if (!extraStr.isEmpty()) {
            vars.add(new String[]{"`Extra`"});
            for (String s : extraStr) {
                vars.add(s.split(","));
            }
        }

        return vars;

    }

    public List<String[]> nameStrings() {

        List<String[]> vars = new ArrayList<String[]>() {
        };

        // Store
        vars.add(new String[]{"`Store`"});
        for (String s : storeStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Racks
        vars.add(new String[]{"`Rack`"});
        for (String s : rackStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Cond
        vars.add(new String[]{"`Condenser`"});
        for (String s : condStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Suction Group
        vars.add(new String[]{"`Suction Group`"});
        for (String s : sgStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // System
        vars.add(new String[]{"`System`"});
        for (String s : sysStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Compressor
        vars.add(new String[]{"`Compressor`"});
        for (String s : compStr) {
            vars.add(new String[]{s.split(",")[0]});
        }

        // Extra
        if (!extraStr.isEmpty()) {
            vars.add(new String[]{"`Extra`"});
            for (String s : extraStr) {
                vars.add(new String[]{s.split(",")[0]});
            }
        }

        return vars;

    }
    
    
    public ArrayList<String> getModbusOptions(){
        ArrayList<String> list = new ArrayList<>();
        Rack r;
        SuctionGroup sg;
        for(int i = 0; i < numRacks; i++){
            r = racks.get(i);
            list.add(r.getName());
            list.add(r.getName() + " Condenser");
            for(int j = 0; j < r.getNumSuctionGroups(); j++){
                sg = r.getSuctionGroupIndex(j);
                for(int k = 0; k < sg.getNumCompressors(); k++){
                    String name = r.getName() + " " + sg.getName() + " " + sg.getCompressorNameIndex(k);
                    list.add(name);
                }
            }
            
        }
        
        return list;
    }

}
