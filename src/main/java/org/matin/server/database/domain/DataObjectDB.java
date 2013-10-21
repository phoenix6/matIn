package org.matin.server.database.domain;

import java.util.Date;
import java.util.List;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

public interface DataObjectDB extends VertexFrame{
	
	@Property("name")
	public void setName(String name);
	@Property("name")
	public String getName();
	
	@Property("description")
	public void setDescription(String description);
	@Property("description")
	public String getDescription();
	
	
	/*
	 * Groups, and permissions
	 */
	@Property("groups")
	public void setGroups(List<String> groups);
	@Property("groups")
	public List<String> getGroups();
	
	@Property("groupWritePermissions")
	public void getGroupWritePermissions();
	@Property("groupWritePermissions")
	public boolean setGroupWritePermissions(boolean val);
	
	
	@Property("dataFileURL")
	public void setProfileImgURL(String url);
	@Property("dataFileURL")
	public String getProfileImgURL();
	
	@Property("isSimulated")
	public void setIsSimulated(boolean b);
	@Property("isSimulated")
	public boolean getIsSimulated();
	
	@Adjacency(label="contains", direction = Direction.OUT)
	public void setCanonicalFeature(CanonicalFeatureDB cf);
	
	@Adjacency(label="spans", direction = Direction.OUT)
	public void setSpace(SpaceDB s);
	
	@Adjacency(label="argumentInto", direction = Direction.OUT)
	public void setDataProcessRunIn(DataProcessRunDB dpr);
	@Adjacency(label="argumentInto", direction = Direction.OUT)
	public Iterable<DataProcessRunDB> getDataProcessRuns();
	
	@Adjacency(label="hasA", direction = Direction.IN)
	public SampleDB getSample();
	
	@Adjacency(label="argumentOut", direction = Direction.IN)
	public Iterable<DataProcessRunDB> getDataProcessRun();
	

}
