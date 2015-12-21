package Creator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.awt.Font;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.border.Border;

/**
 * Store will contain all information for the store
 *
 * @author EricGummerson
 */
public class Store implements java.io.Serializable {

    private static final long serialVersionUID = 222L;
   
    public DisplaySettings ds;
    public ModbusSettings mb;
    public ControlSettings cs;
    public IoNames ioNames;

    public Store() {
        ds = new DisplaySettings();
        mb = new ModbusSettings();
        cs = new ControlSettings();
        ioNames = new IoNames();
    }
    
    public ModbusSettings getMb() {
        return mb;
    }

    public void setMb(ModbusSettings mb) {
        this.mb = mb;
    }

    public IoNames getIoNames() {
        return ioNames;
    }

    public void setIoNames(IoNames ioNames) {
        this.ioNames = ioNames;
    }

    public ControlSettings getCs() {
        return cs;
    }

    public void setCs(ControlSettings cs) {
        this.cs = cs;
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

            writer.writeAll(ioNames.formatStrings(this.cs));
            writer.close();
            
        } catch (IOException ex) {
            System.out.println("Write csv error with " + filepath + " : " + ex.getMessage());
        }

    }
    
    public void writeCSVNoParams(String filepath) {

        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filepath), ',', CSVWriter.NO_QUOTE_CHARACTER);
            // feed in your array (or convert your data to an array)          

            writer.writeAll(ioNames.formatStringsNoParams(this.cs));
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

            writer.writeAll(ioNames.nameStrings());
            writer.close();
        } catch (IOException ex) {
            System.out.println("Write csv error with " + filepath + " : " + ex.getMessage());
        }

    }
    

    public void writeText(String filepath) {

        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filepath), ',', CSVWriter.NO_QUOTE_CHARACTER);
            // feed in your array (or convert your data to an array)          

            writer.writeAll(ioNames.unformattedStrings());
            writer.close();
        } catch (IOException ex) {
            System.out.println("Write text error with " + filepath + " : " + ex.getMessage());
        }

    }
    
    public List<String []> formatStrings(){
        return this.ioNames.formatStrings(cs);
    }

    public String getStoreName(){
        return cs.getStoreName();
    }
}