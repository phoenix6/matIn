package org.matin.server.webservice.controller;

import org.matin.server.database.domain.MaterialDB;
import org.matin.client.MatINException;
import org.matin.client.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinkerpop.blueprints.Vertex;

//import com.orientechnologies.orient.core.index.OIndexException;


@Controller
public class MaterialController extends MatINController {

	private final static Logger logger = LoggerFactory.getLogger(MaterialController.class);
	
	@RequestMapping(value = "/Material", method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Material commit(@RequestBody Material material){
        
		try {
			
			MaterialDB materialDB = graphDBService.getGraphManager().addVertex(null, MaterialDB.class);
	        materialDB.setName(material.getName());
	        materialDB.setDescription(material.getDescription());
	        
	        graphDBService.commit();
	        
			return material;
		
		} 
		
		catch(Exception ex)
		{
			throw new MatINException(ex.getMessage());	
		}
		
	}
	
}
