package chatgpt.shopping_cart;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> cartItems = new ArrayList<>();

    public void addProduct(Product product) {
        for (Product cartItem : cartItems) {
            if (cartItem.getProductID().equals(product.getProductID())) {
                cartItem.setQuantity(cartItem.getQuantity() + product.getQuantity());
                return;
            }
        }
        cartItems.add(product);
    }

    public void removeProduct(String productID) {
        cartItems.removeIf(product -> product.getProductID().equals(productID));
    }

    public void changeQuantityOfProduct(String productID, int quantity) {
        if (quantity == 0) {
            removeProduct(productID);
            return;
        }
        for (Product cartItem : cartItems) {
            if (cartItem.getProductID().equals(productID)) {
                cartItem.setQuantity(quantity);
                return;
            }
        }
    }

    public List<Product> getProducts() {
        return cartItems;
    }

    public Product getProduct(String productID) {
        for (Product product : cartItems) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    public double getTotalCost() {
        return cartItems.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();
    }
}

















