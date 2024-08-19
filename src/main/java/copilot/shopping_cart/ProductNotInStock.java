package copilot.shopping_cart;

public class ProductNotInStock extends RuntimeException {
    private String productID;
    private int quantity;

    public ProductNotInStock(String productID, int quantity) {
        super("Product not in stock: " + productID + ", quantity: " + quantity);
        this.productID = productID;
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }
}

