import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private ShoppingCart shoppingCart;
    private boolean firstPurchase;
    private static ArrayList<User> userArrayList = new ArrayList<>();
    private static User currentUser;

    // Constructor
    public User() {
        this.shoppingCart = new ShoppingCart();
        this.firstPurchase = true;
    }

    // Setters and getters
    public static ArrayList<User> getUserList() {
        return userArrayList;
    }

    public static User getUser(String username, String password) {
        for (User user : userArrayList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public boolean isFirstPurchase() {
        return firstPurchase;
    }

    public void setFirstPurchase(boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setUsername(String username) {
        //check if username is duplicated
        for (User user : userArrayList) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists!");
                return;
            }
        }
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
