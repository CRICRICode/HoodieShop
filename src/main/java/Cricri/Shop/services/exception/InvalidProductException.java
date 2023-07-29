package Cricri.Shop.services.exception;

public class InvalidProductException extends RuntimeException{
    public InvalidProductException(String message)
    {
        super(message);
    }
    public InvalidProductException()
    {
        this("Invalid Product");
    }
}
