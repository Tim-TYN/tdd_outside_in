package tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import shopping_cart.ShippingAddress;

public class ShippingAddressTest {

    @Test
    public void testValidShippingAddress() {
        ShippingAddress address = new ShippingAddress("Germany", "John Doe", "Main Street 1", "12345", "Berlin");
        Assert.assertEquals("Germany", address.getCountry());
        Assert.assertEquals("John Doe", address.getCustomerName());
        Assert.assertEquals("Main Street 1", address.getStreet());
        Assert.assertEquals("12345", address.getPostalCode());
        Assert.assertEquals("Berlin", address.getCity());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCountryThrowsException() {
        new ShippingAddress(null, "John Doe", "Main Street 1", "12345", "Berlin");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCustomerNameThrowsException() {
        new ShippingAddress("Germany", null, "Main Street 1", "12345", "Berlin");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullStreetThrowsException() {
        new ShippingAddress("Germany", "John Doe", null, "12345", "Berlin");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullPostalCodeThrowsException() {
        new ShippingAddress("Germany", "John Doe", "Main Street 1", null, "Berlin");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCityThrowsException() {
        new ShippingAddress("Germany", "John Doe", "Main Street 1", "12345", null);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyCountryThrowsException() {
        new ShippingAddress("", "John Doe", "Main Street 1", "12345", "Berlin");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyCustomerNameThrowsException() {
        new ShippingAddress("Germany", "", "Main Street 1", "12345", "Berlin");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyStreetThrowsException() {
        new ShippingAddress("Germany", "John Doe", "", "12345", "Berlin");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyPostalCodeThrowsException() {
        new ShippingAddress("Germany", "John Doe", "Main Street 1", "", "Berlin");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyCityThrowsException() {
        new ShippingAddress("Germany", "John Doe", "Main Street 1", "12345", "");
    }
}
