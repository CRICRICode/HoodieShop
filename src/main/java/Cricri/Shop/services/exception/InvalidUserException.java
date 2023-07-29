package Cricri.Shop.services.exception;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message)
    {
        super(message);
    }
    public InvalidUserException()
    {
        this("Invalid Utente");
    }
}
