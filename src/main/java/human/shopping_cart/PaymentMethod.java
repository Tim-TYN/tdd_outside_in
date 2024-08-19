package human.shopping_cart;

public abstract class PaymentMethod {
    private String methodType;

    public PaymentMethod(String methodType) {
        this.methodType = methodType;
    }

    public String getMethodType() {
        return methodType;
    }
}
