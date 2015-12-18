package Creator;

import java.io.File;

/**
 * Filter for images on the file selector
 *
 * @author EricGummerson
 */
public class FilterFolder extends javax.swing.filechooser.FileFilter {

    /**
     *
     * @param file
     * @return boolean if the file is a legit file to open
     */
    @Override
    public boolean accept(File file) {
        // Allow only directories
        return file.isDirectory();
    }

    /**
     *
     * @return description of the image files
     */
    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Directory to save pictures";
    }

}
