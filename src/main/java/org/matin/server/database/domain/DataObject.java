package org.matin.server.database.domain;

import java.util.Date;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

public interface DataObject extends VertexFrame{
	
	@Property("objectID")
	public void setObjectID(int id);
	@Property("objectID")
	public int getObjectID();
	
	@Property("name")
	public void setName(String name);
	@Property("name")
	public String getName();
	
	@Property("OwnerUID")
	public void setOwnerUID(int id);
	@Property("OwnerUID")
	public int getOwnerUID();
	
	@Property("dateCreated")
	public void setDateCreated(Date d);
	@Property("dateCreated")
	public Date getDateCreated();

	@Property("dateModified")
	public void setDateModified(Date d);
	@Property("dateModified")
	public Date getDateModified();
	
	@Property("permission")
	public void setPermission(String permission);
	@Property("permission")
	public String getPermission();
	
	@Property("dataFileURL")
	public void setProfileImgURL(String url);
	@Property("dataFileURL")
	public String getProfileImgURL();
	
	@Property("isSimulated")
	public void setIsSimulated(boolean b);
	@Property("isSimulated")
	public boolean getIsSimulated();
	
	@Adjacency(label="contains", direction = Direction.OUT)
	public void setCanonicalFeature(CanonicalFeature cf);
	
	@Adjacency(label="spans", direction = Direction.OUT)
	public void setSpace(Space s);
	
	@Adjacency(label="argumentInto", direction = Direction.OUT)
	public void setDataProcessRunIn(DataProcessRun dpr);
	@Adjacency(label="argumentInto", direction = Direction.OUT)
	public Iterable<DataProcessRun> getDataProcessRuns();
	
	@Adjacency(label="hasA", direction = Direction.IN)
	public SampleDB getSample();
	
	@Adjacency(label="argumentOut", direction = Direction.IN)
	public Iterable<DataProcessRun> getDataProcessRun();
	

}
