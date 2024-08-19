package human.shopping_cart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String productID) {
        try {
            Product product = this.getProduct(productID);
            products.remove(product);
        }
        catch(IllegalArgumentException e) {
            // Do nothing
        }
    }

    public void changeQuantityOfProduct(String productID, int quantity){
        try {
            Product product = this.getProduct(productID);
            if(quantity > 0) {
                product.setQuantity(quantity);
            } else {
                products.remove(product);
            }
        }
        catch(IllegalArgumentException e) {
            // Do nothing
        }
    }

    public List<Product> getProducts(){
        return products;
    }

    public Product getProduct(String productID){
        for (Product product : products) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        throw new IllegalArgumentException("Product not in Cart!");
    }

    public boolean containsProduct(String productID){
        for (Product product : products) {
            if (product.getProductID().equals(productID)) {
                return true;
            }
        }
        return false;
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        for (Product product : products) {
            totalCost += product.getPrice() * product.getQuantity();
        }
        return totalCost;
    }
}