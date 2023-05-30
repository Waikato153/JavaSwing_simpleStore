package ictgradschool.industry.final_project.view.product;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.util.GUITools;

import javax.swing.*;


/**
 * add/edit prodcut
 */
public class productGUI {
	
	static JFrame frame = new JFrame();
	JPanel panel = new JPanel();//创建容器

	productPanel productPanel;//创建注册界面对象

	public productGUI(ProjectUI app) {
		productPanel = new productPanel(app);
		this.run();
	}
	
	//运行此窗口方法
	public void run() {
		frame.setTitle("Add/Edit Product");//设置窗口标题
		frame.setSize(485, 505);
		frame.setResizable(false);//设置窗体尺寸不可改变

		frame.add(productPanel.panel(frame));//向窗口添加界面

		GUITools.center(frame);	//设置窗口在屏幕中居中显示
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new productGUI(null);
	}

}