package ictgradschool.industry.final_project.model.worker;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.ProductsList;
import ictgradschool.industry.final_project.util.productAction;

import javax.swing.*;

/**
 * save product to file
 */
public class CheckoutWorker extends SwingWorker<Void, Void> {
    private ProjectUI app;
    private String fileName;

    public CheckoutWorker(ProjectUI app, String fileName)  {
        this.app = app;
        this.fileName = fileName;
    }
    @Override
    protected Void doInBackground() throws Exception {
        app.getShoppingCartList().writeFile(fileName);
        app.getProductsList().updateFile(app.getShoppingCartList());
        app.getProductsList().generateData();
        app.getShoppingCartList().clear();
        return null;
    }
}
