package org.matin.client;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

/**
 * A class to represent a physical material (Ti6Al4V, etc.)
 * 
 * @author Dave Turner
 *
 */
public class Material extends MatINWriteableObject {

	public Material() 
	{
		name = "";
		description = "";
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name set the unique name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public void addComponentElement(Element element, Double percentage)
	{
		componentElementsUpperBound.put(element, percentage);
		componentElementsLowerBound.put(element, percentage);
	}
	
	public void addComponentElement(Element element, Double percentageLowerBound, Double percentageUpperBound)
	{
		componentElementsUpperBound.put(element, percentageLowerBound);
		componentElementsLowerBound.put(element, percentageUpperBound);
	}
	
	public List<String> getKeywords() { return keywords; }
	public List<String> getReferenceURLs() { return referenceURLs; }
	
	protected String name;
	protected String description;

	protected List<String> referenceURLs = new ArrayList<String>();

	protected EnumMap<Element, Double> componentElementsUpperBound = new EnumMap<Element, Double>(Element.class);
	protected EnumMap<Element, Double> componentElementsLowerBound = new EnumMap<Element, Double>(Element.class);
	
	protected List<String> keywords = new ArrayList<String>();
	
}
