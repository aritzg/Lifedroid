package net.sareweb.lifedroid.model;

import net.sareweb.lifedroid.annotation.LDField;
import net.sareweb.lifedroid.model.generic.LDObject;

public class DLFolder extends LDObject{
	
	@LDField(id=true, sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long folderId = new Long(0);
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long groupId = new Long(0);
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long parentFolderId = new Long(0);
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long userId = new Long(0);
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long repositoryId = new Long(0);
	
	@LDField
	private String name;
	
	@LDField
	private String description;

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(Long parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
