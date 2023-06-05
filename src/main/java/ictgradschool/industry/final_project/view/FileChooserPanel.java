package ictgradschool.industry.final_project.view;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.ProductsList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//filestore panel
public class FileChooserPanel extends SuperPanel implements ActionListener {
   // open filechooser button
    JButton openButton, createButton;
    public JFileChooser fc;
    private String txtFile;

    public FileChooserPanel(ProjectUI app) {
        super(app);
        // Set the layout manager of the main panel to BoxLayout with vertical orientation
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        fc = new JFileChooser();

        // Create a horizontal panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //Create the open button
        openButton = new JButton("Open a File...", createImageIcon("/images/Open16.gif"));
        openButton.addActionListener(this);

        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        createButton = new JButton("Create a File...",
                createImageIcon("/images/Save16.gif"));
        createButton.addActionListener(this);

        buttonPanel.add(openButton);
        buttonPanel.add(createButton);

        this.add(Box.createVerticalGlue()); // Add vertical glue for centering
        this.add(buttonPanel);
        this.add(Box.createVerticalGlue()); // Add vertical glue for centering

        setPreferredSize(new Dimension(400, 300));

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

    @Override
    public void actionPerformed(ActionEvent e) {
        //Create a file chooser
        fc.setCurrentDirectory(new File("./src/main/resources"));

        /**
         * create use create txt fil
         to be selected. default
         fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
         */
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = 0;
        if (e.getSource() == openButton) {
            returnVal = fc.showDialog(FileChooserPanel.this, "Select");
        } else if (e.getSource() == createButton) {
            returnVal = fc.showDialog(FileChooserPanel.this, "Create");
        }
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            if (selectedFile.exists()) {
                // File exists, perform operations on existing file
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            } else {
                // Create the file
                try {
                    if (selectedFile.createNewFile()) {
                        System.out.println("File created successfully: " + selectedFile.getAbsolutePath());
                    } else {
                        System.err.println("Failed to create file.");
                    }
                } catch (Exception ex) {
                    System.out.println("Error creating file: " + ex.getMessage());
                }
            }
            try {
                this.app.tiggerFileSelect(selectedFile.getCanonicalPath());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
            System.out.println("File creation canceled.");
        }
    }

}
