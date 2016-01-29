package Creator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.awt.Font;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.border.Border;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Store will contain all information for the store
 *
 * @author EricGummerson
 */
@XmlRootElement(name = "Store")
@XmlAccessorType(XmlAccessType.FIELD)
public class Store implements java.io.Serializable {

    private static final long serialVersionUID = 222L;

    public DisplaySettings ds;
    public ModbusSettings mb;
    public ControlSettings cs;
    public IoNames ioNames;
    public Map<String,WidgetLink> widgetLinks;

    public Store() {
        ds = new DisplaySettings();
        mb = new ModbusSettings();
        cs = new ControlSettings();
        ioNames = new IoNames();
        widgetLinks = new HashMap<>();
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

    public Map<String,WidgetLink> getWidgetLinks() {
        return widgetLinks;
    }

    public void setWidgetLinks(Map<String,WidgetLink> wl) {
        this.widgetLinks = wl;
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
    
    
    
    public void addWidgetLink(String key, WidgetLink wl){
        
        if(!widgetLinks.containsKey(key)){
            widgetLinks.put(key, wl);
        }        
    }
    
    public WidgetLink getLinkWithKey(String key){
        if(widgetLinks.containsKey(key)){
            return widgetLinks.get(key);
        }else {
            return null;
        }
    }

    /**
     * reads a csv file
     *
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

    public List<String[]> formatStrings() {
        return this.ioNames.formatStrings(cs);
    }

    public String getStoreName() {
        return cs.getStoreName();
    }
}
