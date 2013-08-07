package org.matin.server.database.domain;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

public interface MaterialCategory extends VertexFrame{
	
	@Property("name")
	public void setName(String name);
	@Property("name")
	public String getName();

}
