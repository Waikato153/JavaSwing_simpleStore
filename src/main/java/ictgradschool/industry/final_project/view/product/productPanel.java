package ictgradschool.industry.final_project.view.product;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.control.productCheck;
import ictgradschool.industry.final_project.view.SuperPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 注册界面容器
 */
public class productPanel extends SuperPanel {
	
	JLabel lab1 = new JLabel("Name:");
	JLabel lab2 = new JLabel("Description:");
	JLabel lab3 = new JLabel("Price:");
	JLabel lab4 = new JLabel("Quantity:");

	JTextField text1 = new JTextField();
	JTextArea text2 = new JTextArea();
	JTextField text3 = new JTextField();
	JTextField text4 = new JTextField();

	
	JButton but1 = new JButton("Create");//创建按钮
	JButton but2 = new JButton("Reset");
	
	String id,name,description, price, quantity;//创建信息存储空间

	private JPanel panel = new JPanel();

	public productPanel(ProjectUI app) {
		super(app);
		panel.setLayout(null);//取消容器默认布局流
	}


	/**
	 * 为窗体设置界面容器并添加必要组件
	 * @param frame
	 * @return JPanel
	 */
	public JPanel panel(JFrame frame) {
		panel.add(nameLabel());//向容器中添加标签组件
		panel.add(descriptionLabel());
		panel.add(priceLabel());
		panel.add(quantityLabel());
		
		panel.add(nameText());//向容器中添加文本框组件

		panel.add(priceText());
		panel.add(quantityText());

		JScrollPane tablePane = new JScrollPane(descriptionText());
		tablePane.setBounds(193, 100, 245, 55);
		//tablePane.setOpaque(false);	//设置装表格的视图透明
		///tablePane.getViewport().setOpaque(false);//视图透明
		tablePane.setBorder(null);//设置边框
		panel.add(tablePane);

		panel.add(userButton());//向容器中添加按钮组件
		panel.add(passButton());
		
		but1Click(frame);//添加登录点击事件
		but2Click(frame);
		
		return panel;
	}
	private JLabel nameLabel() {
		lab1.setFont(font);
		lab1.setBounds(50, 40, 120, 30);
		return lab1;
	}
	//姓名标签组件
	private JLabel descriptionLabel() {
		lab2.setFont(font);
		lab2.setBounds(50, 100, 120, 30);
		return lab2;
	}
	//性别标签组件
	private JLabel priceLabel() {
		lab3.setFont(font);
		lab3.setBounds(50, 180, 120, 30);
		return lab3;
	}
	//年龄标签组件
	private JLabel quantityLabel() {
		lab4.setFont(font);
		lab4.setBounds(50, 220, 120, 30);
		return lab4;
	}

	
	//ID文本框组件
	private JTextField nameText() {
		text1.setBounds(190, 37, 250, 35);
		text1.setFont(font);
		return text1;
	}
	private JTextArea descriptionText() {
		text2.setFont(font);
		text2.setLineWrap(true);
		text2.setWrapStyleWord(true);
		return text2;
	}

	private JTextField priceText() {
		text3.setBounds(190, 175, 250, 35);
		text3.setFont(font);
		return text3;

	}
	
	//设置密码文本框组件
	private JTextField quantityText() {
		text4.setBounds(190, 215, 250, 35);
		text4.setFont(font);
		return text4;
	}
	//登录按钮组件
	private JButton userButton() {
		but1.setBounds(100, 300, 100, 40);
		but1.setFont(font);
		return but1;
	}
	//注册按钮组件
	private JButton passButton() {
		but2.setBounds(290, 300, 100, 40);
		but2.setFont(font);
		return but2;
	}

	
	//注册按钮-点击事件
	public void but1Click(JFrame frame) {
		but1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = nameText().getText();
				description = descriptionText().getText();
				price = priceText().getText();
				quantity = quantityText().getText();
				//将信息传给注册信息验证类验证
				new productCheck(frame, id, name, description, price, quantity, app);
			}
		});
	}
	//重置按钮-点击事件
	public void but2Click(JFrame frame) {
		but2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameText().setText("");
				descriptionText().setText("");
				priceText().setText("");
				quantityText().setText("");
			}
		});
	}
}
