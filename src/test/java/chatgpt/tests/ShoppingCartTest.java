package chatgpt.tests;

import chatgpt.shopping_cart.Product;
import chatgpt.shopping_cart.ShoppingCart;
import org.testng.annotations.Test;
import org.testng.Assert;

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
        human.shopping_cart.ShoppingCart cart = new human.shopping_cart.ShoppingCart();
        human.shopping_cart.Product product = new human.shopping_cart.Product("Product1", 2, 2.99);
        cart.addProduct(product);
        cart.removeProduct("Product1");
        Assert.assertEquals(cart.getProducts().size(), 0);
    }

    @Test
    public void testChangeQuantityOfProduct() {
        human.shopping_cart.ShoppingCart cart = new human.shopping_cart.ShoppingCart();
        human.shopping_cart.Product product = new human.shopping_cart.Product("Product1", 2, 2.99);
        cart.addProduct(product);
        cart.changeQuantityOfProduct("Product1", 5);
        Assert.assertEquals(cart.getProduct("Product1").getQuantity(), 5);
    }

    @Test
    public void testChangeQuantityToZeroRemovesProduct() {
        human.shopping_cart.ShoppingCart cart = new human.shopping_cart.ShoppingCart();
        human.shopping_cart.Product product = new human.shopping_cart.Product("Product1", 2, 2.99);
        cart.addProduct(product);
        cart.changeQuantityOfProduct("Product1", 0);
        Assert.assertEquals(cart.getProducts().size(), 0);
    }

    @Test
    public void testGetTotalCost() {
        human.shopping_cart.ShoppingCart cart = new human.shopping_cart.ShoppingCart();
        cart.addProduct(new human.shopping_cart.Product("Product1", 2, 2.99));
        cart.addProduct(new human.shopping_cart.Product("Product2", 1, 1.99));
        Assert.assertEquals(cart.getTotalCost(), 2 * 2.99 + 1 * 1.99);
    }

    @Test
    public void testGetTotalCostWithNoProducts() {
        human.shopping_cart.ShoppingCart cart = new human.shopping_cart.ShoppingCart();
        Assert.assertEquals(cart.getTotalCost(), 0.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetProductNotInCart() {
        human.shopping_cart.ShoppingCart cart = new human.shopping_cart.ShoppingCart();
        cart.getProduct("NonExistingProduct");
    }

    @Test
    public void testContainsProduct() {
        human.shopping_cart.ShoppingCart cart = new human.shopping_cart.ShoppingCart();
        human.shopping_cart.Product product = new human.shopping_cart.Product("Product1", 2, 2.99);
        cart.addProduct(product);
        Assert.assertTrue(cart.containsProduct("Product1"));
    }

    @Test
    public void testDoesNotContainProduct() {
        human.shopping_cart.ShoppingCart cart = new human.shopping_cart.ShoppingCart();
        Assert.assertFalse(cart.containsProduct("NonExistingProduct"));
    }
}
