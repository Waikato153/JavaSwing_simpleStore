package ictgradschool.industry.final_project.model;

public interface ProductsListListener {
    void projectDataAdded(ProductsList model);

    void projectDataRemoved(ProductsList model);

    void projectDataChanged(ProductsList model, int index, String oldValue, String newValue);

}
