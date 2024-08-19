package copilot.shopping_cart;

public class ShippingAddress {
    private String country;
    private String customerName;
    private String street;
    private String postalCode;
    private String city;

    public ShippingAddress(String country, String customerName, String street, String postalCode, String city) {
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        if (customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (postalCode == null || postalCode.isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be null or empty");
        }
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
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

