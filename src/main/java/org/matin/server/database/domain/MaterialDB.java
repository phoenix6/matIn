package org.matin.server.database.domain;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;
import com.tinkerpop.frames.annotations.gremlin.GremlinGroovy;

public interface MaterialDB extends VertexFrame{
 
	@Property("name")
	public void setName(String name);
	@Property("name")
	public String getName();
	
	@Property("description")
	public void setDescription(String description);
	@Property("description")
	public String getDescription();
	
	@Property("referenceURLs")
	public void setReferenceURLs(Iterable<String> urls);
	@Property("referenceURLs")
	public Iterable<String> getRefenceURLs();
	
	@Property("ChemicalFormula")
	public void setChemicalFormula(String s);
	@Property("ChemicalFormula")
	public String getChemicalFormula();
	
	@Adjacency(label="isA", direction=Direction.OUT)
	public void setMaterialFamily(MaterialCategory mf);
	@Adjacency(label="isA", direction=Direction.OUT)
	public MaterialCategory getMaterialFamily();
	
	
}
