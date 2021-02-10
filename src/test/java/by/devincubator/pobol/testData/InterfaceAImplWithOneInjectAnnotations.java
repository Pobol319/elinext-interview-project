package by.devincubator.pobol.testData;

import by.devincubator.pobol.annotation.Inject;

public class InterfaceAImplWithOneInjectAnnotations implements InterfaceA {

    @Inject
    public InterfaceAImplWithOneInjectAnnotations(InterfaceB b1, InterfaceB b2) {
    }

}
