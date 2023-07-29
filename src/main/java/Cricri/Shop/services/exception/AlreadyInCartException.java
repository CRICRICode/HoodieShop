package Cricri.Shop.services.exception;

public class AlreadyInCartException extends RuntimeException{
    public AlreadyInCartException(String message)
    {
        super(message);
    }
    public AlreadyInCartException()
    {
        this("Product already in carrello");
    }
}
