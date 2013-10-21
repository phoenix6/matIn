/**
 * This base class defines common features of all controllers for the MatIN database.
 */
package org.matin.server.webservice.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.matin.server.database.domain.MaterialDB;
import org.matin.client.MatINException;
import org.matin.client.MatINResult;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tinkerpop.blueprints.Vertex;

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
	public @ResponseBody MatINResult handleMatINException(MatINException ex, 
			HttpServletRequest request, HttpServletResponse response) {
		return new MatINResult(ex.getMessage(), ex.getCode());
	}
	
	/**
	 * Get the url of the controller
	 * 
	 * @return
	 */
	protected static String makeUrl()
	{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	    return String.format("%s://%s:%d/matIN/rest",request.getScheme(),  request.getServerName(), request.getServerPort());
	}
	
}
