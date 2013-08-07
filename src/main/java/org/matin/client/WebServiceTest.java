package org.matin.client;

import org.matin.server.webservice.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class WebServiceTest {
	
	@Autowired
	private static RestTemplate restTemplate = new RestTemplate();
	 
	 public static void main(String[] args){
		  String jsonString = "{"   
			        + "\"name\":\"Fe\"" + ","  
			        + "\"description\":\"sample\""  
			        + "}";  
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType( MediaType.APPLICATION_JSON ); 
		  HttpEntity request= new HttpEntity( jsonString, headers );
		  
		  System.out.println("*********************************");
		  System.out.println(jsonString);
		  Material returnedMaterial = restTemplate.postForObject( "http://localhost:8080/matIN/rest/material", request, Material.class );
		  
		  System.out.println(returnedMaterial.getName());
	/* 
		 Material material = new Material();
		 material.setName("Al");
		 material.setDescription("This is just an example");
		 List<String> img = new ArrayList<String>();
		 img.add("test.jpg");
		 img.add("sample.jpg");
		 List<String> ref = new ArrayList<String>();
		 ref.add("www");
		 ref.add("org");
		 material.setImageURLs(img);
		 material.setReferenceURLs(ref);
		 
		 Gson gson = new Gson();
		 String s = gson.toJson(material);
		 System.out.println(s);
		 
		 */
		 
	 }

}
