package net.sareweb.lifedroid.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import net.sareweb.lifedroid.model.DLFileEntry;
import net.sareweb.lifedroid.rest.generic.LDRESTClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonParser;

import android.util.Log;

public class DLFileEntryRESTClient extends LDRESTClient<DLFileEntry> {
	
	RestTemplate rt;
	
	public DLFileEntryRESTClient(ConnectionData connectionData) {
		super(connectionData);
		rt = new RestTemplate(requestFactory);
		rt.getMessageConverters().add(new GsonHttpMessageConverter());
	}
	
	public DLFileEntry getFileEntry(long fileEntryId) {
		String requestURL = getBaseURL() +"/get-file-entry";
		requestURL = addParamToRequestURL(requestURL, "file-entry-id", fileEntryId);
			
		return run(requestURL, HttpMethod.GET);
	}
	
	
	public DLFileEntry addFileEntry(DLFileEntry dlFileEntry, String fileFolderPath) {
		return addFileEntry(dlFileEntry, new File(fileFolderPath + "/" + dlFileEntry.getSourceFileName()));
	}
	
	public DLFileEntry addFileEntry(DLFileEntry dlFileEntry, File file){
		try {
			
			String requestURL = getBaseURL() +"/add-file-entry";
			HttpPost httppost = new HttpPost(requestURL);
			MultipartEntity reqEntity = new MultipartEntity();
			
			reqEntity.addPart("repositoryId", new StringBody(dlFileEntry.getRepositoryId().toString()));
			reqEntity.addPart("folderId", new StringBody(dlFileEntry.getFolderId().toString()));
			reqEntity.addPart("sourceFileName",new StringBody(dlFileEntry.getSourceFileName()));
			reqEntity.addPart("mimeType", new StringBody(dlFileEntry.getMimeType()));
			reqEntity.addPart("title",new StringBody( dlFileEntry.getSourceFileName()));
			reqEntity.addPart("description", new StringBody("a"));
			reqEntity.addPart("changeLog", new StringBody("a"));
			reqEntity.addPart("serviceContext", new StringBody("{}"));
			
			reqEntity.addPart("file", new FileBody(file));
			
			httppost.setEntity(reqEntity);
			
			 HttpResponse response = httpClient.execute(httppost);
			 HttpEntity resEntity = response.getEntity();
			 
	     	return getObjectFromJsonString(EntityUtils.toString(resEntity));
		} catch (Exception e) {
			Log.e(TAG, "Error adding file entry", e);
			return null;
		}
		
		/*String requestURL = getBaseURL() +"/add-file-entry";

		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
	
		parts.add("repository-id", dlFileEntry.getRepositoryId().toString());
		parts.add("folder-id", dlFileEntry.getFolderId().toString());
		parts.add("source-file-name",dlFileEntry.getSourceFileName());
		parts.add("mime-type", dlFileEntry.getMimeType());
		parts.add("title", dlFileEntry.getSourceFileName());
		parts.add("description", "a");
		parts.add("change-log", "a");
		parts.add("service-context", "{}");
		
		parts.add("file", file);
		
		String jsonString = rt.postForObject(requestURL, parts, String.class);
		
		return getObjectFromJsonString(jsonString);*/
	}
	
	public DLFileEntry updateFileEntry(DLFileEntry dlFileEntry, String fileFolderPath) {
		String requestURL = getBaseURL() +"/update-file-entry";
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		
		parts.add("fileEntryId", dlFileEntry.getFileEntryId().toString());
		parts.add("sourceFileName",dlFileEntry.getSourceFileName());
		parts.add("mimeType", dlFileEntry.getMimeType());
		parts.add("title", dlFileEntry.getSourceFileName());
		parts.add("description", "a");
		parts.add("changeLog", "a");
		parts.add("majorVersion", "false");
		parts.add("serviceContext", "{}");
		
		Resource fileResource = new FileSystemResource(fileFolderPath + "/" + dlFileEntry.getSourceFileName());
		parts.add("file", fileResource);
		
		String jsonString = rt.postForObject(requestURL, parts, String.class);
		
		return getObjectFromJsonString(jsonString);
	}
	
	public void deleteFileEntry(String fileEntryId) {
		String requestURL = getBaseURL() +"/delete-file-entry";

		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("fileEntryId", fileEntryId);
		
		try {
			rt.postForObject(requestURL, parts, String.class);
		} catch (Exception e) {
			Log.d(TAG, "Error deleting fileEntry", e);
		}
	}

	@Override
	public String getPorltetContext() {
		return "";
	}
	
	@Override
	public String getModelName() {
		return "dlapp";
	}

}
