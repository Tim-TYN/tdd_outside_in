package copilot.shopping_cart;

import java.util.HashMap;
import java.util.Map;

public class ShopCatalog {
    private Map<String, Product> products = new HashMap<>();

    public void addProduct(String productID, double price, int quantity) {
        products.put(productID, new Product(productID, price, quantity));
    }

    public double getProductPrice(String productID) {
        Product product = products.get(productID);
        return product != null ? product.getPrice() : 0.0;
    }

    public boolean isProductAvailable(String productID, int quantity) {
        Product product = products.get(productID);
        return product != null && product.getQuantity() >= quantity;
    }
}
