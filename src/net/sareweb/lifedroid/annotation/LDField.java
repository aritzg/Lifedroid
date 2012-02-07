package net.sareweb.lifedroid.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface LDField {
	
	boolean id() default false;
	String sqliteType() default SQLITE_TYPE_TEXT;

	
	public final String SQLITE_TYPE_TEXT = "TEXT";
	public final String SQLITE_TYPE_INTEGER = "INTEGER";
	public final String SQLITE_TYPE_REAL = "REAL";
}
