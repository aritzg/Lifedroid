package net.sareweb.lifedroid.model;

import net.sareweb.lifedroid.annotation.LDField;

public abstract class LDObject {

	public static String tableName;

	@LDField(id = true, sqliteType = LDField.SQLITE_TYPE_INTEGER)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
