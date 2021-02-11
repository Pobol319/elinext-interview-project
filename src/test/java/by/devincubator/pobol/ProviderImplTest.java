package by.devincubator.pobol;

import by.devincubator.pobol.exception.ProviderNewInstanceException;
import by.devincubator.pobol.scope.BeanScopeEnum;
import by.devincubator.pobol.testData.InterfaceB;
import by.devincubator.pobol.testData.InterfaceBImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static junit.framework.TestCase.*;

public class ProviderImplTest {
    private static Constructor<InterfaceBImpl> bConstructor;

    @BeforeClass
    public static void setUp() {
        Constructor[] constructors = InterfaceBImpl.class.getConstructors();
        bConstructor = constructors[0];
    }

    @Test
    public void testGetInstanceNewInstance() {
        Provider<InterfaceBImpl> provider = new ProviderImpl<>(bConstructor, BeanScopeEnum.SINGLETON, null);

        assertNotNull(provider.getInstance());
    }

    @Test
    public void testGetInstanceSingleton() {
        Provider<InterfaceBImpl> provider = new ProviderImpl<>(bConstructor, BeanScopeEnum.SINGLETON, null);

        assertSame(provider.getInstance(), provider.getInstance());
    }

    @Test
    public void testGetInstancePrototype() {
        Provider<InterfaceBImpl> provider = new ProviderImpl<>(bConstructor, BeanScopeEnum.PROTOTYPE, null);

        assertNotSame(provider.getInstance(), provider.getInstance());
    }

}
