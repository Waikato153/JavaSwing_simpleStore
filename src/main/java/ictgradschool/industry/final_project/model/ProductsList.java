package ictgradschool.industry.final_project.model;

import java.util.ArrayList;
import java.util.List;

public class ProductsList {
    private final List<Products> products = new ArrayList<>();
    private final List<ProductsListListener> listeners = new ArrayList<>();

    public void add(Products product) {
        products.add(product);

        for (ProductsListListener l : listeners) {
            l.productsListAdded(this);
        }
    }

    public void clear() {
        products.clear();

        for (ProductsListListener l : listeners) {
            l.productsListCleared(this);
        }
    }

    public void addListener(ProductsListListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ProductsListListener listener) {
        listeners.remove(listener);
    }

    public int size() {
        return products.size();
    }

    public Products get(int index) {
        return products.get(index);
    }
}
