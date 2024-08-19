package human.shopping_cart;

public class Product {
    private String productID;
    private int quantity;
    private double price;

    public Product(String productID, int quantity, double price) {
        if (productID == null || productID.isEmpty()){
            throw new IllegalArgumentException("Invalid product ID");
        }
        if (quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int newQuantity) {
        if (newQuantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        quantity = newQuantity;
    }
}
