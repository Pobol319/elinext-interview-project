package by.devincubator.pobol.exception;

public class BindingNotFoundException extends RuntimeException {
    public BindingNotFoundException(Throwable cause) {
        super(cause);
    }

    public BindingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingNotFoundException(String message) {
        super(message);
    }

    public BindingNotFoundException() {
    }
}
