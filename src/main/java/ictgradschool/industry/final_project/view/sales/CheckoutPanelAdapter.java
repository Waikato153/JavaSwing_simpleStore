package ictgradschool.industry.final_project.view.sales;

import ictgradschool.industry.final_project.model.ShoppingCartList;
import ictgradschool.industry.final_project.model.ShoppingCartObserver;

import javax.swing.*;

public class CheckoutPanelAdapter extends JPanel implements ShoppingCartObserver {
    private CheckoutPanel checkoutPanel;
    private ShoppingCartList shoppingCartList;

    public CheckoutPanelAdapter(CheckoutPanel checkoutPanel, ShoppingCartList shoppingCartList) {
        /**********************************************************************
         * YOUR CODE HERE
         */
        this.checkoutPanel = checkoutPanel;
        this.shoppingCartList = shoppingCartList;
        shoppingCartList.addObserver(this);
        // first calculate
        this.update();
    }

    @Override
    public void update() {
        checkoutPanel.update(shoppingCartList.getTotalPrice());
    }

}
