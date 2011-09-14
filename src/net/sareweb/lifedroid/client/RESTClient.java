package net.sareweb.lifedroid.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
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
		_httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(_targetHost.getHostName(), _targetHost.getPort()),
				new UsernamePasswordCredentials(user, pass));
	}
	
	
	public String runRequest(List<NameValuePair> params){
		
		BasicHttpContext ctx = new BasicHttpContext(); 
        HttpPost post = new HttpPost(_serviceBaseURL);
        
        UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(params, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        post.setEntity(entity);
        
        Log.d(TAG,"aaaaaa");
        try {
			HttpResponse resp = _httpClient.execute(_targetHost, post, ctx);
			
		
			
			return EntityUtils.toString(resp.getEntity());
		} catch (ClientProtocolException e) {
			Log.d(TAG,"1");
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(TAG,"2");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Log.d(TAG,"3");
			e.printStackTrace();
		} catch (Exception e) {
			Log.d(TAG,"4");
			e.printStackTrace();
		}
        Log.d(TAG,"bbbbbb");
        return null;

	}
	


public void  closeConn(){
	_httpClient.getConnectionManager().shutdown();
}
	

	private String _server;
	private int _port;
	private String _serviceBaseURL;
	private DefaultHttpClient _httpClient;
	private HttpHost _targetHost;
	
	private static final String TAG = "RESTClient";

}
