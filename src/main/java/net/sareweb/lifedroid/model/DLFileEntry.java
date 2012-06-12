package net.sareweb.lifedroid.model;

import net.sareweb.lifedroid.annotation.LDField;

public class DLFileEntry extends LDObject{
	
	@LDField(id=true, sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long fileEntryId = new Long(0);
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long repositoryId = new Long(0);
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long folderId = new Long(0);
	
	@LDField
	private String sourceFileName;
	
	@LDField
	private String mimeType;

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
	
	

		
}
