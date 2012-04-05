package net.sareweb.lifedroid.manager;

import net.sareweb.lifedroid.model.UserModel;


public class UserManager extends GenericManager {

	@Override
	public void synchronize() {
		// TODO Auto-generated method stub
		
	}
	
	public UserModel validateUser(String userName, String password){
		return null;
	}
	
	public UserModel getUserByIdFromServer(String userId){
		return null;
	}
	
	public UserModel getUserByEmailAddressFromServer(String emailAddress){
		return null;
	}

}
