package ictgradschool.industry.final_project.view;


import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.InventoryTableAdapter;
import ictgradschool.industry.final_project.util.SpringUtilities;
import ictgradschool.industry.final_project.view.product.productGUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

public class InventoryPanel extends ProductPanel {

    private JTextField filterText;

    private JTextField statusText;

    private JTextField descritionText;

    private JComboBox<String> comboBox;
    private TableRowSorter<InventoryTableAdapter> sorter;


    public InventoryPanel(ProjectUI app) {
        super(app);
    }

    @Override
    public void getBottom() {
        //Create a separate form for filterText and statusText

        JPanel form = new JPanel(new SpringLayout());

        form.setBorder(BorderFactory.createTitledBorder("Search"));


        JLabel l1 = new JLabel("Identifier:", SwingConstants.TRAILING);
        form.add(l1);
        filterText = new JTextField();
        //Whenever filterText changes, invoke newFilter.
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter(filterText, 1);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter(filterText, 1);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter(filterText, 1);
                    }
                }
        );
        l1.setLabelFor(filterText);
        form.add(filterText);

        JLabel l2 = new JLabel("Name:", SwingConstants.TRAILING);
        form.add(l2);
        statusText = new JTextField();

        statusText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter(statusText, 2);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter(statusText, 2);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter(statusText, 2);
                    }
                }
        );


        l2.setLabelFor(statusText);
        form.add(statusText);

        JLabel l3 = new JLabel("Description:", SwingConstants.TRAILING);
        form.add(l3);
        descritionText = new JTextField();

        descritionText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter(descritionText, 3);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter(descritionText, 3);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter(descritionText, 3);
                    }
                }
        );


        l3.setLabelFor(descritionText);
        form.add(descritionText);

        JLabel l4 = new JLabel("Stock:", SwingConstants.TRAILING);
        form.add(l4);


        Map<String, Integer> items = new HashMap<>();
        items.put("all", 0);
        items.put("> 0", 1);
        items.put("= 0", 2);

        comboBox = new JComboBox<>(items.keySet().toArray(new String[0]));
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    JComboBox source = (JComboBox) e.getSource();
                    String selectedKey = (String) source.getSelectedItem();
                    int selectedValue = items.get(selectedKey);
                    System.out.println("Selected Key: " + selectedKey);
                    System.out.println("Selected Value: " + selectedValue);
                    stockFilter(selectedValue);

                }

            }
        });


        l4.setLabelFor(comboBox);
        form.add(comboBox);
        SpringUtilities.makeCompactGrid(form, 4, 2, 6, 6, 6, 6);
        add(form, BorderLayout.SOUTH);
    }
    /**
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilter(JTextField field, int indices) {
        RowFilter<InventoryTableAdapter, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(field.getText(), indices);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    @Override
    public void createFrameUI(String title) {
        inventoryFrame = new JFrame(title);
        inventoryFrame.setContentPane(this);
        //add menu bar
        JMenuBar mb = getMenuBar();
        if (mb != null) {
            inventoryFrame.setJMenuBar(mb);
        }

        // Add a WindowListener to the JFrame
        inventoryFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Inventory or sales Window Closed");
                app.getProductsList().clearSelectedIds();
            }
        });

        inventoryFrame.pack();
        inventoryFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        inventoryFrame.setLocationRelativeTo(null);
        inventoryFrame.setVisible(true);
    }

    @Override
    public JMenuBar getMenuBar() {
        JMenuBar mb = new JMenuBar();

        JMenu menuadd = new JMenu("Add");
        menuadd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("add product dialog show up");
                productGUI productGui = new productGUI(app, null);
            }
        });

        JMenu menudelete =new JMenu("Delete");
        menudelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("delete product dialog show up");
                if (app.getProductsList().getSelectedIds().size() == 0) {
                    JOptionPane.showMessageDialog(null, "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        app.getProductsList().batchDelete();
                        app.getProductsList().triggerSave();
                    }
                }
            }
        });

        JMenu welcomemenu =new JMenu("Welcome");
        welcomemenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("welcome dialog show up");
                inventoryFrame.dispose();
                app.createWelcomeFrame();
            }
        });


        mb.add(menuadd);
        mb.add(menudelete);
        mb.add(welcomemenu);
        mb.setPreferredSize(new Dimension(100,40));
        return mb;
    }

    @Override
    public void getTable() {
        tableModel = new InventoryTableAdapter(app.getProductsList(), app);
        tableView = new JTable(tableModel);

        // Enable sorting for all columns
        tableView.setAutoCreateRowSorter(true);
        // Create a TableRowSorter and apply it to the table
        sorter = new TableRowSorter<InventoryTableAdapter>(tableModel) {
            @Override
            public boolean isSortable(int column) {
                return column != 0 && column != 6;
            }
        };
        tableView.setRowSorter(sorter);

        // Create custom cell renderers and editors for specific columns
        TableCellRenderer intRenderer = new DefaultTableCellRenderer();
        TableCellEditor intEditor = new DefaultCellEditor(new JTextField());
        TableCellRenderer doubleRenderer = new DefaultTableCellRenderer();
        TableCellEditor doubleEditor = new DefaultCellEditor(new JTextField());
        // Set the custom renderers and editors for the desired columns
        tableView.getColumnModel().getColumn(4).setCellRenderer(doubleRenderer);
        tableView.getColumnModel().getColumn(4).setCellEditor(doubleEditor);
        tableView.getColumnModel().getColumn(5).setCellRenderer(intRenderer);
        tableView.getColumnModel().getColumn(5).setCellEditor(intEditor);

        AcceptRejectRenderer renderer = new AcceptRejectRenderer();
        tableView.getColumnModel().getColumn(6).setCellRenderer(renderer);
        tableView.getColumnModel().getColumn(6).setCellEditor(new AcceptRejectEditor());
        tableView.setRowHeight(renderer.getTableCellRendererComponent(tableView, null, true, true, 0, 0).getPreferredSize().height);
        tableView.getColumnModel().getColumn(6).setPreferredWidth(150);
        tableView.changeSelection(0, 0, false, false);

        add(new JScrollPane(tableView), BorderLayout.CENTER);
    }

    private void stockFilter(int value) {
        RowFilter<InventoryTableAdapter, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            switch (value) {
                case 0:
                    rf = RowFilter.regexFilter(".*", 5);
                    break;
                case 1:
                    rf = RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, 0, 5);
                    break;
                case 2:
                    rf = RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, 0, 5);
                    break;
            }
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    public class AcceptRejectPane extends JPanel {

        private JButton accept;
        private JButton reject;
        private String state;

        public AcceptRejectPane() {
            setLayout(new GridBagLayout());
            accept = new JButton("Edit");
            accept.setActionCommand("edit");
            reject = new JButton("Delete");
            reject.setActionCommand("delete");

            add(accept);
            add(reject);

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                }
            };

            accept.addActionListener(listener);
            reject.addActionListener(listener);
        }

        public void addActionListener(ActionListener listener) {
            accept.addActionListener(listener);
            reject.addActionListener(listener);
        }

        public String getState() {
            return state;
        }
    }

    public class AcceptRejectRenderer extends DefaultTableCellRenderer {

        private AcceptRejectPane acceptRejectPane;

        public AcceptRejectRenderer() {
            acceptRejectPane = new AcceptRejectPane();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                acceptRejectPane.setBackground(table.getSelectionBackground());
            } else {
                acceptRejectPane.setBackground(table.getBackground());
            }
            return acceptRejectPane;
        }
    }

    public class AcceptRejectEditor extends AbstractCellEditor implements TableCellEditor {

        private AcceptRejectPane acceptRejectPane;

        public AcceptRejectEditor() {
            acceptRejectPane = new AcceptRejectPane();
            acceptRejectPane.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            stopCellEditing();
                        }
                    });
                }
            });
        }

        @Override
        public Object getCellEditorValue() {
            return acceptRejectPane.getState();
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                acceptRejectPane.setBackground(table.getSelectionBackground());
            } else {
                acceptRejectPane.setBackground(table.getBackground());
            }
            return acceptRejectPane;
        }
    }
}