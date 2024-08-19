package human.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import human.shopping_cart.Product;
import human.shopping_cart.ShoppingCart;

public class ShoppingCartTest {

    @Test
    public void testAddProduct() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Product1", 2, 2.99);
        cart.addProduct(product);
        Assert.assertEquals(cart.getProducts().size(), 1);
        Assert.assertEquals(cart.getProducts().get(0).getProductID(), "Product1");
    }

    @Test
    public void testRemoveProduct() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Product1", 2, 2.99);
        cart.addProduct(product);
        cart.removeProduct("Product1");
        Assert.assertEquals(cart.getProducts().size(), 0);
    }

    @Test
    public void testChangeQuantityOfProduct() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Product1", 2, 2.99);
        cart.addProduct(product);
        cart.changeQuantityOfProduct("Product1", 5);
        Assert.assertEquals(cart.getProduct("Product1").getQuantity(), 5);
    }

    @Test
    public void testChangeQuantityToZeroRemovesProduct() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Product1", 2, 2.99);
        cart.addProduct(product);
        cart.changeQuantityOfProduct("Product1", 0);
        Assert.assertEquals(cart.getProducts().size(), 0);
    }

    @Test
    public void testGetTotalCost() {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(new Product("Product1", 2, 2.99));
        cart.addProduct(new Product("Product2", 1, 1.99));
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

    @Test
    public void testContainsProduct() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Product1", 2, 2.99);
        cart.addProduct(product);
        Assert.assertTrue(cart.containsProduct("Product1"));
    }

    @Test
    public void testDoesNotContainProduct() {
        ShoppingCart cart = new ShoppingCart();
        Assert.assertFalse(cart.containsProduct("NonExistingProduct"));
    }
}
