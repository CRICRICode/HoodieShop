package Cricri.Shop.services.exception;

public class NotEnoughtProductsException extends RuntimeException{
    public NotEnoughtProductsException(String message)
    {
        super(message);
    }
    public NotEnoughtProductsException()
    {
        this("Not enought products in store");
    }
}
