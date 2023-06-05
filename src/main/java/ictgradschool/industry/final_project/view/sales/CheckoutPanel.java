package ictgradschool.industry.final_project.view.sales;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.ShoppingCartList;
import ictgradschool.industry.final_project.model.worker.CheckoutWorker;
import ictgradschool.industry.final_project.view.SuperPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class CheckoutPanel extends SuperPanel {
    private JTextField _averageField;
    private double totalPrice;
    private JButton checkoutButton;
    public CheckoutPanel(ProjectUI app ) {
        super(app);
        JLabel averageLabel = new JLabel("Total: ");
        _averageField = new JTextField();
        _averageField.setEditable(false);
        _averageField.setColumns(10);
        _averageField.setBounds(10, 0, 150, 20);
        averageLabel.setBounds(30, 0, 150, 20);

        checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(50, 0, 150, 20);


        checkoutButton.addActionListener(e -> {
            if (app.getShoppingCartList().size() == 0) {
                JOptionPane.showMessageDialog(null, "Empty Shopping Cart", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Create a file chooser
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("./src/main"));

            /**
             * create use create txt fil
             to be selected. default
             fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
             */
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = 0;

            returnVal = fc.showDialog(this.getParent().getParent(), "Select");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                if (selectedFile.exists()) {
                    // File exists, perform operations on existing file
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                } else {
                    // Create the file
                    try {
                        if (selectedFile.createNewFile()) {
                            System.out.println("File created successfully: " + selectedFile.getCanonicalPath());
                        } else {
                            System.err.println("Failed to create file.");
                        }
                    } catch (Exception ex) {
                        System.out.println("Error creating file: " + ex.getMessage());
                    }
                }
                //write file first
                try {


                    CheckoutWorker checkoutWorker = new CheckoutWorker(app, selectedFile.getCanonicalPath() + "/receipt.txt");
                    checkoutWorker.execute();
                    JOptionPane.showMessageDialog(null, "Checkout Success", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                System.out.println("File creation canceled.");
            }
        });


        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //setLayout(null) ;
        add(averageLabel);
        add(_averageField);
        add(checkoutButton);
    }

    public JButton getCheckoutButton() {
        return checkoutButton;
    }

    public void update(double totalPrice) {
        DecimalFormat decimalFormat = new DecimalFormat("$0.00");
        _averageField.setText(String.valueOf(decimalFormat.format(totalPrice)));
    }
}
