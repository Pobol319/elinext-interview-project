package by.devincubator.pobol.testData;

import by.devincubator.pobol.annotation.Inject;

public class InterfaceAImplWithManyInjectAnnotations implements InterfaceA{

    @Inject
    public InterfaceAImplWithManyInjectAnnotations(InterfaceB b1, InterfaceB b2){}

    @Inject
    public InterfaceAImplWithManyInjectAnnotations(InterfaceB b1, InterfaceB b2, InterfaceB b3){}
}
