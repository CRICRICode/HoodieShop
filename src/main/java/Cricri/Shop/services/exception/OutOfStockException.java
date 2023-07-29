package Cricri.Shop.services.exception;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message)
    {
        super(message);
    }
    public OutOfStockException()
    {
        this("Product esaurito");
    }
}
