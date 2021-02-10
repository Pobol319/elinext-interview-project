package by.devincubator.pobol;

import by.devincubator.pobol.exception.ConstructorNotFoundException;
import by.devincubator.pobol.testData.*;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertSame;


public class InjectorImplTest {

    @Test(expected = ConstructorNotFoundException.class)
    void testGetDefaultConstructorIfClassHaveDefaultConstructorExpectException() {

    }

    @Test
    void testExistingBinding() {
        Injector injector = new InjectorImpl();
        injector.bind(InterfaceA.class, InterfaceAImplWithOneInjectAnnotations.class);
        injector.bindSingleton(InterfaceB.class, InterfaceBImpl.class);
        Provider<InterfaceA> daoProvider = injector.getProvider(InterfaceA.class);
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(InterfaceAImplWithOneInjectAnnotations.class, daoProvider.getInstance().getClass());

    }
}