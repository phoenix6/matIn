package org.matin.server.database.domain;

import java.util.Date;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

public interface DataProcessRun extends VertexFrame{

	@Property("dataProcessRunID")
	public void setDataProcessRunID(int id);
	@Property("dataProcessRunID")
	public int getDataProcessRunID();
	
	@Property("OwnerUID")
	public void setOwnerUID(int id);
	@Property("OwnerUID")
	public int getOwnerUID();
	
	@Property("date")
	public void setDate(Date date);
	@Property("date")
	public Date getDate();
	
	@Adjacency(label="instanceOf", direction = Direction.OUT)
	public void setInstance(DataProcess dp);
	@Adjacency(label="instanceOf", direction = Direction.OUT)
	public DataProcess getInstance();
	
	@Adjacency(label="argumentOut", direction = Direction.OUT)
	public void setDataObject(DataObject o);
	@Adjacency(label="argumentOut", direction = Direction.OUT)
	public Iterable<DataObject> getDataObjects();
}
