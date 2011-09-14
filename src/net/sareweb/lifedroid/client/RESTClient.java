package net.sareweb.lifedroid.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import sun.net.www.protocol.http.AuthCache;

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
		
		//new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT);
		
		
		HttpPost post = new HttpPost("/tunnel-web/secure/json");
		
		
		

        

	}

	private String _server;
	private int _port;
	private String _serviceBaseURL;
	private HttpClient _httpClient;

}
