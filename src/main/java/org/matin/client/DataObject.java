/**
 * 
 */
package org.matin.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

/**
 * This class is the base class for all datasets in the MatIN database. This 
 * class can act as an entry to everything from an EBSD scan to a figure. At
 * its core it is really only a list of files uploaded and stored on the MatIN
 * server. However, subclasses of this class implement specific features for
 * different datasets.
 * 
 * @author Dave Turner
 *
 */
public class DataObject extends MatINWriteableObject {

	public DataObject() 
	{
		name = "";
		description = "";
	}
	
	/**
	 * Get the name of this material.
	 * 
	 * @return The name of this material.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this material.
	 * 
	 * @param name The name of this material.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the text description of the material.
	 * 
	 * @return The description of this material.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the text description of this material.
	 * 
	 * @param The description of this material.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Get the search keywords for this material.
	 * 
	 * @return The search keywords for this material.
	 */
	public List<String> getKeywords() { return keywords; }
	
	/**
	 * Set the list of search keywords for this material.
	 * 
	 * @param The search keywords for this material.
	 */
	public void setKeywords(List<String> words) { keywords = words; }
	
	/**
	 * Get the list of files associated with this data object.
	 * 
	 * @return The list of files associated with this data object.
	 */
	public List<String> getFiles() { return files; }
	
	/**
	 * Commit the data object to the MatIN database. This method will upload both the 
	 * meta-data and the files associated with this data object. Depending on the file
	 * size this method may take significant time to execute. 
	 */
	public void commit(MatIN dbObject)
	{
		// First, commit the various meta-data for this data object. We do this by
		// calling the super classes version of this method. This will not commit 
		// the files but it will ensure that this data object is created on the server
		// side.
		super.commit(dbObject);
		
		// The url better not be null, this means something went wrong and we didn't 
		// toss and exception in super.commit()
		assert(url != null);
		
		try
		{
			// Now that the data object exists on the server, lets upload the files.
			fileURLs.clear();
			for(String fileName: files)
			{
				String uploadUrl = url + "/files";
			    logger.debug("file upload request url [post]: " + uploadUrl);
				
				File file = new File(fileName);
				
				if(!file.exists())
					throw new FileNotFoundException();
				
				MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
			    mvm.add("file", file);
			   
				ResponseEntity<String> response = restTemplate.postForEntity(uploadUrl, mvm, String.class);
			    
			    //fileURLs.add(respEnt.getBody());
			    
			    
			}
		} 
		catch (HttpClientErrorException ex)
		{
			logger.error("File upload failed!", ex);
			logger.error("Response Body: ", ex.getResponseBodyAsString());
			throw new MatINException("File upload error!");
		}
		catch (FileNotFoundException fEx)
		{
			logger.error("Couldn't find file to upload!");
			throw new MatINException("Couldn't find file to upload!");
		}
		
			
	}
	
	protected String name;
	protected String description;
	protected List<String> keywords = new ArrayList<String>();
	
	/**
	 *  The list of files associated with this dataobject, these will be
	 *  posted to the server for archival.
	 */
	@JsonIgnore
	protected List<String> files = new ArrayList<String>();
	
	/**
	 * After the files have been posted to the matIN server, they will have their own 
	 * URLs.
	 */
	@JsonIgnore
	protected List<String> fileURLs = new ArrayList<String>();
	
}
