package chatgpt.shopping_cart;

public class ProductNotInStock extends Exception {
    private String productID;
    private int quantity;

    public ProductNotInStock(String productID, int quantity) {
        super("Product " + productID + " is not in stock for quantity " + quantity);
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





