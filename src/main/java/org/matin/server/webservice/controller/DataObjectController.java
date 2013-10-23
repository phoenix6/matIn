/**
 * 
 */
package org.matin.server.webservice.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.matin.client.MatINErrorCode;
import org.matin.client.MatINException;
import org.matin.client.DataObject;
import org.matin.client.DataObject;
import org.matin.server.database.domain.DataObjectDB;
import org.matin.server.database.domain.DataObjectDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Joiner;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Dave Turner
 *
 */
@Controller
public class DataObjectController extends MatINController {

	// The name of the dataobjects index
	private final String INDEX_NAME = "dataobjects-fulltext";
	
	private final static Logger logger = LoggerFactory.getLogger(DataObjectController.class);
	
	/**
	 * Create a dataobject in the database.
	 * 
	 * @param dataobject The dataobject to create.
	 * @param request
	 * @param response
	 * @return The location of the resource.
	 */
	@RequestMapping(value = "/dataobjects", method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody String commit(@RequestBody DataObject dataobject,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
			
			// Create the vertex in the database
			DataObjectDB dataobjectDB = graphDBService.create(dataobject, DataObjectDB.class);
			Vertex v = dataobjectDB.asVertex();
		
			// Add the data directory for this data object
			dataobjectDB.setDataDir(v.getId().toString());
			
			// Add this vertex to the class index with class dataobject	
			graphDBService.addToFulltextIndex("class", "class", "dataobject", v);
		
			// Add the dataobject to the index
			addDataObjectToIndex(dataobjectDB);
		
	        // Commit the transaction to the database.
	        graphDBService.getFramedGraph().commit();
	        
	        // Return the URL for the dataobject
	        response.setStatus(HttpServletResponse.SC_CREATED);
	        
	        return makeUrl() + "/" + dataobjectDB.getURL();
	        
		} 
		
		catch(Exception ex)
		{
			logger.error("Server side error!", ex);
			
			// Set the response
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		       
			throw new MatINException("DataObject creation failed!", MatINErrorCode.DATAOBJECT_NOT_FOUND.ordinal());
			
		}
		
	}

	/**
	 * Add a dataobject to the vertex indices.
	 * 
	 * @param dataobject The dataobject to add.
	 */
	private void addDataObjectToIndex(DataObjectDB dataobject) {
		
		Vertex v = dataobject.asVertex();
		
		// Generate fulltext search index entries for this dataobject. This will help us find this
		// node later.
        graphDBService.addToFulltextIndex(INDEX_NAME, "name", dataobject.getName(), v);
        
        // Remove punctuation characters from the the description string for the search index.
        String descString = dataobject.getDescription().replaceAll("[^A-Za-z0-9]", " ");
        graphDBService.addToFulltextIndex(INDEX_NAME, "description", descString, v);
        
        String allKeywords = Joiner.on(" ").join(dataobject.getKeywords());
        graphDBService.addToFulltextIndex(INDEX_NAME, "keywords", allKeywords, v);
        	
	}

	/**
	 * Get a dataobject  from the database based on its id.
	 * 
	 * @param id The numeric id for this dataobject
 	 * @param request
	 * @param response
	 * @return The dataobject found.
	 */
	@RequestMapping(value = "/dataobjects/{id}", method=RequestMethod.GET)
	public @ResponseBody DataObject get(@PathVariable int id,
			HttpServletRequest request, HttpServletResponse response){
        
		try {
			
			// Get the dataobject from the database.
			DataObject dataobject = graphDBService.get(id, DataObject.class, DataObjectDB.class);
			
			// Set the response
	        response.setStatus(HttpServletResponse.SC_FOUND);
	        
	        return dataobject;
	        
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
	 * Modify a dataobject that already exists in the database.
	 * 
	 * @param id The id of the dataobject to modify.
	 * @param dataobject The new dataobject object to post.
	 * @param request
	 * @param response
	 * @return The updated dataobject object.
	 */
	@RequestMapping(value = "/dataobjects/{id}", method=RequestMethod.POST)
	public @ResponseBody DataObject post(@PathVariable int id, @RequestBody DataObject dataobject,
			HttpServletRequest request, HttpServletResponse response){
        
		try {
			
			DataObjectDB dataobjectDB = graphDBService.modify(id, dataobject, DataObjectDB.class);
			
			// Now remove this vertex from the database indices and re-add so we update the indices
			graphDBService.removeFromFulltextIndex(INDEX_NAME, dataobjectDB.asVertex());
			addDataObjectToIndex(dataobjectDB);
		
			graphDBService.getFramedGraph().commit();
			
			// Set the response
	        response.setStatus(HttpServletResponse.SC_FOUND);
	        
	        return dataobject;
	        
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
	 * Add a file to a dataobject that already exists in the database.
	 * 
	 * @param id The id of the dataobject to modify.
	 * @param dataobject The new dataobject object to post.
	 * @param request
	 * @param response
	 * @return The updated dataobject object.
	 */
	@RequestMapping(value = "/dataobjects/{id}/files", method=RequestMethod.POST)
	public @ResponseBody DataObject addFile(
			@PathVariable int id, @RequestParam MultipartFile file,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
		
			String DATAOBJECT_DIR = graphDBService.getDBDirectory() + "\\dataobjects\\";
			
			DataObjectDB dataobjectDB = graphDBService.getFramedGraph().getVertex(id, DataObjectDB.class);
				
			// Make sure the data object directory exists
			File dataDir = new File(DATAOBJECT_DIR);
			if (!dataDir.exists()) dataDir.mkdir();	
			dataDir = new File(DATAOBJECT_DIR + "\\" + dataobjectDB.getDataDir() + "\\"); 
			if (!dataDir.exists()) dataDir.mkdir();	
			
			// Get file name
			String fileName = (new File(file.getOriginalFilename())).getName();
			if(fileName == "")
				fileName = file.getName();
			
			File f = new File(DATAOBJECT_DIR + "\\" + dataobjectDB.getDataDir() + "\\" + fileName);

			// If this file exists, throw an exception
			if(f.exists())
				throw new MatINFileExistsException();
			
			// Transfer the file
			file.transferTo(f);
			
			graphDBService.getFramedGraph().commit();
			
			// Set the response
	        response.setStatus(HttpServletResponse.SC_FOUND);
	        
	        // Get the data object to return to the client.
	        DataObject dataobject = graphDBService.get(id, DataObject.class, DataObjectDB.class);
			
	        return dataobject;
	        
		} 
		catch(IllegalStateException iEx)
		{
			logger.error("Transfer failed", iEx);
		
			// Set the response
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			
			throw new MatINException("File transfer failed!", MatINErrorCode.DATAOBJECT_FILE_TRANSFER_FAIL.ordinal());
		
		}
		catch(MatINFileExistsException mEx)
		{
			logger.error("File already exists in data object.", mEx);
			
			// Set the response
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			
			throw new MatINException("File already exists in dataobject!", MatINErrorCode.DATAOBJECT_FILE_EXISTS.ordinal());
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
	 * Search for dataobjects in the database. The query string can take the following form
	 * 
	 * @param queryString
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/dataobjects/search/{queryString}", method=RequestMethod.GET)
	public @ResponseBody List<String> search(
			@PathVariable String queryString,
			HttpServletRequest request, HttpServletResponse response){
		
		List<String> urls = new ArrayList<String>();
		
		try {
			
			// Search for the dataobjects using the query string		
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
