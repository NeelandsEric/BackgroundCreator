package panel.creator;

import java.io.File;

/**
 * Store filter for the file selector
 * @author EricGummerson
 */
public class FilterStore extends javax.swing.filechooser.FileFilter {

    /**
     * 
     * @param file
     * @return boolean if the file should be accepted
     */
    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".sto");
    }

    /**
     * 
     * @return the description for the filter
     */
    @Override
    public String getDescription() {
            // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Store files (.sto)";
    }

}
