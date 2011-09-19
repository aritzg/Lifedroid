package net.sareweb.lifedroid.manager;

import net.sareweb.lifedroid.client.RESTClient;

public abstract class GenericManager {

	public GenericManager(String server, int port, String serviceBaseURL) {
		_rClient = new RESTClient(server, port, serviceBaseURL);
	}

	public void setCredentials(String user, String pass) {
		_rClient.setCredentials(user, pass);
	}

	protected RESTClient _rClient = null;
	protected String _serviceClassName = null;

}
