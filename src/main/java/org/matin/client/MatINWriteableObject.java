package org.matin.client;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonFilter("myFilter")
public class MatINWriteableObject {
	
	/**
	 * The url for this resource 
	 */
	@JsonProperty
	protected String url;
	
	protected static RestTemplate restTemplate = new RestTemplate();
	
	private final static Logger logger = LoggerFactory.getLogger(MatINWriteableObject.class);

	/**
	 * When was this object created on the server side.
	 */
	@JsonProperty
	Date creationDate;
	
	/**
	 * When was this object last committed to the database
	 */
	@JsonProperty
	Date lastModifiedDate;	
	
	/**
	 * The owner of this object, who commited it to the database.
	 */
	@JsonProperty
	String owner;
	
	/**
	 * This method commits the MatINWritableObject to a remote database.
	 *  
	 * @param dbObject The database to commit this object to.
	 */
	public void commit(MatIN dbObject)
		throws MatINException
	{ 
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(Feature.INDENT_OUTPUT);
		
		try {
			
			FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter",
					SimpleBeanPropertyFilter.serializeAllExcept("url", "creationDate", "lastModifiedDate", "owner"));
			
			// Turn the object into JSON
			String jsonString = mapper.writer(filters).writeValueAsString(this);
			
			// Setup the HTTP headers for the request
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType( MediaType.APPLICATION_JSON ); 
			HttpEntity<String> request= new HttpEntity<String>( jsonString, headers );
			
			// If the url is empty then we are creating this object for the first time.
			if(url == null)
			{	
				// The general practice will for puts will be to take the class name and lowercase it, then make it plural
				String requestURL = dbObject.getDatabaseURL() + "/matIN/rest/" + this.getClass().getSimpleName().toLowerCase() + "s";
			
				logger.info("post [create]: " + this.getClass().getSimpleName());
				logger.info(requestURL);
				logger.info(jsonString);
				
				String result = restTemplate.postForObject( requestURL, request, String.class);  
				url = result;
		
				logger.info("result: " + result);			
			} else { // The object exists already, post for a change
				logger.info("post [update]: " + this.getClass().getSimpleName());
				logger.info(jsonString);
				logger.info(url);
						
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
				
				// The returned object needs to be copied to this object so it reflects any
				// changes to fields done on the server.
				ObjectMapper oMapper = new ObjectMapper();
				oMapper.readerForUpdating(this).readValue(response.getBody());
				
				// Turn off the JSON filter, I want to see it all.
				filters = new SimpleFilterProvider().addFilter("myFilter",
						SimpleBeanPropertyFilter.serializeAllExcept());
				
				logger.info("result: \n" + mapper.writer(filters).writeValueAsString(this));				
			}
			
			
		} catch (HttpClientErrorException ex) {		
			MatINResult serverError;
			try {
				logger.info(ex.getResponseBodyAsString());
				serverError = mapper.readValue(ex.getResponseBodyAsString(), MatINResult.class);
			} catch(Exception e) {
				throw new MatINException("Improperly formatted error response from MatIN server!", e);
			}
			throw new MatINException("Failed to commit material!", serverError.serverSideException);
		} catch (Exception ex) {
			throw new MatINException("Failed to commit material.", ex);
		}
	}
	
	/**
	 * Get the date this object was created in the MatIN database
	 * 
	 * @return The objects creation date.
	 */
	Date getCreationDate() { return creationDate; }
	
	/**
	 * Get the last time this object was modified in the MatIN database.
	 * 
	 * @return The date the object was last modified.
	 */
	Date getLastModifiedDate() { return lastModifiedDate; }
	
}
