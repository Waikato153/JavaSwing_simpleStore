package ictgradschool.industry.final_project.util;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GUITools {

    static Toolkit kit = Toolkit.getDefaultToolkit();

    public static void setAll(JFrame frame){

        center(frame);

        frame.setResizable(false);//设置窗体尺寸不可改变

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //center the component
    public static void center(Component c) {
        int x = (kit.getScreenSize().width - c.getWidth()) / 2;
        int y = (kit.getScreenSize().height - c.getHeight()) / 2;
        c.setLocation(x, y-50);
    }
}
