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
	 
	public static void main(String[] args){
		  
		// Connect to the database
		try {
			MatIN connectionMatIN = MatINConnectionFactory.MatINConnect("http://localhost:8080", "dmt36", "password");
		
			// Define a material for our sample or dataset.
			Material testMat = new Material();
			
			// Set the material's names
			testMat.setName("Titanium Ti-6Al-4V (Grade 5), STA");
			
			// Add component elements (if they are known)
			testMat.addComponentElement(Element.Ti, 87.725, 91.0);
			testMat.addComponentElement(Element.V, 3.5, 4.5);
			testMat.addComponentElement(Element.Al, 5.5, 6.75);
			testMat.addComponentElement(Element.C, 0.0, 0.08);
			testMat.addComponentElement(Element.H, 0.0, 0.015);
			testMat.addComponentElement(Element.Fe, 0.0, 0.4);
			testMat.addComponentElement(Element.N, 0.0, 0.03);
			testMat.addComponentElement(Element.O, 0.0, 0.02);
			
			// Add search tags or keywords for this material
			testMat.getKeywords().add("Ti-6-4");
			testMat.getKeywords().add("UNS R56400");
			testMat.getKeywords().add("ASTM Grade 5 titanium");
			testMat.getKeywords().add("Ti6Al4V");
			testMat.getKeywords().add("biomedical implants");
			
			// Add reference URLs
			testMat.getReferenceURLs().add("http://www.matweb.com/search/datasheet.aspx?MatGUID=b350a789eda946c6b86a3e4d3c577b39");
			
			// Add a description
			testMat.setDescription("Applications: Blades, discs, rings, airframe, fasteners, components. Vessels, cases, hubs, forgings.. Biomedical implants.\n" +
								   "Biocompatibility: Excellent, especially when direct contact with tissue or bone is required. Ti-6Al-4V's poor shear strength " + 
								   "makes it undesirable for bone screws or plates. It also has poor surface wear properties and tends to seize when in sliding " + 
								   "contact with itself and other metals. Surface treatments such as nitriding and oxidizing can improve the surface wear properties.");
			
			
			testMat.commit(connectionMatIN);
		
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
			
		 
	 }

}
