package org.matin.server.webservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.matin.server.database.domain.MaterialDB;
import org.matin.client.MatINErrorCode;
import org.matin.client.MatINException;
import org.matin.client.Material;
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

@Controller
public class MaterialController extends MatINController {

	// The name of the materials index
	private final String INDEX_NAME = "materials-fulltext";
	
	private final static Logger logger = LoggerFactory.getLogger(MaterialController.class);
	
	/**
	 * Create a material in the database.
	 * 
	 * @param material The material to create.
	 * @param request
	 * @param response
	 * @return The location of the resource.
	 */
	@RequestMapping(value = "/materials", method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody String commit(@RequestBody Material material,
			HttpServletRequest request, HttpServletResponse response){
        
		try {
			
			// Create the vertex in the database
			MaterialDB materialDB = graphDBService.create(material, MaterialDB.class);
			Vertex v = materialDB.asVertex();
			
			// Add this vertex to the class index with class material	
			graphDBService.addToFulltextIndex("class", "class", "material", v);
		
			// Add the material to the index
			addMaterialToIndex(materialDB);
			
	        // Commit the transaction to the database.
	        graphDBService.getFramedGraph().commit();
	        
	        // Return the URL for the material
	        response.setStatus(HttpServletResponse.SC_CREATED);
	        
	        return makeUrl() + "/" + materialDB.getURL();
	        
		} 
		
		catch(Exception ex)
		{
			logger.error("Server side error!", ex);
			
			// Set the response
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		       
			throw new MatINException("Material creation failed!", MatINErrorCode.MATERIAL_NOT_FOUND.ordinal());
			
		}
		
	}

	/**
	 * Add a material to the vertex indices.
	 * 
	 * @param material The material to add.
	 */
	private void addMaterialToIndex(MaterialDB material) {
		
		Vertex v = material.asVertex();
		
		// Generate fulltext search index entries for this material. This will help us find this
		// node later.
        graphDBService.addToFulltextIndex(INDEX_NAME, "name", material.getName(), v);
        
        // Remove punctuation characters from the the description string for the search index.
        String descString = material.getDescription().replaceAll("[^A-Za-z0-9]", " ");
        graphDBService.addToFulltextIndex(INDEX_NAME, "description", descString, v);
        
        String allKeywords = Joiner.on(" ").join(material.getKeywords());
        graphDBService.addToFulltextIndex(INDEX_NAME, "keywords", allKeywords, v);
        
        String allElements = Joiner.on(" ").join(material.getComponentElements());
        graphDBService.addToFulltextIndex(INDEX_NAME, "componentElements", allElements, v);		
	
	}

	/**
	 * Get a material  from the database based on its id.
	 * 
	 * @param id The numeric id for this material
 	 * @param request
	 * @param response
	 * @return The material found.
	 */
	@RequestMapping(value = "/materials/{id}", method=RequestMethod.GET)
	public @ResponseBody Material get(@PathVariable int id,
			HttpServletRequest request, HttpServletResponse response){
        
		try {
			
			// Get the material from the database.
			Material material = graphDBService.get(id, Material.class, MaterialDB.class);
			
			// Set the response
	        response.setStatus(HttpServletResponse.SC_FOUND);
	        
	        return material;
	        
		} 
		
		catch(Exception ex)
		{
			logger.error("Server side error!", ex);
			
			// Set the response
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		       
			throw new MatINException("Server side error!", MatINErrorCode.INTERNAL_SERVER_ERROR.ordinal());	
		}
		
	}
	
	/**
	 * Modify a material that already exists in the database.
	 * 
	 * @param id The id of the material to modify.
	 * @param material The new material object to post.
	 * @param request
	 * @param response
	 * @return The updated material object.
	 */
	@RequestMapping(value = "/materials/{id}", method=RequestMethod.POST)
	public @ResponseBody Material post(@PathVariable int id, @RequestBody Material material,
			HttpServletRequest request, HttpServletResponse response){
        
		try {
			
			MaterialDB materialDB = graphDBService.modify(id, material, MaterialDB.class);
			
			// Now remove this vertex from the database indices and re-add so we update the indices
			graphDBService.removeFromFulltextIndex(INDEX_NAME, materialDB.asVertex());
			addMaterialToIndex(materialDB);
		
			graphDBService.getFramedGraph().commit();
			
			// Set the response
	        response.setStatus(HttpServletResponse.SC_FOUND);
	        
	        return material;
	        
		} 
		
		catch(Exception ex)
		{
			logger.error("Server side error!", ex);
			
			// Set the response
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		       
			throw new MatINException("Server side error!", MatINErrorCode.INTERNAL_SERVER_ERROR.ordinal());	
		}
		
	}
	
	/**
	 * Search for materials in the database. The query string can take the following form
	 * 
	 * @param queryString
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/materials/search/{queryString}", method=RequestMethod.GET)
	public @ResponseBody List<String> search(
			@PathVariable String queryString,
			HttpServletRequest request, HttpServletResponse response){
		
		List<String> urls = new ArrayList<String>();
		
		try {
			
			// Search for the materials using the query string		
			List<Vertex> vs = graphDBService.queryFulltextIndex(INDEX_NAME, queryString);
			
			for(Vertex v : vs)
				urls.add(makeUrl() + "/" + (String)v.getProperty("url"));
			
			// Set the response
	        response.setStatus(HttpServletResponse.SC_FOUND);
	        
	        return urls;
	        
		} 
		
		catch(Exception ex)
		{
			 response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			 
			 return urls;
		}
		
	}

	
}
