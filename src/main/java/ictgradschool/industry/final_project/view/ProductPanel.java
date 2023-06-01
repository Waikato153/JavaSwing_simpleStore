package ictgradschool.industry.final_project.view;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.InventoryTableAdapter;
import ictgradschool.industry.final_project.model.ProductsList;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public abstract class ProductPanel extends SuperPanel{
    protected ProductsList model;
    public JTable tableView;
    public JFrame inventoryFrame;
    public InventoryTableAdapter tableModel;

    public ProductPanel(ProjectUI app) {
        super(app);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(700, 450));
        getTable();
        getBottom();
    }

    public void createFrameUI(String title) {
        inventoryFrame = new JFrame(title);
        inventoryFrame.setContentPane(this);
        //add menu bar
        JMenuBar mb = getMenuBar();
        if (mb != null) {
            inventoryFrame.setJMenuBar(mb);
        }

        // Add a WindowListener to the JFrame
        inventoryFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Inventory or sales Window Closed");
                app.getProductsList().clearSelectedIds();
            }
        });

        inventoryFrame.pack();
        inventoryFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        inventoryFrame.setLocationRelativeTo(null);
        inventoryFrame.setVisible(true);
    }

    public abstract void getBottom();
    public abstract JMenuBar getMenuBar();
    public abstract void getTable();


}