package net.sareweb.lifedroid.manager;

import java.util.HashMap;

public class UserManager extends GenericManager {

	public UserManager(String server, int port, String serviceBaseURL) {
		super(server, port, serviceBaseURL);
		_serviceClassName = "com.liferay.portal.service.UserServiceUtil";
	}

	public String getUserById(String userId) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);

		return _rClient.runGET(_serviceClassName, _METHOD_NAME_GET_USER_BY_ID,
				params);
	}

	private String _METHOD_NAME_GET_USER_BY_ID = "getUserById";

}
