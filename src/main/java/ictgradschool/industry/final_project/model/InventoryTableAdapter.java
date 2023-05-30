package ictgradschool.industry.final_project.model;

import javax.swing.table.AbstractTableModel;

public class InventoryTableAdapter extends AbstractTableModel implements ProductsListListener {

    /**********************************************************************
     * YOUR CODE HERE
     */
    private String[] _columnNames = {"Product ID", "Name", "Description", "Price", "Quantity"};
    private ProductsList model;

    public InventoryTableAdapter(ProductsList model) {
        this.model = model;
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
                return product.getId();
            case 1:
                return product.getName();
            case 2:
                return product.getDescription();
            case 3:
                return product.getPrice();
            case 4:
                return product.getQuantity();
        }
        return null;
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
    }

    @Override
    public void projectDataRemoved(ProductsList model, String dataItem) {
        fireTableDataChanged();
    }

    @Override
    public void projectDataChanged(ProductsList model, int index, String oldValue, String newValue) {
        fireTableDataChanged();
    }
}
