package by.devincubator.pobol.data.impl;

import by.devincubator.pobol.annotation.Inject;
import by.devincubator.pobol.data.InterfaceA;
import by.devincubator.pobol.data.InterfaceB;

public class InterfaceAImplWithManyInjectAnnotations implements InterfaceA {

    @Inject
    public InterfaceAImplWithManyInjectAnnotations(InterfaceB b1, InterfaceB b2){}

    @Inject
    public InterfaceAImplWithManyInjectAnnotations(InterfaceB b1, InterfaceB b2, InterfaceB b3){}
}
