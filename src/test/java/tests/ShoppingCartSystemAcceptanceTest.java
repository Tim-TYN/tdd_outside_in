package tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import shopping_cart.*;


public class ShoppingCartSystemAcceptanceTest {
    private ShoppingCartSystem shoppingCartSystem;
    private ShopCatalog shopCatalog;
    private PaymentService paymentService;

    @Test
    public void testAddProductsToShoppingCart() {
        shopCatalog = new ShopCatalog();
        paymentService = new PaymentService();
        shoppingCartSystem = new ShoppingCartSystem(shopCatalog, paymentService);

        shopCatalog.addProduct("Product1", 2.99, 200);
        shopCatalog.addProduct("Product2", 1.99, 300);
        shopCatalog.addProduct("Product3", 5.99, 150);

        shoppingCartSystem.addProduct("Product1", 2);
        shoppingCartSystem.addProduct("Product2", 3);
        shoppingCartSystem.addProduct("Product2", 3);
        shoppingCartSystem.addProduct("Product3", 1);

        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Product addedProduct = shoppingCart.getProduct("Product1");
        Assert.assertEquals(addedProduct.getProductID(), "Product1", "The product name should be 'Product1'.");
        Assert.assertEquals(addedProduct.getQuantity(), 2, "The product quantity should be 2.");
        Assert.assertEquals(addedProduct.getPrice(), 2.99, "The product price should be 2.99.");

        Assert.assertEquals(shoppingCart.getProducts().size(), 3, "The shopping cart should contain three products.");
        Assert.assertEquals(shoppingCart.getTotalCost(), 2.99 * 2 + 1.99 * 6 + 5.99);

    }

    @Test
    public void testRemoveProductFromShoppingCart() {
        shopCatalog = new ShopCatalog();
        paymentService = new PaymentService();
        shoppingCartSystem = new ShoppingCartSystem(shopCatalog, paymentService);

        shopCatalog.addProduct("Product1", 2.99, 200);
        shopCatalog.addProduct("Product2", 1.99, 300);

        shoppingCartSystem.addProduct("Product1", 2);
        shoppingCartSystem.addProduct("Product2", 3);

        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 2, "The shopping cart should contain two products.");

        shoppingCartSystem.removeProduct("Product1");
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");
        Assert.assertEquals(shoppingCart.getProduct("Product2").getProductID(), "Product2", "The product name should be 'Product1'.");
    }

    @Test
    public void testChangeQuantityOfProduct() {
        shopCatalog = new ShopCatalog();
        paymentService = new PaymentService();
        shoppingCartSystem = new ShoppingCartSystem(shopCatalog, paymentService);

        shopCatalog.addProduct("Product1", 2.99, 200);
        shoppingCartSystem.addProduct("Product1", 2);

        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Product product = shoppingCart.getProducts().get(0);
        Assert.assertEquals(product.getProductID(), "Product1", "The product name should be 'Product1'.");
        Assert.assertEquals(product.getQuantity(), 2, "The initial product quantity should be 2.");

        shoppingCartSystem.changeQuantityOfProduct("Product1", 5);
        shoppingCart = shoppingCartSystem.viewCart();
        product = shoppingCart.getProducts().get(0);
        Assert.assertEquals(product.getProductID(), "Product1", "The product name should be 'Product1'.");
        Assert.assertEquals(product.getQuantity(), 5, "The product quantity should be updated to 5.");
    }

    @Test
    public void testSelectCreditCard(){
        shopCatalog = new ShopCatalog();
        paymentService = new PaymentService();
        shoppingCartSystem = new ShoppingCartSystem(shopCatalog, paymentService);

        shoppingCartSystem.selectCreditCard("4111111111111111", "12/25", "123", "Sam Smith");

        PaymentMethod paymentMethod =  shoppingCartSystem.getPaymentMethod();
        Assert.assertEquals(paymentMethod.getMethodType(), "creditCard" );

        CreditCardPaymentMethod creditCardPaymentMethod = (CreditCardPaymentMethod) paymentMethod;
        Assert.assertEquals(creditCardPaymentMethod.getCardNumber(), "4111111111111111" );
        Assert.assertEquals(creditCardPaymentMethod.getCardHolderName(), "Sam Smith" );
        Assert.assertEquals(creditCardPaymentMethod.getExpiryDate(), "12/25" );
        Assert.assertEquals(creditCardPaymentMethod.getCvv(), "123" );
    }

    @Test
    public void testSelectPayPal(){
        shopCatalog = new ShopCatalog();
        paymentService = new PaymentService();
        shoppingCartSystem = new ShoppingCartSystem(shopCatalog, paymentService);

        shoppingCartSystem.selectPayPal("sam.smith@example.com", "example-auth-token");

        PaymentMethod paymentMethod =  shoppingCartSystem.getPaymentMethod();
        Assert.assertEquals(paymentMethod.getMethodType(), "paypal" );

        PayPalPaymentMethod payPalPaymentMethod = (PayPalPaymentMethod) paymentMethod;
        Assert.assertEquals(payPalPaymentMethod.getPaypalEmail(), "sam.smith@example.com" );
        Assert.assertEquals(payPalPaymentMethod.getPaypalAuthToken(), "example-auth-token" );
    }

    @Test
    public void testSelectShippingAddress(){
        shopCatalog = new ShopCatalog();
        paymentService = new PaymentService();
        shoppingCartSystem = new ShoppingCartSystem(shopCatalog, paymentService);

        shoppingCartSystem.selectShippingAddress("Germany", "Sam Smith", "Street 1", "55555", "City");
        ShippingAddress shippingAddress = shoppingCartSystem.getShippingAddress();

        Assert.assertEquals(shippingAddress.getCountry(), "Germany" );
        Assert.assertEquals(shippingAddress.getCustomerName(), "Sam Smith" );
        Assert.assertEquals(shippingAddress.getStreet(), "Street 1" );
        Assert.assertEquals(shippingAddress.getPostalCode(), "55555" );
        Assert.assertEquals(shippingAddress.getCity(), "City" );
    }

    @Test
    public void testProcessPayment(){
        shopCatalog = new ShopCatalog();
        paymentService = new PaymentService();
        shoppingCartSystem = new ShoppingCartSystem(shopCatalog, paymentService);

        shopCatalog.addProduct("Product1", 2.99, 200);
        shopCatalog.addProduct("Product2", 1.99, 300);
        shopCatalog.addProduct("Product3", 5.99, 150);

        shoppingCartSystem.addProduct("Product1", 4);
        shoppingCartSystem.addProduct("Product2", 6);
        shoppingCartSystem.addProduct("Product3", 10);

        shoppingCartSystem.selectShippingAddress("Germany", "Sam Smith", "Street 1", "55555", "City");
        shoppingCartSystem.selectPayPal("sam.smith@example.com", "example-auth-token");

        boolean paymentSuccessful = shoppingCartSystem.processPayment();

        Assert.assertTrue(paymentSuccessful, "The payment should be successful.");

        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 0, "The shopping cart should be empty after a successful payment.");
    }
}