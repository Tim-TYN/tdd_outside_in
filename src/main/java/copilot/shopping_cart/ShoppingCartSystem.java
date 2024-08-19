package copilot.shopping_cart;

public class ShoppingCartSystem {
    private ShopCatalog shopCatalog;
    private PaymentService paymentService;
    private ShoppingCart shoppingCart;
    private PaymentMethod paymentMethod;
    private ShippingAddress shippingAddress;

    public ShoppingCartSystem(ShopCatalog shopCatalog, PaymentService paymentService) {
        this.shopCatalog = shopCatalog;
        this.paymentService = paymentService;
        this.shoppingCart = new ShoppingCart();
    }

    public void addProduct(String productID, int quantity) {
        if (productID == null || productID.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        Double price = shopCatalog.getProductPrice(productID);
        if (price == null) {
            throw new IllegalArgumentException("Product not found in the catalog");
        }
        int totalQuantity = quantity;
        if (shoppingCart.hasProduct(productID)) {
            Product existingProduct = shoppingCart.getProduct(productID);
            totalQuantity += existingProduct.getQuantity();
        }
        if (!shopCatalog.isProductAvailable(productID, totalQuantity)) {
            throw new ProductNotInStock(productID, totalQuantity);
        }
        if (shoppingCart.hasProduct(productID)) {
            shoppingCart.changeQuantityOfProduct(productID, totalQuantity);
        } else {
            Product productToAdd = new Product(productID, price, quantity);
            shoppingCart.addProduct(productToAdd);
        }
    }

    public void removeProduct(String productID) {
        shoppingCart.removeProduct(productID);
    }

    public void changeQuantityOfProduct(String productID, int newQuantity) {
        if (productID == null || productID.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than or equal to zero");
        }

        if (newQuantity == 0) {
            shoppingCart.removeProduct(productID);
        } else if (shopCatalog.isProductAvailable(productID, newQuantity)) {
            shoppingCart.changeQuantityOfProduct(productID, newQuantity);
        } else {
            throw new ProductNotInStock(productID, newQuantity);
        }
    }

    public void selectCreditCard(String cardNumber, String expiryDate, String cvv, String cardHolderName) {
        this.paymentMethod = new CreditCardPaymentMethod(cardNumber, expiryDate, cvv, cardHolderName);
    }

    public void selectPayPal(String paypalEmail, String paypalAuthToken) {
        this.paymentMethod = new PayPalPaymentMethod(paypalEmail, paypalAuthToken);
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void selectShippingAddress(String country, String customerName, String street, String postalCode, String city) {
        this.shippingAddress = new ShippingAddress(country, customerName, street, postalCode, city);
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public ShoppingCart viewCart() {
        return shoppingCart;
    }

    public double getTotalCost() {
        return shoppingCart.getTotalCost();
    }

    public boolean processPayment() {
        if (paymentMethod == null || shippingAddress == null) {
            throw new IllegalStateException("Payment method and shipping address must be selected before processing payment.");
        }

        boolean paymentSuccessful = paymentService.processPayment(shippingAddress, paymentMethod, shoppingCart.getTotalCost());

        if (paymentSuccessful) {
            shoppingCart.clear();
        }

        return paymentSuccessful;
    }
}

