package ictgradschool.industry.final_project.view;

import ictgradschool.industry.final_project.ProjectUI;

import javax.swing.*;
import java.awt.*;

//filestore panel
public class SuperPanel extends JPanel {
    // open filechooser button
    protected ProjectUI app;
    protected Font font = new Font("Arial", Font.PLAIN, 20);

    public SuperPanel(ProjectUI app) {
        this.app = app;
    }
}
