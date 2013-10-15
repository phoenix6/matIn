package org.matin.server.database.domain;

import java.util.Date;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

public interface DataProcessDB extends VertexFrame{
	
	@Property("dataProcessID")
	public void setDataProcessID(int id);
	@Property("dataProcessID")
	public int getDataProcessID();
	
	@Property("name")
	public void setName(String name);
	@Property("name")
	public String getName();
	
	@Property("CreatorUID")
	public void setCreatorUID(int id);
	@Property("CreatorUID")
	public int getCreatorUID();
	
	@Property("date")
	public void setDate(Date date);
	@Property("date")
	public Date getDate();
	
	@Property("scriptFile")
	public void setScriptFile(String s);
	@Property("scriptFile")
	public String getScriptFile();
	
	@Property("language")
	public void setLanguage(String language);
	@Property("language")
	public String getLanguage();
	
	@Property("command")
	public void setCommand(String command);
	@Property("command")
	public String getCommand();

}
