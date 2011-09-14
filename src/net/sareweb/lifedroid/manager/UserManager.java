package net.sareweb.lifedroid.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class UserManager extends GenericManager {

	public UserManager(String server, int port, String serviceBaseURL) {
		super(server, port, serviceBaseURL);
		_serviceClassName = "com.liferay.portal.service.UserServiceUtil";
	}
	
	public String getUser(String userId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("serviceMethodName", "getUserById"));
		params.add(new BasicNameValuePair("serviceParameters", "[userId]"));
		params.add(new BasicNameValuePair("userId", userId));
		
		return _rClient.runRequest(params);
	}

}
