package panel.creator;

import java.io.File;

/**
 *
 * @author EricGummerson
 */
public class FilterImage extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".png") || file.getAbsolutePath().endsWith(".jpg") || file.getAbsolutePath().endsWith(".gif");
    }

    @Override
    public String getDescription() {
            // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Image files (.png, .jpg, .gif)";
    }

}
