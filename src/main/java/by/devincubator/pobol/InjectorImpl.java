package by.devincubator.pobol;

import by.devincubator.pobol.annotation.Inject;
import by.devincubator.pobol.exception.BindingNotFoundException;
import by.devincubator.pobol.exception.ConstructorNotFoundException;
import by.devincubator.pobol.exception.TooManyConstructorsException;
import by.devincubator.pobol.scope.BeanScopeEnum;

import java.lang.reflect.Constructor;
import java.util.*;

public class InjectorImpl implements Injector {
    private final Map<Class, Class> beanBindings;
    private final Map<Class, BeanScopeEnum> beanScopes;
    private final Map<Class, Provider> iocContainer;

    public InjectorImpl() {
        beanBindings = new HashMap<>();
        beanScopes = new HashMap<>();
        iocContainer = new HashMap<>();
    }

    public synchronized <T> Provider<T> getProvider(Class<T> type) {

        if (!beanBindings.containsKey(type)) {
            return null;
        }

        if (iocContainer.containsKey(type)) {
            return iocContainer.get(type);
        }

        Constructor constructor = getDefaultOrInjectAnnotatedConstructor(beanBindings.get(type));
        Class[] parameterizedTypes = constructor.getParameterTypes();

        Provider<T> provider;

        if (parameterizedTypes.length != 0) {
            for (Class parameterizedType : parameterizedTypes) {
                if (!beanBindings.containsKey(parameterizedType)) {
                    throw new BindingNotFoundException("Not found Binding for" + type);
                }
            }

            List<Object> instancesOfParameterizedTypes = new ArrayList<Object>();

            for (Class parameterizedType : parameterizedTypes) {
                instancesOfParameterizedTypes.add(getProvider(parameterizedType).getInstance());
            }

            provider = new ProviderImpl(constructor, beanScopes.get(type), instancesOfParameterizedTypes.toArray());
        } else {
            provider = new ProviderImpl(constructor, beanScopes.get(type), null);
        }

        iocContainer.put(type, provider);

        return provider;
    }

    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        getDefaultOrInjectAnnotatedConstructor(impl);
        beanScopes.put(intf, BeanScopeEnum.PROTOTYPE);
        beanBindings.put(intf, impl);
    }

    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        getDefaultOrInjectAnnotatedConstructor(impl);
        beanScopes.put(intf, BeanScopeEnum.SINGLETON);
        beanBindings.put(intf, impl);
    }

    private <T> Constructor getDefaultOrInjectAnnotatedConstructor(Class<? extends T> impl) throws TooManyConstructorsException, ConstructorNotFoundException {
        final Constructor[] existingConstructors = impl.getConstructors();

        final Optional<Constructor> oneInjectConstructorOpt = Optional.ofNullable(getOneInjectConstructor(existingConstructors));
        return oneInjectConstructorOpt.orElseGet(() -> getDefaultConstructor(existingConstructors));
    }

    private Constructor getOneInjectConstructor(Constructor[] existingConstructors) throws TooManyConstructorsException {
        Constructor oneInjectConstructor = null;

        int constructorsWithInjectAnnotation = 0;
        for (Constructor currentConstructor : existingConstructors) {
            if (currentConstructor.isAnnotationPresent(Inject.class)) {
                constructorsWithInjectAnnotation++;
                if (constructorsWithInjectAnnotation > 1) {
                    throw new TooManyConstructorsException("The class " + currentConstructor.getClass() + " have more than one constructor with @Inject annotation");
                }
                oneInjectConstructor = currentConstructor;
            }
        }
        return oneInjectConstructor;
    }

    private Constructor getDefaultConstructor(Constructor[] existingConstructors) throws ConstructorNotFoundException {
        Optional<Constructor> defaultConstructor = Arrays.stream(existingConstructors).filter(constr -> constr.getParameterTypes().length == 0).findFirst();
        if (defaultConstructor.isPresent()) {
            return defaultConstructor.get();
        }
        throw new ConstructorNotFoundException("The class" + existingConstructors[0].getDeclaringClass() + " does not have a default constructor");
    }
}