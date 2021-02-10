package by.devincubator.pobol;

public class ProviderImpl<T> implements Provider {
    private T type;

    public ProviderImpl(T type) {
        this.type = type;
    }

    public T getInstance() {
        return type;
    }
}
