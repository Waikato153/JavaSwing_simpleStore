package ictgradschool.industry.final_project.control;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.ProductsList;
import ictgradschool.industry.final_project.model.bean.Product;
import ictgradschool.industry.final_project.util.UniqueIdentifierGenerator;
import ictgradschool.industry.final_project.util.productAction;

import java.awt.*;
import javax.swing.*;


/**
 * product add and edit parameter check
 */
public class productCheck {
    protected static JDialog dialog;//create dialog object
    protected JLabel label = new JLabel();//create label object
    protected JButton button = new JButton("Confirm");// create confirm button
    protected Font font = new Font("Arial", Font.PLAIN, 20);

    //构造注册事件验证
    public productCheck(JFrame frame, String id, String name, String description, String price, String quantity, String primarykey, ProjectUI app) {
        if (isNull(name, "Name") || isNull(description, "Description") || isNull(price, "Price")) {
            return;
        }
        // check price
        double newprice = 0;
        try {
            newprice = Double.parseDouble(price);

            if (newprice >= 0 == false) {
                JOptionPane.showMessageDialog(null, "Price must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Price must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // check quantity
        int newquantity = 0;
        try {
            newquantity = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {

        }
        if (newquantity >= 0 == false) {
            JOptionPane.showMessageDialog(null, "Quantity must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        productAction action = productAction.Edit;
        if (id== null || id.length() == 0) {
            id = UniqueIdentifierGenerator.getCharacters(ProductsList.getProductIds());
            action = productAction.Add;
        }
        int newKey = 1;
        try {
            newKey = Integer.parseInt(primarykey);
        } catch (NumberFormatException e) {
            if (app.getProductsList().size() > 0) {
                newKey = app.getProductsList().get(0).getPrimarykey() + 1;
            }
        }

        //add record
        Product pro = new Product(id, name, description, newprice, newquantity, newKey);
        app.getProductsList().add(pro);
        right(frame, action);
        app.getProductsList().triggerSave();
    }

    public Boolean isNull(String para, String para2) {
        if(para.length() == 0 || para.length() == 0) {
            JOptionPane.showMessageDialog(null, para2 + " cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }else {
            return false;
        }
    }

    private void right(JFrame frame, productAction action) {
        frame.dispose();
        JOptionPane.showMessageDialog(null, action == productAction.Add ? "Add product successfully!" : "Edit product successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
