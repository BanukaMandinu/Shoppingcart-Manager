import java.util.ArrayList;

public interface ShoppingManager {
    public void addNewProduct(Product product_item);

    public void deleteProduct();

    public void printListOfProducts();

    public void saveFileProducts(ArrayList<Product> products);

}
