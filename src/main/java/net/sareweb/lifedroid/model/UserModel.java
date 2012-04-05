package net.sareweb.lifedroid.model;

import net.sareweb.lifedroid.annotation.LDField;

public class UserModel extends LDObject{
	
	@LDField(id=true, sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long userId;
	
	@LDField
	private String screenName;
	
	@LDField
	private String password;
	
	@LDField
	private String emailAddress;
	
	@LDField
	private String firstName;
	
	@LDField
	private String middleName;
	
	@LDField
	private String lastName;
	
	@LDField
	private String languageId;
	
	@LDField(sqliteType=LDField.SQLITE_TYPE_INTEGER)
	private Long contactId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	
}
