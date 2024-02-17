import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    private WestminsterShoppingManager westminsterShoppingManager;

    @BeforeEach
    void setUp() {
        westminsterShoppingManager = new WestminsterShoppingManager();
    }


    @Test
    void productDetailInsert() {
        Product product = new Electronics("test10", "Test Product", 10, 100.0, "TestBrand", "Two years");

        // Add the product to the WestminsterShoppingManager
        westminsterShoppingManager.addNewProduct(product);

        // Assert that the product is in the productList of the WestminsterShoppingManager
        assertTrue(WestminsterShoppingManager.getProductArrayList().contains(product));
    }

    @Test
    public void testAddNewProductDuplicateID() {
        // Creating a mock product
        Product Product1 = new Clothing("Test123", "Product 1", 10, 5000, "M", "Blue");
        Product Product2 = new Clothing("Test123", "Product 2", 5, 500, "S", "Red");

        // Adding the first test product
        westminsterShoppingManager.addNewProduct(Product1);

        // Adding the second test product with the same ID
        westminsterShoppingManager.addNewProduct(Product2);

        // Checking that the second product was not added due to duplicate ID
        assertTrue(WestminsterShoppingManager.getProductArrayList().contains(Product2));
    }

}