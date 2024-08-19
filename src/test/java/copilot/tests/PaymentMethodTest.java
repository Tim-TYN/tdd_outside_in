package copilot.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import copilot.shopping_cart.*;


public class PaymentMethodTest {

    @Test
    public void testValidCreditCardPaymentMethod() {
        CreditCardPaymentMethod creditCard = new CreditCardPaymentMethod("1234567890123456", "12/24", "123", "John Doe");
        Assert.assertEquals("creditCard", creditCard.getMethodType());
        Assert.assertEquals("1234567890123456", creditCard.getCardNumber());
        Assert.assertEquals("12/24", creditCard.getExpiryDate());
        Assert.assertEquals("123", creditCard.getCvv());
        Assert.assertEquals("John Doe", creditCard.getCardHolderName());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCardNumberThrowsException() {
        new CreditCardPaymentMethod(null, "12/24", "123", "John Doe");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyCardNumberThrowsException() {
        new CreditCardPaymentMethod("", "12/24", "123", "John Doe");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullExpiryDateThrowsException() {
        new CreditCardPaymentMethod("1234567890123456", null, "123", "John Doe");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyExpiryDateThrowsException() {
        new CreditCardPaymentMethod("1234567890123456", "", "123", "John Doe");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCvvThrowsException() {
        new CreditCardPaymentMethod("1234567890123456", "12/24", null, "John Doe");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyCvvThrowsException() {
        new CreditCardPaymentMethod("1234567890123456", "12/24", "", "John Doe");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullCardHolderNameThrowsException() {
        new CreditCardPaymentMethod("1234567890123456", "12/24", "123", null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyCardHolderNameThrowsException() {
        new CreditCardPaymentMethod("1234567890123456", "12/24", "123", "");
    }

    @Test
    public void testValidPayPalPaymentMethod() {
        PayPalPaymentMethod paypal = new PayPalPaymentMethod("user@example.com", "authToken123");
        Assert.assertEquals("paypal", paypal.getMethodType());
        Assert.assertEquals("user@example.com", paypal.getPaypalEmail());
        Assert.assertEquals("authToken123", paypal.getPaypalAuthToken());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullEmailThrowsException() {
        new PayPalPaymentMethod(null, "authToken123");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyEmailThrowsException() {
        new PayPalPaymentMethod("", "authToken123");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullAuthTokenThrowsException() {
        new PayPalPaymentMethod("user@example.com", null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEmptyAuthTokenThrowsException() {
        new PayPalPaymentMethod("user@example.com", "");
    }
}
