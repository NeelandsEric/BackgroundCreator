/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.io.File;

/**
 *
 * @author EricGummerson
 */
public class ImageFilter extends javax.swing.filechooser.FileFilter {

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
