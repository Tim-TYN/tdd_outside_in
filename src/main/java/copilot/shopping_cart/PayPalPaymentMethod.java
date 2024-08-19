package copilot.shopping_cart;

public class PayPalPaymentMethod implements PaymentMethod {
    private String paypalEmail;
    private String paypalAuthToken;

    public PayPalPaymentMethod(String paypalEmail, String paypalAuthToken) {
        if (paypalEmail == null || paypalEmail.isEmpty()) {
            throw new IllegalArgumentException("PayPal email cannot be null or empty");
        }
        if (paypalAuthToken == null || paypalAuthToken.isEmpty()) {
            throw new IllegalArgumentException("PayPal auth token cannot be null or empty");
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