package ictgradschool.industry.final_project.control;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.Product;
import ictgradschool.industry.final_project.model.ProductsList;
import ictgradschool.industry.final_project.model.worker.SaveWorker;
import ictgradschool.industry.final_project.util.GUITools;
import ictgradschool.industry.final_project.util.UniqueIdentifierGenerator;
import ictgradschool.industry.final_project.util.productAction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    public productCheck(JFrame frame, String id, String name, String description, String price, String quantity, ProjectUI app) {
        dialog = new JDialog(frame,"Warning",true);//add dialog to Jframe
        dialog().add(label());	//add lable to dialog
        dialog().add(button());	//add button to dialog
        buttonClick();//add button click event

        if (isNull(name, "Name") || isNull(description, "Description") || isNull(price, "Price") || isNull(quantity, "Quantity")) {
            dialog.setVisible(true);
            return;
        }
        // check price
        double newprice = 0;
        try {
            newprice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            label.setText("Price must be a number!");
            label.setForeground(Color.red);
            dialog.setVisible(true);
            return;
        }

        // check quantity
        int newquantity = 0;
        try {
            newquantity = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {

        }
        if (newquantity > 0 == false) {
            label.setText("Quantity must be a number!");
            label.setForeground(Color.red);
            dialog.setVisible(true);
            return;
        }
        productAction action = productAction.Edit;
        if (id== null || id.length() == 0) {
            id = UniqueIdentifierGenerator.getCharacters(ProductsList.getProductIds());
            action = productAction.Add;
        }
        //记录增添信息
        Product pro = new Product(id, name, description, newprice, newquantity);
        app.getProductsList().add(pro);
        SaveWorker saveWorker = new SaveWorker(app.getProductsList(), action);
        saveWorker.execute();
    }

    public Boolean isNull(String para, String para2) {
        if(para.length() == 0 || para.length() == 0) {
            label.setText(para2 + "cannot be empty!");
            label.setForeground(Color.red);
            return true;
        }else {
            return false;
        }
    }

    //信息正确执行
    private void right(JFrame frame) {
        label.setText("注册成功，请牢记密码！");
        label.setForeground(Color.blue);
        frame.dispose();//关闭注册窗口
        dialog.setVisible(true);//信息正确提示
    }
    //弹出窗组件设置
    private JDialog dialog() {
        dialog.setLayout(new FlowLayout(FlowLayout.CENTER,30,40));
        dialog.setResizable(false);//设置窗体尺寸不可改变
        dialog.setSize(300, 250);
        GUITools.center(dialog);	//设置弹窗在屏幕中居中显示
        return dialog;
    }
    //标签提示信息组件
    private JLabel label() {
        label.setSize(180, 35);		//设置标签信息大小
        label.setFont(font);		//添加字体
        return label;
    }
    //'确认'按钮组件
    private JButton button() {
        button.setSize(180, 50);	//按钮大小
        button.setFont(font);		//添加字体样式
        return button;
    }
    //确认按钮-点击事件
    private void buttonClick() {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();//关闭当前弹窗
            }
        });
    }


}
