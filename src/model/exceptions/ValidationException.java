package model.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String msg) {
        super(msg);
        System.exit(1);
    }
}
