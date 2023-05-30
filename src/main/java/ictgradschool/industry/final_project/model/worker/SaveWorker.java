package ictgradschool.industry.final_project.model.worker;

import ictgradschool.industry.final_project.model.ProductsList;
import ictgradschool.industry.final_project.util.productAction;

import javax.swing.*;
/**
 * save product to file
 */
public class SaveWorker extends SwingWorker<Void, Void> {
    private ProductsList productsList;
    private productAction action;

    public SaveWorker(ProductsList productsList, productAction action) {
        this.productsList = productsList;
        this.action = action;
    }
    @Override
    protected Void doInBackground() throws Exception {
        Boolean append = true;

        if (action == productAction.Edit) {
            append = false;
        }

        productsList.saveFile(append);
        return null;
    }
}
