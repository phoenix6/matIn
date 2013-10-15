package org.matin.server.database;

import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

import org.matin.server.database.domain.CanonicalFeatureDB;
import org.matin.server.database.domain.DataObjectDB;
import org.matin.server.database.domain.DataProcessDB;
import org.matin.server.database.domain.DataProcessRunDB;
import org.matin.server.database.domain.GriddedSpaceDB;
import org.matin.server.database.domain.MaterialDB;
import org.matin.server.database.domain.MaterialCategory;
import org.matin.server.database.domain.SampleDB;
import org.matin.server.webservice.controller.GraphDBConnectionService;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.frames.FramedGraph;
import com.tinkerpop.frames.FramedGraphFactory;

/*
import com.orientechnologies.orient.core.command.script.OCommandScript;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
*/

/**
 * Hello world!
 *
 */
public class App 
{
	
	private FramedGraph<Graph> manager;
	private Graph graph;
	private GraphDBConnectionService conn;
	
	public void connectDatabase(){
		conn = new GraphDBConnectionService();
		manager = conn.getGraphManager();
		graph = conn.getGraph();
	}
	
	public void createDatabase()
	{
		System.out.println("Creating Database ... ");
		
        MaterialDB materialDB = manager.addVertex(null, MaterialDB.class);
        materialDB.setName("Iron");
        materialDB.setDescription("Iron is a metal.");

        for( Vertex v : graph.getVertices("name", "Iron") ) {
        	  System.out.println("Found vertex: " + v );
        }
       	
		graph.shutdown();
		manager.shutdown();	
		
		System.out.println("Done.");
	}
	

	
	public static void main( String[] args )
    {
		App app = new App();
		app.connectDatabase();
		app.createDatabase();
    }
}
