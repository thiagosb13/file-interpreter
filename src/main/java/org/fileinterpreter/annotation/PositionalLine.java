package org.fileinterpreter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.fileinterpreter.parser.PositionalLineParser;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PositionalLine {
	boolean optional() default false;
    Class<PositionalLineParser> parser() default PositionalLineParser.class;
}
