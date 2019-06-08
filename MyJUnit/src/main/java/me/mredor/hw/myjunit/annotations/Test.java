package me.mredor.hw.myjunit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for test methods.
 * Test can be ignored if {@code ignore} parameter is not empty.
 * If some exception could be thrown in method, {@code expected} parameter should be description class of possible exception.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

    String EMPTY = "";
    /** Gets the reason to ignore the test.
     * Default value -- EMPTY -- if test shouldn't be ignored. */
    String ignore() default EMPTY;

    /** Gets class (extended throwable) which is expected to be thrown in the test.
     * Default value -- NoException.class -- if no throws are expected */
    Class<? extends Throwable> expected() default NoException.class;

    /** Special exception class means no exception. */
    class NoException extends Throwable {}
}
