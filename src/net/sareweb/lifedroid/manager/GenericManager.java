package net.sareweb.lifedroid.manager;

import net.sareweb.lifedroid.client.RESTClient;

public abstract class GenericManager {

	public GenericManager(String server, int port, String serviceBaseURL) {
		_rClient = new RESTClient(server, port, serviceBaseURL);
	}

	private RESTClient _rClient = null;

}
