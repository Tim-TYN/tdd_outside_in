package human.shopping_cart;

import static org.testng.util.Strings.isNullOrEmpty;

public class ShippingAddress {
    private String country;
    private String customerName;
    private String street;
    private String postalCode;
    private String city;

    public ShippingAddress(String country, String customerName, String street, String postalCode, String city) {

        if (isNullOrEmpty(country) || isNullOrEmpty(customerName) || isNullOrEmpty(street) || isNullOrEmpty(postalCode) || isNullOrEmpty(city)) {
            throw new IllegalArgumentException("Invalid address details");
        }

        this.country = country;
        this.customerName = customerName;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
