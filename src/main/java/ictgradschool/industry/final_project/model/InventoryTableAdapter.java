package ictgradschool.industry.final_project.model;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.bean.Product;
import ictgradschool.industry.final_project.model.bean.ShoppingItem;
import ictgradschool.industry.final_project.view.product.productGUI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class InventoryTableAdapter extends AbstractTableModel implements ProductsListListener {
    private static final long serialVersionUID = 1L;
    /**********************************************************************
     * YOUR CODE HERE
     */
    private String[] _columnNames = {"Checked?", "Product ID", "Name", "Description", "Price", "Quantity", "Action"};
    private ProductsList model;
    private ProjectUI app;


    public InventoryTableAdapter(ProductsList model, ProjectUI app) {
        this.model = model;
        this.app = app;
        model.addListener(this);
    }

    @Override
    public int getRowCount() {
        return model.size();
    }

    //this case equals 6
    @Override
    public int getColumnCount() {
        return _columnNames.length;
    }


    @Override
    public String getColumnName(int column) {
        return _columnNames[column];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Product product = model.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return product.isSelected();
            case 1:
                return product.getId();
            case 2:
                return product.getName();
            case 3:
                return product.getDescription();
            case 4:
                return product.getPrice();
            case 5:
                return product.getQuantity();
        }
        return null;
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Boolean.class;
        } else if (columnIndex == 6) {
            return Object.class;
        } else if (columnIndex == 4) {
            return Double.class;
        } else if (columnIndex == 5) {
            return Integer.class;
        }
        return String.class;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Only the first column in the table should be editable
        return columnIndex == 0 || columnIndex == 6;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // How do I toggle the checked state of the cell when columnIndex == 0?
        System.out.println("(" + rowIndex + ", " + columnIndex +
                ") clicked. Value = " + aValue);
        Product item = model.get(rowIndex);
        if (columnIndex == 0) {
            item.setSelected(Boolean.valueOf(aValue.toString()));
            if (item.isSelected()) {
                model.addSelect(item.getId());
            } else {
                model.removeSelect(item.getId());
            }
        }

        if (columnIndex == 6) {
            if ("edit".equals(aValue)) {
                System.out.println("edit");
                System.out.println("add product dialog show up");
                productGUI productGui = new productGUI(app, item);
            } else if ("delete".equals(aValue)) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    model.addSelect(item.getId());
                    model.batchDelete();
                    model.triggerSave();
                }
                System.out.println("delete");
            } else {
                item.setQuantity(item.getQuantity() - 1);
                ShoppingItem shoppingItem = new ShoppingItem(item.getId(), 1, item);
                app.getShoppingCartList().addShoppingCartResult(shoppingItem);
                if (item.getQuantity() <= 0) {
                    model.addSelect(item.getId());
                    model.batchDelete();
                }
            }
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }


    /**
     * Called when data is added to the underlying {@link Product}. Forwards the change to the {@link JTable} using
     * the {@link #fireTableDataChanged()} method, which is implemented by {@link AbstractTableModel}.
     *
     * <p>Note: There are many other <code>fire...()</code> methods implemented by {@link AbstractTableModel}, which
     * may be more efficient. For example, {@link #fireTableDataChanged()} notifies the table that any of its data
     * could have changed and it needs to entirely redraw itself, whereas {@link #fireTableCellUpdated(int, int)}
     * specifies the exact table cell which changed, meaning it doesn't need to redraw the whole table. Investigate
     * these other methods yourself. You can see them at:
     * https://docs.oracle.com/javase/8/docs/api/javax/swing/table/AbstractTableModel.html</p>
     *
     * @param model the model that changed
     * @param dataItem the item that was added
     * @param index the index of the new item
     */

    @Override
    public void projectDataAdded(ProductsList model, String dataItem, int index) {
        fireTableDataChanged();
        System.out.println("projectDataAdded");
        //model.triggerSave();
    }


    @Override
    public void projectDataRemoved(ProductsList model) {
        fireTableDataChanged();
        System.out.println("projectDataChanged");
        //model.triggerSave();

    }

    @Override
    public void projectDataChanged(ProductsList model, int index, String oldValue, String newValue) {
        fireTableDataChanged();
    }
}
