package org.matin.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataObjectTest {

	@Test
	public void testCommit() {
		
		MatIN connectionMatIN = MatINConnectionFactory.MatINConnect("http://localhost:8080", "dmt36", "password");
		
		DataObject dObject = new DataObject();
		
		// Set the name and description
		dObject.setName("M40Scan1");
		dObject.setDescription("This is an EBSD scan taken from a Iron sample that was 40% cold rolled");
		
		// Add some keywords for this dataobject
		dObject.getKeywords().add("EBSD");
		dObject.getKeywords().add("TSL");
		dObject.getKeywords().add("Iron");
		dObject.getKeywords().add("Fe");
		
		dObject.getFiles().add("C:\\testdata\\testfile1");
		
		// Commit the object to the database.
		dObject.commit(connectionMatIN);
		
	}

}
