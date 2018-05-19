package org.fileinterpreter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PositionalField {
    String name();
    String defaultValue() default "";
    int startIndex();
    int size();
    char spaceFilling() default ' ';
    boolean rtl() default false;
}
