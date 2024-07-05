package shopping_cart;

public class ShoppingCartSystem {
    private ShoppingCart shoppingCart;
    private ShopCatalog shopCatalog;

    public ShoppingCartSystem(ShopCatalog shopCatalog) {
        this.shopCatalog = shopCatalog;
        this.shoppingCart = new ShoppingCart();
    }

    public void addProduct(String productID, int quantity) {
        if (productID == null || productID.isEmpty() || quantity <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        double price = shopCatalog.getProductPrice(productID);

        if(shoppingCart.containsProduct(productID)){
            int existingProductQuantity = shoppingCart.getProduct(productID).getQuantity();

            checkProductInStock(productID, quantity + existingProductQuantity);

            shoppingCart.changeQuantityOfProduct(productID, quantity + existingProductQuantity);
        } else{
            checkProductInStock(productID, quantity);

            Product product = new Product(productID, quantity, price);

            shoppingCart.addProduct(product);
        }
    }

    public void removeProduct(String productName) {
        shoppingCart.removeProduct(productName);
    }

    public void changeQuantityOfProduct(String productID, int quantity) {
        if (productID == null || productID.isEmpty() || quantity < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        checkProductInStock(productID, quantity);

        shoppingCart.changeQuantityOfProduct(productID, quantity);
    }

    public double getTotalCost() {
        return shoppingCart.getTotalCost();
    }

    public ShoppingCart viewCart() {
        return shoppingCart;
    }

    private void checkProductInStock(String productID, int quantity){
        if (!shopCatalog.isProductAvailable(productID, quantity)){
            throw new ProductNotInStock(productID, quantity);
        }
    }
}