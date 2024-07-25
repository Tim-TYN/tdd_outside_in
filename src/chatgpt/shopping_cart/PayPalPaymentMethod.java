package chatgpt.shopping_cart;

public class PayPalPaymentMethod implements PaymentMethod {
    private String paypalEmail;
    private String paypalAuthToken;

    public PayPalPaymentMethod(String paypalEmail, String paypalAuthToken) {
        if (paypalEmail == null || paypalEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("PayPal email must not be null or empty");
        }
        if (paypalAuthToken == null || paypalAuthToken.trim().isEmpty()) {
            throw new IllegalArgumentException("PayPal auth token must not be null or empty");
        }
        this.paypalEmail = paypalEmail;
        this.paypalAuthToken = paypalAuthToken;
    }

    @Override
    public String getMethodType() {
        return "paypal";
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public String getPaypalAuthToken() {
        return paypalAuthToken;
    }
}



