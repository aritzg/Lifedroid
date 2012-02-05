package net.sareweb.lifedroid.manager;

import java.util.HashMap;

import android.util.Log;

public class UserManager extends GenericManager {

	public UserManager(String server, int port, String serviceBaseURL) {
		super(server, port, serviceBaseURL);
		_serviceClassName = "com.liferay.portal.service.UserServiceUtil";
	}

	public boolean validateUserCredentials() {
		String tmp = _rClient.runGET(null, null, null);
		if (!tmp.equals("")) {
			Log.w(TAG, "Wrong Credentials!");
			return false;
		}
		return true;
	}

	public String getUserByEmailAddress(String companyId, String emailAddress) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("companyId", companyId);
		params.put("emailAddress", emailAddress);

		return _rClient.runGET(_serviceClassName,
				_METHOD_NAME_GET_USER_BY_EMAIL_ADDRESS, params);
	}

	public String getUserById(String userId) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		return _rClient.runGET(_serviceClassName, _METHOD_NAME_GET_USER_BY_ID,
				params);
	}

	public String getUserByScreenName(String companyId, String screenName) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("companyId", companyId);
		params.put("screenName", screenName);

		return _rClient.runGET(_serviceClassName,
				_METHOD_NAME_GET_USER_BY_SCREEN_NAME, params);
	}

	public String addUser(long companyId, boolean autoPassword,
			String password1, String password2,
			boolean autoScreenName,
			String screenName,
			String emailAddress,
			long facebookId,
			String openId,
			// Locale locale,
			String firstName, String middleName, String lastName, int prefixId,
			int suffixId, boolean male, int birthdayMonth, int birthdayDay,
			int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds, long[] userGroupIds,
			boolean sendEmail/* , ServiceContext serviceContext */) {

		HashMap<String, String> params = new HashMap<String, String>();

		params.put("companyId", String.valueOf(companyId));
		params.put("autoPassword", String.valueOf(autoPassword));
		params.put("password1", password1);
		params.put("password2", password2);
		params.put("autoScreenName", String.valueOf(autoScreenName));
		params.put("screenName", screenName);
		params.put("emailAddress", emailAddress);
		params.put("facebookId", String.valueOf(facebookId));
		params.put("openId", openId);
		params.put("locale", "");
		params.put("firstName", firstName);
		params.put("middleName", middleName);
		params.put("lastName", lastName);
		params.put("prefixId", String.valueOf(prefixId));
		params.put("suffixId", String.valueOf(suffixId));
		params.put("male", String.valueOf(male));
		params.put("birthdayMonth", String.valueOf(birthdayMonth));
		params.put("birthdayDay", String.valueOf(birthdayDay));
		params.put("birthdayYear", String.valueOf(birthdayYear));
		params.put("jobTitle", jobTitle);
		params.put("groupIds", longArrayToString(groupIds));
		params.put("organizationIds", longArrayToString(organizationIds));
		params.put("roleIds", longArrayToString(roleIds));
		params.put("userGroupIds", longArrayToString(userGroupIds));
		params.put("sendEmail", String.valueOf(sendEmail));
		params.put("serviceContext", "{}");

		return _rClient
				.runGET(_serviceClassName, _METHOD_NAME_ADD_USER, params);
	}

	private String _METHOD_NAME_GET_USER_BY_EMAIL_ADDRESS = "getUserByEmailAddress";
	private String _METHOD_NAME_GET_USER_BY_ID = "getUserById";
	private String _METHOD_NAME_GET_USER_BY_SCREEN_NAME = "getUserByScreenName";
	private String _METHOD_NAME_ADD_USER = "addUser";

	private static final String TAG = "UserManager";

}
