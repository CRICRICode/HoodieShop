package Cricri.Shop.services.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String message)
    {
        super(message);
    }
    public EmailNotFoundException()
    {
        this("Email not found or null");
    }
}
