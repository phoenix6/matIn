/**
 * 
 */
package org.matin.server.database.domain;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

/**
 * @author Dave Turner
 *
 * This interface is common to all objects that can be persisted to the MatIN 
 * database. It contains common methods for all these objects.
 *
 */
public interface MatINDBObject extends VertexFrame {

	@Property("creationDate")
	public void setCreationDate(String date);
	@Property("creationDate")
	public String getCreationDate();

	@Property("lastModifiedDate")
	public void setLastModifiedDate(String date);
	@Property("lastModifiedDate")
	public String getLastModifiedDate();
	
	@Property("url")
	public void setURL(String url);
	@Property("url")
	public String getURL();
	
}
