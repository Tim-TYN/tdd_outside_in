package shopping_cart;

public class Product {
    private String productID;
    private int quantity;
    private double price;

    public Product(String productID, int quantity, double price) {
        if (quantity <= 0 || price < 0){
            throw new IllegalArgumentException("Invalid quantity or price");
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
        quantity = newQuantity;
    }
}
