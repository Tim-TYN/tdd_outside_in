package copilot.tests;

import copilot.shopping_cart.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
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

    // ------------------ 1. Add Product ------------------
    @Test
    public void testPriceQuery() {
        shoppingCartSystem.addProduct("Product1", 2);

        verify(mockShopCatalog, times(1)).getProductPrice("Product1");
    }

    @Test
    public void testAddSameProductTwice() {
        shoppingCartSystem.addProduct("Product1", 2);
        shoppingCartSystem.addProduct("Product1", 3);

        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");

        Product addedProduct = shoppingCart.getProducts().iterator().next();
        Assert.assertEquals(addedProduct.getProductID(), "Product1", "The product name should be 'Product1'.");
        Assert.assertEquals(addedProduct.getQuantity(), 5, "The product quantity should be 5.");
        Assert.assertEquals(addedProduct.getPrice(), 2.99, "The product price should be 2.99.");
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
    public void testRemoveProductWithNullName() {
        shoppingCartSystem.addProduct("Product1", 2);
        ShoppingCart shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should contain one product.");

        shoppingCartSystem.removeProduct(null);

        shoppingCart = shoppingCartSystem.viewCart();
        Assert.assertEquals(shoppingCart.getProducts().size(), 1, "The shopping cart should still contain one product when trying to remove with a null name.");
    }

    @Test
    public void testProductAvailability(){
        shoppingCartSystem.addProduct("Product1", 5);
        verify(mockShopCatalog, times(1)).isProductAvailable("Product1", 5);

        shoppingCartSystem.addProduct("Product1", 3);
        verify(mockShopCatalog, times(1)).isProductAvailable("Product1", 8);
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

    @Test
    public void testChangedProductAvailability(){
        shoppingCartSystem.addProduct("Product1", 5);
        verify(mockShopCatalog, times(1)).isProductAvailable("Product1", 5);

        shoppingCartSystem.changeQuantityOfProduct("Product1", 3);
        verify(mockShopCatalog, times(1)).isProductAvailable("Product1", 3);
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

}