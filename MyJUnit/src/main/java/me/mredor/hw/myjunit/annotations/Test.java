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
    String ignore() default EMPTY;

    Class<? extends Throwable> expected() default NoException.class;
    class NoException extends Throwable {}
}
