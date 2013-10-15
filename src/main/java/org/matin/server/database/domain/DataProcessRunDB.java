package org.matin.server.database.domain;

import java.util.Date;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

public interface DataProcessRunDB extends VertexFrame{

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
	public void setInstance(DataProcessDB dp);
	@Adjacency(label="instanceOf", direction = Direction.OUT)
	public DataProcessDB getInstance();
	
	@Adjacency(label="argumentOut", direction = Direction.OUT)
	public void setDataObject(DataObjectDB o);
	@Adjacency(label="argumentOut", direction = Direction.OUT)
	public Iterable<DataObjectDB> getDataObjects();
}
