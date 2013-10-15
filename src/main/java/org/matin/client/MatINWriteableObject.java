package org.matin.client;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class MatINWriteableObject {
	
	@Autowired
	protected static RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * This method commits the MatINWritableObject to a remote database.
	 *  
	 * @param dbObject The database to commit this object to.
	 */
	public void commit(MatIN dbObject)
		throws MatINException
	{ 
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			// Turn the object into JSON
			String jsonString = mapper.writeValueAsString(this);
			
			// Setup the HTTP headers for the request
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType( MediaType.APPLICATION_JSON ); 
			HttpEntity request= new HttpEntity( jsonString, headers );
			
			String requestURL = dbObject.getDatabaseURL() + "/matIN/rest/" + this.getClass().getSimpleName();
			System.out.println("commit: " + this.getClass().getSimpleName());
			System.out.println("\t" + requestURL);
			System.out.println("\t" + jsonString);
			
			restTemplate.postForObject( requestURL, request, Material.class );  
			
		} catch (HttpClientErrorException ex) {		
			try {
				MatINError serverError = mapper.readValue(ex.getResponseBodyAsString(), MatINError.class);
				throw new MatINException(serverError.getMessage());
			} catch(Exception e) {
				e.printStackTrace();
				//throw new MatINException("Improperly formatted error response from MatIN server!");
			}
		} catch (Exception ex) {
			throw new MatINException(ex.getMessage());
		}
	}

}
