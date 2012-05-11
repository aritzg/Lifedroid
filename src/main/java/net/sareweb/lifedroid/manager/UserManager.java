package net.sareweb.lifedroid.manager;

import net.sareweb.lifedroid.liferay.service.UserRESTService;
import net.sareweb.lifedroid.model.User;

import com.googlecode.androidannotations.annotations.EBean;

@EBean
public class UserManager extends GenericManager {

	@Override
	public void synchronize() {
		// TODO Auto-generated method stub
	}

	public User validateUser(String emailAddress, String password){
		UserRESTService urs = new UserRESTService(emailAddress, password);
		try {
			return urs.getUserByEmailAddress(emailAddress);

		} catch (Exception e) {
			return null;
		}

	}

	public User getUserByIdFromServer(String userId) {
		return null;
	}

	public User getUserByEmailAddressFromServer(String emailAddress) {
		return null;
	}

}
