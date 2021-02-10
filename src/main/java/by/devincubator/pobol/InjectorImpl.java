package by.devincubator.pobol;

import by.devincubator.pobol.annotation.Inject;
import by.devincubator.pobol.exception.BindingNotFoundException;
import by.devincubator.pobol.exception.ConstructorNotFoundException;
import by.devincubator.pobol.exception.TooManyConstructorsException;
import by.devincubator.pobol.scope.ScopeEnum;

import java.lang.reflect.Constructor;
import java.util.*;

public class InjectorImpl implements Injector {
    private Map<Class, Class> bindDefinition;
    private Map<Class, ScopeEnum> bindScopeMap;
    private Map<Class, Provider> iocContainer;

    public InjectorImpl() {
        bindDefinition = new HashMap<Class, Class>();
        bindScopeMap = new HashMap<Class, ScopeEnum>();
        iocContainer = new HashMap<Class, Provider>();
    }


    public synchronized <T> Provider<T> getProvider(Class<T> type) {
        Provider provider = null;
        Class impl = bindDefinition.get(type);

        if (!bindDefinition.containsKey(type)) {
            return provider;
        }

        if (iocContainer.containsKey(type) && bindScopeMap.get(type).equals(ScopeEnum.SINGLETON)) {
            return iocContainer.get(type);
        }

        Constructor constructor = getDefaultOrInjectAnnotatedConstructor(impl);
        Class[] parameterizedTypes = constructor.getParameterTypes();

        try {
            if (parameterizedTypes.length != 0) {
                for (Class parameterizedType : parameterizedTypes) {
                    if (!bindDefinition.containsKey(parameterizedType)) {
                        throw new BindingNotFoundException("Not found Binding for" + type);
                    }
                }

                List<Object> instancesOfParameterizedTypes = new ArrayList<Object>();

                for (Class parameterizedType : parameterizedTypes) {
                    instancesOfParameterizedTypes.add(getProvider(parameterizedType).getInstance());
                }

                provider = new ProviderImpl(constructor.newInstance(instancesOfParameterizedTypes.toArray()));
            } else {
                provider = new ProviderImpl(constructor.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bindScopeMap.get(type).equals(ScopeEnum.SINGLETON)) {
            iocContainer.put(type, provider);
        }

        return provider;
    }

    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        getDefaultOrInjectAnnotatedConstructor(impl);
        bindScopeMap.put(intf, ScopeEnum.PROTOTYPE);
        bindDefinition.put(intf, impl);
    }

    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        getDefaultOrInjectAnnotatedConstructor(impl);
        bindScopeMap.put(intf, ScopeEnum.SINGLETON);
        bindDefinition.put(intf, impl);
    }

    private <T> Constructor getDefaultOrInjectAnnotatedConstructor(Class<? extends T> impl) throws TooManyConstructorsException, ConstructorNotFoundException {
        Constructor constructor = getConstructorIfClassHaveOnlyOneConstructorWithInjectAnnotation(impl);
        if (constructor == null) {
            return getDefaultConstructorIfClassHaveDefaultConstructor(impl);
        }
        return constructor;
    }

    private <T> Constructor getConstructorIfClassHaveOnlyOneConstructorWithInjectAnnotation(Class<? extends T> impl) throws TooManyConstructorsException {
        int constructorsWithInjectAnnotation = 0;
        Constructor constructor = null;
        Constructor[] constructors = impl.getConstructors();

        for (Constructor constr : constructors) {
            if (constr.isAnnotationPresent(Inject.class)) {
                constructorsWithInjectAnnotation++;
                constructor = constr;
            }
        }

        if (constructorsWithInjectAnnotation > 1) {
            throw new TooManyConstructorsException("The class " + impl + " have more than one constructor with @Inject annotation");
        }

        return constructor;
    }

    private <T> Constructor getDefaultConstructorIfClassHaveDefaultConstructor(Class<? extends T> impl) throws ConstructorNotFoundException {
        Constructor[] constructors = impl.getConstructors();

        for (Constructor constructor : constructors) {
            if (constructor.getParameterTypes().length == 0) {
                return constructor;
            }
        }
        throw new ConstructorNotFoundException("The class " + impl + " does not have a default constructor");
    }
}
