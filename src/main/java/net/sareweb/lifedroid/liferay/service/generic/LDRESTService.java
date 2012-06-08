package net.sareweb.lifedroid.liferay.service.generic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import net.sareweb.lifedroid.model.LDObject;
import net.sareweb.lifedroid.model.User;
import net.sareweb.lifedroid.util.LDConstants;

public abstract class LDRESTService<T extends LDObject> {

	public LDRESTService(String emailAddress, String password){

		DefaultHttpClient httpClient = new DefaultHttpClient();

		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(LDConstants.LIFERAY_SERVER_URL,
						Integer.parseInt(LDConstants.LIFERAY_SERVER_PORT)),
				new UsernamePasswordCredentials(emailAddress, password));

		requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		
		setPorltetContext();
		composeServiceURI();
	}

	private void composeServiceURI() {
		_serviceURI = LDConstants.LIFERAY_SERVER_PROTOCOL +
				"://" + LDConstants.LIFERAY_SERVER_URL + 
				":" + LDConstants.LIFERAY_SERVER_PORT;
		if(!_portletContext.equals("")){
			_serviceURI = _serviceURI + "/" + _portletContext;
		}
		_serviceURI = _serviceURI + LDConstants.LIFERAY_SERVER_SERVICE_PATH;
		
	}
	
	public abstract void setPorltetContext();
	
	protected T run(String requestURL, HttpMethod method){
		try {
        	ClientHttpResponse response =   requestFactory.createRequest(new URI(requestURL), method).execute();
        	Writer writer = new StringWriter();
        	char[] buffer = new char[1024];
        	Reader reader = new BufferedReader(
        	new InputStreamReader(response.getBody(), "UTF-8"));
        	int n;
        	while ((n = reader.read(buffer)) != -1) {
        		writer.write(buffer, 0, n);
        	}
        	Log.d(TAG,writer.toString());
        	return getObjectFromJsonString(writer.toString());
		} catch (Exception e) {
			Log.d(TAG,"Error running get", e);
			return null;
		} 
	}
	
	protected List<T> getList(String requestURL, HttpMethod method){
		try {
        	ClientHttpResponse response =   requestFactory.createRequest(new URI(requestURL), method).execute();
        	Writer writer = new StringWriter();
        	char[] buffer = new char[1024];
        	Reader reader = new BufferedReader(
        	new InputStreamReader(response.getBody(), "UTF-8"));
        	int n;
        	while ((n = reader.read(buffer)) != -1) {
        		writer.write(buffer, 0, n);
        	}
        	Log.d(TAG,writer.toString());
        	JsonParser parser = new JsonParser();
        	
        	JsonArray array = parser.parse(writer.toString()).getAsJsonArray();
        	
        	List<T> result = new ArrayList<T>();
        	if(array!=null){
        		for (int i = 0; i<array.size(); i++) {
            		result.add(getObjectFromJsonString(array.get(i).toString()));
    			}
        	}
        	
        	return result;
        	
		} catch (Exception e) {
			Log.d(TAG,"Error running getList", e);
			return null;
		} 
	}
	
	private Class<T> getTypeArgument(){
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class)parameterizedType.getActualTypeArguments()[0];
	}
	
	protected String addParamToRequestURL(String requestURL, String paramName, Object value){
		String newRequestURL = requestURL;
		if(value==null || value.toString().equals(""))  return requestURL + "/-" + paramName;
		else{
			if(value instanceof Date) {
				newRequestURL = newRequestURL + "/" + paramName + "/" + ((Date)value).getTime();
			}
			else  {
				newRequestURL = newRequestURL + "/" + paramName + "/" + value.toString();
			} 
		}
		return newRequestURL;
	}

	protected T getObjectFromJsonString(String jsonString){
		GsonBuilder gsonBuilder = new GsonBuilder(); 
		return gsonBuilder.create().fromJson(jsonString, getTypeArgument());
	}
	
	protected HttpComponentsClientHttpRequestFactory requestFactory;
	protected String _serviceURI;
	protected String _portletContext="";
	protected String TAG = "LDRESTService";

}
