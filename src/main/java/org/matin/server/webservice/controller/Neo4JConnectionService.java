package org.matin.server.webservice.controller;

import javax.annotation.PreDestroy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.matin.server.database.domain.MaterialDB;
import org.modelmapper.ModelMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.frames.FrameInitializer;
import com.tinkerpop.frames.FramedGraph;
import com.tinkerpop.frames.FramedGraphConfiguration;
import com.tinkerpop.frames.FramedGraphFactory;
import com.tinkerpop.frames.FramedTransactionalGraph;
import com.tinkerpop.frames.VertexFrame;
import com.tinkerpop.frames.modules.AbstractModule;

/**
 * This class implements the setup of and embedded Neo4j database located in a local 
 * file. 
 * 
 * @author Dave
 *
 */
@Service
public class Neo4JConnectionService implements GraphDBConnectionService {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
	private final static Logger logger = LoggerFactory.getLogger(GraphDBConnectionService.class);
	
	//private final String DB_PATH = "remote:localhost/mathub";
	private final static String DB_PATH = "C:\\mathub_db";
	
	protected static Neo4jGraph graph;
	
	protected static FramedTransactionalGraph<TransactionalGraph> manager;
	
	public Neo4JConnectionService() { }
	
	// Load the database, this should only execute once because it will lock 
	// the database. 
    static 
    {
		logger.debug("Creating database connection.");

    	// Initialize a static instances of variables for accessing graph database.  
    	// This sets up a frame initializer that is called whenever a vertex is 
		// created. 
		final FrameInitializer myInitializer = new FrameInitializer() {
			public void initElement(final Class<?> kind, final FramedGraph<?> framedGraph, final Element element) {
				element.setProperty("className", kind.getSimpleName().substring(0, kind.getSimpleName().length()-2)); // save the class name of the frame on the element
				element.setProperty("creationDate", dateFormat.format(new Date()));
				
			}
		};
		
		// Check if the database exists already, if it doesn't then lets create it.
		graph = new Neo4jGraph(DB_PATH);
		
		// Start the neo4j web admin console. This will allow us to see the graph from web browser.
		GraphDatabaseService tmp = ((Neo4jGraph)graph).getRawGraph();
		WrappingNeoServerBootstrapper bootstrapper = new WrappingNeoServerBootstrapper((GraphDatabaseAPI) tmp);
		bootstrapper.start();
		
		FramedGraphFactory f = new FramedGraphFactory(new AbstractModule() {
			  public void doConfigure(FramedGraphConfiguration config) {
				    config.addFrameInitializer(myInitializer);
				  }
				});
		
		manager = f.create((TransactionalGraph)graph);	
 
    }
    
    public TransactionalGraph getGraph()
    {
    	return (TransactionalGraph)graph;
    }
    
    public FramedTransactionalGraph<TransactionalGraph> getFramedGraph()
    {
    	return manager;
    }
    
    public void addToFulltextIndex(String indexName, String propertyName, String value, Vertex v)
    {
    	Node node = graph.getRawGraph().getNodeById((Long)v.getId());
		IndexManager index = graph.getRawGraph().index();
		Index<Node> fulltextIndex = index.forNodes(indexName,
		        MapUtil.stringMap( IndexManager.PROVIDER, "lucene", "type", "fulltext" ) );
		fulltextIndex.add(node, propertyName, value);
    }
    
    public void removeFromFulltextIndex(String indexName, Vertex v)
    {
        	Node node = graph.getRawGraph().getNodeById((Long)v.getId());
			IndexManager index = graph.getRawGraph().index();
			Index<Node> fulltextIndex = index.forNodes(indexName,
			        MapUtil.stringMap( IndexManager.PROVIDER, "lucene", "type", "fulltext" ) );
			
	    	fulltextIndex.remove(node);
    }
    
    public List<Vertex> queryFulltextIndex(String indexName, String query)
    {
    	IndexManager index = graph.getRawGraph().index();
    	Index<Node> fulltextIndex = index.forNodes(indexName,
		        MapUtil.stringMap( IndexManager.PROVIDER, "lucene", "type", "fulltext" ) );
		IndexHits<Node> hits = fulltextIndex.query(query);
	
		// For all the hits, turn them into blueprints vertices.
		List<Vertex> vertices = new ArrayList<Vertex>();
		for(Node hit : hits)
			vertices.add(graph.getVertex(hit.getId()));
		
		return vertices;
		
    }
    
    public <ClassType, ClassTypeDB> ClassTypeDB create(ClassType source, Class<ClassTypeDB> clazz)
    {
    	// Create the vertex in the database
		ClassTypeDB objDB = getFramedGraph().addVertex(null, clazz);
		Vertex v = ((VertexFrame)objDB).asVertex();
	
		// Use ModelMapper to automatically map the equivalent data from the request object to the
		// database side object. This persists the values to the database.
		ModelMapper mapper = new ModelMapper();
		mapper.map(source, objDB);

		// Set the modify date
		v.setProperty("modifyDate", dateFormat.format(new Date()));
		
		// Set the resource URL
		v.setProperty("url", source.getClass().getSimpleName().toLowerCase() + "s/" + v.getId());
		
		return objDB;
    }
	
    public <ClassType, ClassTypeDB> ClassTypeDB modify(long id, ClassType source, Class<ClassTypeDB> clazz)
	{
    	// Create the vertex in the database
		ClassTypeDB objDB = getFramedGraph().getVertex(id, clazz);
		Vertex v = ((VertexFrame)objDB).asVertex();
		
		// Use ModelMapper to automatically map the equivalent data from the request object to the
		// database side object. This persists the values to the database.
		ModelMapper mapper = new ModelMapper();
		mapper.map(source, objDB);
	
		// Set the modify date
		v.setProperty("modifyDate", dateFormat.format(new Date()));
				
		return objDB;
	}

    public <ClassType, ClassTypeDB> ClassType get(long id, Class<ClassType> retClass, Class<ClassTypeDB> clazz) throws Exception
   	{
       	// Create the vertex in the database
   		ClassTypeDB objDB = getFramedGraph().getVertex(id, clazz);
   	
   		ClassType ret = retClass.newInstance();
   		
   		// Use ModelMapper to automatically map the equivalent data from the request object to the
   		// database side object. This persists the values to the database.
   		ModelMapper mapper = new ModelMapper();
   		mapper.map(objDB, ret);
   	
   		return ret;
   	}
         
	/**
	 * Shutdown the connection to the graph database.
	 */
	public synchronized void close(){
		logger.info("Closing database connection");
		
		if(graph != null) {
			graph.shutdown();
			manager.shutdown();
			graph = null;
			manager = null;
		}
	}

	@PreDestroy
	public void shutdownDatabase() {
		close();
	}

}
