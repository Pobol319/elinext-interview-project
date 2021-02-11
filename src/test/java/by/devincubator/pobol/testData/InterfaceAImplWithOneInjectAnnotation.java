package by.devincubator.pobol.testData;

import by.devincubator.pobol.annotation.Inject;

public class InterfaceAImplWithOneInjectAnnotation implements InterfaceA {

    @Inject
    public InterfaceAImplWithOneInjectAnnotation(InterfaceB b1, InterfaceB b2) {
    }

}
