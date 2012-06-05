package net.sareweb.lifedroid.liferay.service;

import java.io.File;

import net.sareweb.lifedroid.liferay.service.generic.LDRESTService;
import net.sareweb.lifedroid.model.DLFileEntry;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.net.Uri;
import android.webkit.MimeTypeMap;

public class DLFileEntryRESTService extends LDRESTService<DLFileEntry> {

	public DLFileEntryRESTService(String emailAddress, String password) {
		super(emailAddress, password);
	}

	public DLFileEntry addFileEntry(DLFileEntry dlFileEntry) {
		RestTemplate rt = new RestTemplate(requestFactory);
		String requestURL = _serviceURI
				+ "/dlapp/add-file-entry/-serviceContext";

		File file = new File(dlFileEntry.getSourceFileName());
		Resource fileResource = new FileSystemResource(file);
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		Uri fileUri = Uri.fromFile(file);
		String fileExtension = MimeTypeMap.getSingleton()
				.getFileExtensionFromUrl(fileUri.toString());
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				fileExtension);

		parts.add("repositoryId", dlFileEntry.getRepositoryId());
		parts.add("folderId", dlFileEntry.getFileEntryId());
		parts.add("sourceFileName", dlFileEntry.getSourceFileName());
		parts.add("mimeType", mimeType);
		parts.add("title", null);
		parts.add("description", "");
		parts.add("changeLog", "");
		parts.add("serviceContext", "{}");
		parts.add("file", fileResource);

		rt.postForLocation(requestURL, parts);
		return null;
	}

	@Override
	public void setPorltetContext() {

	}

}
