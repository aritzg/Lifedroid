package net.sareweb.lifedroid.liferay.service;

import net.sareweb.lifedroid.model.User;
import net.sareweb.lifedroid.util.LDConstants;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

@Rest(LDConstants.LIFERAY_REST_BASE_URL)
public interface UserService {
	
	@Get("/user/get-user-by-id/userId/{userId}")
	User getUserById(String userId);

}
