package human.shopping_cart;
import java.util.HashMap;
import java.util.Map;

public class ShopCatalog {

    private Map<String, Product> products = new HashMap<>();

    public double getProductPrice(String productID){
        if (!products.containsKey(productID)) {
            throw new IllegalArgumentException("Product not found in catalog");
        }
        return products.get(productID).getPrice();
    }

    public boolean isProductAvailable(String productID, int quantity){
        if (!products.containsKey(productID)) {
            throw new IllegalArgumentException("Product not found in catalog");
        }
        return products.get(productID).getQuantity() >= quantity;
    }

    public void addProduct(String productID, double price, int quantity){
        products.put(productID, new Product(productID, quantity, price));
    }
}