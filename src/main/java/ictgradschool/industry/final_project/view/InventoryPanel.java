package ictgradschool.industry.final_project.view;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.InventoryTableAdapter;
import ictgradschool.industry.final_project.model.Product;
import ictgradschool.industry.final_project.model.ProductsList;
import ictgradschool.industry.final_project.view.product.productGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InventoryPanel extends SuperPanel{
    private ProductsList model;
    private JTable tableView;
    private static JFrame inventoryFrame;

    public InventoryPanel(ProjectUI app) {
        super(app);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 450));
        tableView = new JTable(new InventoryTableAdapter(app.getProductsList()));
        add(new JScrollPane(tableView), BorderLayout.CENTER);
    }

    public void createFrameUI() {
        inventoryFrame = new JFrame("Inventory Management System");
        inventoryFrame.setContentPane(this);
        //add menu bar
        JMenuBar mb=new JMenuBar();
        JMenu menu=new JMenu("Add");
        menu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                productGUI productGui = new productGUI(app);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });



        mb.add(menu);
        mb.setPreferredSize(new Dimension(100,40));

        inventoryFrame.setJMenuBar(mb);
        inventoryFrame.pack();
        inventoryFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        inventoryFrame.setLocationRelativeTo(null);
        inventoryFrame.setVisible(true);
    }
}
