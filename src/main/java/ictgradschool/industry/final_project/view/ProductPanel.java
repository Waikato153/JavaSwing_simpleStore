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

    public abstract void createFrameUI(String title);
    public abstract void getBottom();
    public abstract JMenuBar getMenuBar();
    public abstract void getTable();


}