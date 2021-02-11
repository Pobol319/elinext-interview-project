package by.devincubator.pobol.exception;

public class ProviderNewInstanceException extends RuntimeException {
    public ProviderNewInstanceException(Throwable cause) {
        super(cause);
    }

    public ProviderNewInstanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProviderNewInstanceException(String message) {
        super(message);
    }

    public ProviderNewInstanceException() {
    }
}
