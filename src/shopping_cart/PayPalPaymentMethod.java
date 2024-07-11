package shopping_cart;

public class PayPalPaymentMethod extends PaymentMethod {
    private String paypalEmail;
    private String paypalAuthToken;

    public PayPalPaymentMethod(String paypalEmail, String paypalAuthToken) {
        super("paypal");

        if (paypalEmail == null || paypalAuthToken == null) {
            throw new IllegalArgumentException("Invalid PayPal details");
        }

        this.paypalEmail = paypalEmail;
        this.paypalAuthToken = paypalAuthToken;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public String getPaypalAuthToken() {
        return paypalAuthToken;
    }
}
