package org.matin.server.database.domain;

import java.util.Vector;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

public interface CanonicalFeature extends VertexFrame{

	
	@Property("featureID")
	public void setFeatureID(int id);
	@Property("featureID")
	public int getFeatureID();
	
	@Property("name")
	public void setName(String name);
	@Property("name")
	public String getName();
	
	@Property("numVariables")
	public void setNumVariables(int num);
	@Property("numVariables")
	public int getNumVariables();
	
	@Property("variableNames")
	public void setVariableNames(Vector<String> v);
	@Property("variableNames")
	public Vector<String> getVariableNames();
	
	@Property("variableUnits")
	public void setVariableUnits(Vector<String> v);
	@Property("variableUnits")
	public Vector<String> getVariableUnits();
	
	@Property("variableTypes")
	public void setVariableTypes(Vector<String> v);
	@Property("variableTypes")
	public Vector<String> getVariableTypes();
	
	@Property("datasetName")
	public void setDataSetName(String name);
	@Property("datasetName")
	public String getDataSetName();
	
}
