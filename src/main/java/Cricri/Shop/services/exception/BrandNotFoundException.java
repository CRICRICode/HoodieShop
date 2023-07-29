package Cricri.Shop.services.exception;

public class BrandNotFoundException extends RuntimeException{
    public BrandNotFoundException(String message)
    {
        super(message);
    }

    public BrandNotFoundException()
    {
        this("Brand not found");
    }
}
