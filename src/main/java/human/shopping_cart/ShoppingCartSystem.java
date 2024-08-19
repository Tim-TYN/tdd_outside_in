package human.shopping_cart;

import java.util.List;

public class ShoppingCartSystem {
    private ShoppingCart shoppingCart;
    private ShopCatalog shopCatalog;
    private PaymentService paymentService;
    private PaymentMethod paymentMethod;
    private ShippingAddress shippingAddress;

    public ShoppingCartSystem(ShopCatalog shopCatalog, PaymentService paymentService) {
        this.shopCatalog = shopCatalog;
        this.shoppingCart = new ShoppingCart();
        this.paymentService = paymentService;
        this.paymentMethod = null;
        this.shippingAddress = null;
    }

    public void addProduct(String productID, int quantity) {
        if (productID == null || productID.isEmpty() || quantity <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        double price = shopCatalog.getProductPrice(productID);

        if(shoppingCart.containsProduct(productID)){
            int existingProductQuantity = shoppingCart.getProduct(productID).getQuantity();

            checkProductInStock(productID, quantity + existingProductQuantity);

            shoppingCart.changeQuantityOfProduct(productID, quantity + existingProductQuantity);
        } else{
            checkProductInStock(productID, quantity);

            Product product = new Product(productID, quantity, price);

            shoppingCart.addProduct(product);
        }
    }

    public void removeProduct(String productName) {
        shoppingCart.removeProduct(productName);
    }

    public void changeQuantityOfProduct(String productID, int quantity) {
        if (productID == null || productID.isEmpty() || quantity < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        checkProductInStock(productID, quantity);

        shoppingCart.changeQuantityOfProduct(productID, quantity);
    }

    public void selectCreditCard(String cardNumber, String expiryDate, String cvv, String cardHolderName){
        paymentMethod = new CreditCardPaymentMethod(cardNumber, expiryDate, cvv, cardHolderName);
    }

    public void selectPayPal(String paypalEmail, String paypalAuthToken){
        paymentMethod = new PayPalPaymentMethod(paypalEmail, paypalAuthToken);
    }

    public void selectShippingAddress(String country, String customerName, String street, String postalCode, String city){
        shippingAddress = new ShippingAddress(country, customerName, street, postalCode, city);
    }

    public boolean processPayment(){
        if(paymentMethod == null){
            throw new IllegalArgumentException("Invalid paymentMethod");
        } else if (shippingAddress == null) {
            throw new IllegalArgumentException("Invalid shippingAddress");
        }
        List<Product> products = shoppingCart.getProducts();
        for(Product product : products){
            checkProductInStock(product.getProductID(), product.getQuantity());
        }

        if(paymentService.processPayment(shippingAddress, paymentMethod, getTotalCost())){
            shoppingCart = new ShoppingCart();
            return true;
        }else{
            return false;
        }
    }

    public ShippingAddress getShippingAddress(){
        return shippingAddress;
    }

    public PaymentMethod getPaymentMethod(){
        return paymentMethod;
    }

    public double getTotalCost() {
        return shoppingCart.getTotalCost();
    }

    public ShoppingCart viewCart() {
        return shoppingCart;
    }

    private void checkProductInStock(String productID, int quantity){
        if (!shopCatalog.isProductAvailable(productID, quantity)){
            throw new ProductNotInStock(productID, quantity);
        }
    }
}