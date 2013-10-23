package org.matin.server.webservice.controller;

import java.util.List;

import com.tinkerpop.blueprints.TransactionalGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.frames.FramedTransactionalGraph;

public interface GraphDBConnectionService {
	
	public String getDBDirectory();
	
    public TransactionalGraph getGraph();
    
	public FramedTransactionalGraph<TransactionalGraph> getFramedGraph();
	
    public <ClassType, ClassTypeDB> ClassTypeDB create(ClassType source, Class<ClassTypeDB> clazz);
	
    public <ClassType, ClassTypeDB> ClassTypeDB modify(long id, ClassType source, Class<ClassTypeDB> clazz);

    public <ClassType, ClassTypeDB> ClassType get(long id, Class<ClassType> retClass, Class<ClassTypeDB> clazz) throws Exception;
    
	public void close();
	
    public void addToFulltextIndex(String indexName, String propertyName, String value, Vertex v);
		
    public List<Vertex> queryFulltextIndex(String indexName, String query);
    
    public void removeFromFulltextIndex(String indexName, Vertex v);
    
}

