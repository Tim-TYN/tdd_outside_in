package chatgpt.shopping_cart;
public class PaymentService {
    private PaymentMethod paymentMethod;

    public void selectCreditCard(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.paymentMethod = new CreditCardPaymentMethod(cardNumber, cardHolderName, expiryDate, cvv);
    }

    public void selectPayPal(String paypalEmail, String paypalAuthToken) {
        this.paymentMethod = new PayPalPaymentMethod(paypalEmail, paypalAuthToken);
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public boolean processPayment(ShippingAddress address, PaymentMethod method, double amount) {
        // Simulate payment processing logic
        if (method != null && address != null) {
            // In a real application, this would involve contacting the payment gateway
            return true;
        }
        return false;
    }
}





