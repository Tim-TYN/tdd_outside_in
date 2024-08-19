package copilot.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import copilot.shopping_cart.*;


public class ShoppingCartTest {

    @Test
    public void testAddProduct() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Product1", 2.99,2);
        cart.addProduct(product);
        Assert.assertEquals(cart.getProducts().size(), 1);
        Assert.assertEquals(cart.getProducts().get(0).getProductID(), "Product1");
    }

    @Test
    public void testRemoveProduct() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Product1", 2.99, 2);
        cart.addProduct(product);
        cart.removeProduct("Product1");
        Assert.assertEquals(cart.getProducts().size(), 0);
    }

    @Test
    public void testChangeQuantityOfProduct() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Product1", 2.99, 2);
        cart.addProduct(product);
        cart.changeQuantityOfProduct("Product1", 5);
        Assert.assertEquals(cart.getProduct("Product1").getQuantity(), 5);
    }

    @Test
    public void testChangeQuantityToZeroRemovesProduct() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Product1", 2.99, 2);
        cart.addProduct(product);
        cart.changeQuantityOfProduct("Product1", 0);
        Assert.assertEquals(cart.getProducts().size(), 0);
    }

    @Test
    public void testGetTotalCost() {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(new Product("Product1", 2.99, 2));
        cart.addProduct(new Product("Product2", 1.99, 1));
        Assert.assertEquals(cart.getTotalCost(), 2 * 2.99 + 1 * 1.99);
    }

    @Test
    public void testGetTotalCostWithNoProducts() {
        ShoppingCart cart = new ShoppingCart();
        Assert.assertEquals(cart.getTotalCost(), 0.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetProductNotInCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.getProduct("NonExistingProduct");
    }
}
