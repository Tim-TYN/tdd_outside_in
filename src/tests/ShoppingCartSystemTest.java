package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import shopping_cart.*;

import static org.mockito.Mockito.*;
import org.mockito.stubbing.Answer;

public class ShoppingCartSystemTest {
    private ShoppingCartSystem shoppingCartSystem;
    private ShopCatalog mockShopCatalog;
    private PaymentService mockPaymentService;

    @BeforeMethod
    public void setUp() {
        mockShopCatalog = mock(ShopCatalog.class);
        mockPaymentService = mock(PaymentService.class);
        shoppingCartSystem = new ShoppingCartSystem(mockShopCatalog, mockPaymentService);

        when(mockShopCatalog.getProductPrice("Product1")).thenReturn(2.99);
        when(mockShopCatalog.getProductPrice("Product2")).thenReturn(1.99);
        when(mockShopCatalog.getProductPrice("Product3")).thenReturn(5.99);

        when(mockShopCatalog.isProductAvailable(eq("Product1"), anyInt())).thenAnswer((Answer<Boolean>) invocation -> {
            int quantity = invocation.getArgument(1);
            return quantity < 15;
        });
        when(mockShopCatalog.isProductAvailable(eq("Product2"), anyInt())).thenAnswer((Answer<Boolean>) invocation -> {
            int quantity = invocation.getArgument(1);
            return quantity < 20;
        });
        when(mockShopCatalog.isProductAvailable(eq("Product3"), anyInt())).thenAnswer((Answer<Boolean>) invocation -> {
            int quantity = invocation.getArgument(1);
            return quantity < 30;
        });
    }
    // 1. Add Product
    @Test
    public void testAddProductToCart() {
        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 0, "The shopping cart should not contain any product.");
        shoppingCartSystem.addProduct("Product1", 2);
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");
    }

    @Test
    public void testPriceQuery() {
        shoppingCartSystem.addProduct("Product1", 2);

        verify(mockShopCatalog, times(1)).getProductPrice("Product1");
    }

    @Test
    public void testProductAttributes(){
        shoppingCartSystem.addProduct("Product1", 2);
        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Product addedProduct = shoppingCart.getProducts().get(0);
        Assert.assertEquals(addedProduct.getProductID(), "Product1", "The product name should be 'Product1'.");
        Assert.assertEquals(addedProduct.getQuantity(), 2, "The product quantity should be 2.");
        Assert.assertEquals(addedProduct.getPrice(), 2.99, "The product price should be 2.99.");
    }

    @Test
    public void testAddSameProductTwice() {
        shoppingCartSystem.addProduct("Product1", 2);
        shoppingCartSystem.addProduct("Product1", 3);

        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");

        Product addedProduct = shoppingCart.getProducts().get(0);
        Assert.assertEquals(addedProduct.getProductID(), "Product1", "The product name should be 'Product1'.");
        Assert.assertEquals(addedProduct.getQuantity(), 5, "The product quantity should be 5.");
        Assert.assertEquals(addedProduct.getPrice(), 2.99, "The product price should be 2.99.");  // Assuming price per unit remains the same
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNegativeQuantity() {
        shoppingCartSystem.addProduct("Product2", -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddZeroQuantity() {
        shoppingCartSystem.addProduct("Product2", 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddEmptyProductName() {
        shoppingCartSystem.addProduct("", 2);
    }
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullProductName() {
        shoppingCartSystem.addProduct(null, 2);
    }

    // Remove Product
    @Test
    public void testRemoveProductFromCart() {
        shoppingCartSystem.addProduct("Product1", 2);
        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");

        shoppingCartSystem.removeProduct("Product1");

        shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 0, "The shopping cart should be empty after removing the product.");
    }

    @Test
    public void testFailToRemoveProductFromCart() {
        shoppingCartSystem.addProduct("Product1", 2);
        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");

        shoppingCartSystem.removeProduct("Product3");

        shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");
    }

    @Test
    public void testRemoveProductWithEmptyName() {
        shoppingCartSystem.addProduct("Product1", 2);
        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");

        shoppingCartSystem.removeProduct("");

        shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should still contain one product when trying to remove with an empty name.");
    }

    @Test
    public void testRemoveProductWithNullName() {
        shoppingCartSystem.addProduct("Product1", 2);
        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");

        shoppingCartSystem.removeProduct(null);

        shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should still contain one product when trying to remove with a null name.");
    }

    // Change Quantity
    @Test
    public void testChangeQuantityOfProduct() {
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeQuantityToNegative() {
        shoppingCartSystem.changeQuantityOfProduct("Product1", -1);
    }

    @Test
    public void testChangeQuantityToZero() {
        shoppingCartSystem.addProduct("Product1", 2);
        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");

        shoppingCartSystem.changeQuantityOfProduct("Product1", 0);
        shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 0, "The shopping cart should be empty.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeQuantityWithEmptyName() {
        shoppingCartSystem.addProduct("Product1", 2);
        shoppingCartSystem.changeQuantityOfProduct("", 5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeQuantityWithNullName() {
        shoppingCartSystem.addProduct("Product1", 2);
        shoppingCartSystem.changeQuantityOfProduct(null, 5);
    }

    // Get Total Cost
    @Test
    public void testGetTotalCost() {
        shoppingCartSystem.addProduct("Product1", 2);
        double totalCost = shoppingCartSystem.getTotalCost();
        Assert.assertEquals(totalCost, 2.99 * 2 );
        shoppingCartSystem.addProduct("Product2", 4);
        totalCost = shoppingCartSystem.getTotalCost();
        Assert.assertEquals(totalCost, 2.99 * 2 + 1.99 * 4);
    }

    @Test
    public void testGetTotalCostWithNoProducts() {
        double totalCost = shoppingCartSystem.getTotalCost();
        Assert.assertEquals(totalCost, 0 );
    }

    // Select Payment Method
    @Test
    public void testSelectCreditCard(){
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
        shoppingCartSystem.selectPayPal("sam.smith@example.com", "example-auth-token");

        PaymentMethod paymentMethod =  shoppingCartSystem.getPaymentMethod();
        Assert.assertEquals(paymentMethod.getMethodType(), "paypal" );

        PayPalPaymentMethod payPalPaymentMethod = (PayPalPaymentMethod) paymentMethod;
        Assert.assertEquals(payPalPaymentMethod.getPaypalEmail(), "sam.smith@example.com" );
        Assert.assertEquals(payPalPaymentMethod.getPaypalAuthToken(), "example-auth-token" );
    }

    @Test
    public void testSwitchPaymentMethod(){
        shoppingCartSystem.selectPayPal("sam.smith@example.com", "example-auth-token");
        PaymentMethod paymentMethod =  shoppingCartSystem.getPaymentMethod();
        Assert.assertEquals(paymentMethod.getMethodType(), "paypal" );

        shoppingCartSystem.selectCreditCard("4111111111111111", "Sam Smith", "12/25", "123");
        paymentMethod =  shoppingCartSystem.getPaymentMethod();
        Assert.assertEquals(paymentMethod.getMethodType(), "creditCard" );
    }

    @Test
    public void testSelectShippingAddress(){
        shoppingCartSystem.selectShippingAddress("Germany", "Sam Smith", "Street 1", "55555", "City");
        ShippingAddress shippingAddress = shoppingCartSystem.getShippingAddress();

        Assert.assertEquals(shippingAddress.getCountry(), "Germany" );
        Assert.assertEquals(shippingAddress.getCustomerName(), "Sam Smith" );
        Assert.assertEquals(shippingAddress.getStreet(), "Street 1" );
        Assert.assertEquals(shippingAddress.getPostalCode(), "55555" );
        Assert.assertEquals(shippingAddress.getCity(), "City" );

    }

    @Test
    public void testChangeShippingAddress(){
        shoppingCartSystem.selectShippingAddress("Germany", "Sam Smith", "Street 1", "55555", "City");
        shoppingCartSystem.selectShippingAddress("Poland", "Tom Smith", "Street 11", "51111", "new City");

        ShippingAddress shippingAddress = shoppingCartSystem.getShippingAddress();

        Assert.assertEquals(shippingAddress.getCountry(), "Poland" );
        Assert.assertEquals(shippingAddress.getCustomerName(), "Tom Smith" );
        Assert.assertEquals(shippingAddress.getStreet(), "Street 11" );
        Assert.assertEquals(shippingAddress.getPostalCode(), "51111" );
        Assert.assertEquals(shippingAddress.getCity(), "new City" );
    }

    @Test
    public void testProcessPayment(){
        shoppingCartSystem.addProduct("Product1", 4);
        shoppingCartSystem.addProduct("Product2", 6);
        shoppingCartSystem.addProduct("Product3", 10);

        shoppingCartSystem.selectShippingAddress("Germany", "Sam Smith", "Street 1", "55555", "City");
        shoppingCartSystem.selectPayPal("sam.smith@example.com", "example-auth-token");

        when(mockPaymentService.processPayment(any(ShippingAddress.class), any(PaymentMethod.class), anyDouble())).thenReturn(true);

        boolean paymentSuccessful = shoppingCartSystem.processPayment();

        Assert.assertTrue(paymentSuccessful, "The payment should be successful.");

        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 0, "The shopping cart should be empty after a successful payment.");
        verify(mockPaymentService, times(1)).processPayment(any(ShippingAddress.class), any(PaymentMethod.class), anyDouble());
    }

    // Check Product Availability
    @Test
    public void testProductAvailability(){
        shoppingCartSystem.addProduct("Product1", 5);
        verify(mockShopCatalog, times(1)).isProductAvailable("Product1", 5);

        shoppingCartSystem.addProduct("Product1", 3);
        verify(mockShopCatalog, times(1)).isProductAvailable("Product1", 8);

        shoppingCartSystem.changeQuantityOfProduct("Product1", 7);
        verify(mockShopCatalog, times(1)).isProductAvailable("Product1", 7);
    }

    @Test
    public void testProductNotInStockException() {
        try {
            shoppingCartSystem.addProduct("Product1", 17);
            Assert.fail("Expected ProductNotInStock exception to be thrown");
        } catch (ProductNotInStock e) {
            verify(mockShopCatalog, times(1)).isProductAvailable("Product1", 17);
            Assert.assertEquals(e.getProductID(), "Product1");
            Assert.assertEquals(e.getQuantity(), 17);
        }
    }

    @Test
    public void testMergedProductNotInStockException() {

        shoppingCartSystem.addProduct("Product2", 15);
        verify(mockShopCatalog, times(1)).isProductAvailable("Product2", 15);
        try {
            shoppingCartSystem.addProduct("Product2", 12);
            Assert.fail("Expected ProductNotInStock exception to be thrown");
        } catch (ProductNotInStock e) {
            verify(mockShopCatalog, times(1)).isProductAvailable("Product2", 27);
            Assert.assertEquals(e.getProductID(), "Product2");
            Assert.assertEquals(e.getQuantity(), 27);
        }
    }

    @Test
    public void testChangedProductNotInStockException() {
        shoppingCartSystem.addProduct("Product3", 22);
        verify(mockShopCatalog, times(1)).isProductAvailable("Product3", 22);
        try {
            shoppingCartSystem.changeQuantityOfProduct("Product3", 50);
            Assert.fail("Expected ProductNotInStock exception to be thrown");
        } catch (ProductNotInStock e) {
            verify(mockShopCatalog, times(1)).isProductAvailable("Product3", 50);
            Assert.assertEquals(e.getProductID(), "Product3");
            Assert.assertEquals(e.getQuantity(), 50);
        }
    }
}
