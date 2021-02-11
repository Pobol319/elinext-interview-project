package by.devincubator.pobol.data.impl;

import by.devincubator.pobol.annotation.Inject;
import by.devincubator.pobol.data.InterfaceA;
import by.devincubator.pobol.data.InterfaceB;

public class InterfaceAImplWithOneInjectAnnotation implements InterfaceA {

    @Inject
    public InterfaceAImplWithOneInjectAnnotation(InterfaceB b1, InterfaceB b2) {
    }

}
