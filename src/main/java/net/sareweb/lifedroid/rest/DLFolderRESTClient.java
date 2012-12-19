package net.sareweb.lifedroid.rest;

import net.sareweb.lifedroid.model.DLFolder;
import net.sareweb.lifedroid.rest.generic.LDRESTClient;

import org.springframework.http.HttpMethod;

public class DLFolderRESTClient extends LDRESTClient<DLFolder> {

	public DLFolderRESTClient(ConnectionData connectionData) {
		super(connectionData);
	}
	
	
	public DLFolder addFolder(DLFolder dLFolder) {
		String requestURL = getBaseURL() +"/add-folder/";
		
		requestURL = addParamToRequestURL(requestURL, "group_id", dLFolder.getGroupId());
		requestURL = addParamToRequestURL(requestURL, "repository-id", dLFolder.getRepositoryId());
		requestURL = addParamToRequestURL(requestURL, "mount-point", false);
		requestURL = addParamToRequestURL(requestURL, "parent-polder-id", dLFolder.getParentFolderId());
		requestURL = addParamToRequestURL(requestURL, "name", dLFolder.getName(), true);
		requestURL = addParamToRequestURL(requestURL, "description", dLFolder.getDescription(),true);
		requestURL = addParamToRequestURL(requestURL, "service-context", "{}");
		
		return run(requestURL, HttpMethod.POST);
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
