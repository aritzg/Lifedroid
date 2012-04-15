package net.sareweb.lifedroid.liferay.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;

import net.sareweb.lifedroid.liferay.service.generic.LDRESTService;
import net.sareweb.lifedroid.model.User;
import net.sareweb.lifedroid.util.LDConstants;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.google.gson.GsonBuilder;

public class UserRESTService extends LDRESTService<User> {

	public UserRESTService() {
		super();
	}

	public User getUserById(String userId) {
		String requestURL = _serviceURI + "/user/get-user-by-id/userId/" + userId;
		return runGET(requestURL);
	}
	
	public User getUserByEmailAddress(String emailAddress) {
		String requestURL = _serviceURI + "/user/get-user-by-email-address/companyId/" + LDConstants.LIFERAY_COMPANY_ID + "/emailAddress/" + emailAddress;
		Log.d(TAG, "Invoking GET " + requestURL);
		return runGET(requestURL);
	}

}
