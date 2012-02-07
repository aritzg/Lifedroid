package net.sareweb.lifedroid.model;

import net.sareweb.lifedroid.annotation.LDField;

public abstract class LDObject {

	public static String tableName;

	@LDField(id = true, sqliteType = LDField.SQLITE_TYPE_INTEGER)
	public String id;

}
