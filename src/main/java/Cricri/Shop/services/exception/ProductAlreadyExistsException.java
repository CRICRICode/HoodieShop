package Cricri.Shop.services.exception;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String message)
    {
        super(message);
    }

    public ProductAlreadyExistsException()
    {
        this("Product already exists");
    }
}
