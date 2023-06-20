package ictgradschool.industry.final_project.model.worker;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.ProductsList;
import ictgradschool.industry.final_project.util.productAction;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * save product to file
 */
public class CheckoutWorker extends SwingWorker<Void, Void> {
    private ProjectUI app;
    private String fileName;

    public CheckoutWorker(ProjectUI app, String fileName)  {
        this.app = app;
        this.fileName = fileName;
    }
    @Override
    protected Void doInBackground() throws Exception {
        app.getShoppingCartList().writeFile(fileName);
        app.getProductsList().updateFile(app.getShoppingCartList());
        app.getProductsList().generateData();
        app.getShoppingCartList().clear();

        try {
            // Create a file object
            File file = new File(fileName);
            // Check if Desktop is supported
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                // Check if file exists
                if (file.exists()) {
                    // Open the file
                    desktop.open(file);
                } else {
                    System.out.println("File does not exist.");
                }
            } else {
                System.out.println("Desktop is not supported.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
