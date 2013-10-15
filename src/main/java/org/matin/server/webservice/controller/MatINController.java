/**
 * This base class defines common features of all controllers for the MatIN database.
 */
package org.matin.server.webservice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.matin.server.database.domain.MaterialDB;
import org.matin.client.MatINException;
import org.matin.client.MatINError;
import org.matin.client.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This base class defines common features of all controllers for the MatIN database.
 * 
 * @author Dave Turner
 */
@Controller
public abstract class MatINController {

	/**
	 * Every MatINController should have a connection to the database
	 * through this GraphDBConnectionService
	 */
	@Autowired
	protected GraphDBConnectionService graphDBService;
	
	@ExceptionHandler(MatINException.class)
	public @ResponseBody MatINError handleMatINException(MatINException ex, 
			HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(response.SC_BAD_REQUEST);
		return new MatINError(ex.getMessage());
	}
	
}
