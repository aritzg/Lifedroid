package net.sareweb.lifedroid.liferay;

import net.sareweb.lifedroid.util.LDConstants;
import net.sareweb.lifedroid.util.rest.RESTClient;

public abstract class GenericLiferayConsumer {

	public GenericLiferayConsumer() {
		_rClient = new RESTClient(LDConstants.LIFERAY_SERVER_URL, LDConstants.LIFERAY_SERVER_PORT, LDConstants.LIFERAY_SERVER_SERVICE_PATH);
	}

	public void setCredentials(String user, String pass) {
		_rClient.setCredentials(user, pass);
	}

	protected String longArrayToString(long[] values) {

		if (values == null || values.length < 1)
			return "{}";
		String tmp = "{";
		for (int i = 0; i < values.length; i++) {
			tmp += values[i];
			if (i < values.length - 1)
				tmp += ",";
		}
		tmp += "}";
		return tmp;

	}

	protected RESTClient _rClient = null;
	protected String _serviceClassName = null;

}
