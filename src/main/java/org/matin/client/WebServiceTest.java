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
			testMat.setDescription("Applications: Blades, discs, rings, airframe, fasteners, components. Vessels, cases, hubs, forgings.. Biomedical implants. " +
								   "Biocompatibility: Excellent, especially when direct contact with tissue or bone is required. Ti-6Al-4V's poor shear strength " + 
								   "makes it undesirable for bone screws or plates. It also has poor surface wear properties and tends to seize when in sliding " + 
								   "contact with itself and other metals. Surface treatments such as nitriding and oxidizing can improve the surface wear properties.");
			
			
			testMat.commit(connectionMatIN);
			
			// Define a material for our sample or dataset.
			Material testMat2 = new Material();
			
			// Set the material's names
			testMat2.setName("Aluminum 1060-O");
			
			// Add component elements (if they are known)
			testMat2.addComponentElement(Element.Al, 99.6, 100.0);
			testMat2.addComponentElement(Element.Cu, 0.0, 0.05);
			testMat2.addComponentElement(Element.Fe, 0.0, 0.35);
			testMat2.addComponentElement(Element.Mg, 0.0, 0.030);
			testMat2.addComponentElement(Element.Mn, 0.0, 0.030);
			testMat2.addComponentElement(Element.Si, 0.0, 0.030);
			
			// Add search tags or keywords for this material
			testMat2.getKeywords().add("UNS A91060");
			testMat2.getKeywords().add("AA1060-O");
			
			// Add reference URLs
			testMat2.getReferenceURLs().add("http://www.matweb.com/search/DataSheet.aspx?MatGUID=896dfdfb65834ca8aa8b0fcc6a7efcc4&ckck=1");
			
			// Add a description
			testMat2.setDescription("Data points with the AA note have been provided by the Aluminum Association, Inc. and are NOT FOR DESIGN. \n" +
								    "Composition Notes: \n" + 	 
								    "The aluminum content for unalloyed aluminum not made by a refining process is the difference between 100.00 percent and the sum of all other analyzed metallic elements present in amounts of 0.010 percent of more each, expressed to the second decimal before determining the sum. For alloys and unalloyed aluminum not made by a refining process, when the specified maximum limit is 0.XX, an observed value or a calculated value greater than 0.005 but less than 0.010% is rounded off and shown as less than 0.01% " +
								    "Composition information provided by the Aluminum Association and is not for design.");
			
			testMat2.commit(connectionMatIN);
		
			testMat2.setDescription("This is changed");
			
			testMat2.commit(connectionMatIN);
			
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
			
		 
	 }

}
