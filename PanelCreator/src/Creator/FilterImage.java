package Creator;

import java.io.File;

/**
 * Filter for images on the file selector
 *
 * @author EricGummerson
 */
public class FilterImage extends javax.swing.filechooser.FileFilter {

    /**
     *
     * @param file
     * @return boolean if the file is a legit file to open
     */
    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".png") || file.getAbsolutePath().endsWith(".jpg") || file.getAbsolutePath().endsWith(".gif");
    }

    /**
     *
     * @return description of the image files
     */
    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Image files (.png, .jpg, .gif)";
    }

}
