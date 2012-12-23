package net.sareweb.lifedroid.model;

import net.sareweb.lifedroid.annotation.LDField;
import net.sareweb.lifedroid.model.generic.LDObject;

/**
 * @author aritz
 *
 */
public class DLFileEntry extends LDObject{
	
	@LDField(id=true, sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long fileEntryId = new Long(0);
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long repositoryId = new Long(0);
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long folderId = new Long(0);
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long groupId = new Long(0);
	
	@LDField
	private String sourceFileName;
	
	@LDField
	private String mimeType;
	
	@LDField
	private String title;

	public Long getFileEntryId() {
		return fileEntryId;
	}

	public void setFileEntryId(Long fileEntryId) {
		this.fileEntryId = fileEntryId;
	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

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

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
