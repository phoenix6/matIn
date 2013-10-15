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
	 * Search for a material in the database by case-sensitive name. 
	 * 
	 * @param name The name of the material to search.
	 * @return The material found in the database.
	 * @throws MatINMaterialNotFoundException
	 */
	public Material FindMaterial(String name)
		throws MatINMaterialNotFoundException
	{
		return FindMaterial(name, true);
	}

	/**
	 * Search for a material in the database by name. 
	 * 
	 * @param name The name of the material to search.
	 * @param isCaseSensitive Whether the search is case sensitive.
	 * @throws MatINMaterialNotFoundException
	 * @return The material found in the database.
	 */
	public Material FindMaterial(String name, boolean isCaseSensitive)
		throws MatINMaterialNotFoundException
	{
		// TODO: Query the database for a material with the name
		Material m = new Material();

		return m;
	}

	/**
	 * Search for any material in the database whose name partially
	 * matches the case-sensitive name
	 * 
	 * @param name The name of the material to search.
	 * @return A list of matching materials.
	 */
	public List<Material> FindMaterials(String name)
			throws MatINMaterialNotFoundException
	{
		return FindMaterials(name, true);
	}


	/**
	 * Search for any material in the database whose name partially
	 * matches the name
	 * 
	 * @param name The name of the material to search.
	 * @param isCaseSensitive Whether the search is case sensitive.
	 * @return A list of matching materials.
	 */
	public List<Material> FindMaterials(String name, boolean isCaseSensitive)
			throws MatINMaterialNotFoundException
	{
		// TODO: Query the database for any materials that partially matches 
		// the name passed in as a parameter.
		return new ArrayList<Material>();
	}

}
