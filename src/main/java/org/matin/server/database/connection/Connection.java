package org.matin.server.database.connection;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.frames.FramedGraph;
import com.tinkerpop.frames.FramedGraphFactory;

public class Connection {
	
	private final String DB_PATH = "remote:localhost/mathub";
	private final String username = "admin";
	private final String password = "admin";
	private Graph graph;
    public FramedGraph<Graph> manager;
	
	public Connection(){
		
		graph = new OrientGraph(DB_PATH, username, password);
		FramedGraphFactory f = new FramedGraphFactory();
		manager = f.create(graph);
		
	}
	
	
	public void close(){
		graph.shutdown();
		manager.shutdown();
	}

}
