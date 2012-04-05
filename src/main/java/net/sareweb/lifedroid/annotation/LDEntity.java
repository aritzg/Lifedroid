package net.sareweb.lifedroid.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LDEntity {

	String tableName() default "";

}
