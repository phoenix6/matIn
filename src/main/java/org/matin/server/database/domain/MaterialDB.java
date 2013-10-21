package org.matin.server.database.domain;

import java.util.List;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;
import com.tinkerpop.frames.annotations.gremlin.GremlinGroovy;

public interface MaterialDB extends MatINDBObject {
 
	@Property("name")
	public void setName(String name);
	@Property("name")
	public String getName();
	
	@Property("description")
	public void setDescription(String description);
	@Property("description")
	public String getDescription();
	
	@Property("referenceURLs")
	public void setReferenceURLs(List<String> urls);
	@Property("referenceURLs")
	public List<String> getReferenceURLs();
	
	@Property("keywords")
	public void setKeywords(List<String> keywords);
	@Property("keywords")
	public List<String> getKeywords();
	
	@Property("componentElements")
	public void setComponentElements(List<String> elements);
	@Property("componentElements")
	public List<String> getComponentElements();
	
	@Property("componentElementUpperBounds")
	public void setComponentElementUpperBounds(List<Double> bounds);
	@Property("componentElementUpperBounds")
	public List<Double> getComponentElementUpperBounds();
	
	@Property("componentElementLowerBounds")
	public void setComponentElementLowerBounds(List<Double> bounds);
	@Property("componentElementLowerBounds")
	public List<Double> getComponentElementLowerBounds();
	
	/*
	@Adjacency(label="isA", direction=Direction.OUT)
	public void setMaterialFamily(MaterialCategory mf);
	@Adjacency(label="isA", direction=Direction.OUT)
	public MaterialCategory getMaterialFamily();
	*/
	
}
