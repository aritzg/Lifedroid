package net.sareweb.lifedroid.liferay.service.generic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.net.URI;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.google.gson.GsonBuilder;

import net.sareweb.lifedroid.model.LDObject;
import net.sareweb.lifedroid.model.User;
import net.sareweb.lifedroid.util.LDConstants;

public abstract class LDRESTService<T extends LDObject> {

	public LDRESTService() {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(LDConstants.LIFERAY_SERVER_URL,
						Integer.parseInt(LDConstants.LIFERAY_SERVER_PORT)),
				new UsernamePasswordCredentials(LDConstants.LIFERAY_USER, LDConstants.LIFERAY_PASSWORD));

		requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		
		composeServiceURI();
	}

	private void composeServiceURI() {
		_serviceURI = LDConstants.LIFERAY_SERVER_PROTOCOL +"://" + LDConstants.LIFERAY_SERVER_URL + ":" + LDConstants.LIFERAY_SERVER_PORT + LDConstants.LIFERAY_SERVER_SERVICE_PATH;
		
	}
	
	protected T runGET(String requestURL){
		try {
        	ClientHttpResponse response =   requestFactory.createRequest(new URI(requestURL), HttpMethod.GET).execute();
        	Writer writer = new StringWriter();
        	char[] buffer = new char[1024];
        	Reader reader = new BufferedReader(
        	new InputStreamReader(response.getBody(), "UTF-8"));
        	int n;
        	while ((n = reader.read(buffer)) != -1) {
        		writer.write(buffer, 0, n);
        	}
        	Log.d(TAG,writer.toString());
        	GsonBuilder gsonBuilder = new GsonBuilder(); 
        	return gsonBuilder.create().fromJson(writer.toString(), getTypeArgument());
		} catch (Exception e) {
			Log.d(TAG,"Error running get", e);
			return null;
		} 
        
	}
	
	private Class<T> getTypeArgument(){
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class)parameterizedType.getActualTypeArguments()[0];
	}

	protected HttpComponentsClientHttpRequestFactory requestFactory;
	protected String _serviceURI;
	protected String TAG = "LDRESTService";

}
