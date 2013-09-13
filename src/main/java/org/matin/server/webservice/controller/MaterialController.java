package org.matin.server.webservice.controller;

import org.matin.server.database.connection.Connection;
import org.matin.server.database.domain.MaterialDB;
import org.matin.client.Material;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 

@Controller
public class MaterialController {

	@RequestMapping(value = "/material", method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody
	Material getMaterialInJSON(@RequestBody Material material){
        material.setName("Cu");
        
        Connection conn = new Connection();
        MaterialDB materialDB = conn.manager.addVertex(null, MaterialDB.class);
        materialDB.setName(material.getName());
        materialDB.setDescription(material.getDescription());
        conn.close();
		return material;

	}
}
