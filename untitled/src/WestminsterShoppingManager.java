import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Collections;

public class WestminsterShoppingManager implements ShoppingManager {
    Scanner scanner = new Scanner(System.in);
    private static final String FILE_PATH_PRODUCTS = "Products.ser";
    private static final String FILE_PATH_USER = "Users.ser";
    public static ArrayList<Product> productArrayList = new ArrayList<>();
    public static ArrayList<User> userArrayList = new ArrayList<>();

    //menu
    public void menu() {
        loadFromFileProducts();
        loadFromFileUser();
        boolean exit = true;
        while (exit) {
            try {
                System.out.println("----------------------------------------");
                System.out.println("Select an option: ");
                System.out.println("1. Add a new Product");
                System.out.println("2. Delete a Product");
                System.out.println("3. Print the list of Products");
                System.out.println("4. Save in a file");
                System.out.println("5. Open in GUI interface");
                System.out.println("0. Exit the program");
                System.out.println("----------------------------------------");

                System.out.print("Option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        productDetailInsert(); // add the products to the system.
                        break;
                    case 2:
                        deleteProduct();
                        break;
                    case 3:
                        printListOfProducts();
                        break;
                    case 4:
                        saveFileProducts(productArrayList);
                        break;
                    case 5:
                        GUI();
                        break;
                    case 0:
                        System.out.println("Exited the Program");
                        exit = false;
                        break;
                    default:
                        System.out.println("Invalid input.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, Try Again!!");
                scanner.next();
                menu();
            }
        }
    }

    public void productDetailInsert() {
        scanner.nextLine();
        System.out.print("Product ID: ");
        String productID = scanner.nextLine();
        for (Product product : productArrayList) {
            if (product.getProductID().equals(productID)) {
                System.out.println("ProductID is already ADDED, try a new one.");
                return;

            }
        }
        System.out.print("Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Number of Available Products: ");
        int numberOfAvailableProducts = scanner.nextInt();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        System.out.println("----------------------------------------");
        System.out.println("Inset the Product Type, ");
        System.out.println("1. Clothing");
        System.out.println("2. Electronic");
        System.out.println("----------------------------------------");
        System.out.print("Product Type:  ");
        int productType = scanner.nextInt();
        scanner.nextLine();
        System.out.println("----------------------------------------");

        if (productType == 1) {
            System.out.print("Size: ");
            String size = scanner.next();
            scanner.nextLine();
            System.out.print("Color: ");
            String color = scanner.nextLine();
            Clothing clothing = new Clothing(productID, productName, numberOfAvailableProducts, price, size, color);
            addNewProduct(clothing);
        } else if (productType == 2) {
            System.out.print("Brand: ");
            String brand = scanner.nextLine();
            System.out.print("Warranty Period: ");
            String warranty = scanner.nextLine();
            Electronics electronics = new Electronics(productID, productName, numberOfAvailableProducts, price, brand, warranty);
            addNewProduct(electronics);
        } else {
            System.out.println("Invalid input, Try Again!");
        }
    }

    @Override
    public void addNewProduct(Product product_item) {
        if (productArrayList.size() < 50) {
            productArrayList.add(product_item);
            System.out.println("Product have been added.");
        } else System.out.println("Maximum product count that can added is 50");
    }

    @Override
    public void deleteProduct() {
        scanner.nextLine();
        System.out.print("Insert the product ID: ");
        String productID = scanner.nextLine();

        // Find the index of the product with the given ID
        int indexNumber = -1;
        for (int i = 0; i < productArrayList.size(); i++) {
            if (productArrayList.get(i).getProductID().equals(productID)) {
                indexNumber = i;
                break;
            }
        }

        if (indexNumber == -1) {
            System.out.println("Product with ID " + productID + " not found in the list.");
        } else {
            Product deletedProduct = productArrayList.remove(indexNumber);

            if (deletedProduct instanceof Clothing) {
                System.out.println("Clothing product deleted\n");
                System.out.println("Deleted product Details: \n" + deletedProduct);
            } else if (deletedProduct instanceof Electronics) {
                System.out.println("Electronics product deleted\n");
                System.out.println("Deleted product Details: \n" + deletedProduct);
            }

            System.out.println("Total number of products left in the system: " + productArrayList.size());
        }
    }

    @Override
    public void printListOfProducts() {
        // Sort the productArrayList based on product IDs
        Collections.sort(productArrayList);

        // Print the sorted list of products
        for (Product product : productArrayList) {
            System.out.println(product);
        }
    }

    @Override
    public void saveFileProducts(ArrayList<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH_PRODUCTS))) {
            oos.writeObject(products);
            System.out.println("Data saved to " + FILE_PATH_PRODUCTS);
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }


    public static void saveFileUsers() {
        ArrayList<User> userList = WestminsterShoppingManager.getUserArrayList();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH_USER))) {
            oos.writeObject(userList);
            System.out.println("Data saved to " + FILE_PATH_PRODUCTS);
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    public void loadFromFileProducts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH_PRODUCTS))) {
            productArrayList = (ArrayList<Product>) ois.readObject();
            System.out.println("----------------------------------------");
            System.out.println("Data loaded from " + FILE_PATH_PRODUCTS);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from file, Error: " + e.getMessage());
        }
    }

    public void loadFromFileUser() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH_USER))) {
            userArrayList = (ArrayList<User>) ois.readObject();
            System.out.println("----------------------------------------");
            System.out.println("Data loaded from " + FILE_PATH_USER);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from file, Error: " + e.getMessage());
        }
    }


    public void GUI() {
        userLogin userLogin = new userLogin();
        userLogin.setVisible(true);
        userLogin.setResizable(false);
    }

    public static void setProductArrayList(ArrayList<Product> productArrayList) {
        WestminsterShoppingManager.productArrayList = productArrayList;
    }

    public static void setUserArrayList(ArrayList<User> userArrayList) {
        WestminsterShoppingManager.userArrayList = userArrayList;
    }

    public static Product getProduct(String productId) {
        Product product = null;
        for (Product p : productArrayList) {
            if (p.getProductID().equals(productId)) {
                product = p;
                break;
            }
        }
        return product;
    }

    public static ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public static ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

}
