package org.matin.client;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class for accessing the Materials Innovation Network (MatIN)
 * database.
 * 
 * This class allows users to login to the MatIN database, add samples, data objects,
 * query the data graph, etc. It is the main interface to the database. 
 * 
 * @author Dave Turner
 *
 */
public class MatIN {

	private final String databaseURL;
	private final int uid;
	private final String sessionKey;
	
	public MatIN(String databaseURL, int uid, String sessionKey)
	{
		this.databaseURL = databaseURL;
		this.uid = uid;
		this.sessionKey = sessionKey;
	}

	/**
	 * Get the URL of the database.
	 * 
	 * @return The URL for the MatIN database server.
	 */
	public String getDatabaseURL()
	{
		return databaseURL;
	}
	
	/**
	 * Get the user id number of the currently logged in user.
	 * 
	 * @return The user id number.
	 */
	public int getUserID()
	{
		return uid;
	}
	
	/**
	 * Get the session key for this database connection session
	 * 
	 * @return The session key string.
	 */
	public String getSessionKey()
	{
		return sessionKey;
	}
	
	/**
	 * Search for any material in the database whose name matches exactly.
	 * Case insensitive.
	 *  
	 * @param name The name of the material to search.
	 * @return The first matching material found.
	 */
	public Material GetMaterial(String name)
			throws MatINResourceNotFoundException
	{
		// TODO: Query the database for any materials that partially matches 
		// the name passed in as a parameter.
		return new Material();
	}
	
		
	/**
	 * Search for any material in the database whose name, keywords, or description partially
	 * match the queryString.
	 * 
	 * @param name The query string to search on.
	 * @return A list of matching materials.
	 */
	public List<Material> FindMaterials(String queryString)
			throws MatINResourceNotFoundException
	{
		// TODO: Query the database for any materials that partially matches 
		// the name passed in as a parameter.
		return new ArrayList<Material>();
	}
		
}
