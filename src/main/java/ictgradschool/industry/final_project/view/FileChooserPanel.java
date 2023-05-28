package ictgradschool.industry.final_project.view;

import javax.swing.*;

//filestore panel
public class FileChooserPanel extends JPanel {
   // open filechooser button
    JButton openButton;
    JFileChooser fc;

    public FileChooserPanel() {
        //Create a file chooser
        fc = new JFileChooser();
        //Create the open button
        openButton = new JButton("Open a File...", createImageIcon("/images/Open16.gif"));
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = FileChooserPanel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path + "\n");
            return null;
        }
    }
}
