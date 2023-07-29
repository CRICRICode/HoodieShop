package Cricri.Shop.services.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message)
    {
        super(message);
    }

    public ProductNotFoundException()
    {
        this("Product not found");
    }
}
