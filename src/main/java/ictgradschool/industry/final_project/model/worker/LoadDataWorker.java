package ictgradschool.industry.final_project.model.worker;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.ProductsList;
import ictgradschool.industry.final_project.model.bean.Product;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoadDataWorker extends SwingWorker<List<Product>, Void> {
    private boolean needZero = true;
    private ProjectUI app;
    public LoadDataWorker(boolean needZero, ProjectUI app) {
        this.needZero = needZero;
        this.app = app;
    }
    @Override
    protected List<Product> doInBackground() {
        System.out.println("Loading data...");
        return ProductsList.readData(this.needZero);
    }

    @Override
    protected void done() {
        System.out.println("Done.");
        List<Product> Loaddata;
        try {
            Loaddata = get();
            if (Loaddata == null || Loaddata.isEmpty()) {
                System.out.println("No data loaded.");

            } else {
                // Populate the Course model object with the loaded data.
                Iterator<Product> iterator = Loaddata.iterator();

                while (iterator.hasNext()) {
                    Product product = iterator.next();
                    System.out.println(product);
                    app.getProductsList().add(product);
                    app.getProductsCartList().add(product);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
