import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductGUI extends JFrame {
    private JLabel selectCategory, productDetails, productID, category, productName, data1, data2, itemsAvailable;
    private JButton addToShoppingCart, ViewShoppingCart, SortButton;
    private JComboBox productComboBox;
    private JTable productsTable;
    private JScrollPane scrollPane;
    private JPanel panel;

    public ProductGUI(User user) {
        initializeComponents(user);
        setLayout(new GridBagLayout());
        GUILayouts();
        setVisible(true);
        setSize(800, 600);
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeComponents(User user) {
        setTitle("Westminster Shopping Center");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //open the shopping cart
        ViewShoppingCart = new JButton("VIEW SHOPPING CART");

        ViewShoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(user);
                shoppingCartGUI.setVisible(true);
                shoppingCartGUI.setResizable(false);
            }
        });


        //product category selection comboBox
        selectCategory = new JLabel("Select Product Category");
        productComboBox = new JComboBox(new String[]{"All", "Electronics", "Clothing"});
        productComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) productComboBox.getSelectedItem();
                ArrayList<Product> products = WestminsterShoppingManager.getProductArrayList();
                if (selectedCategory.equals("All")) {
                    setDataFromArrayList(products);
                    return;
                } else if (selectedCategory.equals("Electronics")) {
                    ArrayList<Product> electronics = new ArrayList<>();
                    for (Product product : products) {
                        if (product instanceof Electronics) {
                            electronics.add(product);
                        }
                    }
                    setDataFromArrayList(electronics);
                } else if (selectedCategory.equals("Clothing")) {
                    ArrayList<Product> clothing = new ArrayList<>();
                    for (Product product : products) {
                        if (product instanceof Clothing) {
                            clothing.add(product);
                        }
                    }
                    setDataFromArrayList(clothing);
                }

            }
        });

        // sort
        SortButton = new JButton("SORT");
        SortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Product> products = WestminsterShoppingManager.getProductArrayList();
                Collections.sort(products, Comparator.comparing(Product::getProductID));
                setDataFromArrayList(products);
            }
        });

        productsTable = new JTable();
        ArrayList<Product> products = WestminsterShoppingManager.getProductArrayList();
        setDataFromArrayList(products);
        scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(700, 200));

        productsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String productId = (String) table.getValueAt(row, 0);
                Product product = WestminsterShoppingManager.getProduct(productId);
                if (product != null && product.getNumberOfAvailableProducts() <= 3) {
                    component.setBackground(Color.RED);
                } else {
                    component.setBackground(Color.WHITE);
                }
                if (component instanceof JLabel) {
                    ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
                }
                return component;
            }
        });

        // product details Labels
        productDetails = new JLabel("Selected Product - Details");
        productID = new JLabel("Product Id: ");
        category = new JLabel("Category: ");
        productName = new JLabel("Name: ");
        data1 = new JLabel("Data1: ");
        data2 = new JLabel("Data2: ");
        itemsAvailable = new JLabel("Available Products");

        // add to cart button
        addToShoppingCart = new JButton("ADD TO CART ");

        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(user);
        shoppingCartGUI.setVisible(false);

        // action listenr to add to cart products
        addToShoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = productsTable.getSelectedRow();
                if (row >= 0) {
                    String productId = (String) productsTable.getValueAt(row, 0);
                    Product product = WestminsterShoppingManager.getProduct(productId);
                    if (product != null) {
                        user.getShoppingCart().addProduct(product, 1);
                        productsTable.clearSelection();
                        JOptionPane.showMessageDialog(null, product.getProductName() + " added to the cart!");
                        product.setNumberOfAvailableProducts(product.getNumberOfAvailableProducts() - 1);
                        panel.setVisible(true);
                        ShoppingCartGUI.updateCart(user);
                    }
                    pack();
                }
            }
        });
        // action listener for products.(table)
        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = productsTable.getSelectedRow();
                if (row >= 0) {
                    String Id = (String) productsTable.getValueAt(row, 0);
                    Product product = WestminsterShoppingManager.getProduct(Id);

                    panel.setVisible(true);
                    pack();

                    if (product.getNumberOfAvailableProducts() == 0) {
                        addToShoppingCart.setEnabled(false);
                    } else {
                        addToShoppingCart.setEnabled(true);
                    }

                    if (product != null) {
                        productID.setText("Product Id: " + product.getProductID());
                        productName.setText("Name: " + product.getProductName());
                        if (product instanceof Electronics) {
                            category.setText("Category: " + ((Electronics) product).getProductType());
                            data1.setText("Brand: " + ((Electronics) product).getBrand());
                            data2.setText("Warranty Period: " + ((Electronics) product).getWarrantyPeriod());
                        } else if (product instanceof Clothing) {
                            category.setText("Category: " + ((Clothing) product).getProductType());
                            data1.setText("Size: " + ((Clothing) product).getSize());
                            data2.setText("Color: " + ((Clothing) product).getColor());
                        }
                        itemsAvailable.setText("Items Available: " + product.getNumberOfAvailableProducts());
                    }
                }
            }
        });

    }

    // set data of the default table model
    private void setDataFromArrayList(ArrayList<Product> products) {
        String[] columnNames = {"Product ID", "Name", "Category", "Price(RS)", "Product Info"};
        Object[][] data = new Object[products.size()][5];
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            data[i][0] = product.getProductID();
            data[i][1] = product.getProductName();
            data[i][3] = product.getPrice();

            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                data[i][2] = ((Electronics) product).getProductType();
                data[i][4] = electronics.information();
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                data[i][2] = ((Clothing) product).getProductType();
                data[i][4] = clothing.information();
            }
        }

        // Default table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productsTable.setModel(model);

    }

    private void GUILayouts() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);

        // adding comboBox , sort and cart button
        add(SortButton, constraints);
        constraints.gridx++;
        add(selectCategory, constraints);
        constraints.gridx++;
        add(productComboBox, constraints);
        constraints.gridx++;
        add(ViewShoppingCart, constraints);

        // the table
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(15, 15, 10, 15);
        add(scrollPane, constraints);

        //product details
        panel = new JPanel(new GridLayout(8, 1, 10, 10));
        constraints.gridx = 0;
        constraints.gridy++;
        panel.add(productDetails);
        constraints.gridy++;
        panel.add(productID);
        constraints.gridy++;
        panel.add(category);
        constraints.gridy++;
        panel.add(productName);
        constraints.gridy++;
        panel.add(data1);
        constraints.gridy++;
        panel.add(data2);
        panel.add(itemsAvailable);
        constraints.gridy = 6;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        add(panel, constraints);

        // add to shopping cart button
        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridy = 9;
        constraints.gridx = 0;
        add(addToShoppingCart, constraints);
    }

//   public static void main(String[] args) {
//       new ProductGUI(new User());
//    }

}