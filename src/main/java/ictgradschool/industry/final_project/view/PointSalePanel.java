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

public class PointSalePanel extends ProductPanel{

    public PointSalePanel(ProjectUI app) {
        super(app);
    }

    @Override
    public void getBottom() {

    }

    @Override
    public JMenuBar getMenuBar() {
        JMenuBar mb = new JMenuBar();

        JMenu menuadd = new JMenu("Add to Cart");
        menuadd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        mb.add(welcomemenu);
        mb.setPreferredSize(new Dimension(100,40));
        return mb;
    }

    @Override
    public void getTable() {
        tableModel = new InventoryTableAdapter(app.getProductsList(), app);
        tableView = new JTable(tableModel);

        AcceptRejectRenderer renderer = new AcceptRejectRenderer();
        tableView.getColumnModel().getColumn(6).setCellRenderer(renderer);
        tableView.getColumnModel().getColumn(6).setCellEditor(new AcceptRejectEditor());
        tableView.setRowHeight(renderer.getTableCellRendererComponent(tableView, null, true, true, 0, 0).getPreferredSize().height);
        tableView.getColumnModel().getColumn(6).setPreferredWidth(150);
        tableView.changeSelection(0, 0, false, false);

        add(new JScrollPane(tableView), BorderLayout.CENTER);
    }

    public class AcceptRejectPane extends JPanel {

        private JButton accept;
        private String state;

        public AcceptRejectPane() {
            setLayout(new GridBagLayout());
            accept = new JButton("Add to Cart");
            accept.setActionCommand("add");
            add(accept);
            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                }
            };

            accept.addActionListener(listener);
        }

        public void addActionListener(ActionListener listener) {
            accept.addActionListener(listener);
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