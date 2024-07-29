package chatgpt.shopping_cart;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartSystem {
    private ShopCatalog shopCatalog;
    private PaymentService paymentService;
    private ShoppingCart shoppingCart;
    private Map<String, Integer> productQuantities;
    private PaymentMethod paymentMethod;
    private ShippingAddress shippingAddress;

    public ShoppingCartSystem(ShopCatalog shopCatalog, PaymentService paymentService) {
        this.shopCatalog = shopCatalog;
        this.paymentService = paymentService;
        this.shoppingCart = new ShoppingCart();
        this.productQuantities = new HashMap<>();
    }

    public void addProduct(String productID, int quantity) throws ProductNotInStock {
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID must not be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        int currentQuantity = productQuantities.getOrDefault(productID, 0);
        int totalQuantity = currentQuantity + quantity;

        if (!shopCatalog.isProductAvailable(productID, totalQuantity)) {
            throw new ProductNotInStock(productID, totalQuantity);
        }

        productQuantities.put(productID, totalQuantity);

        double price = shopCatalog.getProductPrice(productID);
        if (price > 0) {
            Product product = new Product(productID, quantity, price);
            shoppingCart.addProduct(product);
        }
    }

    public void removeProduct(String productID) {
        shoppingCart.removeProduct(productID);
        productQuantities.remove(productID);
    }

    public void changeQuantityOfProduct(String productID, int newQuantity) throws ProductNotInStock {
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID must not be empty");
        }
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity must be positive or zero");
        }

        int currentQuantity = productQuantities.getOrDefault(productID, 0);

        if (newQuantity == 0) {
            removeProduct(productID);
            return;
        }

        if (!shopCatalog.isProductAvailable(productID, newQuantity)) {
            throw new ProductNotInStock(productID, newQuantity);
        }

        productQuantities.put(productID, newQuantity);
        shoppingCart.changeQuantityOfProduct(productID, newQuantity);
    }

    public void selectCreditCard(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.paymentMethod = new CreditCardPaymentMethod(cardNumber, cardHolderName, expiryDate, cvv);
        paymentService.selectCreditCard(cardNumber, cardHolderName, expiryDate, cvv);
    }

    public void selectPayPal(String paypalEmail, String paypalAuthToken) {
        this.paymentMethod = new PayPalPaymentMethod(paypalEmail, paypalAuthToken);
        paymentService.selectPayPal(paypalEmail, paypalAuthToken);
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void selectShippingAddress(String country, String customerName, String street, String postalCode, String city) {
        this.shippingAddress = new ShippingAddress(country, customerName, street, postalCode, city);
    }

    public ShippingAddress getShippingAddress() {
        return this.shippingAddress;
    }

    public ShoppingCart viewCart() {
        return shoppingCart;
    }

    public double getTotalCost() {
        return shoppingCart.getTotalCost();
    }

    public boolean processPayment() {
        double totalCost = getTotalCost();
        boolean paymentSuccessful = paymentService.processPayment(shippingAddress, paymentMethod, totalCost);

        if (paymentSuccessful) {
            shoppingCart = new ShoppingCart(); // Clear the shopping cart
            productQuantities.clear();
        }

        return paymentSuccessful;
    }
}


































