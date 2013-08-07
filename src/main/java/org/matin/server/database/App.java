package org.matin.server.database;

import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

import org.matin.server.database.domain.CanonicalFeature;
import org.matin.server.database.domain.DataObject;
import org.matin.server.database.domain.DataProcess;
import org.matin.server.database.domain.DataProcessRun;
import org.matin.server.database.domain.GriddedSpace;
import org.matin.server.database.domain.MaterialDB;
import org.matin.server.database.domain.MaterialCategory;
import org.matin.server.database.domain.SampleDB;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.frames.FramedGraph;
import com.tinkerpop.frames.FramedGraphFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final String DB_PATH = "remote:localhost/mathub";
//	private static final String DB_PATH = "local:databases/mathub";
	private final String username = "admin";
	private final String password = "admin";
	
	private FramedGraph<Graph> manager;
	private Graph graph;
	
	public void connectDatabase(){
		
		graph = new OrientGraph(DB_PATH, username, password);
		FramedGraphFactory f = new FramedGraphFactory();
		manager = f.create(graph);
	}
	
	public void createDatabase(){


		//create material
		MaterialDB material = manager.addVertex(null, MaterialDB.class);
		material.setName("Iron");
		material.setDescription("Decription of Iron from wiki");
		Vector<String> v = new Vector<String>();
		v.addElement("wiki.org");
		material.setReferenceURLs(v);
		material.setChemicalFormula("Fe");
		
		System.out.println(material.getDescription());
	
		graph.shutdown();
		manager.shutdown();
		
	}
	

	
	public static void main( String[] args )
    {
		App app = new App();
		app.connectDatabase();
		app.createDatabase();

    }
}
