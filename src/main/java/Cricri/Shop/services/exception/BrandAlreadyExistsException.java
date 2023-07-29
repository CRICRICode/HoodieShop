package Cricri.Shop.services.exception;

public class BrandAlreadyExistsException extends RuntimeException{
    public BrandAlreadyExistsException(String message)
    {
        super(message);
    }
    public BrandAlreadyExistsException()
    {
        this("Brand already exists");
    }
}
