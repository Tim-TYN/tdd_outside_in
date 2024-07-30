package shopping_cart;

import static org.testng.util.Strings.isNullOrEmpty;

public class CreditCardPaymentMethod extends PaymentMethod {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolderName;

    public CreditCardPaymentMethod(String cardNumber, String expiryDate, String cvv, String cardHolderName) {
        super("creditCard");

        if (isNullOrEmpty(cardNumber) || isNullOrEmpty(expiryDate) || isNullOrEmpty(cvv) || isNullOrEmpty(cardHolderName)) {
            throw new IllegalArgumentException("Invalid credit card details");
        }
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardHolderName = cardHolderName;
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
