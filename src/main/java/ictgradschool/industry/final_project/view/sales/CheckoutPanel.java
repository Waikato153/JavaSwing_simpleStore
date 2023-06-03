package ictgradschool.industry.final_project.view.sales;

import ictgradschool.industry.final_project.model.ShoppingCartObserver;

import javax.swing.*;
import java.awt.*;

public class CheckoutPanel extends JPanel {
    private JTextField _averageField;
    private double totalPrice;
    public CheckoutPanel() {
        
        JLabel averageLabel = new JLabel("Total: ");
        _averageField = new JTextField();
        _averageField.setEditable(false);
        _averageField.setColumns(10);
        _averageField.setBounds(10, 0, 150, 20);
        averageLabel.setBounds(30, 0, 150, 20);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(50, 0, 150, 20);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //setLayout(null) ;
        add(averageLabel);
        add(_averageField);
        add(checkoutButton);
    }

    public void update(double totalPrice) {
        _averageField.setText(String.valueOf(totalPrice));
    }
}
