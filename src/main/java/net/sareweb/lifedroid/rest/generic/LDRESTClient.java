package net.sareweb.lifedroid.rest.generic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.URLEncoder;
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

import net.sareweb.lifedroid.model.User;
import net.sareweb.lifedroid.model.generic.LDObject;
import net.sareweb.lifedroid.rest.ConnectionData;
import net.sareweb.lifedroid.util.LDConstants;

public abstract class LDRESTClient<T extends LDObject> {

	public LDRESTClient(ConnectionData connectionData){
		this.connectionData=connectionData;
		httpClient = new DefaultHttpClient();

		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(connectionData.getServerURL(),
						Integer.parseInt(connectionData.getPort())),
				new UsernamePasswordCredentials(connectionData.getUser(), connectionData.getPass()));

		requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		
	}

	public String getBaseURL() {
		String baseURL = connectionData.getProtocol() +
				"://" + connectionData.getServerURL() + 
				":" + connectionData.getPort();
		if(!getPorltetContext().equals("")){
			baseURL = baseURL + "/" + getPorltetContext();
		}
		baseURL = baseURL + LDConstants.LIFERAY_JSON_WS_PATH;
		if(!getModelName().equals("")){
			baseURL = baseURL + "/" + getModelName();
		}
		return baseURL;
	}
	
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
        	return getObjectFromJsonString(writer.toString());
		} catch (Exception e) {
			Log.d(TAG,"Error running get", e);
			return null;
		} 
	}
	
	protected Object runForObject(String requestURL, HttpMethod method, Class clazz){
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
        	return getObjectFromJsonString(writer.toString(), clazz);
		} catch (Exception e) {
			Log.d(TAG,"Error running get", e);
			return null;
		} 
	}
	
	protected boolean runForBoolean(String requestURL, HttpMethod method){
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
        	return new Boolean(writer.toString());
		} catch (Exception e) {
			Log.d(TAG,"Error running get", e);
			return false;
		} 
	}
	
	protected double runForDouble(String requestURL, HttpMethod method){
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
        	return new Double(writer.toString());
		} catch (Exception e) {
			Log.d(TAG,"Error running get", e);
			return 0.0;
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
        	JsonParser parser = new JsonParser();
        	
        	List<T> result = new ArrayList<T>();
        	String jsonString = writer.toString();
        	if(jsonString.equals("{}"))return result;
        	
        	JsonArray array = parser.parse(jsonString).getAsJsonArray();
        	
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
	
	protected List getList(String requestURL, HttpMethod method, Class clazz){
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
        	JsonParser parser = new JsonParser();
        	
        	List result = new ArrayList();
        	String jsonString = writer.toString();
        	if(jsonString.equals("{}"))return result;
        	
        	JsonArray array = parser.parse(jsonString).getAsJsonArray();
        	
        	
        	if(array!=null){
        		for (int i = 0; i<array.size(); i++) {
            		result.add(getObjectFromJsonString(array.get(i).toString(),clazz));
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
	
	protected String addParamToRequestURL(String requestURL, String paramName, Object value, boolean encode){
		if(encode)
			return addParamToRequestURL(requestURL, paramName, encode(value.toString()));
		else
			return addParamToRequestURL(requestURL, paramName, value);
	}

	protected T getObjectFromJsonString(String jsonString){
		GsonBuilder gsonBuilder = new GsonBuilder(); 
		return gsonBuilder.create().fromJson(jsonString, getTypeArgument());
	}
	
	protected Object getObjectFromJsonString(String jsonString, Class clazz){
		GsonBuilder gsonBuilder = new GsonBuilder(); 
		return gsonBuilder.create().fromJson(jsonString, clazz);
	}
	
	private String encode(String text){
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "Error encoding text", e);
			return null;
		}
	}
	
	public abstract String getPorltetContext();
	public abstract String getModelName();
	
	protected DefaultHttpClient httpClient;
	protected HttpComponentsClientHttpRequestFactory requestFactory;
	protected ConnectionData connectionData;
	protected String TAG = "LDRESTService";

}
