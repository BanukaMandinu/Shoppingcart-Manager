public class Clothing extends Product {
    private String size;
    private String color;
    final private String productType;

    public Clothing(String productID, String productName, int numberOfAvailableProducts, double price, String size, String color) {
        super(productID, productName, numberOfAvailableProducts, price);
        this.size = size;
        this.color = color;
        this.productType = "Clothing"; // adding a default value for the product type
    }

    public String getProductType() {
        return productType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String information() {
        return size + ", " + color;
    }

    @Override
    public String toString() {
        return "\n" + "Product Type: " + this.getProductType() + '\n' + "productID: " + getProductID() + '\n' + "Product Name: " + getProductName() + '\n' + "Available Products: " + getNumberOfAvailableProducts() + '\n' + "Price: " + getPrice() + '\n' + "Size: " + this.size + '\n' + "Color: " + this.color;
    }

    @Override
    public int compareTo(Product o) {
        return this.getProductID().compareTo(o.getProductID());
    }
}
