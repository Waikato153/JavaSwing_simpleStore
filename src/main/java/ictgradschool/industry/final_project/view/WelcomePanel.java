package ictgradschool.industry.final_project.view;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.ProductsList;
import ictgradschool.industry.final_project.model.bean.Product;
import ictgradschool.industry.final_project.model.worker.LoadDataWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

//filestore panel
public class WelcomePanel extends SuperPanel implements ActionListener {
    // open welcome interface
    JButton backButton, inventoryButton, salesButton;
    SwingWorker myWorker = null;

    public WelcomePanel(ProjectUI app) {
        super(app);
        // Set the layout manager of the main panel to BoxLayout with vertical orientation
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Create a horizontal panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //Create the open button
        backButton = new JButton("Back");
        backButton.setBackground(new Color(239, 236, 232));
        backButton.addActionListener(this);


        inventoryButton = new JButton("Inventory");
        inventoryButton.setBackground(new Color(239, 68, 8));
        inventoryButton.addActionListener(this);

        salesButton = new JButton("Sales");
        salesButton.setBackground(new Color(65, 129, 39));
        salesButton.addActionListener(this);

        buttonPanel.add(backButton);
        buttonPanel.add(inventoryButton);
        buttonPanel.add(salesButton);

        this.add(Box.createVerticalGlue()); // Add vertical glue for centering
        this.add(buttonPanel);
        this.add(Box.createVerticalGlue()); // Add vertical glue for centering

        setPreferredSize(new Dimension(400, 300));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Boolean needZero = true;
        if (e.getSource() == backButton) {
            System.out.println("back");
            this.app.tiggerBack();
        } else if (e.getSource() == inventoryButton) {
            System.out.println("inventory panel");
            this.app.createInventoryTableFrame();
        } else if (e.getSource() == salesButton) {
            System.out.println("sales panel");
            this.app.createSalesTableFrame();
            needZero = false;
        }
        if (e.getSource() != backButton) {
            myWorker = new LoadDataWorker(needZero, app);
            myWorker.execute();
        }
    }
}
