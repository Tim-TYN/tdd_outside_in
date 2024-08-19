package human.shopping_cart;

import static org.testng.util.Strings.isNullOrEmpty;

public class PayPalPaymentMethod extends PaymentMethod {
    private String paypalEmail;
    private String paypalAuthToken;

    public PayPalPaymentMethod(String paypalEmail, String paypalAuthToken) {
        super("paypal");

        if (isNullOrEmpty(paypalEmail) || isNullOrEmpty(paypalAuthToken)) {
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
