package me.mredor.injector;

public class ClassWithOneInterfaceDependency {
    public final Interface dependency;

    public ClassWithOneInterfaceDependency(Interface dependency) {
        this.dependency = dependency;
    }
}
