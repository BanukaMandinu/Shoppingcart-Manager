import java.util.*;

public class ShoppingCart {
    private Map<Product, Integer> products;

    //Constructor
    public ShoppingCart() {
        this.products = new HashMap<>();
    }

    //Getters
    public Map<Product, Integer> getProducts() {
        return this.products;
    }

    //add products to the cart
    public void addProduct(Product product, int AvailbleProducts) {
        if (this.products.containsKey(product)) {
            AvailbleProducts += this.products.get(product);
        } else {
            if (product.getNumberOfAvailableProducts() < AvailbleProducts) {
                System.out.println("No Items Available!");
                return;
            }
        }
        this.products.put(product, AvailbleProducts);
        System.out.println(product.getProductName() + " added to the cart.");
    }

    //remove products from the cart
    public void removeProducts(Product product) {
        if (this.products.containsKey(product)) {
            this.products.remove(product);
            System.out.println(product.getProductName() + " The product have removed from the cart"); //can take the id instead of the name
        } else {
            System.out.println("Product is not found in car.");
        }
    }

    //calculate the total cost of the products in the cart
    public double CalculateTotalCost() {
        double totalCost = 0.0;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
        return totalCost;
    }

    //calculate the first purchase discount
    public double firstPurchaseDiscount(User user) {
        double totalCost = CalculateTotalCost();
        if (user.isFirstPurchase()) {
            return (totalCost * 0.1);
        } else {
            return 0;
        }
    }

    //calculate discount when the user buys three products from same category
    public double discount() {
        int clothingCount = 0;
        int electronicsCount = 0;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            Product product = entry.getKey();
            if (product instanceof Clothing) {
                clothingCount += entry.getValue();
            } else if (product instanceof Electronics) {
                electronicsCount += entry.getValue();
            }
        }

        if (clothingCount >= 3 || electronicsCount >= 3) {
            return (CalculateTotalCost() * 0.2);
        } else {
            return 0.0;
        }
    }


}

