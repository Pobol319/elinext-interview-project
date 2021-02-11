package by.devincubator.pobol;

import by.devincubator.pobol.exception.ProviderNewInstanceException;
import by.devincubator.pobol.scope.BeanScopeEnum;

import java.lang.reflect.Constructor;

public class ProviderImpl<T> implements Provider<T> {
    private Constructor<T> constructor;
    private BeanScopeEnum scope;
    private Object[] newInstanceParameters;
    private T instance = null;

    public ProviderImpl( Constructor<T> constructor, BeanScopeEnum scope, Object[] newInstanceParameters) {
        this.constructor = constructor;
        this.scope = scope;
        this.newInstanceParameters = newInstanceParameters;
    }

    public T getInstance() {
        if (instance == null) {
            instance = createNewInstance();
            return instance;
        } else if (scope == BeanScopeEnum.SINGLETON) {
            return instance;
        } else {
            return createNewInstance();
        }
    }

    private T createNewInstance() throws ProviderNewInstanceException{
        T newInstance;
        try {
            newInstance = constructor.newInstance(newInstanceParameters);
        } catch (Exception e) {
            throw new ProviderNewInstanceException("Exception in createNewInstance() method");
        }
        return newInstance;
    }
}
