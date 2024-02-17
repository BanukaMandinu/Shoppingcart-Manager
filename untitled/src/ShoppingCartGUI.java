import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCartGUI extends JFrame {
    private static JTable productTable;
    private static JLabel finalTotalLabel;
    private static JLabel firstPurchaseDiscountLabel;
    private static JLabel discountForSameCategory;
    private static JLabel totalLabel;
    private static JButton checkout;
    private JScrollPane scrollPane;
    private ShoppingCart shoppingCart;

    public ShoppingCartGUI(User user) {
        this.shoppingCart = user.getShoppingCart();
        setLayout(new GridBagLayout());
        setTitle("Westminster shopping cart");
        initializeComponents(user);
        GUILayouts();
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initializeComponents(User user) {
        double total = user.getShoppingCart().CalculateTotalCost();
        double fistPurchasedUserDiscount = user.getShoppingCart().firstPurchaseDiscount(user);
        double TotalDiscount = user.getShoppingCart().discount();


        productTable = new JTable();
        setDataIntoHashmap(shoppingCart.getProducts());
        scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(700, 300));

        var centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel center = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                center.setHorizontalAlignment(SwingConstants.CENTER);
                return center;
            }
        };

        productTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        productTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        productTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);


        totalLabel = new JLabel("Total RS - " + total);
        firstPurchaseDiscountLabel = new JLabel("First Purchase Discount(10%) - RS" + fistPurchasedUserDiscount);
        discountForSameCategory = new JLabel("Three Items in same Category Discount(20%) - RS " + TotalDiscount);
        finalTotalLabel = new JLabel("Total Cost - RS " + (total - fistPurchasedUserDiscount - TotalDiscount));
        checkout = new JButton("Checkout");

        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user.getShoppingCart().getProducts().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please add products to the cart!");
                } else {
                    JOptionPane.showMessageDialog(null, "Checkout successful!");
                    user.getShoppingCart().getProducts().clear();
                    user.setFirstPurchase(false);

                    //need to Update users file

                    //need to Update products file
                    ShoppingCartGUI.this.dispose();
                }
            }
        });


    }

    private static void setDataIntoHashmap(Map<Product, Integer> hashmap) {
        String[] columnName = {"Product", "Quantity", "Price"};
        Object[][] data = new Object[hashmap.size()][3];
        int i = 0;

        for (Product product : hashmap.keySet()) {
            if (product instanceof Electronics) {
                data[i][0] = product.getProductID() + ", " + product.getProductName() + " " + ((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarrantyPeriod();
            } else if (product instanceof Clothing) {
                data[i][0] = product.getProductID() + " " + product.getProductName() + " " + ((Clothing) product).getSize() + ", " + ((Clothing) product).getColor();
            }
            data[i][1] = hashmap.get(product);
            data[i][2] = "RS " + product.getPrice();
            i++;
        }
        DefaultTableModel model = new DefaultTableModel(data, columnName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable.setModel(model);
    }

    private static void total(User user) {
        totalLabel.setText("Total -RS " + user.getShoppingCart().CalculateTotalCost());
        firstPurchaseDiscountLabel.setText("First Purchase Discount(10%) -RS " + user.getShoppingCart().firstPurchaseDiscount(user));
        discountForSameCategory.setText("Three Items in same Category Discount(20%) -RS " + user.getShoppingCart().discount());
        finalTotalLabel.setText("Final Total - RS " + (user.getShoppingCart().CalculateTotalCost() - user.getShoppingCart().firstPurchaseDiscount(user)
                - user.getShoppingCart().discount()));
    }

    public static void updateCart(User user) {
        Map<Product, Integer> Items = new LinkedHashMap<>();
        Items = user.getShoppingCart().getProducts();
        setDataIntoHashmap(Items);
        //fireTableDataChanged();
        productTable.repaint();
        total(user);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };
        productTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        productTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        productTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

    }


    public void GUILayouts() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 5, 10);
        constraints.gridwidth = 1;

        //table
        constraints.gridy = 0;
        constraints.gridx = 0;
        add(scrollPane, constraints);

        // total amounts and discounts
        constraints.fill = GridBagConstraints.LINE_END;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.gridy++;
        add(totalLabel, constraints);
        constraints.gridy++;
        add(firstPurchaseDiscountLabel, constraints);
        constraints.gridy++;
        add(discountForSameCategory, constraints);
        constraints.gridy++;
        add(finalTotalLabel, constraints);

        //checkout bytton
        constraints.fill = GridBagConstraints.LAST_LINE_END;
        constraints.gridy++;
        add(checkout, constraints);
    }

    public static void main(String[] args) {
        new ShoppingCartGUI(new User());
    }


}
