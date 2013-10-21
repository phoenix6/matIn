package org.matin.client;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

/**
 * A class to represent a physical material (Ti6Al4V, etc.). This class extends MatINWriteableObject
 * so that it may be persisted to the matIn online database via a call to the commit method. Materials
 * may be linked to samples and datasets within matIn database to denote the material system of the
 * data.
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
	 * Get the name of this material.
	 * 
	 * @return The name of this material.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this material.
	 * 
	 * @param name The name of this material.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the text description of the material.
	 * 
	 * @return The description of this material.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the text description of this material.
	 * 
	 * @param The description of this material.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Add a component element of this material and its percentage composition.
	 * 
	 * @param element The periodic element that this material is composed of.
	 * @param percentage A number between 0 to 100 that specifies the percent composition.
	 */
	public void addComponentElement(Element element, Double percentage)
	{
		componentElements.add(element);
		componentElementUpperBounds.add(percentage);
		componentElementLowerBounds.add(percentage);
	}
	
	/**
	 * Add a component element of this material and its lower and upper bound percentage compositions.
	 *
	 * @param element The periodic element that this material is composed of.
	 * @param percentageLowerBound A number between 0 to 100 that specifies the percent composition lower bound.
	 * @param percentageUpperBound A number between 0 to 100 that specifies the percent composition upper bound.
	 */
	public void addComponentElement(Element element, Double percentageLowerBound, Double percentageUpperBound)
	{
		componentElements.add(element);
		componentElementUpperBounds.add(percentageUpperBound);
		componentElementLowerBounds.add(percentageLowerBound);
	}
	
	/**
	 * Get the list of component elements of this material.
	 * 
	 * @return The list of elements that this material is composed of.
	 */
	public List<String> getComponentElements()
	{
		List<String> keys = new ArrayList<String>();
		
		for (Element key : componentElements)
			keys.add(key.toString());
			
		return keys;
	}
	
	/**
	 * Get the percentage composition upper bounds for each component element.
	 * 
	 * @return The percentage composition upper bounds for each component element. 
	 */
	public List<Double> getComponentElementUpperBounds()
	{
		return componentElementUpperBounds;
	}
	
	/**
	 * Get The percentage composition lower bounds for each component element. 
	 * 
	 * @return the percentage composition lower bounds for each component element. 
	 */
	public List<Double> getComponentElementLowerBounds()
	{
		return componentElementLowerBounds;
	}
		
	/**
	 * Get the search keywords for this material.
	 * 
	 * @return The search keywords for this material.
	 */
	public List<String> getKeywords() { return keywords; }
	
	/**
	 * Set the list of search keywords for this material.
	 * 
	 * @param The search keywords for this material.
	 */
	public void setKeywords(List<String> words) { keywords = words; }
	
	/**
	 * Get the reference URLs for this material.
	 * 
	 * @return The reference URLs for this material.
	 */
	public List<String> getReferenceURLs() { return referenceURLs; }
	
	/**
	 * Set the list of reference URLs for this material.
	 * 
	 * @param The reference URLs for this material.
	 */
	public void setReferenceURLs(List<String> refs) { referenceURLs = refs; }
	
	/**
	 * Set the component elements list for this material. It is better to use
	 * the addComponentElement methods.
	 * 
	 * @param componentElements the componentElements to set
	 */
	public void setComponentElements(List<Element> componentElements) {
		this.componentElements = componentElements;
	}

	/**
	 * Set the component elements percentage composition upper bounds list 
	 * for this material. It is better to use the addComponentElement methods.
	 * 
	 * @param componentElementUpperBounds the componentElementUpperBounds to set
	 */
	public void setComponentElementUpperBounds(
			List<Double> componentElementUpperBounds) {
		this.componentElementUpperBounds = componentElementUpperBounds;
	}


	/**
	 * Set the component elements percentage composition lower bounds list 
	 * for this material. It is better to use the addComponentElement methods.
	 * 
	 * @param componentElementLowerBounds the componentElementLowerBounds to set
	 */
	public void setComponentElementLowerBounds(
			List<Double> componentElementLowerBounds) {
		this.componentElementLowerBounds = componentElementLowerBounds;
	}

	protected String name;
	protected String description;

	protected List<String> referenceURLs = new ArrayList<String>();

	protected List<Element> componentElements = new ArrayList<Element>();
	protected List<Double> 	componentElementUpperBounds = new ArrayList<Double>();
	protected List<Double> 	componentElementLowerBounds = new ArrayList<Double>();

	protected List<String> keywords = new ArrayList<String>();
	
}
