package org.matin.client;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.matin.client.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class WebServiceTest {
	
	@Autowired
	private static RestTemplate restTemplate = new RestTemplate();
	 
	public static void main(String[] args){
		  
		Material testMat = new Material("Fe", "sample description");
		 
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(testMat);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType( MediaType.APPLICATION_JSON ); 
			HttpEntity request= new HttpEntity( jsonString, headers );
			  
			System.out.println("*********************************");
			System.out.println(jsonString);
			Material returnedMaterial = restTemplate.postForObject( "http://localhost:8080/matIN/rest/material", request, Material.class );
			  
			System.out.println(returnedMaterial.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	 }

}
