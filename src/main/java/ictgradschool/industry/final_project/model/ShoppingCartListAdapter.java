package ictgradschool.industry.final_project.model;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.bean.Product;
import ictgradschool.industry.final_project.model.bean.ShoppingItem;
import ictgradschool.industry.final_project.view.product.productGUI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class ShoppingCartListAdapter extends AbstractTableModel implements ShoppingCartListener {

    /**********************************************************************
     * YOUR CODE HERE
     */
    private String[] _columnNames = {"Product ID", "Name", "Quantity", "Price", "Action"};
    private ShoppingCartList model;
    private ProjectUI app;
    /** Creates a new {@link ShoppingCartListAdapter} and adds a lisener to it so we can be notified of changes */
    public ShoppingCartListAdapter(ShoppingCartList model, ProjectUI app) {
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
        ShoppingItem item = model.getResultAt(rowIndex);
        //Jolley, Paul, 57990651, exam:54, test: 66, assignment: 96, overall: 63
        switch (columnIndex) {
            case 0:
                return item.getProductId();
            case 1:
                return item.getProduct().getName();
            case 2:
                return item.getQuantity();
            case 3:
                return String.format("%.2f", item.getQuantity() * item.getProduct().getPrice());
        }

        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // How do I toggle the checked state of the cell when columnIndex == 0?
        System.out.println("(" + rowIndex + ", " + columnIndex +
                ") clicked. Value = " + aValue);
        ShoppingItem item = model.getResultAt(rowIndex);


        if (columnIndex == 4) {
            if ("delete".equals(aValue)) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    model.removeShoppingItem(rowIndex);
                    item.getProduct().setQuantity(item.getProduct().getQuantity() + item.getQuantity());
                    app.getProductsCartList().add(item.getProduct());
                }
            }
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Only the first column in the table should be editable
        return columnIndex == 4;
    }
    @Override
    public void CartDataChanged(ShoppingCartList model) {
        System.out.println("CartDataChanged");
        fireTableDataChanged();

        for (ShoppingCartObserver observer : model.listObserver()) {
            observer.update();
        }


    }

}
