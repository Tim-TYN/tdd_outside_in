package human.shopping_cart;


public class ProductNotInStock extends RuntimeException {
    private String productID;
    private int quantity;

    public ProductNotInStock(String productID, int quantity) {
        super(String.format("Product %s is not in stock for the requested quantity: %d", productID, quantity));
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