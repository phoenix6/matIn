package org.matin.server.webservice.controller;

import javax.annotation.PreDestroy;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.frames.FrameInitializer;
import com.tinkerpop.frames.FramedGraph;
import com.tinkerpop.frames.FramedGraphConfiguration;
import com.tinkerpop.frames.FramedGraphFactory;
import com.tinkerpop.frames.modules.AbstractModule;

//import com.tinkerpop.blueprints.impls.orient.OrientGraph;

@Service
public class GraphDBConnectionService {
	
	private final static Logger logger = LoggerFactory.getLogger(GraphDBConnectionService.class);
	
	//private final String DB_PATH = "remote:localhost/mathub";
	private final static String DB_PATH = "C:\\mathub_db";
	private final static String username = "admin";
	private final static String password = "admin";
	
	private static Graph graph;
    private static FramedGraph<Graph> manager;

    static 
    {
		logger.debug("Creating database connection.");

    	// Initialize a static instances of variables for accessing graph database.  
    	// This sets up a frame initializer that is called whenever a vertex is 
		// created. 
		final FrameInitializer myInitializer = new FrameInitializer() {
			public void initElement(final Class<?> kind, final FramedGraph<?> framedGraph, final Element element) {
				element.setProperty("className", kind.getSimpleName()); // save the class name of the frame on the element
			}
		};
		
		//graph = new OrientGraph(DB_PATH, username, password);
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
		
		manager = f.create(graph);	
 
    }
    
	public GraphDBConnectionService()
	{
 		
	}
	
	public Graph getGraph()
	{
		return graph;
	}
	
	public FramedGraph<Graph> getGraphManager()
	{
		return manager;
	}
	
	public void commit()
	{
		((Neo4jGraph)graph).commit();
	}
	
	/**
	 * Shutdown the connection to the graph database
	 */
	public static synchronized void close(){
		logger.info("Closing database connection");
		
		if(graph != null) {
			graph.shutdown();
			manager.shutdown();
			graph = null;
			manager = null;
		}
	}

	@PreDestroy
	public void clearMovieCache() {
		close();
	}
	
}

