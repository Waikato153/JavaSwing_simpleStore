package ictgradschool.industry.final_project.view;

import ictgradschool.industry.final_project.ProjectUI;
import ictgradschool.industry.final_project.model.InventoryTableAdapter;
import ictgradschool.industry.final_project.model.ProductsCartList;
import ictgradschool.industry.final_project.model.ShoppingCartListAdapter;
import ictgradschool.industry.final_project.view.sales.CheckoutPanel;
import ictgradschool.industry.final_project.view.sales.CheckoutPanelAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import java.util.HashMap;

public class PointSalePanel extends ProductPanel{
    private ShoppingCartListAdapter _cartModel;

    private CheckoutPanel checkoutPanel;

    private JTable tableCartView;


    public PointSalePanel(ProjectUI app) {
        super(app);
        _cartModel = new ShoppingCartListAdapter(app.getShoppingCartList(), app);
    }

    @Override
    public void getBottom() {

    }
    @Override
    public void createFrameUI(String title) {
        inventoryFrame = new JFrame(title);

        //add menu bar
        JMenuBar mb = getMenuBar();
        if (mb != null) {
            inventoryFrame.setJMenuBar(mb);
        }
        tableCartView = new JTable(_cartModel);

        HashMap<String, String> map = new HashMap<>();
        map.put("delete", "Delete");

        AcceptRejectRenderer renderer = new AcceptRejectRenderer(map);
        tableCartView.getColumnModel().getColumn(4).setCellRenderer(renderer);
        tableCartView.getColumnModel().getColumn(4).setCellEditor(new AcceptRejectEditor(map));
        tableCartView.setRowHeight(renderer.getTableCellRendererComponent(tableView, null, true, true, 0, 0).getPreferredSize().height);


        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(tableCartView);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Shopping Cart List"));
        right.add(scrollPane);
        right.add(Box.createRigidArea(new Dimension(10, 0)));

        checkoutPanel = new CheckoutPanel();

        CheckoutPanelAdapter checkoutPanelAdapter = new CheckoutPanelAdapter(checkoutPanel, app.getShoppingCartList());

        right.add(checkoutPanel);

        /* Create main pxane for the application. */
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.X_AXIS));
        mainPane.add(this);
        mainPane.add(Box.createRigidArea(new Dimension(10, 0)));
        mainPane.add(right);
        inventoryFrame.add(mainPane);
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
        tableModel = new InventoryTableAdapter(app.getProductsCartList(), app);
        tableView = new JTable(tableModel);

        HashMap<String, String> map = new HashMap<>();
        map.put("add", "Add to Cart");

        AcceptRejectRenderer renderer = new AcceptRejectRenderer(map);
        tableView.getColumnModel().getColumn(6).setCellRenderer(renderer);
        tableView.getColumnModel().getColumn(6).setCellEditor(new AcceptRejectEditor(map));

        tableView.setRowHeight(renderer.getTableCellRendererComponent(tableView, null, true, true, 0, 0).getPreferredSize().height);
        tableView.getColumnModel().getColumn(6).setPreferredWidth(150);
        tableView.changeSelection(0, 0, false, false);

        add(new JScrollPane(tableView), BorderLayout.CENTER);
    }

    public class AcceptRejectPane extends JPanel {

        private JButton accept;
        private String state;

        public AcceptRejectPane(HashMap<String, String> map) {
            setLayout(new GridBagLayout());
            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                }
            };

            for (String key : map.keySet()) {
                accept = new JButton(map.get(key));
                accept.setActionCommand(key);
                add(accept);
                accept.addActionListener(listener);
            }
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

        public AcceptRejectRenderer(HashMap<String, String> map) {
            acceptRejectPane = new AcceptRejectPane(map);
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

        public AcceptRejectEditor(HashMap<String, String> map) {
            acceptRejectPane = new AcceptRejectPane(map);
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