package org.yaod.stcc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yaod
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TryTCC {

    String confirm() default "";

    String cancel() default "";

    //async mode for confirm/cancel.
    boolean asyncMode() default true;
}
