package me.mredor.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;

public class Injector {
    private static HashSet<Class<?>> was;
    private static HashMap<Class<?>, Object> instances;


    /**
     * Creates the object of classRootName type
     *
     * @throws ClassNotFoundException if rootClassName is not the name of class
     * @throws AmbiguousImplementationException if there are 2 or more implementations for one or more dependences
     * @throws ImplementationNotFoundException if there no implementation for one or more dependence
     * @throws InjectionCycleException if there is a cycle in dependences
     * */

    public static Object initialize(String rootClassName, Class<?>... classes) throws ClassNotFoundException, ImplementationNotFoundException, AmbiguousImplementationException, InjectionCycleException, InvocationTargetException, InstantiationException, IllegalAccessException {
        was = new HashSet<>();
        instances = new HashMap<>();
        return make(Class.forName(rootClassName), classes);
    }

    private static Object make(Class<?> clz, Class<?>[] classes) throws ImplementationNotFoundException, AmbiguousImplementationException, InjectionCycleException, IllegalAccessException, InvocationTargetException, InstantiationException {
        was.add(clz);
        Constructor<?> constructor = clz.getDeclaredConstructors()[0]; //clz.getDeclaredConstructor();
        Class<?>[] dependences = constructor.getParameterTypes();
        var size = dependences.length;
        Object[] result = new Object[dependences.length];
        for (int i = 0; i < size; i++) {
            Class<?> parameter = dependences[i];
            Class<?> last = null;
            int numberOfImplementations = 0;
            for (Class<?> current : classes) {
                if (parameter.isAssignableFrom(current)) {   //(other instanceof parameters[i]) {
                    numberOfImplementations++;
                    last = current;
                }
            }

            if (was.contains(last)) {
                throw new InjectionCycleException("There is a cycle with " + parameter.getName());
            }

            if (numberOfImplementations == 0) {
                throw new ImplementationNotFoundException("There is no implementation for " + parameter.getName());
            }
            if (numberOfImplementations > 1) {
                throw new AmbiguousImplementationException("There is too many implementations for " + parameter.getName());
            }

            if (instances.get(last) != null) {
                result[i] = instances.get(parameter);
                continue;
            } else {
                instances.put(last, make(last, classes));
                result[i] = instances.get(last);
            }
        }
        return constructor.newInstance(result);
    }
}

















