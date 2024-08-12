import java.util.HashMap;
import java.util.Map;

public class CartManager{

    private Map<String, Map<CartItem, Integer>> userCarts;

    public CartManager(Map<String, Map<CartItem, Integer>> userCarts){
        this.userCarts=userCarts;
    }

    public CartManager(){
        this.userCarts = new HashMap<>();
    }

    public Map<String, Map<CartItem, Integer>> getUserCarts(){
        return this.userCarts;
    }

    public Map<CartItem, Integer> getUserCart(String email) {
        if (email != null) {
            return this.userCarts.get(email); 
        }
        return null; 
    }

    public void addToCart(String email, CartItem item){
        if (email != null && item != null) {
            // Make sure user already has an active cart
            Map<CartItem, Integer> cart = userCarts.get(email);

            // if he hasn't create a new one
            if (cart == null) {
                cart = new HashMap<>();
                userCarts.put(email, cart);
            }

            // Check if item is already in cart
            Integer quantity = cart.get(item);

            if (quantity == null) {
                // if item is not in cart, add it to one with the quantity of one
                cart.put(item, 1);
            } else {
                // if the item is already in the cart, increase the amount by one
                cart.put(item, quantity + 1);
            }
        } else {
            throw new IllegalArgumentException("Email and/or item are not correct");
        }
        }

    //Testing
    /* 
    public static void main(String[] args) {
        // new CartManager gets created with the defined paths
        CartManager cartManager = new CartManager();

        // creating CartItem objects
        CartItem apple = new CartItem("http://example.com/apple", "Apple", 1);
        CartItem banana = new CartItem("http://example.com/banana", "Banana", 2);
        CartItem airpods = new CartItem("http://example.com/apple/ipods", "AirPods", 4);

        // testing out the addToCart method
        cartManager.addToCart("user1@example.com", apple);
        cartManager.addToCart("user1@example.com", banana);
        cartManager.addToCart("user1@example.com", apple); // testing to increase by one
        cartManager.addToCart("user1@example.com", airpods);

        // display cart for user user1@example.com
        System.out.println("Cart for user1@example.com: " + cartManager.getUserCart("user1@example.com"));

        // testing getUserCarts method
        System.out.println("All user carts: " + cartManager.getUserCarts());

        // testing addToCart with no email/item
        try {
            cartManager.addToCart(null, apple);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        try {
            cartManager.addToCart("user2@example.com", null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }*/

}