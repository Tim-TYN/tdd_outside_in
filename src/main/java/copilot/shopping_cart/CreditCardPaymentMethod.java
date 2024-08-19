package copilot.shopping_cart;

public class CreditCardPaymentMethod implements PaymentMethod {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolderName;

    public CreditCardPaymentMethod(String cardNumber, String expiryDate, String cvv, String cardHolderName) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }
        if (expiryDate == null || expiryDate.isEmpty()) {
            throw new IllegalArgumentException("Expiry date cannot be null or empty");
        }
        if (cvv == null || cvv.isEmpty()) {
            throw new IllegalArgumentException("CVV cannot be null or empty");
        }
        if (cardHolderName == null || cardHolderName.isEmpty()) {
            throw new IllegalArgumentException("Card holder name cannot be null or empty");
        }
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public String getMethodType() {
        return "creditCard";
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }
}
