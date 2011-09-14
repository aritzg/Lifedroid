package net.sareweb.lifedroid.manager;

public class UserManager extends GenericManager {

	public UserManager(String server, int port, String serviceBaseURL) {
		super(server, port, serviceBaseURL);
		_serviceClassName = "com.liferay.portal.service.UserServiceUtil";
	}

}
