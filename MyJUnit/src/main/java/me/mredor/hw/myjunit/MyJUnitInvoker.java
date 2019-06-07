package me.mredor.hw.myjunit;

import me.mredor.hw.myjunit.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Special class to invoke all tests in the given class.
 *
 * Before all test methods should be invoked all methods with annotation BeforeClass.
 * Before each test method should be invoked all methods with annotation Before.
 * After each test methods should be invoked all methods with annotation After.
 * After all test methods should be invoked all methods with annotation AfterClass
 */
public class MyJUnitInvoker {
    private Class clazz;
    private List<Method> beforeClassMethods;
    private List<Method> beforeMethods;
    private List<Method> afterMethods;
    private List<Method> afterClassMethods;
    private List<TestMethod> testMethods;
    private Object instance;

    /** Creates invoker for class clazz. */
    public MyJUnitInvoker(Class clazz) {
        this.clazz = clazz;
    }

    /** Runs invoker. */
    public void run() throws MultipleAnnotationException {
        try {
            instance = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println("Unexpected exception: " + e.getMessage());
            return;
        }
        getMethods(clazz);
        try {
            invokeBeforeClass();
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Unexpected exception in BeforeClass methods: " + e.getMessage());
            return;
        }
        invokeTests();
        try {
            invokeAfterClass();
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Unexpected exception in AfterClass methods: " + e.getMessage());
        }
    }

    private void invokeBeforeClass() throws IllegalAccessException, InvocationTargetException {
        invokeAllSimply(beforeClassMethods);
    }

    private void invokeBefore() throws IllegalAccessException, InvocationTargetException {
        invokeAllSimply(beforeMethods);
    }

    private void invokeAfter() throws IllegalAccessException, InvocationTargetException {
        invokeAllSimply(afterMethods);
    }

    private void invokeAfterClass() throws IllegalAccessException, InvocationTargetException {
        invokeAllSimply(afterClassMethods);
    }

    private void invokeAllSimply(List<Method> methods) throws IllegalAccessException, InvocationTargetException {
        for (var current : methods) {
            current.invoke(instance);
        }
    }

    private void invokeTests() {
        int ignoredNumber = 0;
        int passedNumber = 0;
        int failedNumber = 0;
        for (var current : testMethods) {
            if (!current.ignore.equals(Test.EMPTY)) {
                System.out.print("Test " + current.method.getName() + " is ignored. ");
                System.out.println("Reason: " + current.ignore);
                ignoredNumber++;
                continue;
            }
            try {
                invokeBefore();
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Unexpected exception in Before methods (before Test " + current.method.getName() + "): " + e.getMessage());
            }

            var timeStart = System.currentTimeMillis();

            boolean success = true;
            String reasonFail = null;

            try {
                Throwable exception = null;
                try {
                    current.method.invoke(instance);
                } catch (Exception e) {
                    exception = e.getCause();
                }

                if (current.expected == Test.NoException.class) {
                    if (exception != null) {
                        //throw exception;
                        throw new FailedExpectationException("Method should throw nothing but throws " + exception);
                    }
                } else {
                    if (exception == null) {
                        throw new FailedExpectationException("Method should throw " + current.expected.getName() + " but throws nothing.");
                    } else {
                        if (current.expected != exception.getClass()) {
                            throw new FailedExpectationException("Method should throw " + current.expected.getName() + " but throws " + exception + " with message " + exception.getMessage());
                        }
                    }
                }


            } catch (Throwable e) {
                success = false;
                reasonFail = e.getMessage();
            }

            var timeEnd = System.currentTimeMillis();
            double time = (timeEnd - timeStart) / 1000.0;

            if (success) {
                System.out.println("Test " + current.method.getName() + " passed in " + String.valueOf(time) + " sec.");
                passedNumber++;
            } else {
                System.out.println("Test " + current.method.getName() + " failed in " + String.valueOf(time) + " sec. Reason: " + reasonFail);
                failedNumber++;
            }

            try {
                invokeAfter();
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Unexpected exception in After methods (after Test " + current.method.getName() + "): " + e.getMessage());
            }
        }
        System.out.println("Test invocation finished with " + String.valueOf(passedNumber) + " successes, " + String.valueOf(failedNumber) + " failures and " + String.valueOf(ignoredNumber) + " ignores.");
    }


    private void getMethods(Class clazz) throws MultipleAnnotationException {
        beforeMethods = new ArrayList<>();
        beforeClassMethods = new ArrayList<>();
        afterMethods = new ArrayList<>();
        afterClassMethods = new ArrayList<>();
        testMethods = new ArrayList<>();

        for (var current : clazz.getDeclaredMethods()) {
            int annotationsNumber = 0;
            current.setAccessible(true);

            if (current.getAnnotation(BeforeClass.class) != null) {
                beforeClassMethods.add(current);
                annotationsNumber++;
            }
            if (current.getAnnotation(Before.class) != null) {
                beforeMethods.add(current);
                annotationsNumber++;
            }
            if (current.getAnnotation(After.class) != null) {
                afterMethods.add(current);
                annotationsNumber++;
            }
            if (current.getAnnotation(AfterClass.class) != null) {
                afterClassMethods.add(current);
                annotationsNumber++;
            }
            if (current.getAnnotation(Test.class) != null) {
                var annotation = current.getAnnotation(Test.class);
                testMethods.add(new TestMethod(current, annotation.expected(), annotation.ignore()));
                annotationsNumber++;
            }

            if (annotationsNumber > 1) {
                throw new MultipleAnnotationException();
            }
        }
    }

    private static class TestMethod {
        Method method;
        Class expected;
        String ignore;

        TestMethod(Method method, Class expected, String ignore) {
            this.method = method;
            this.expected = expected;
            this.ignore = ignore;
        }
    }
}
