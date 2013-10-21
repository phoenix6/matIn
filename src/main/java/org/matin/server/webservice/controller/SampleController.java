/**
 * 
 */
package org.matin.server.webservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.matin.server.database.domain.MaterialDB;
import org.matin.server.database.domain.SampleDB;
import org.matin.client.MatINException;
import org.matin.client.Material;
import org.matin.client.Sample;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Joiner;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Dave Turner
 *
 */
public class SampleController extends MatINController {

	// The name of the materials index
		private final String SAMPLES_INDEX_NAME = "samples-fulltext";
		
		private final static Logger logger = LoggerFactory.getLogger(SampleController.class);
		
		@RequestMapping(value = "/samples", method=RequestMethod.POST, consumes="application/json")
		public @ResponseBody String commit(@RequestBody Sample sample,
				HttpServletRequest request, HttpServletResponse response){
	        
			try {
				
				// Create the vertex in the database
				SampleDB sampleDB = graphDBService.getFramedGraph().addVertex(null, SampleDB.class);
				Vertex v = sampleDB.asVertex();
				
				// Use ModelMapper to automatically map the equivalent data from the request object to the
				// database side object. This persists the values to the database.
				ModelMapper mapper = new ModelMapper();
				mapper.map(sample, sampleDB);
				
				// Add this vertex to the class index with class sample	
				graphDBService.addToFulltextIndex("class", "class", "sample", v);
			
		        // Generate fulltext search index entries for this sample. This will help us find this
				// node later.
		        graphDBService.addToFulltextIndex(SAMPLES_INDEX_NAME, "name", sample.getName(), v);
		        
		        // Remove punctuation characters from the the description string for the search index.
		        String descString = sample.getDescription().replaceAll("[^A-Za-z0-9]", " ");
		        graphDBService.addToFulltextIndex(SAMPLES_INDEX_NAME, "description", descString, v);
		        
		        String allKeywords = Joiner.on(" ").join(sample.getKeywords());
		        graphDBService.addToFulltextIndex(SAMPLES_INDEX_NAME, "keywords", allKeywords, v);
		       
		        // Make a url for this resource
		        String resourceURL = "samples/" + v.getId().toString();
		        
		        v.setProperty("url", resourceURL);
		        
		        // Commit the transaction to the database.
		        graphDBService.getFramedGraph().commit();
		        
		        // Return the URL for the sample
		        response.setStatus(HttpServletResponse.SC_CREATED);
		        
		        return makeUrl() + "/" + resourceURL;
		        
			} 
			
			catch(Exception ex)
			{
				throw new MatINException("Server side error!", ex);	
			}
			
		}

	}
