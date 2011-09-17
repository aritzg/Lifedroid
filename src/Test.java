import net.sareweb.lifedroid.model.UserModel;
import net.sareweb.lifedroid.model.parser.GenericParser;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenericParser parser = new GenericParser(UserModel.class);
		System.out.println("aaaaaaaaa");
		parser.parse("{\"portraitId\":0,\"agreedToTermsOfUse\":true,\"passwordEncrypted\":false,\"screenName\":\"10135\",\"facebookId\":0,\"reminderQueryAnswer\":\"\",\"digest\":\"5533ed38b5e33c076a804bb4bca644f9,4b2c83b9c14991181b3134f9975c6229,4b2c83b9c14991181b3134f9975c6229\",\"password\":\"password\",\"passwordReset\":false,\"defaultUser\":true,\"lastFailedLoginDate\":\"\",\"userId\":10135,\"passwordModifiedDate\":\"\",\"loginDate\":\"1315856252000\",\"lockoutDate\":\"\",\"createDate\":\"1315856252000\",\"firstName\":\"\",\"jobTitle\":\"\",\"middleName\":\"\",\"lastName\":\"\",\"lastLoginDate\":\"\",\"greeting\":\"Welcome!\",\"reminderQueryQuestion\":\"\",\"emailAddress\":\"default@liferay.com\",\"lockout\":false,\"languageId\":\"en_US\",\"modifiedDate\":\"1315856252000\",\"lastLoginIP\":\"\",\"contactId\":10136,\"failedLoginAttempts\":0,\"loginIP\":\"\",\"active\":true,\"graceLoginCount\":0,\"companyId\":10132,\"uuid\":\"d64f60ea-c7a2-431e-b96d-58af48dcd0e6\",\"comments\":\"\",\"openId\":\"\",\"timeZoneId\":\"UTC\"}");
	}

}
