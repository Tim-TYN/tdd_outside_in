package chatgpt.shopping_cart;

public class Product {
    private String productID;
    private double price;
    private int quantity;

    public Product(String productID, int quantity, double price) {
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID must not be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
    }
}









