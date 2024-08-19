package copilot.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import copilot.shopping_cart.Product;

public class ProductTest {

    @Test
    public void testProductCreation() {
        Product product = new Product("Product1", 2.99, 2);
        Assert.assertEquals(product.getProductID(), "Product1");
        Assert.assertEquals(product.getQuantity(), 2);
        Assert.assertEquals(product.getPrice(), 2.99);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidProductID() {
        new Product(null, 2.99, 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyProductID() {
        new Product("", 2.99, 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNegativePrice() {
        new Product("Product1", -1.99, 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testZeroQuantity() {
        new Product("Product1", 2.99, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNegativeQuantity() {
        new Product("Product1", 2.99, -1);
    }

    @Test
    public void testSetQuantity() {
        Product product = new Product("Product1", 2.99, 2);
        product.setQuantity(5);
        Assert.assertEquals(product.getQuantity(), 5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetInvalidQuantity() {
        Product product = new Product("Product1", 2.99, 2);
        product.setQuantity(0);
    }
}
