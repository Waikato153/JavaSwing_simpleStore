package ictgradschool.industry.final_project.model.worker;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.ShoppingCartList;
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
        System.out.println("Loading data1..." + this.needZero);
        return app.getProductsList().readData(this.needZero);
    }

    @Override
    protected void done() {
        System.out.println("Done.");
        List<Product> Loaddata;
        try {
            app.getProductsCartList().clear();

            Loaddata = get();

            System.out.println(Loaddata);
            if (Loaddata == null || Loaddata.isEmpty()) {
                System.out.println("No data loaded.");
            } else {
                // Populate the Course model object with the loaded data.
                Iterator<Product> iterator = Loaddata.iterator();


                ShoppingCartList model = app.getShoppingCartList();

                while (iterator.hasNext()) {
                    Product product = iterator.next();
                    app.getProductsList().add(product);
                    if (this.needZero == true) {

                    } else {
                        int OldQuantity = product.getQuantity();
                        for (int i = 0; i < model.size(); i++) {
                            if (model.getResultAt(i).getProductId().equals(product.getId())) {
                                OldQuantity = OldQuantity - model.getResultAt(i).getQuantity();
                            }
                        }
                        if (OldQuantity > 0) {
                            product.setQuantity(OldQuantity);
                            app.getProductsCartList().add(product);
                        } else {
                            product.setQuantity(0);
                        }


                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
