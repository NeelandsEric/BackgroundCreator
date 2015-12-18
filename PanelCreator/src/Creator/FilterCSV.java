package Creator;

import java.io.File;

/**
 * Filter for images on the file selector
 *
 * @author EricGummerson
 */
public class FilterCSV extends javax.swing.filechooser.FileFilter {

    /**
     * 
     * @param file
     * @return boolean if the file should be accepted
     */
    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".csv");
    }

    /**
     * 
     * @return the description for the filter
     */
    @Override
    public String getDescription() {
            // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "CSV files (.csv)";
    }

}
