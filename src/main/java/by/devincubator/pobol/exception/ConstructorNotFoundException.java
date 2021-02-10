package by.devincubator.pobol.exception;

public class ConstructorNotFoundException extends RuntimeException {
    public ConstructorNotFoundException(Throwable cause) {
        super(cause);
    }

    public ConstructorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstructorNotFoundException(String message) {
        super(message);
    }

    public ConstructorNotFoundException() {
    }
}
