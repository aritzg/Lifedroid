package net.sareweb.lifedroid.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

public class RESTClient {

	public RESTClient(String server, int port, String serviceBaseURL) {
		_server = server;
		_port = port;
		_serviceBaseURL = serviceBaseURL;
	}

	public void init(String user, String pass) {

		HttpHost targetHost = new HttpHost(_server, _port, "http");
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(user, pass));
		
		// Create AuthCache instance
        /*
         * AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local
        // auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        HttpPost post = new HttpPost("/tunnel-web/secure/json");*/

	}

	private String _server;
	private int _port;
	private String _serviceBaseURL;
	private HttpClient _httpClient;

}
