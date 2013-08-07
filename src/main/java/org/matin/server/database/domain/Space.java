package org.matin.server.database.domain;

import java.util.Vector;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.modules.typedgraph.TypeField;

@TypeField("type")
public interface Space {

	@Property("dimensions")
	public void setDimensions(Vector<Integer> v);
	@Property("dimensions")
	public Vector<Integer> getDimensions();
	
	@Property("origin")
	public void setOrigin(Vector<Double> v);
	@Property("origin")
	public Vector<Double> getOrigin();
	
	@Property("units")
	public void setUnits(String units);
	@Property("units")
	public String getUnits();
	
}
