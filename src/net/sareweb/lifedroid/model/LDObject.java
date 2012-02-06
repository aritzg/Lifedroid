package net.sareweb.lifedroid.model;

import net.sareweb.lifedroid.annotation.Field;

public abstract class LDObject {
	
	public static String tableName;
	public static String[] fields;
	
	@Field(id="true", type="Long" )
	public String id;
	
}
