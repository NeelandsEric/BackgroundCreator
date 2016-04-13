package Creator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.awt.Font;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
    public WidgetSettings ws;

    public Store() {
        ds = new DisplaySettings();
        mb = new ModbusSettings();
        cs = new ControlSettings();
        ioNames = new IoNames();
        ws = new WidgetSettings();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.ds);
        hash = 37 * hash + Objects.hashCode(this.mb);
        hash = 37 * hash + Objects.hashCode(this.cs);
        hash = 37 * hash + Objects.hashCode(this.ioNames);
        hash = 37 * hash + Objects.hashCode(this.ws);
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
        final Store other = (Store) obj;
        if (!Objects.equals(this.ds, other.ds)) {
            return false;
        }
        if (!Objects.equals(this.mb, other.mb)) {
            return false;
        }
        if (!Objects.equals(this.cs, other.cs)) {
            return false;
        }
        if (!Objects.equals(this.ioNames, other.ioNames)) {
            return false;
        }
        if (!Objects.equals(this.ws, other.ws)) {
            return false;
        }
        return true;
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

    public WidgetSettings getWidgetSettings() {
        return ws;
    }

    public void setWidgetSettings(WidgetSettings ws) {
        this.ws = ws;
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

            writer.writeAll(ioNames.formatStringsNoParams(this.cs, true));
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
