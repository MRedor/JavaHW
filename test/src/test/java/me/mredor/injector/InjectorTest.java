package me.mredor.injector;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class InjectorTest {

    @Test
    public void injectorShouldInitializeClassWithoutDependencies()
            throws Exception {
        Object object = Injector.initialize("me.mredor.injector.ClassWithoutDependencies");
        assertTrue(object instanceof ClassWithoutDependencies);
    }

    @Test
    public void injectorShouldInitializeClassWithOneClassDependency()
            throws Exception {
        Object object = Injector.initialize(
                "me.mredor.injector.ClassWithOneClassDependency", me.mredor.injector.ClassWithoutDependencies.class);
        assertTrue(object instanceof ClassWithOneClassDependency);
        ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }

    @Test
    public void injectorCycleOfOneElement() {
        assertThrows(InjectionCycleException.class, () -> Injector.initialize("me.mredor.injector.A", A.class));
    }

    @Test
    public void injectorCycleofThree() {
        assertThrows(InjectionCycleException.class, () -> Injector.initialize("me.mredor.injector.B", B.class, C.class, D.class));
    }

    @Test
    public void injectorTestImplementationNotFoundException() {
        assertThrows(ImplementationNotFoundException.class, () -> Injector.initialize("me.mredor.injector.B", B.class, C.class));
    }

    @Test
    public void injectorConnectedWithCycle() {
        assertThrows(InjectionCycleException.class, () -> Injector.initialize("me.mredor.injector.AA", A.class));
    }
    //  public void injectorTestAmbiguousImplementationException(){
    //      assertThrows(AmbiguousImplementationException.class,
    //              () -> Injector.initialize("me.mredor.injector.Q3", Q1.class, Q2.class)
    //      );
    //  }
}