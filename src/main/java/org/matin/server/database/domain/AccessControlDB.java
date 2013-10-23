/**
 * 
 */
package org.matin.server.database.domain;

import java.util.List;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

/**
 * This vertex frame can be used add access control properties to objects in
 * the database.
 * 
 * @author Dave Turner
 *
 */
public interface AccessControlDB extends VertexFrame {

	/*
	 * Groups, projects, and permissions
	 */
	@Property("groups")
	public void setGroups(List<String> groups);
	@Property("groups")
	public List<String> getGroups();
	
	@Property("groupWritePermissions")
	public void setGroupWritePermissions(List<Boolean> val);
	@Property("groupWritePermissions")
	public List<Boolean> getGroupWritePermissions();
	
	@Property("projects")
	public void setProjects(List<String> projects);
	@Property("projects")
	public List<String> getProjects();
	
	@Property("projectWritePermissions")
	public void setProjectWritePermissions(List<Boolean> val);
	@Property("projectWritePermissions")
	public List<Boolean> getProjectWritePermissions();
	
	@Property("worldWritePermissions")
	public void setWorldWritePermissions(Boolean val);
	@Property("worldWritePermissions")
	public Boolean getWorldWritePermissions();
	
}
