package ictgradschool.industry.final_project.model.bean;

import java.io.Serializable;

/**
 * represt a single product
 */
public class Product implements Serializable {
    //unique id 10-characters
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;

    private boolean selected;

    public int getPrimarykey() {
        return primarykey;
    }
    public void setPrimarykey(int primarykey) {
        this.primarykey = primarykey;
    }

    private int primarykey;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Product(String id, String name, String description, double price, int quantity, int primarykey) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.primarykey = primarykey;
        this.selected = false;
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
        return id.equals(pp.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getId() + " " + getName() + " " + getDescription() + " " + getPrice() + " " + getQuantity() + " " + getPrimarykey();
    }
}
