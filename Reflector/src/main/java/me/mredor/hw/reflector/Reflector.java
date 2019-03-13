package me.mredor.hw.reflector;

import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

public class Reflector {

    private FileWriter outputFile;

    /**  Print fields and methods, which are different in two given classes  */
    public void diffClasses(@NotNull Class<?> first, @NotNull Class<?> second) throws IOException {
        outputFile = new FileWriter( "DiffClasses");
        var firstFields = Arrays.asList(first.getDeclaredFields());
        var secondFields = Arrays.asList(second.getDeclaredFields());
        for (Field current : firstFields) {
            if (!secondFields.contains(current)) {
                printField(current);
                outputFile.write(", ");
            }
        }
        for (Field current : secondFields) {
            if (!firstFields.contains(current)) {
                printField(current);
                outputFile.write(", ");
            }
        }
        var firstMethods = Arrays.asList(first.getDeclaredMethods());
        var secondMethods = Arrays.asList(second.getDeclaredMethods());
        for (Method current : firstMethods) {
            if (!secondMethods.contains(current)) {
                printMethod(current);
                outputFile.write(", ");
            }
        }
        for (Method current : secondMethods) {
            if (!firstMethods.contains(current)) {
                printMethod(current);
                outputFile.write(", ");
            }
        }
        outputFile.close();
    }

    /** Prints structure of class into correct .java file
     *
     * @throws IOException if could not create file or there are some problems with recording
     * */
    public void printStructure(@NotNull Class<?> someClass) throws IOException {
        outputFile = new FileWriter(someClass.getSimpleName() + ".java");
        printClass(someClass);
    }

    private void printClass(@NotNull Class<?> someClass) throws IOException {
        outputFile.write(Modifier.toString(someClass.getModifiers()) + " ");
        outputFile.write("class " + someClass.getSimpleName());
        if (someClass.getTypeParameters().length > 0) {
            var size = someClass.getTypeParameters().length;
            outputFile.write("<");
            for (int i = 0; i < someClass.getTypeParameters().length - 1; i++) {
                outputFile.write(someClass.getTypeParameters()[i].getTypeName() + ", ");
            }
            outputFile.write(someClass.getTypeParameters()[size - 1].getTypeName() + ">");
        }
        //extends
        if (someClass.getGenericSuperclass() != null && someClass.getGenericSuperclass() != Object.class) {
            outputFile.write("extends " + getType(someClass.getGenericSuperclass()) + " ");
        }
        //implements
        if (someClass.getGenericInterfaces().length > 0) {
            outputFile.write("implements ");
            for (int i = 0; i < someClass.getGenericInterfaces().length - 1; i++) {
                outputFile.write(someClass.getGenericInterfaces()[i].getTypeName() + ", ");
            }
            outputFile.write(someClass.getGenericInterfaces()[ someClass.getGenericInterfaces().length - 1 ].getTypeName());
        }
        //
        outputFile.write("{\n");
        //fields
        for (var current : someClass.getDeclaredFields()) {
            printField(current);
        }
        printConstructors(someClass);
        //methods
        for (var current : someClass.getDeclaredMethods()) {
            printMethod(current);
        }
        //classes
        for (var current : someClass.getDeclaredClasses()) {
            printClass(current);
        }
        outputFile.write("}\n");
        outputFile.close();
    }

    private void printField(@NotNull Field field) throws IOException {
        outputFile.write(Modifier.toString(field.getModifiers()) + " " + getType(field.getGenericType()) + " " + field.getName() + ";\n" );
    }

    private void printConstructors(@NotNull Class<?> someClass) throws IOException {
        Constructor[] constructors = someClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            outputFile.write(Modifier.toString(constructor.getModifiers()) + " " + constructor.getDeclaringClass().getSimpleName() + "(");
            if (constructor.getGenericParameterTypes().length > 0) {
                outputFile.write(getType(constructor.getGenericParameterTypes()[0]) + " " + constructor.getParameters()[0].getName());
                for (int i = 1; i < constructor.getGenericParameterTypes().length; i++) {
                    outputFile.write(", " + getType(constructor.getGenericParameterTypes()[i]) + " " + constructor.getParameters()[i].getName());
                }
            }
            outputFile.write(") ");
            if (constructor.getGenericExceptionTypes().length > 0) {
                var exceptions = constructor.getGenericExceptionTypes();
                outputFile.write("throws ");
                for (int i = 0; i < exceptions.length - 1; i++) {
                    outputFile.write(exceptions[i].getTypeName() + ", ");
                }
                outputFile.write(exceptions[exceptions.length - 1].getTypeName());
            }
            outputFile.write(" { }" + "\n");
        }
    }

    private void printMethod(@NotNull Method method) throws IOException {
        outputFile.write(Modifier.toString(method.getModifiers()) + " ");
        outputFile.write(method.getGenericReturnType() + " " + method.getName() + "(");
        var types = method.getGenericParameterTypes();
        if (types.length > 0) {
            outputFile.write(getType(types[0]) + " " + method.getParameters()[0].getName());
            for (int i = 1; i < types.length; i++) {
                outputFile.write(", " + getType(types[i]) + " " + method.getParameters()[i].getName());
            }
        }
        outputFile.write(") ");
        if (method.getGenericExceptionTypes().length > 0) {
            var exceptions = method.getGenericExceptionTypes();
            outputFile.write("throws ");
            for (int i = 0; i < exceptions.length - 1; i++) {
                outputFile.write(exceptions[i].getTypeName() + ", ");
            }
            outputFile.write(exceptions[exceptions.length - 1].getTypeName());
        }
        if (method.getReturnType() == void.class) {
            outputFile.write("{ }\n");
        } else {
            outputFile.write("{ return ");
            var typeClass = (Class<?>) method.getReturnType();
            if (typeClass.isPrimitive()) {
                var value = Array.newInstance((Class<?>) method.getReturnType(), 1);
                outputFile.write(Array.get(value, 0).toString());
            }
            outputFile.write("; }\n");
        }
    }

    private String getType(@NotNull Type type) {
        return type.getTypeName().replace('$', '.');
    }

}