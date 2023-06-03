package ictgradschool.industry.final_project.model.bean;

public class ShoppingItem {
    private String productId;
    private int quantity;

    private Product product;

    public ShoppingItem(String productId, int quantity, Product product) {
        this.productId = productId;
        this.quantity = quantity;
        this.product = product;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product pp = (Product) obj;
        return productId.equals(pp.getId());
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return getProductId() + " " + getQuantity() + " " + getProduct();
    }


}
