package chatgpt.shopping_cart;

public class ShippingAddress {
    private String country;
    private String customerName;
    private String street;
    private String postalCode;
    private String city;

    public ShippingAddress(String country, String customerName, String street, String postalCode, String city) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country must not be null or empty");
        }
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name must not be null or empty");
        }
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street must not be null or empty");
        }
        if (postalCode == null || postalCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Postal code must not be null or empty");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City must not be null or empty");
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

