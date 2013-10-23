package org.matin.server.database.domain;

import java.util.List;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;

public interface DataObjectDB extends MatINDBObject, AccessControlDB {
	
	@Property("name")
	public void setName(String name);
	@Property("name")
	public String getName();
	
	@Property("description")
	public void setDescription(String description);
	@Property("description")
	public String getDescription();

	@Property("keywords")
	public void setKeywords(List<String> keywords);
	@Property("keywords")
	public List<String> getKeywords();
	
	@Property("fileURLs")
	public void setFileURLs(List<String> urls);
	@Property("fileURLs")
	public List<String> getFileURLs();
	
	/*
	 * Does this data object come from experimental data or is it simulated.
	 */
	@Property("isSimulated")
	public void setIsSimulated(Boolean b);
	@Property("isSimulated")
	public Boolean getIsSimulated();
	
	/*
	 * Datasets can be made of materials, just like samples
	 */
	@Adjacency(label="madeOf", direction = Direction.OUT)
	public void setMadeOf(MaterialDB material);
	@Adjacency(label="madeOf", direction = Direction.OUT)
	public MaterialDB getMadeOf();
	
	/*
	 * Datasets can be related to a particular physical sample.
	 */
	@Adjacency(label="hasA", direction = Direction.IN)
	public void setSample();
	
	/*
	 * Describe the connection between this data object and data process runs.
	 */
	@Adjacency(label="argument", direction = Direction.OUT)
	public void setDataProcessRunIn(DataProcessRunDB dpr);
	@Adjacency(label="argument", direction = Direction.OUT)
	public Iterable<DataProcessRunDB> getDataProcessRuns();
		
	@Adjacency(label="produced", direction = Direction.IN)
	public Iterable<DataProcessRunDB> getDataProcessRun();

	/*
	 * Directory on the server where the data objects files reside
	 */
	@Property("dataDir")
	public void setDataDir(String name);
	@Property("dataDir")
	public String getDataDir();
	
}
