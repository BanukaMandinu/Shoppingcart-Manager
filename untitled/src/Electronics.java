public class Electronics extends Product {
    private String brand;
    private String warrantyPeriod;
    final private String productType;

    public Electronics(String productID, String productName, int numberOfAvailableProducts, double price, String brand, String warrantyPeriod) {
        super(productID, productName, numberOfAvailableProducts, price);
        productType = "Electronics"; // adding a default value for the product type
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public String getProductType() {
        return productType;
    }

    public String information() {
        return brand + ", " + warrantyPeriod;
    }


    @Override
    public String toString() {
        return "\n" + "Product Type: " + getProductType() + '\n' +
                "productID: " + getProductID() + '\n' +
                "Product Name: " + getProductName() + '\n' +
                "Available Products: " + getNumberOfAvailableProducts() + '\n' +
                "Price: " + getPrice() + '\n' +
                "Brand: " + getBrand() + '\n' +
                "Warranty Period: " + this.warrantyPeriod;
    }

    @Override
    public int compareTo(Product o) {
        return this.getProductID().compareTo(o.getProductID());
    }
}

