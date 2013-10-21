package org.matin.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public class RequestGenerator {
	
	private RestTemplate restTemplate;
	private Gson gson;
	
	public RequestGenerator(){
		restTemplate = new RestTemplate();
		gson = new Gson();
	}
	
	public void sendRequest(MatINWriteableObject object){
		
		String jsonString = gson.toJson(object);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.APPLICATION_JSON ); 
		HttpEntity<String> request= new HttpEntity<String>( jsonString, headers );
		MatINWriteableObject returnedObject = restTemplate.postForObject( "http://localhost:8080/matIN/rest/"+object.getClass(), request, object.getClass() );
	}

}
