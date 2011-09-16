package net.sareweb.lifedroid.manager;

import java.util.HashMap;

public class UserManager extends GenericManager {

	public UserManager(String server, int port, String serviceBaseURL) {
		super(server, port, serviceBaseURL);
		_serviceClassName = "com.liferay.portal.service.UserServiceUtil";
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

	private String _METHOD_NAME_GET_USER_BY_EMAIL_ADDRESS = "getUserByEmailAddress";
	private String _METHOD_NAME_GET_USER_BY_ID = "getUserById";
	private String _METHOD_NAME_GET_USER_BY_SCREEN_NAME = "getUserByScreenName";

}
