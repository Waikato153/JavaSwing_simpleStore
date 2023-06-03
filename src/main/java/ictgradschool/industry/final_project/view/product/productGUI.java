package ictgradschool.industry.final_project.view.product;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.bean.Product;
import ictgradschool.industry.final_project.util.GUITools;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * add/edit prodcut
 */
public class productGUI {
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	productPanel productPanel;

	Product product;


	public productGUI(ProjectUI app, Product product) {
		productPanel = new productPanel(app);
		this.product = product;
		this.run();
	}


	public void run() {
		// Add a WindowListener to the JFrame
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				System.out.println("Opening the Product JFrame...");
				if (product != null) {
					productPanel.reset(product);
				}
			}
		});

		frame.setTitle("Add/Edit Product");//设置窗口标题
		frame.setSize(485, 455);
		frame.setResizable(false);//设置窗体尺寸不可改变
		frame.add(productPanel.panel(frame));//向窗口添加界面
		GUITools.center(frame);	//设置窗口在屏幕中居中显示
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new productGUI(null,null);
	}

}