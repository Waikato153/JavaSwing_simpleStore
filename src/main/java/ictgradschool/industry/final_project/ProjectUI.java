package ictgradschool.industry.final_project;

import ictgradschool.industry.final_project.model.ProductsCartList;
import ictgradschool.industry.final_project.model.ProductsList;
import ictgradschool.industry.final_project.model.ShoppingCartList;
import ictgradschool.industry.final_project.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProjectUI {
    private ProductsList productsList = new ProductsList();
    private ProductsCartList productsCartList = new ProductsCartList();
    private ShoppingCartList shoppingCartList = new ShoppingCartList();
    private static JFrame chooseFileFrame;
    private FileChooserPanel choosePane;
    private WelcomePanel welcomePane;


    public static void main(String[] args) {
        // TODO: Your code here
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.

        SwingUtilities.invokeLater(()-> {
            ProjectUI projectUI = new ProjectUI();
            projectUI.createAndShowGUI();
        });
    }
    /**
     * Helper method to display the GUI.
     */
    private void createAndShowGUI() {
        // Create and set up the window.
        //filechooser frame
        this.createChooseFileFrame();
    }
    /**
     * Helper method to create the filechooser frame.
     */
    private void createChooseFileFrame() {
        //filechooser frame
        chooseFileFrame = new JFrame("Please choose inventory file");
        chooseFileFrame.setSize(300, 200);
        chooseFileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create and set up the content pane.
        choosePane = new FileChooserPanel(this);

        if (welcomePane != null) {
            chooseFileFrame.remove(welcomePane);
        }
        chooseFileFrame.add(choosePane);
        // Display the window.
        chooseFileFrame.pack();
        chooseFileFrame.setLocationRelativeTo(null);
        chooseFileFrame.setVisible(true);
    }
    //welcome panel
    public void createWelcomeFrame() {
//        welcomeFrame = new JFrame("Welcome to the Inventory Management System");
//        welcomeFrame.setSize(500, 500);
//        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create and set up the content pane.
        chooseFileFrame.setTitle("Welcome to the Inventory Management System");
        if (choosePane != null) {
            chooseFileFrame.remove(choosePane);
        }
        welcomePane = new WelcomePanel(this);
        chooseFileFrame.add(welcomePane);
        // Display the window.
        chooseFileFrame.pack();
        chooseFileFrame.setLocationRelativeTo(null);
        chooseFileFrame.setVisible(true);
    }

    public void createInventoryTableFrame() {
        InventoryPanel inventoryPanel = new InventoryPanel(this);
        inventoryPanel.createFrameUI("Inventory Management System");
    }

    public void createSalesTableFrame() {

        PointSalePanel pointSalePanel = new PointSalePanel(this);
        pointSalePanel.createFrameUI("Point of Sale System");
    }


    //action after choosing the filestore
    public void tiggerFileSelect(String txtFile) {
        productsList.setTxtFile(txtFile);
        createWelcomeFrame();
        saveCsv();
    }
    public ProductsList getProductsList() {
        return productsList;
    }

    public ProductsCartList getProductsCartList() {
        return productsCartList;
    }

    public ShoppingCartList getShoppingCartList() {
        return shoppingCartList;
    }
    //action after click back
    public void tiggerBack() {
        createChooseFileFrame();
    }

    public void saveCsv() {
        //load data to .dat file
        SwingWorker myWorker = new SaveWorker();
        myWorker.execute();
    }
    /*
     * Nested inner class to load Course data from file using a separate thread.
     */
    private class SaveWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            ProductsList.generateData();
            return null;
        }
    }

}
