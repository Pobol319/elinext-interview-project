package by.devincubator.pobol.container.impl;

import by.devincubator.pobol.container.Injector;
import by.devincubator.pobol.container.Provider;
import by.devincubator.pobol.data.*;
import by.devincubator.pobol.data.impl.*;
import by.devincubator.pobol.exception.BindingNotFoundException;
import by.devincubator.pobol.exception.ConstructorNotFoundException;
import by.devincubator.pobol.exception.TooManyConstructorsException;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;


public class InjectorImplTest {

    @Test(expected = ConstructorNotFoundException.class)
    public void testGetDefaultConstructorExpectException() {
        Injector injector = new InjectorImpl();
        injector.bind(InterfaceA.class, InterfaceAImplWithOneParameterizedConstructor.class);
    }

    @Test(expected = TooManyConstructorsException.class)
    public void testGetOneInjectConstructorExpectException() {
        Injector injector = new InjectorImpl();
        injector.bind(InterfaceA.class, InterfaceAImplWithManyInjectAnnotations.class);
    }

    @Test
    public void testGetProviderExpectNull() {
        Injector injector = new InjectorImpl();
        Provider<InterfaceA> nullProvider = injector.getProvider(InterfaceA.class);

        assertNull(nullProvider);
    }

    @Test
    public void testExistingBinding() {
        Injector injector = new InjectorImpl();
        injector.bind(InterfaceA.class, InterfaceAImplWithDefaultConstructor.class);
        Provider<InterfaceA> provider = injector.getProvider(InterfaceA.class);

        assertNotNull(provider);
        assertNotNull(provider.getInstance());
        assertSame(InterfaceAImplWithDefaultConstructor.class, provider.getInstance().getClass());
    }

    @Test
    public void testGetProviderExpectSameProvider(){
        Injector injector = new InjectorImpl();
        injector.bind(InterfaceA.class, InterfaceAImplWithDefaultConstructor.class);

        Provider<InterfaceA> provider1 = injector.getProvider(InterfaceA.class);
        Provider<InterfaceA> provider2 = injector.getProvider(InterfaceA.class);

        assertEquals(provider1, provider2);
    }

    @Test(expected = BindingNotFoundException.class)
    public void testGetProviderExpectException(){
        Injector injector = new InjectorImpl();
        injector.bind(InterfaceA.class, InterfaceAImplWithOneInjectAnnotation.class);
        injector.getProvider(InterfaceA.class);
    }

    @Test
    public void testGetProviderExpectSingletonScope(){
        Injector injector = new InjectorImpl();
        injector.bindSingleton(InterfaceA.class, InterfaceAImplWithOneInjectAnnotation.class);
        injector.bindSingleton(InterfaceB.class, InterfaceBImpl.class);

        Provider<InterfaceA> provider = injector.getProvider(InterfaceA.class);
        InterfaceA impl1 = provider.getInstance();
        InterfaceA impl2 = provider.getInstance();

        assertNotNull(impl1);
        assertEquals(impl1, impl2);
    }

    @Test
    public void testGetProviderExpectPrototypeScope() {
        Injector injector = new InjectorImpl();
        injector.bind(InterfaceA.class, InterfaceAImplWithDefaultConstructor.class);

        Provider<InterfaceA> provider = injector.getProvider(InterfaceA.class);
        InterfaceA impl1 = provider.getInstance();
        InterfaceA impl2 = provider.getInstance();

        assertNotNull(impl1);
        assertNotEquals(impl1, impl2);
    }
}