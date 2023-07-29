package Cricri.Shop.services.exception;

public class PriceChangedException extends RuntimeException{
    public PriceChangedException(String message)
    {
        super(message);
    }
    public PriceChangedException()
    {
        this("Prezzo cambiato exception");
    }
}
