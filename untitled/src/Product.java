import java.io.Serializable;

public abstract class Product implements Comparable<Product>, Serializable {
    private String productID;
    private String productName;
    private int numberOfAvailableProducts;
    private double price;
    private static final long serialVersionUID = 1L;

    public Product(String productID, String productName, int numberOfAvailableProducts, double price) {
        this.productID = productID;
        this.productName = productName;
        this.numberOfAvailableProducts = numberOfAvailableProducts;
        this.price = price;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setNumberOfAvailableProducts(int numberOfAvailableProducts) {
        this.numberOfAvailableProducts = numberOfAvailableProducts;
    }

    public int getNumberOfAvailableProducts() {
        return numberOfAvailableProducts;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

}
