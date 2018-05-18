package org.fileinterpreter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.fileinterpreter.parser.PositionalLineParser;

@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PositionalLine {
    Class<PositionalLineParser> parser() default PositionalLineParser.class;
}
