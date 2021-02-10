package by.devincubator.pobol.exception;

public class TooManyConstructorsException extends RuntimeException {
    public TooManyConstructorsException(Throwable cause) {
        super(cause);
    }

    public TooManyConstructorsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyConstructorsException(String message) {
        super(message);
    }

    public TooManyConstructorsException() {
    }
}
