package net.sareweb.lifedroid.util.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class RESTClient {

	public RESTClient(String server, int port, String serviceBaseURL) {
		_server = server;
		_port = port;
		_serviceBaseURL = serviceBaseURL;
		_httpClient = new DefaultHttpClient();

		_targetHost = new HttpHost(_server, _port);

	}

	public void setCredentials(String user, String pass) {
		_user = user;
		_pass = pass;
		_httpClient.getCredentialsProvider()
				.setCredentials(
						new AuthScope(_targetHost.getHostName(),
								_targetHost.getPort()),
						new UsernamePasswordCredentials(user, pass));
	}

	public String runGET(String serviceClassName, String serviceMethodName,
			HashMap<String, String> params) {
		BasicHttpContext localContext = new BasicHttpContext();
		BasicScheme basicAuth = new BasicScheme();
		localContext.setAttribute("preemptive-auth", basicAuth);
		String fullUrl = "http://" + _user + ":" + _pass + "@" + _server + ":"
				+ _port + _serviceBaseURL;

		if (serviceClassName != null && !serviceClassName.equals("")) {
			fullUrl += "?serviceClassName=" + serviceClassName;
		}

		if (serviceMethodName != null && !serviceMethodName.equals("")) {
			fullUrl += "&serviceMethodName=" + serviceMethodName;
		}

		if (params != null && params.size() > 0) {
			fullUrl += "&serviceParameters=" + getParamNameString(params)
					+ getNameValuePairsString(params);
		}

		Log.d(TAG, "Full URL: " + fullUrl);
		HttpGet httpGet = new HttpGet(fullUrl);
		httpGet.setHeader("Content-Type", "application/xml");
		HttpResponse response;
		try {
			response = _httpClient.execute(_targetHost, httpGet, localContext);
			HttpEntity entity = response.getEntity();

			String content = EntityUtils.toString(entity);
			Log.d(TAG, "RESULT: " + content);
			return content;
		} catch (ClientProtocolException e) {
			Log.d(TAG, "ClientProtocolException in REST GET", e);
		} catch (IOException e) {
			Log.d(TAG, "IOException in REST GET", e);
		}
		return "";
	}
	
	
	public String runPOST(String serviceClassName, String serviceMethodName,
			HashMap<String, String> params) {
		
		BasicHttpContext localContext = new BasicHttpContext();
		BasicScheme basicAuth = new BasicScheme();
		localContext.setAttribute("preemptive-auth", basicAuth);
		String fullUrl = "http://" + _user + ":" + _pass + "@" + _server + ":"
				+ _port + _serviceBaseURL;

		if (serviceClassName != null && !serviceClassName.equals("")) {
			fullUrl += "?serviceClassName=" + serviceClassName;
		}

		if (serviceMethodName != null && !serviceMethodName.equals("")) {
			fullUrl += "&serviceMethodName=" + serviceMethodName;
		}

		if (params != null && params.size() > 0) {
			fullUrl += "&serviceParameters=" + getParamNameString(params)
					+ getNameValuePairsString(params);
		}

		Log.d(TAG, "Full URL: " + fullUrl);
		
		HttpPost post = new HttpPost(fullUrl);
		
		
		return "";
	}

	private String getParamNameString(HashMap<String, String> params) {
		String names = "";
		if (params == null)
			return names;
		Iterator<String> paramNames = params.keySet().iterator();
		while (paramNames.hasNext()) {
			String name = (String) paramNames.next();
			names = names + name;
			if (paramNames.hasNext())
				names = names + ",";
		}
		return "[" + names + "]";
	}

	private String getNameValuePairsString(HashMap<String, String> params) {
		String nameValues = "";
		if (params == null)
			return nameValues;
		Iterator<String> paramNames = params.keySet().iterator();
		while (paramNames.hasNext()) {
			String name = (String) paramNames.next();
			nameValues = "&" + name + "=" + params.get(name);
		}
		return nameValues;
	}

	public void closeConn() {
		_httpClient.getConnectionManager().shutdown();
		Log.d(TAG, "conn manager shut down");
	}

	private String _server;
	private int _port;
	private String _serviceBaseURL;
	private DefaultHttpClient _httpClient;
	private HttpHost _targetHost;

	private String _user;
	private String _pass;

	private static final String TAG = "RESTClient";

}
