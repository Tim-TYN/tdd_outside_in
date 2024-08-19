package copilot.shopping_cart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        for (Product existingProduct : products) {
            if (existingProduct.getProductID().equals(product.getProductID())) {
                existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
                return;
            }
        }
        products.add(product);
    }

    public void removeProduct(String productID) {
        products.removeIf(product -> product.getProductID().equals(productID));
    }

    public void changeQuantityOfProduct(String productID, int newQuantity) {
        if (productID == null || productID.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than or equal to zero");
        }

        if (newQuantity == 0) {
            removeProduct(productID);
        } else {
            for (Product product : products) {
                if (product.getProductID().equals(productID)) {
                    product.setQuantity(newQuantity);
                    break;
                }
            }
        }
    }

    public Product getProduct(String productID) {
        for (Product product : products) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        throw new IllegalArgumentException("Product not found in the cart");
    }

    public boolean hasProduct(String productID) {
        for (Product product : products) {
            if (product.getProductID().equals(productID)) {
                return true;
            }
        }
        return false;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalCost() {
        return products.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
    }

    public void clear() {
        products.clear();
    }
}