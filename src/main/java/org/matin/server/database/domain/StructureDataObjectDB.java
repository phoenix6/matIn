/**
 * 
 */
package org.matin.server.database.domain;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Adjacency;

/**
 * @author Dave Turner
 *
 */
public interface StructureDataObjectDB extends DataObjectDB {

	/*
	 * Datasets may have canonical features within them.
	 */
	@Adjacency(label="contains", direction = Direction.OUT)
	public CanonicalFeatureDB getCanonicalFeature();
	@Adjacency(label="contains", direction = Direction.OUT)
	public void setCanonicalFeature(CanonicalFeatureDB cf);
	
	/*
	 * Describes the space the dataset spans.
	 */
	@Adjacency(label="spans", direction = Direction.OUT)
	public void setSpace(SpaceDB s);
	
}
