package org.matin.client;

public class MatINConnectionFactory {

	public static MatIN MatINConnect(
			String databaseURL, String username, String password)
	{

		// After successful authentication with the remote database we should 
		// a valid session key. Create a MatIN object using this session key
		// so the user can begin operations on the database.
		String sessionKey = "testingKey";
		int uid = 0;

		return new MatIN(databaseURL, uid, sessionKey);
	}

}
