package ictgradschool.industry.final_project.model;

public interface ProductsListListener {
    void projectDataAdded(ProductsList model, String dataItem, int index);

    void projectDataRemoved(ProductsList model, String dataItem);

    void projectDataChanged(ProductsList model, int index, String oldValue, String newValue);

}
